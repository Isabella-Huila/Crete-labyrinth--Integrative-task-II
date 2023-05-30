package com.example.demo;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
public class Maze {
    private GraphMatrix<Integer> graphMatrix;
    static String path= "data.txt";


    public Maze() {
        graphMatrix= new GraphMatrix<>(9, false, false, false);
    }

    public void load() throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        String content = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);

        JsonArray verticesArray = jsonObject.getAsJsonArray("vertices");
        Vertex<Integer>[] vertices = new Vertex[verticesArray.size()];

        for (int i = 0; i < verticesArray.size(); i++) {
            JsonObject vertexObject = verticesArray.get(i).getAsJsonObject();
            int dato = vertexObject.get("dato").getAsInt();
            vertices[i] = new Vertex<>(dato);
        }

        int[][] matAd = gson.fromJson(jsonObject.getAsJsonArray("matAd"), int[][].class);
        int numVertices = jsonObject.get("numVertices").getAsInt();
        int maxSize = jsonObject.get("maxSize").getAsInt();


        // Crear una instancia de GraphMatrix con los valores cargados
        graphMatrix.setVertices(vertices);
        graphMatrix.setMatAd(matAd);
        graphMatrix.setNumVertices(numVertices);
        /*
        GraphMatrix grafo = gson.fromJson(content, GraphMatrix.class);
        graphMatrix.setVertices(grafo.getVertices());
        graphMatrix.setMatAd(grafo.getMatAd());
        graphMatrix.setNumVertices(grafo.getNumVertices());
        graphMatrix.setMaxSize(grafo.getMaxSize());
        fis.close();*/

    }

    public GraphMatrix<Integer> getGraphMatrix() {
        return graphMatrix;
    }

    public void setGraphMatrix(GraphMatrix<Integer> graphMatrix) {
        this.graphMatrix = graphMatrix;
    }
}
