package com.example.demo;

public class GraphMatrix<K> implements IGraph<K> {
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


    Vertex<K> searchVertexM(K dato) {
        if (dato == null) {
            throw new NullPointerException("nul vertex");
        }
        for (Vertex<K> vertex : vertices) {
            if (vertex == null){
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
            if (matAd[originIndex][destinationIndex] == 0 && matAd[destinationIndex][originIndex] == 0){
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
}
