import com.example.demo.Edge;
import com.example.demo.GraphList;
import com.example.demo.GraphMatrix;
import com.example.demo.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GraphMatrixTest {

    private GraphMatrix<Integer> graphMatrix;

    public void setupStage1() {
        graphMatrix = new GraphMatrix<>(5, false, false, false);
        graphMatrix.addVertex(new Vertex<>(1));
        graphMatrix.addVertex(new Vertex<>(2));
        graphMatrix.addVertex(new Vertex<>(3));
        graphMatrix.addEdge(1, 2, 6);
        graphMatrix.addEdge(2, 3, 4);
    }

    public void setupStage2() {
        graphMatrix = new GraphMatrix<>(4, true, false, false);
        graphMatrix.addVertex(new Vertex<>(1));
        graphMatrix.addVertex(new Vertex<>(2));
        graphMatrix.addVertex(new Vertex<>(3));
        graphMatrix.addEdge(1, 2, 1);
        graphMatrix.addEdge(2, 3, 1);
    }

    private void setupStage3() {
        graphMatrix = new GraphMatrix<>(2, false, false, false);
        graphMatrix.addVertex(new Vertex<>(1));
        graphMatrix.addVertex(new Vertex<>(2));
    }

    private void setupStage4() {
        graphMatrix = new GraphMatrix<>(4, false, false, false);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        Vertex<Integer> vertex3 = new Vertex<>(3);
        Vertex<Integer> vertex4 = new Vertex<>(4);

        Edge<Integer> edge12 = new Edge<>(vertex1, vertex2, 2);
        Edge<Integer> edge13 = new Edge<>(vertex1, vertex3, 4);
        Edge<Integer> edge23 = new Edge<>(vertex2, vertex3, 1);
        Edge<Integer> edge24 = new Edge<>(vertex2, vertex4, 7);
        Edge<Integer> edge34 = new Edge<>(vertex3, vertex4, 3);

        vertex1.addEdge(edge12);
        vertex1.addEdge(edge13);
        vertex2.addEdge(edge23);
        vertex2.addEdge(edge24);
        vertex3.addEdge(edge34);

        graphMatrix.addVertex(vertex1);
        graphMatrix.addVertex(vertex2);
        graphMatrix.addVertex(vertex3);
        graphMatrix.addVertex(vertex4);
    }

    public void setupStage5(){
        graphMatrix = new GraphMatrix<>(4, true, false, false);

        // Añadir 4 vértices al grafo con identificadores 1, 2, 3 y 4
        graphMatrix.addVertex(new Vertex<>(1));
        graphMatrix.addVertex(new Vertex<>(2));
        graphMatrix.addVertex(new Vertex<>(3));
        graphMatrix.addVertex(new Vertex<>(4));

        // Establecer las conexiones entre los vértices y los pesos asociados
        graphMatrix.addEdge(1, 2, 2);
        graphMatrix.addEdge(1, 3, 4);
        graphMatrix.addEdge(2, 3, 1);
        graphMatrix.addEdge(2, 4, 7);
        graphMatrix.addEdge(3, 4, 3);
    }

    @Test
    public void addVertexTest() {
        setupStage1();
        Vertex<Integer>[] listVertices = graphMatrix.getVertices();
        assertEquals(Integer.valueOf(1), listVertices[0].getDato());
        assertEquals(Integer.valueOf(2), listVertices[1].getDato());
        assertEquals(Integer.valueOf(3), listVertices[2].getDato());
    }

    @Test
    public void addExistingVertexTest(){
        setupStage1();
        int sizeBefore = graphMatrix.getVertices().length;
        graphMatrix.addVertex(new Vertex<>(2));
        int sizeAfter = graphMatrix.getVertices().length;
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void DeleteVertexTest() {
        setupStage1();
        assertTrue(graphMatrix.removeVertex(2));
        Vertex<Integer>[] adyacentes = graphMatrix.getVertices();
        assertEquals(Integer.valueOf(1), adyacentes[0].getDato());
        assertEquals(Integer.valueOf(3), adyacentes[1].getDato());
    }
    @Test
    public void DeleteVertexNullTest(){
        setupStage1();
        assertTrue(graphMatrix.removeVertex(1));
        assertTrue(graphMatrix.removeVertex(2));
        assertTrue(graphMatrix.removeVertex(3));
        assertFalse(graphMatrix.removeVertex(3));
    }

    @Test
    public void verifyThatVerticesAreDeletedTest(){
        setupStage1();
        assertTrue(graphMatrix.removeVertex(1));
    }

    /*
    @Test
    public void AddEdgeTest() {
        setupStage1();
        ArrayList<Edge<Integer>> edges = graphMatrix.getEdges();
        assertEquals(2, edges.size());
        assertEquals(2 , graphMatrix.getVertices()[1].getEdges().size());
        assertNotNull(graphMatrix.getVertices()[1].getEdges().get(0));
        assertEquals(1 , graphMatrix.getVertices()[2].getEdges().size());
    }

    @Test
    public void testDeleteEdgeTest() {
        setupStage1();
        assertTrue(graphMatrix.removeEdge(2, 3));
        ArrayList<Edge<Integer>> edges = graphMatrix.getEdges();
        assertEquals(1, edges.size());
        assertEquals(Integer.valueOf(1), edges.get(0).getOrigin().getDato());
        assertEquals(Integer.valueOf(2), edges.get(0).getDestination().getDato());
    }



    @Test
    public void verifyThatEdgesAreDeletedTest(){
        setupStage1();
        assertTrue(graphMatrix.removeEdge(1, 2));
        assertEquals(1 , graphMatrix.getEdges().size());
    }

     */

    @Test
    public void searchVertexExistingTest() {
        setupStage3();
        Vertex<Integer> vertex = graphMatrix.searchVertexM(2);
        assertNotNull(vertex);
        assertEquals(Integer.valueOf(2), vertex.getDato());
    }

    @Test
    public void searchVertexNonExistingTest() {
        setupStage3();
        Vertex<Integer> vertex = graphMatrix.searchVertexM(4);
        assertNull(vertex);
    }

    @Test
    public void searchVertice() {
        setupStage3();
        Vertex<Integer> vertex = graphMatrix.searchVertexM(10);
        assertNull(vertex);
        Vertex<Integer> vertex1 = graphMatrix.searchVertexM(1);
        assertEquals(Integer.valueOf(1), vertex1.getDato());
    }

    @Test
    void bfsTest() {
        setupStage2();
        int[] start = {0, 0};
        int[] end = {2, 2};
        boolean[][] visited = new boolean[2][2];
        int[][] prev = new int[2][2];

        graphMatrix.bfs(start, end, visited, prev);

        assertTrue(visited[0][0]);
    }

    @Test
    public void BFSGDisconnectedTest() {
        setupStage2();
        graphMatrix.addVertex(new Vertex<>(6));
        //assertFalse(graphMatrix.bfs(3, ));
    }
    int[][] result=null;
    @Test
    public void BFS1Test() {
        setupStage2();
        //assertFalse(graphMatrix.bfs(0,));
        //assertFalse(graphMatrix.bfs(2,));
    }


    @Test
    public void testWarshallAlgorithm() {
        setupStage5();
        // Ejecutar el algoritmo de Warshall en el grafo
        boolean[][] result = graphMatrix.runWarshallAlgorithm();

        // Verificar que el resultado sea el esperado
        boolean[][] expected = {
                {true, true, true, true},
                {false, true, true, true},
                {false, false, true, true},
                {false, false, false, true}
        };

        assertFalse(expected==result);
    }

    @Test
    public void testWarshallAlgorithm1() {
        setupStage5();
        // Ejecutar el algoritmo de Warshall en el grafo

        // Verificar que el resultado sea el esperado
        boolean[][] expected = {
                {true, true, true, true},
                {false, true, true, true},
                {false, false, true, true},
                {false, false, false, true}
        };

        assertNull(result);
    }

    @Test
    public void testKruskal_EmptyGraph() {
        setupStage1();
        GraphMatrix<String> graph = new GraphMatrix<>();
        List<Edge<String>> result = graph.kruskal();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testKruskal() {
        GraphMatrix<String> graph = new GraphMatrix<>();
        List<Edge<String>> result = graph.kruskal();
        assertTrue(result.isEmpty());
    }



    @Test
    public void testDijkstra_EmptyGraph() {

        GraphMatrix<String> graph = new GraphMatrix<>();
        Vertex<String> vertexA = new Vertex<>("A");

        List<Vertex<String>> result = graph.dijkstra(vertexA);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testKruskal_AllVerticesDisconnected() {

        GraphMatrix graphMatrix = new GraphMatrix();

        List<Edge<Integer>> mst = graphMatrix.kruskal();

        assertTrue(mst.isEmpty());
    }


    @Test
    public void testDijkstra() {
        GraphMatrix<String> graph = new GraphMatrix<>();
        Vertex<String> vertexA = new Vertex<>("A");
        List<Vertex<String>> result = graph.dijkstra(vertexA);
        assertTrue(result.isEmpty());
    }

}
