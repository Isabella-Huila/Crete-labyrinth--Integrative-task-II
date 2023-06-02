package com.example.demo;

import java.util.*;

public class GraphList<K> implements IGraph<K> {
    private ArrayList<Vertex<K>> vertices;
    private ArrayList<Edge<K>> edges;
    private int time;

    private boolean isDirectGraph;
    private boolean isMultiple;
    private boolean hasLoops;

    public ArrayList<Vertex<K>> getVertices() {
        return vertices;
    }

    public ArrayList<Edge<K>> getEdges() {
        return edges;
    }

    public GraphList(boolean isDirectGraph, boolean isMultiple, boolean hasLoops) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.isDirectGraph = isDirectGraph;
        this.isMultiple = isMultiple;
        this.hasLoops = hasLoops;
    }


    public Vertex<K> searchVertex(K dato) {
        if (dato == null) {
            throw new NullPointerException("nul vertex");
        }
        for (Vertex<K> vertex : vertices) {
            if (vertex.getDato().equals(dato)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public void addVertex(Vertex<K> vertex) {
        if (vertex == null) {
            throw new NullPointerException("nul vertex");
        } else if (searchVertex(vertex.getDato()) != null) {
            return;
        }
        vertices.add(vertex);
    }

    @Override
    public boolean removeVertex(K vertex) {
        Vertex<K> vertex1 = searchVertex(vertex);
        boolean delete = false;
        if (vertex1 != null) {
            vertices.remove(vertex1);
            vertex1.removeEdge();
            delete = true;
        }
        // Buscar y eliminar todas las aristas que involucren el vértice eliminado
        for (Iterator<Edge<K>> iterator = edges.iterator(); iterator.hasNext(); ) {
            Edge<K> edge = iterator.next();
            if (edge.getOrigin().equals(vertex1) || edge.getDestination().equals(vertex1)) {
                iterator.remove();
            }
        }
        return delete;
    }

    @Override
    public void addEdge(K origin, K destination, int cost) {
        Vertex<K> originVertex = searchVertex(origin);
        Vertex<K> destinationVertex = searchVertex(destination);

        if (originVertex == null || destinationVertex == null) {
            throw new IllegalArgumentException("Source or destination vertex not found in the graph");
        }

        // Grafo simple
        if (!isDirectGraph && !isMultiple && !hasLoops) {
            if (originVertex != destinationVertex) {
                Edge<K> edge = new Edge<>(originVertex, destinationVertex, cost);
                if (!edges.contains(edge)) {
                    originVertex.addEdge(edge);
                    destinationVertex.addEdge(edge);
                    edges.add(edge);
                }
            }
            // Grafo multiple
        } else if (!isDirectGraph && isMultiple && !hasLoops) {
            if (originVertex != destinationVertex) {
                Edge<K> edge = new Edge<>(originVertex, destinationVertex, cost);
                originVertex.addEdge(edge);
                destinationVertex.addEdge(edge);
                edges.add(edge);
            }
            // Grafo pseudo grafo
        } else if (!isDirectGraph && isMultiple && hasLoops) {
            Edge<K> edge = new Edge<>(originVertex, destinationVertex, cost);
            if (originVertex.equals(destinationVertex)) {
                originVertex.addEdge(edge);
                edges.add(edge);
            } else {
                originVertex.addEdge(edge);
                destinationVertex.addEdge(edge);
                edges.add(edge);
            }
            // Grafo dirigido
        } else if (isDirectGraph && !isMultiple && hasLoops) {
            Edge<K> edge = new Edge<>(originVertex, destinationVertex, cost);
            if (!edges.contains(edge)) {
                originVertex.addEdge(edge);
                edges.add(edge);
            }
        } else if (isDirectGraph && isMultiple && hasLoops) {
            Edge<K> edge = new Edge<>(originVertex, destinationVertex, cost);
            originVertex.addEdge(edge);
            edges.add(edge);
        }
    }

    @Override
    public boolean removeEdge(K origin, K destination) {
        Vertex<K> vertexOrigin = searchVertex(origin);
        Vertex<K> vertexDestination = searchVertex(destination);
        boolean delete = false;
        if (vertexOrigin != null && vertexDestination != null) {
            Edge<K> edgeToRemove = null;
            for (Edge<K> edge : edges) {
                // Busca la arista que conecta los dos vértices en la lista de aristas del grafo
                if (edge.getOrigin() == vertexOrigin && edge.getDestination() == vertexDestination) {
                    edgeToRemove = edge;
                    break;
                }
            }
            // Si encuentra la arista, la elimina de la lista de aristas del grafo y de los vértices de origen y destino
            if (edgeToRemove != null) {
                edges.remove(edgeToRemove);
                vertexOrigin.getEdges().remove(edgeToRemove);
                vertexDestination.getEdges().remove(edgeToRemove);
                delete = true;
            }
        }
        return delete;
    }

    public void BFS(int startVIndex) {
        if (startVIndex < 0 || startVIndex >= vertices.size()) {
            throw new IllegalArgumentException("Invalid start vertex index");
        }

        Vertex<K> startVertex = vertices.get(startVIndex);

        // Marcar todos los vértices como no visitados (blanco)
        for (Vertex<K> vertex : vertices) {
            vertex.setColor(Color.WHITE);
            vertex.setDistance(0);
            vertex.setPre(null);
        }

        // Usar una cola para el recorrido BFS
        Queue<Vertex<K>> queue = new LinkedList<>();

        // Marcar el vértice inicial como visitado (gris) y encolarlo
        startVertex.setColor(Color.GRAY);
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            Vertex<K> currentVertex = queue.poll();

            // Recorrer los vértices adyacentes del vértice actual
            List<Edge<K>> edges = currentVertex.getEdges();
            for (Edge<K> edge : edges) {
                Vertex<K> neighbor = edge.getDestination();
                if (neighbor.getColor() == Color.WHITE) {
                    // Marcar el vértice adyacente como visitado (gris) y encolarlo
                    neighbor.setColor(Color.GRAY);
                    neighbor.setDistance(currentVertex.getDistance() + 1);
                    neighbor.setPre(currentVertex);
                    queue.offer(neighbor);
                }
            }
            // Marcar el vértice actual como visitado completamente (negro)
            currentVertex.setColor(Color.BLACK);
        }
    }

    public void DFS() {

        // Marcar todos los vértices como no visitados (blanco)
        for (Vertex<K> vertex : vertices) {
            vertex.setColor(Color.WHITE);
            vertex.setPre(null);
        }
        time = 0;
        for (Vertex<K> vertex : vertices) {
            if (vertex.getColor() == Color.WHITE) {
                DFSVisit(vertex);
            }
        }
    }

    private void DFSVisit(Vertex<K> vertex) {
        time++;
        vertex.setStartTime(time);
        vertex.setColor(Color.GRAY);
        for (Edge<K> edge : vertex.getEdges()) {
            Vertex<K> neighbor = edge.getDestination();
            if (neighbor.getColor() == Color.WHITE) {
                neighbor.setPre(vertex);
                DFSVisit(neighbor);
            }
        }
        // Marcar el vértice actual como visitado completamente (negro)
        vertex.setColor(Color.BLACK);
        time++;
        vertex.setFinishTime(time);
    }

    public int dijkstra(Vertex<K> origin, Vertex<K> destination) {
        // Inicializar la distancia de todos los vértices como infinito, excepto el origen
        Map<Vertex<K>, Integer> distanceMap = new HashMap<>();
        for (Vertex<K> vertex : getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        distanceMap.put(origin, 0);

        // Crear una cola de prioridad para almacenar los vértices y sus distancias
        PriorityQueue<Vertex<K>> queue = new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));
        queue.add(origin);

        while (!queue.isEmpty()) {
            Vertex<K> currentVertex = queue.poll();

            // Si se alcanza el destino, se puede interrumpir el algoritmo y retornar el costo del camino
            if (currentVertex == destination) {
                return distanceMap.get(currentVertex);
            }

            // Obtener las aristas adyacentes al vértice actual
            List<Edge<K>> edges = currentVertex.getEdges();

            for (Edge<K> edge : edges) {
                Vertex<K> neighbor = edge.getNeighbor(currentVertex);
                int cost = edge.getCost();

                // Calcular la distancia tentativa desde el origen al vecino a través del vértice actual
                int tentativeDistance = distanceMap.get(currentVertex) + cost;

                if (tentativeDistance < distanceMap.get(neighbor)) {
                    // Actualizar la distancia del vecino si la distancia tentativa es menor
                    distanceMap.put(neighbor, tentativeDistance);
                    neighbor.setPre(currentVertex);

                    // Agregar el vecino a la cola de prioridad para procesarlo posteriormente
                    queue.add(neighbor);
                }
            }
        }

        // Si no se pudo encontrar un camino al destino, se retorna un valor sentinela
        return -1;
    }


        public int prim(int n, List<List<K>> edges, int start) {
            Map<Integer, Vertex<Integer>> map = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                map.put(i, new Vertex<>(i));
            }

            for (List<K> edge : edges) {
                int source = (int) edge.get(0);
                int destination = (int) edge.get(1);
                int weight = (int) edge.get(2);

                Vertex<Integer> sourceVertex = map.get(source);
                Vertex<Integer> destinationVertex = map.get(destination);

                Edge<Integer> edge1 = new Edge<>(sourceVertex, destinationVertex, weight);
                Edge<Integer> edge2 = new Edge<>(destinationVertex, sourceVertex, weight);

                sourceVertex.addEdge(edge1);
                destinationVertex.addEdge(edge2);
            }

            Set<Integer> visited = new HashSet<>();
            PriorityQueue<Edge<Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.getCost()));
            visited.add(start);

            Vertex<Integer> startVertex = map.get(start);
            for (Edge<Integer> edge : startVertex.getEdges()) {
                Vertex<Integer> neighbor = edge.getNeighbor(startVertex);
                int weight = edge.getCost();
                pq.add(new Edge<>(startVertex, neighbor, weight));
            }

            int mstWeight = 0;

            while (!visited.containsAll(map.keySet())) {
                Edge<Integer> edge = pq.poll();

                if (!visited.contains(edge.getDestination().getDato())) {
                    mstWeight += edge.getCost();
                    visited.add(edge.getDestination().getDato());

                    Vertex<Integer> destinationVertex = map.get(edge.getDestination().getDato());
                    for (Edge<Integer> neighborEdge : destinationVertex.getEdges()) {
                        Vertex<Integer> neighbor = neighborEdge.getNeighbor(destinationVertex);
                        int weight = neighborEdge.getCost();
                        pq.add(new Edge<>(destinationVertex, neighbor, weight));
                    }
                }
            }

            return mstWeight;
        }


    // Implementación del algoritmo de Kruskal

    /**
     *El algoritmo de Kruskal encuentra el árbol de expansión mínima de un grafo uniendo los conjuntos
     * de vértices mediante aristas de menor costo, evitando la formación de ciclos.
     * @return Una lista de aristas que conforman el árbol de expansión mínima del grafo.
     */

    public ArrayList<Edge<K>> kruskal() {
        ArrayList<Edge<K>> minimumSpanningTree = new ArrayList<>();
        Collections.sort(edges, Comparator.comparingInt(Edge::getCost)); // ordena arristas de forma creciente
        Set<Vertex<K>> visitedVertices = new HashSet<>(); // existencia de ciclo?
        for (Edge<K> edge : edges) {
            Vertex<K> origin = edge.getOrigin();
            Vertex<K> destination = edge.getDestination();

            if (!hasCycle(minimumSpanningTree, origin, destination)) {
                minimumSpanningTree.add(edge);

                // Marca los vértices como visitados
                visitedVertices.add(origin);
                visitedVertices.add(destination);
            }
        }
        return minimumSpanningTree;
    }


    /**
     * Verifica si agregar una arista al árbol de expansión mínima crea un ciclo.
     * Se realiza una búsqueda en profundidad desde el vértice de origen y desde el vértice de destino
     * para detectar si hay un camino entre ellos en el árbol de expansión mínima actual.
     *
     * @param minimumSpanningTree El árbol de expansión mínima actual.
     * @param origin El vértice de origen de la arista.
     * @param destination El vértice de destino de la arista.
     * @return true si agregar la arista crea un ciclo, false de lo contrario.
     */


    private boolean hasCycle(ArrayList<Edge<K>> minimumSpanningTree, Vertex<K> origin, Vertex<K> destination) {
        Set<Vertex<K>> visited = new HashSet<>();
        if (hasCycleDFS(origin, destination, visited, null)) {
            return true;
        }
        visited.clear();
        if (hasCycleDFS(destination, origin, visited, null)) {
            return true;
        }

        return false;
    }

/**
 * Realiza una búsqueda en profundidad para verificar la existencia de un camino entre el vértice actual y el vértice de destino.
 * Se ignora el vértice padre en el recorrido para evitar ciclos.
 * @param current El vértice actual.
 * @param destination El vértice de destino.
 * @param visited El conjunto de vértices visitados.
 * @param parent El vértice padre en el recorrido.
 * @return true si se encuentra un camino entre el vértice actual y el vértrtice de destino, false de lo contrario.
 *
 * **/
    private boolean hasCycleDFS(Vertex<K> current, Vertex<K> destination, Set<Vertex<K>> visited, Vertex<K> parent) {
        visited.add(current);
        for (Edge<K> edge : current.getEdges()) {  // recorre vertices Adyacentes
            Vertex<K> neighbor = edge.getDestination();

            if (neighbor == parent) {
                continue;  // aquí no tiene en cuenta al padre
            }
            if (neighbor == destination) {
                return true;
            }
            if (visited.contains(neighbor)) { // verifica vertiice adyacente visitado
                continue;
            }

            if (hasCycleDFS(neighbor, destination, visited, current)) {
                return true;
            }
        }

        return false;
    }





}
