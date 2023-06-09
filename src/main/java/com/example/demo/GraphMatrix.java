package com.example.demo;

import java.util.*;

public class   GraphMatrix<K> implements IGraph<K> {
    private Vertex<K>[] vertices;
    private int[][] matAd;
    private int numVertices;
    private int maxSize;

    private boolean isDirectGraph;
    private boolean isMultiple;
    private boolean hasLoops;

    public GraphMatrix(int maxSize, boolean isDirectGraph, boolean isMultiple, boolean hasLoops) {
        this.maxSize = maxSize;
        this.vertices = new Vertex[maxSize];
        this.matAd = new int[maxSize][maxSize];
        this.numVertices = 0;
        this.isDirectGraph = isDirectGraph;
        this.isMultiple = isMultiple;
        this.hasLoops = hasLoops;
    }

    public GraphMatrix() {

    }


    public Vertex<K>[] getVertices() {
        return vertices;
    }

    public int[][] getMatAd() {
        return matAd;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean isDirectGraph() {
        return isDirectGraph;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public boolean isHasLoops() {
        return hasLoops;
    }

    private int getIndex(Vertex<K> vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] == vertex) {
                return i;
            }
        }
        return -1; // Si no se encuentra el vértice, devuelve -1
    }


    public Vertex<K> searchVertexM(K dato) {
        if (dato == null) {
            throw new NullPointerException("nul vertex");
        }
        for (Vertex<K> vertex : vertices) {
            if (vertex == null) {
                return null;
            }
            K vertexDato = vertex.getDato();
            if (vertexDato != null && vertexDato.equals(dato)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public void addVertex(Vertex<K> vertex) {
        if (numVertices >= maxSize) {
            throw new IllegalStateException("Graph is full, cannot add more vertices");
        }

        if (vertex == null) {
            throw new NullPointerException("Null vertex");
        } else if (searchVertexM(vertex.getDato()) != null) {
            return;
        }

        vertices[numVertices] = vertex;
        numVertices++;
    }

    @Override
    public boolean removeVertex(K vertex) {
        Vertex<K> vertexToRemove = searchVertexM(vertex);
        if (vertexToRemove == null) {
            return false;
        }

        int index = getIndex(vertexToRemove);
        if (index == -1) {
            return false;
        }

        // Eliminar la fila y la columna correspondiente a ese vértice en la matriz de adyacencia
        for (int i = 0; i < numVertices; i++) {
            matAd[i][index] = 0;
            matAd[index][i] = 0;
        }

        // Desplazar los vértices hacia arriba para llenar el espacio vacío
        for (int i = index; i < numVertices - 1; i++) {
            vertices[i] = vertices[i + 1];
        }

        vertices[numVertices - 1] = null;
        numVertices--;

        // Actualizar el tamaño máximo
        maxSize--;

        // Actualizar la matriz de adyacencia con el nuevo tamaño máximo
        int[][] newMatAd = new int[maxSize][maxSize];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                newMatAd[i][j] = matAd[i][j];
            }
        }
        matAd = newMatAd;

        return true;
    }

    @Override
    public void addEdge(K source, K destination, int cost) {
        Vertex<K> originVertex = searchVertexM(source);
        Vertex<K> destinationVertex = searchVertexM(destination);

        if (originVertex == null || destinationVertex == null) {
            throw new IllegalArgumentException("Source or destination vertex not found in the graph");
        }

        int originIndex = getIndex(originVertex);
        int destinationIndex = getIndex(destinationVertex);

        if (originIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException("Invalid vertices");
        }

        // Grafo simple
        if (!isDirectGraph && !isMultiple && !hasLoops) {
            if (originVertex != destinationVertex) {
                // Verificar si ya existe una arista entre los vértices
                if (matAd[originIndex][destinationIndex] == 0 && matAd[destinationIndex][originIndex] == 0) {
                    matAd[originIndex][destinationIndex] = 1;
                    matAd[destinationIndex][originIndex] = 1;
                }
            }
        }
        // Grafo múltiple
        else if (!isDirectGraph && isMultiple && !hasLoops) {
            if (originVertex != destinationVertex) {
                matAd[originIndex][destinationIndex] = matAd[originIndex][destinationIndex] + 1;
                matAd[destinationIndex][originIndex] = matAd[destinationIndex][originIndex] + 1;
            }
        }
        // Grafo pseudogra
        else if (!isDirectGraph && isMultiple && hasLoops) {
            matAd[originIndex][destinationIndex] = matAd[originIndex][destinationIndex] + 1;
            matAd[destinationIndex][originIndex] = matAd[destinationIndex][originIndex] + 1;
        }
        // Grafo dirigido
        else if (isDirectGraph && !isMultiple && hasLoops) {
            if (matAd[originIndex][destinationIndex] == 0 && matAd[destinationIndex][originIndex] == 0) {
                matAd[originIndex][destinationIndex] = 1;
            }
        }
        // Multigrafo dirigido
        else if (isDirectGraph && isMultiple && hasLoops) {
            matAd[originIndex][destinationIndex] = matAd[originIndex][destinationIndex] + 1;
        }
    }

    @Override
    public boolean removeEdge(K source, K destination) {
        Vertex<K> originVertex = searchVertexM(source);
        Vertex<K> destinationVertex = searchVertexM(destination);

        if (originVertex == null || destinationVertex == null) {
            return false;
        }

        int originIndex = getIndex(originVertex);
        int destinationIndex = getIndex(destinationVertex);

        if (originIndex == -1 || destinationIndex == -1) {
            return false;
        }

        matAd[originIndex][destinationIndex] -= 1;

        if (!isDirectGraph) {
            matAd[destinationIndex][originIndex] -= 1;
        }

        return true;
    }

    public void printAdjacencyMatrix() {
        int numVertices = vertices.length;

        System.out.print("  ");
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null) {
                System.out.print(vertices[i].getDato() + " ");
            }
        }
        System.out.println();

        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null) {
                System.out.print(vertices[i].getDato() + " ");
                for (int j = 0; j < numVertices; j++) {
                    if (vertices[j] != null) {
                        System.out.print(matAd[i][j] + " ");
                    }
                }
                System.out.println();
            }
        }


    }


    public void setVertices(Vertex<K>[] vertices) {
        this.vertices = vertices;
    }

    public void setMatAd(int[][] matAd) {
        this.matAd = matAd;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Arriba, abajo, izquierda, derecha


    public void bfs(int[] start, int[] end, boolean[][] visited, int[][] prev) {
        int rows = matAd.length;
        int cols = matAd[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            if (Arrays.equals(current, end)) {

            }

            for (int[] direction : DIRECTIONS) {
                int newRow = current[0] + direction[0];
                int newCol = current[1] + direction[1];

                if (isValidMove(matAd, visited, newRow, newCol)) {
                    queue.offer(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                    prev[newRow][newCol] = current[0] * cols + current[1];
                }
            }
        }
    }

    public void dfs(int row, int col, int[] end, boolean[][] visited, int[][] prev) {
        visited[row][col] = true;

        if (Arrays.equals(new int[]{row, col}, end)) {
            return;
        }

        int rows = matAd.length;
        int cols = matAd[0].length;

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isValidMove(matAd, visited, newRow, newCol)) {
                prev[newRow][newCol] = row * cols + col;
                dfs(newRow, newCol, end, visited, prev);
            }
        }
    }

    private static boolean isValidMove(int[][] maze, boolean[][] visited, int row, int col) {
        int rows = maze.length;
        int cols = maze[0].length;

        return row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] == 1 && !visited[row][col];
    }


    public boolean[][] runWarshallAlgorithm() {
        int[][] adjacencyMatrix = getAdjacencyMatrix();
        int size = adjacencyMatrix.length;
        boolean[][] result = new boolean[size][size];

        // Copiar los elementos de adjacencyMatrix a result
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = adjacencyMatrix[i][j] != 0;
            }
        }

        // Algoritmo de Warshall
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result[i][j] = result[i][j] || (result[i][k] && result[k][j]);
                }
            }
        }

        return result;
    }

    private int[][] getAdjacencyMatrix() {
        int numVertices = vertices.length;
        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (vertices[i] != null && vertices[j] != null) {
                    adjacencyMatrix[i][j] = matAd[i][j];
                }
            }
        }

        return adjacencyMatrix;
    }





    // algoritmo de Kruskal

    public List<Edge<K>> kruskal() {
        List<Edge<K>> result = new ArrayList<>();
        List<Edge<K>> edges = getAllEdges();
        Collections.sort(edges, (e1, e2) -> Integer.compare(e1.getCost(), e2.getCost()));
        int[][] vertexSets = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                vertexSets[i][j] = i == j ? i : -1;
            }
        }


        for (Edge<K> currentEdge : edges) { // orden ascendente
            int origin = getIndex(currentEdge.getOrigin());
            int destination = getIndex(currentEdge.getDestination());
            if (findSet(vertexSets, origin) != findSet(vertexSets, destination)) {
                result.add(currentEdge);
                unionSets(vertexSets, origin, destination);
            }
        }

        return result;
    }

    // Obtener todos los arcos del grafo
    private List<Edge<K>> getAllEdges() {
        List<Edge<K>> edges = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matAd[i][j] != 0) {
                    Vertex<K> origin = vertices[i];
                    Vertex<K> destination = vertices[j];
                    int cost = matAd[i][j];
                    Edge<K> edge = new Edge<>(origin, destination, cost);
                    edges.add(edge);
                }
            }
        }

        return edges;
    }

    // Encontrar el conjunto al que pertenece un vértice
    private int findSet(int[][] vertexSets, int vertex) {
        int parent = vertex;
        while (vertexSets[parent][vertex] != parent) {
            parent = vertexSets[parent][vertex];
        }
        return parent;
    }

    // Unir dos conjuntos
    private void unionSets(int[][] vertexSets, int vertex1, int vertex2) {
        int parent1 = findSet(vertexSets, vertex1);
        int parent2 = findSet(vertexSets, vertex2);
        vertexSets[parent2][vertex2] = parent1;
    }

    // Disjtraj
    public List<Vertex<K>> dijkstra(Vertex<K> source) {
        int sourceIndex = getIndex(source);

        // Crear arreglos de distancias y nodos visitados
        int[] distances = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        // Recorrido del grafo
        for (int i = 0; i < numVertices - 1; i++) {
            // Encontrar el nodo con la distancia mínima aún no visitado
            int minDistanceIndex = findMinDistance(distances, visited);
            visited[minDistanceIndex] = true;

            // Actualizar las distancias de los nodos adyacentes
            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && matAd[minDistanceIndex][j] != 0) {
                    int newDistance = distances[minDistanceIndex] + matAd[minDistanceIndex][j];
                    if (newDistance < distances[j]) {
                        distances[j] = newDistance;
                    }
                }
            }
        }

        // Construir la lista de nodos alcanzables desde el nodo fuente
        List<Vertex<K>> reachableVertices = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (distances[i] != Integer.MAX_VALUE) {
                reachableVertices.add(vertices[i]);
            }
        }

        return reachableVertices;
    }
    // Encontrar el índice del nodo con la distancia mínima
    private int findMinDistance(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceIndex = -1;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minDistanceIndex = i;
            }
        }

        if (minDistanceIndex == -1) {
            throw new IllegalStateException("No se encontró un nodo con distancia mínima");
        }

        return minDistanceIndex;
    }

    public int prim(int start) {
        boolean[] visited = new boolean[numVertices];
        int[] key = new int[numVertices];
        int[] parent = new int[numVertices];

        // Inicializar los arrays
        for (int i = 0; i < numVertices; i++) {
            key[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        key[start] = 0;
        parent[start] = -1;

        for (int count = 0; count < numVertices - 1; count++) {
            int minKey = Integer.MAX_VALUE;
            int minIndex = -1;

            // Encontrar el vértice con la clave mínima que no ha sido visitado
            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && key[v] < minKey) {
                    minKey = key[v];
                    minIndex = v;
                }
            }

            visited[minIndex] = true; // Marcar el vértice como visitado

            // Actualizar las claves y los padres de los vértices adyacentes no visitados
            for (int v = 0; v < numVertices; v++) {
                if (matAd[minIndex][v] != 0 && !visited[v] && matAd[minIndex][v] < key[v]) {
                    parent[v] = minIndex;
                    key[v] = matAd[minIndex][v];
                }
            }
        }

        int mstWeight = 0;

        // Calcular el peso total del MST sumando las claves de los vértices
        for (int i = 0; i < numVertices; i++) {
            mstWeight += key[i];
        }

        return mstWeight;
    }

}