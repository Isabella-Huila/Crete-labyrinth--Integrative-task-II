public class Main {
    public static void main(String[] args) {
        // Crear el grafo
        GraphMatrix<Integer> graph = new GraphMatrix<Integer>(4 , true, true, true);

        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        Vertex<Integer> vertex3 = new Vertex<>(3);
        Vertex<Integer> vertex4 = new Vertex<>(4);

        // Agregar vértices
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);

        // Agregar aristas
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 7);
        graph.addEdge(4, 1, 4);
        graph.addEdge(4, 1, 4);

        // Imprimir la matriz de adyacencia
        graph.printAdjacencyMatrix();


        // Eliminar una arista
        boolean removeEdge = graph.removeEdge(3, 4);
        if (removeEdge) {
            System.out.println("Arista eliminada: (3, 4)");
        } else {
            System.out.println("No se encontró la arista (3, 4)");
        }

        // Imprimir la matriz de adyacencia actualizada
        graph.printAdjacencyMatrix();
    }
}