import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GraphTest {
    private Graph<Integer> graph;

    public void stangeUno(){
        graph= new Graph<>(false, false, false);
        Vertex<Integer> vertex1= new Vertex<>(1);
        Vertex<Integer> vertex2= new Vertex<>(2);
        Vertex<Integer> vertex3= new Vertex<>(3);
        Vertex<Integer> vertex4= new Vertex<>(4);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex4);
        graph.addVertex(vertex3);
    }

    public void stageTwo(){
        graph = new Graph<>(false, false, false);
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(1, 2, 10);
    }

    public void stageThree(){
        graph = new Graph<>(false, true, false);
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 2, 20);
    }

    public void stageFour(){
        graph = new Graph<>(false, true, true);
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 1, 20);
    }

    public void stageFive(){
        graph = new Graph<>(true, false, true);
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(2);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(1, 2, 10);
    }

    public void stageSix(){
        graph = new Graph<>(true, true, true);
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(2);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(1, 2, 10);
    }

    public void stageSeven(){
        graph = new Graph<>(false, false, false);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        Vertex<Integer> vertex3 = new Vertex<>(3);
        Vertex<Integer> vertex4 = new Vertex<>(4);
        Vertex<Integer> vertex5 = new Vertex<>(5);
        Vertex<Integer> vertex6 = new Vertex<>(6);

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);

        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 4, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 5, 1);
        graph.addEdge(4, 5, 1);
        graph.addEdge(5, 6, 1);
    }

    public void stangeEight(){
        graph = new Graph<>(false, false, false);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        Vertex<Integer> vertex3 = new Vertex<>(3);
        Vertex<Integer> vertex4 = new Vertex<>(4);
        Vertex<Integer> vertex5 = new Vertex<>(5);
        Vertex<Integer> vertex6 = new Vertex<>(6);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);
        graph.addEdge(1, 2 , 1);
        graph.addEdge(1, 4, 2);
        graph.addEdge(2 , 3 , 3);
        graph.addEdge(2, 4, 4);
        graph.addEdge(3, 4, 5);
        graph.addEdge(4 ,5 , 6);
        graph.addEdge(5 , 6, 7);
    }

    public void stangeNine(){
        graph = new Graph<>(true, false, true);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        Vertex<Integer> vertex3 = new Vertex<>(3);
        Vertex<Integer> vertex4 = new Vertex<>(4);
        Vertex<Integer> vertex5 = new Vertex<>(5);
        Vertex<Integer> vertex6 = new Vertex<>(6);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);

        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(3, 4, 0);
        graph.addEdge(5, 6, 0);

    }

    @Test
    public void addSuccessfullyVertextest(){
        stangeUno();
        Vertex<Integer> vertex= new Vertex<>(5);
        graph.addVertex(vertex);
        assertEquals(5, graph.searchVertex(5).getDato());
    }

    @Test
    public void addNonSuccessfullyVertextest(){
        stangeUno();
        //Verice que ya existe
        int sizeBefore = graph.getVertices().size();
        Vertex<Integer> vertex= new Vertex<>(2);
        graph.addVertex(vertex);
        int sizeAfter = graph.getVertices().size();
        assertEquals(sizeBefore, sizeAfter);

    }

    @Test
    public void addANullVertexTest(){
        stangeUno();
        int sizeBefore2 = graph.getVertices().size();
        assertThrows(NullPointerException.class, () -> graph.addVertex(null));
        int sizeAfter2 = graph.getVertices().size();
        assertEquals(sizeBefore2, sizeAfter2);
    }

    @Test
    public void findVertexThatExistsTest(){
        stangeUno();
        Vertex<Integer> vertex = new Vertex<>(6);
        graph.addVertex(vertex);
        Vertex<Integer> result1 = graph.searchVertex(6);
        assertEquals(vertex, result1);
    }

    @Test
    public void findVertexThatDoesNotExistTest(){
        stangeUno();
        Vertex<Integer> result2 = graph.searchVertex(8);
        assertNull(result2);
    }
    @Test
    public void findNullValue(){
        assertThrows(NullPointerException.class, () -> graph.searchVertex(null));
    }

    @Test
    void testAddEdgeSimpleGraph() {
        //Validar que sea un grafo simple
        stageTwo();
        assertEquals(1, graph.getEdges().size());
        assertEquals(1, graph.getVertices().get(0).getEdges().size());
        assertEquals(1, graph.getVertices().get(1).getEdges().size());
    }

    @Test
    void testAddEdgeMultipleGraph() {
        //Validar que sea un grado multiple
        stageThree();
        assertEquals(2, graph.getEdges().size());
        assertEquals(2, graph.getVertices().get(0).getEdges().size());
        assertEquals(2, graph.getVertices().get(1).getEdges().size());
    }

    @Test
    void testAddEdgePseudograph() {
        //Validar que sea un pseudograph
        stageFour();
        assertEquals(2, graph.getEdges().size());
        assertEquals(2, graph.getVertices().get(0).getEdges().size());
        assertEquals(1, graph.getVertices().get(1).getEdges().size());
    }

    @Test
    void testAddEdgeDirectedGraph() {
        //Validar que sea directo
        stageFive();
        assertEquals(1 , graph.getVertices().get(0).getEdges().size());
        assertNotNull(graph.getVertices().get(0).getEdges().get(0));
        assertEquals(0 , graph.getVertices().get(1).getEdges().size());
    }

    @Test
    void testAddEdgeDirectedGraphMultiple() {
        // Validar si es dirigido multiple
        stageSix();
        assertEquals(1 , graph.getVertices().get(0).getEdges().size());
        assertNotNull(graph.getVertices().get(0).getEdges().get(0));
        assertEquals(0 , graph.getVertices().get(1).getEdges().size());
        //multiple
        graph.addEdge(1, 2, 20);
        assertEquals(2, graph.getVertices().get(0).getEdges().size());
        assertEquals(0 , graph.getVertices().get(1).getEdges().size());
    }

    @Test
    void testBFS() {
        stageSeven();

        graph.BFS(0);

        assertEquals(Color.BLACK, graph.getVertices().get(0).getColor());
        assertEquals(0, graph.getVertices().get(0).getDistance());
        assertEquals(null, graph.getVertices().get(0).getPre());

        assertEquals(Color.BLACK, graph.getVertices().get(1).getColor());
        assertEquals(1, graph.getVertices().get(1).getDistance());
        assertEquals(graph.getVertices().get(0), graph.getVertices().get(1).getPre());

        assertEquals(Color.BLACK, graph.getVertices().get(2).getColor());
        assertEquals(2, graph.getVertices().get(2).getDistance());
        assertEquals(graph.getVertices().get(1), graph.getVertices().get(2).getPre());

        assertEquals(Color.BLACK, graph.getVertices().get(3).getColor());
        assertEquals(1, graph.getVertices().get(3).getDistance());
        assertEquals(graph.getVertices().get(0), graph.getVertices().get(3).getPre());

        assertEquals(Color.BLACK, graph.getVertices().get(4).getColor());
        assertEquals(2, graph.getVertices().get(4).getDistance());
        assertEquals(graph.getVertices().get(3), graph.getVertices().get(4).getPre());

        assertEquals(Color.BLACK, graph.getVertices().get(5).getColor());
        assertEquals(3, graph.getVertices().get(5).getDistance());
        assertEquals(graph.getVertices().get(4), graph.getVertices().get(5).getPre());
    }

    @Test
    public void testBFSInvalidStartIndex(){
        assertThrows(NullPointerException.class, () -> graph.BFS(10));
    }

    @Test
    public void checkDFSTime() {
        stangeEight();

        graph.DFS();

        assertEquals(1, graph.getVertices().get(0).getStartTime());
        assertEquals(2, graph.getVertices().get(1).getStartTime());
        assertEquals(3, graph.getVertices().get(2).getStartTime());
        assertEquals(4, graph.getVertices().get(3).getStartTime());
        assertEquals(5, graph.getVertices().get(4).getStartTime());
        assertEquals(6, graph.getVertices().get(5).getStartTime());

        assertEquals(12, graph.getVertices().get(0).getFinishTime());
        assertEquals(11, graph.getVertices().get(1).getFinishTime());
        assertEquals(10, graph.getVertices().get(2).getFinishTime());
        assertEquals(9, graph.getVertices().get(3).getFinishTime());
        assertEquals(8, graph.getVertices().get(4).getFinishTime());
        assertEquals(7, graph.getVertices().get(5).getFinishTime());
    }

    @Test
    public void CheckTotalPathOfDFSWithColorsTest(){
        stangeEight();
        graph.DFS();
        assertEquals(Color.BLACK, graph.getVertices().get(0).getColor());
        assertEquals(Color.BLACK, graph.getVertices().get(1).getColor());
        assertEquals(Color.BLACK, graph.getVertices().get(2).getColor());
        assertEquals(Color.BLACK, graph.getVertices().get(3).getColor());
        assertEquals(Color.BLACK, graph.getVertices().get(4).getColor());
    }

    @Test
    public void verifyDFSOnDirectedGraphs(){
        stangeNine();

        graph.DFS();

        assertEquals(Color.BLACK, graph.getVertices().get(0).getColor());
        assertEquals(1, graph.getVertices().get(0).getStartTime());
        assertEquals(8, graph.getVertices().get(0).getFinishTime());
        assertEquals(Color.BLACK, graph.getVertices().get(1).getColor());
        assertEquals(2, graph.getVertices().get(1).getStartTime());
        assertEquals(7, graph.getVertices().get(1).getFinishTime());
        assertEquals(Color.BLACK, graph.getVertices().get(2).getColor());
        assertEquals(3, graph.getVertices().get(2).getStartTime());
        assertEquals(6, graph.getVertices().get(2).getFinishTime());
        assertEquals(Color.BLACK, graph.getVertices().get(3).getColor());
        assertEquals(4, graph.getVertices().get(3).getStartTime());
        assertEquals(5, graph.getVertices().get(3).getFinishTime());
        assertEquals(Color.BLACK, graph.getVertices().get(4).getColor());
        assertEquals(9, graph.getVertices().get(4).getStartTime());
        assertEquals(12, graph.getVertices().get(4).getFinishTime());
        assertEquals(Color.BLACK, graph.getVertices().get(5).getColor());
        assertEquals(10, graph.getVertices().get(5).getStartTime());
        assertEquals(11, graph.getVertices().get(5).getFinishTime());

    }
    public void stageTen(){
        graph = new Graph<>(false, false, false);

        Vertex<Integer> vertex1= new Vertex<>(1);
        Vertex<Integer> vertex2= new Vertex<>(2);
        Vertex<Integer> vertex3= new Vertex<>(3);
        Vertex<Integer> vertex4= new Vertex<>(4);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex4);
        graph.addVertex(vertex3);

        graph.addEdge(1, 2, 10);
        graph.addEdge(2, 3, 20);
        graph.addEdge(3, 1, 30);
        graph.addEdge(4, 1, 40);
    }
    @Test
    public void removeVertexTest(){
        stageTen();
        assertTrue(graph.removeVertex(1));
        assertNull(graph.searchVertex(1));
    }

    @Test
    public void verifyThatEdgesAreDeleted(){
        stageTen();
        assertTrue(graph.removeVertex(1));
        assertEquals(1 , graph.getEdges().size());
    }

    @Test
    public void testRemoveEdgeSimpleGraph() {
        Graph<Integer> graph = new Graph<>(false, false, false);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(1, 2, 5);

        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(1, 2));
    }

    @Test
    public void testRemoveEdgeMultipleGraph() {
        Graph<Integer> graph = new Graph<>(false, true, false);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 2, 10);

        assertTrue(graph.removeEdge(1, 2));
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(1, 2));
    }

    @Test
    public void testRemoveEdgePseudoGraph() {
        Graph<Integer> graph = new Graph<>(false, true, true);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 1, 10);

        assertTrue(graph.removeEdge(1, 2));
        assertTrue(graph.removeEdge(1, 1));
        assertFalse(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(1, 1));
    }


    @Test
    public void testRemoveEdgeDirectedGraph() {
        Graph<Integer> graph = new Graph<>(true, false, true);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(1, 2, 5);

        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(2, 1));
        assertFalse(graph.removeEdge(1, 2));
    }

    @Test
    public void testRemoveEdgeDirectedMultipleGraph() {
        Graph<Integer> graph = new Graph<>(true, true, true);
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 2, 10);

        assertTrue(graph.removeEdge(1, 2));
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(2, 1));
        assertFalse(graph.removeEdge(1, 2));
    }
}
