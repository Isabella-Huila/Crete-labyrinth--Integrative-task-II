import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GraphMatrixTest {
    private GraphMatrix<Integer> graph;

    public void stangeUno(){
        graph= new GraphMatrix<>(6, false, false, false);
        Vertex<Integer> vertex1= new Vertex<>(1);
        Vertex<Integer> vertex2= new Vertex<>(2);
        Vertex<Integer> vertex3= new Vertex<>(3);
        Vertex<Integer> vertex4= new Vertex<>(4);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex4);
        graph.addVertex(vertex3);
    }

    @Test
    public void addSuccessfullyVertextest(){
        stangeUno();
        Vertex<Integer> vertex= new Vertex<>(5);
        graph.addVertex(vertex);
        assertEquals(5, graph.searchVertexM(5).getDato());
    }
}
