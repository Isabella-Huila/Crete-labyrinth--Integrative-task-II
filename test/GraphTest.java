
import com.example.demo.Edge;
import com.example.demo.Graph;
import com.example.demo.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GraphTest {

    private Graph<Integer> graphList;

    public void setupStage1() {
        graphList = new Graph<>(false, false, false);
        graphList.addVertex(new Vertex<>(1));
        graphList.addVertex(new Vertex<>(2));
        graphList.addVertex(new Vertex<>(3));
        graphList.addEdge(1, 2, 0);
        graphList.addEdge(2, 3, 0);
    }

    public void setupStage2() {
        graphList = new Graph<>(true, false, false);
        graphList.addVertex(new Vertex<>(1));
        graphList.addVertex(new Vertex<>(2));
        graphList.addVertex(new Vertex<>(3));
        graphList.addEdge(1, 2, 0);
        graphList.addEdge(2, 3, 0);
    }

    private void setupStage3() {
        graphList = new Graph<>(false, false, false);
        graphList.addVertex(new Vertex<>(1));
        graphList.addVertex(new Vertex<>(2));
    }

    private void setupStage4() {
        graphList = new Graph<>(false, false, false);
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

        graphList.addVertex(vertex1);
        graphList.addVertex(vertex2);
        graphList.addVertex(vertex3);
        graphList.addVertex(vertex4);
    }

    @Test
    public void addVertexTest() {
        setupStage1();
        ArrayList<Vertex<Integer>> listVertices = graphList.getVertices();
        assertEquals(3, listVertices.size());
        assertEquals(Integer.valueOf(1), listVertices.get(0).getDato());
        assertEquals(Integer.valueOf(2), listVertices.get(1).getDato());
        assertEquals(Integer.valueOf(3), listVertices.get(2).getDato());
    }

    @Test
    public void addExistingVertexTest(){
        setupStage1();
        int sizeBefore = graphList.getVertices().size();
        graphList.addVertex(new Vertex<>(2));
        int sizeAfter = graphList.getVertices().size();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void DeleteVertexTest() {
        setupStage1();
        assertTrue(graphList.removeVertex(2));
        ArrayList<Vertex<Integer>> adyacentes = graphList.getVertices();
        assertEquals(2, adyacentes.size());
        assertEquals(Integer.valueOf(1), adyacentes.get(0).getDato());
        assertEquals(Integer.valueOf(3), adyacentes.get(1).getDato());
    }
    @Test
    public void DeleteVertexNullTest(){
        setupStage1();
        assertTrue(graphList.removeVertex(1));
        assertTrue(graphList.removeVertex(2));
        assertTrue(graphList.removeVertex(3));
        assertNull(graphList.removeVertex(3));
    }

    @Test
    public void verifyThatVerticesAreDeletedTest(){
        setupStage1();
        assertTrue(graphList.removeVertex(1));
        assertEquals(2 , graphList.getVertices().size());
    }

    @Test
    public void AddEdgeTest() {
        setupStage1();
        ArrayList<Edge<Integer>> edges = graphList.getEdges();
        assertEquals(2, edges.size());
        assertEquals(2 , graphList.getVertices().get(1).getEdges().size());
        assertNotNull(graphList.getVertices().get(1).getEdges().get(0));
        assertEquals(1 , graphList.getVertices().get(2).getEdges().size());
    }

    @Test
    void AddEdgeDirectGraphTest() {
        setupStage2();
        assertEquals(1 , graphList.getVertices().get(1).getEdges().size());
        assertNotNull(graphList.getVertices().get(1).getEdges().get(0));
        assertEquals(0 , graphList.getVertices().get(2).getEdges().size());
    }

    @Test
    public void testDeleteEdgeTest() {
        setupStage1();
        assertTrue(graphList.removeEdge(2, 3));
        ArrayList<Edge<Integer>> edges = graphList.getEdges();
        assertEquals(1, edges.size());
        assertEquals(Integer.valueOf(1), edges.get(0).getOrigin().getDato());
        assertEquals(Integer.valueOf(2), edges.get(0).getDestination().getDato());
    }

    @Test
    public void DeleteEdgeDirectGraphTest() {
        setupStage2();
        assertEquals(1, graphList.getVertices().get(1).getEdges().size());
        assertNotNull(graphList.getVertices().get(1).getEdges().get(0));
        assertEquals(0, graphList.getVertices().get(2).getEdges().size());

        assertTrue(graphList.removeEdge(1, 2));

        assertEquals(0, graphList.getVertices().get(0).getEdges().size());
        assertEquals(0, graphList.getVertices().get(1).getEdges().size());
    }

    @Test
    public void verifyThatEdgesAreDeletedTest(){
        setupStage1();
        assertTrue(graphList.removeEdge(1, 2));
        assertEquals(1 , graphList.getEdges().size());
    }

    @Test
    public void searchVertexExistingTest() {
        setupStage3();
        Vertex<Integer> vertex = graphList.searchVertex(2);
        assertNotNull(vertex);
        assertEquals(Integer.valueOf(2), vertex.getDato());
    }

    @Test
    public void searchVertexNonExistingTest() {
        setupStage3();
        Vertex<Integer> vertex = graphList.searchVertex(4);
        assertNull(vertex);
    }

    @Test
    public void searchVertice() {
        setupStage3();
        Vertex<Integer> vertex = graphList.searchVertex(10);
        assertNull(vertex);
        Vertex<Integer> vertex1 = graphList.searchVertex(1);
        assertEquals(Integer.valueOf(1), vertex1.getDato());
    }

    /*
    @Test
    public void BFSTest() {
        setupStage1();
        assertTrue(graphList.BFS(1));
    }

    @Test
    public void BFSGDisconnectedTest() {
        setupStage2();
        graphList.addVertex(new Vertex<>(6));
        assertFalse(graphList.BFS(6));
    }

    @Test
    public void BFS1Test() {
        setupStage2();
        assertTrue(graphList.BFS(1));
        assertFalse(graphList.BFS(3));
    }

    @Test
    public void DFSTest() {
        setupStage1();
        assertEquals(1, graphList.DFS());
        graphList.addVertex(4);
        graphList.addEdge(4, 1, 0);
        assertEquals(2, graphList.DFS());
    }



    @Test
    public void DFSGMultipleTreesTest() {
        setupStage2();
        graphList.addVertex(new Vertex<>(9));
        graphList.addVertex(new Vertex<>(8));
        graphList.addEdge(8,9, 0);
        assertEquals(2, graphList.DFS());
    }

    @Test
    public void DFSNoVerticesTest(){
        setupStage1();
        graphList.removeVertex(1);
        graphList.removeVertex(2);
        graphList.removeVertex(3);
        assertEquals(0, graphList.DFS());
    }

 */

    @Test
    public void dijkstra(){
        setupStage4();
        Vertex<Integer> startVertex =graphList.searchVertex(1);

        Vertex<Integer> endVertex = graphList.searchVertex(4);

        // Calcular la ruta más corta desde el vértice de inicio al vértice de destino
        int result = graphList.dijkstra(startVertex, endVertex);

        assertEquals(6, result);
    }

    @Test
    public void dijkstraSameDestination(){
        setupStage4();
        Vertex<Integer> startVertex =graphList.searchVertex(1);

        Vertex<Integer> endVertex = graphList.searchVertex(1);

        // Calcular la ruta más corta desde el vértice de inicio al vértice de destino
        int result = graphList.dijkstra(startVertex, endVertex);

        assertEquals(0, result);
    }

    @Test
    public void dijkstraInvalidPath(){
        setupStage4();
        Vertex<Integer> startVertex =graphList.searchVertex(1);

        Vertex<Integer> endVertex = graphList.searchVertex(6);

        // Calcular la ruta más corta desde el vértice de inicio al vértice de destino
        int result = graphList.dijkstra(startVertex, endVertex);

        assertEquals(-1, result);
    }


    @Test
    public void testKruskal1() {


    }

    @Test
    public void testKruskal2() {

    }

  @Test
  public void testKruskal3() {

  }

    @Test
    public void testPrims() {
        graphList = new Graph<>(false, false, false);
        int n = 4;
        List<List<Integer>> edges = new ArrayList<>();
        edges.add(Arrays.asList(1, 2, 1));
        edges.add(Arrays.asList(2, 3, 2));
        edges.add(Arrays.asList(3, 4, 3));
        edges.add(Arrays.asList(4, 1, 4));

        int start = 1;

        int mstWeight = graphList.prim(n, edges, start);
        Assertions.assertEquals(6, mstWeight);
    }




}



