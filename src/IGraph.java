

public interface IGraph<V> {

    public void addVertex(Vertex<V> vertex);
    public boolean removeVertex(V vertex);
    public void addEdge(V source, V destination, int cost);
    public boolean removeEdge(V source, V destination);

}
