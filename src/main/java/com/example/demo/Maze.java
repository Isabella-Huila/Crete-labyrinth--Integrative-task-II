package com.example.demo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    }

    public GraphMatrix<Integer> getGraphMatrix() {
        return graphMatrix;
    }

    public void setGraphMatrix(GraphMatrix<Integer> graphMatrix) {
        this.graphMatrix = graphMatrix;
    }

    public List<int[]> solveMaze(int[] start, int[] end, boolean useBFS) {
        int rows = graphMatrix.getMatAd().length;
        int cols = graphMatrix.getMatAd()[0].length;

        boolean[][] visited = new boolean[rows][cols];
        int[][] prev = new int[rows][cols];

        if (useBFS) {
            graphMatrix.bfs(start, end, visited, prev);
        } else {
            graphMatrix.dfs(start[0], start[1], end, visited, prev);
        }

        return reconstructPath(prev, start, end);
    }

    private static List<int[]> reconstructPath(int[][] prev, int[] start, int[] end) {
        List<int[]> path = new ArrayList<>();
        int rows = prev.length;
        int cols = prev[0].length;

        int currentRow = end[0];
        int currentCol = end[1];

        while (!Arrays.equals(new int[]{currentRow, currentCol}, start)) {
            path.add(0, new int[]{currentRow, currentCol});

            int prevIndex = prev[currentRow][currentCol];
            currentRow = prevIndex / cols;
            currentCol = prevIndex % cols;
        }

        path.add(0, start);

        return path;

    }
}
