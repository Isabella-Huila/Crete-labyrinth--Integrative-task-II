package com.example.demo;

public class Edge<K> {
    private Vertex<K> origin;
    private Vertex<K> destination;

    private int cost;

    public Edge(Vertex<K> verticeUno, Vertex<K> verticeDos, int cost) {
        this.origin = verticeUno;
        this.destination = verticeDos;
        this.cost= cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Vertex<K> getOrigin() {
        return origin;
    }

    public Vertex<K> getDestination() {
        return destination;
    }

    public void setOrigin(Vertex<K> verticeUno) {
        this.origin = verticeUno;
    }

    public void setDestination(Vertex<K> destination) {
        this.destination = destination;
    }

    public Vertex<K> getVecino(Vertex<K> vertex){
        if (vertex == origin) {
            return destination;
        } else if (vertex == destination) {
            return origin;
        } else {
            throw new IllegalArgumentException("Vertex is not connected by this edge");
        }
    }


}
