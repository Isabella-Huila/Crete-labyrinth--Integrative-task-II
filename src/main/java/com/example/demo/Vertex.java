package com.example.demo;

import java.util.ArrayList;

public class Vertex<K> {
    private ArrayList<Edge<K>> edges;
    private int Distance;
    private Color color;
    private Vertex<K> pre;
    private K dato;

    private int startTime;

    private int finishTime;

    public Vertex(K dato) {
        this.edges = new ArrayList<>();
        this.dato = dato;
        pre = null;
        color= null;
        startTime= 0;
        finishTime=0;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public ArrayList<Edge<K>> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge<K>> edges) {
        this.edges = edges;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        this.Distance = distance;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vertex<K> getPre() {
        return pre;
    }

    public void setPre(Vertex<K> pre) {
        this.pre = pre;
    }

    public K getDato() {
        return dato;
    }

    public void setDato(K dato) {
        this.dato = dato;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int time) {
        this.startTime = time;
    }

    public void addEdge(Edge<K> edge){
        edges.add(edge);
    }

    public void removeEdge() {
        for (Edge<K> edge : edges) {
            Vertex<K> vertex = edgeStatus(this, edge);
            if (vertex != null) {
                vertex.getEdges().remove(edge);
            }
        }
    }

    private Vertex<K> edgeStatus(Vertex<K> currentVertex, Edge<K> edge) {
        if (currentVertex != edge.getOrigin()) {
            return edge.getOrigin();
        } else if (currentVertex != edge.getDestination()) {
            return edge.getDestination();
        } else {
            return null;
        }
    }


}
