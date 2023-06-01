package com.example.demo;

import java.util.*;

public class Graph<K> implements IGraph<K> {
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

    public Graph(boolean isDirectGraph, boolean isMultiple, boolean hasLoops) {
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
