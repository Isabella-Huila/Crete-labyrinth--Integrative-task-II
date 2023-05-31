package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
public class HelloController implements Initializable {
    @FXML
    private Canvas canvas;

    @FXML
    private Button solverBFSButton;

    @FXML
    private Button solverDFSButton;

    private GraphicsContext gc;
    private GraphMatrix<Integer> graph;

    private static Maze maze= new Maze();

    private final int size = 50; // Tamaño de cada cuadrado




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            maze.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Dibujar laberinto
        gc = canvas.getGraphicsContext2D();
        drawLaberinto();
        //Dibujar recorridos
        solverBFSButton.setOnAction(action->{
            solveMaze(true);
        });

        solverDFSButton.setOnAction(action->{
            solveMaze(false);
        });


    }

    private void solveMaze(boolean useBFS) {
        int[] start = {0, 0};
        int[] end = {4, 8};
        Thread solveThread = new Thread(() -> {
            List<int[]> path = maze.solveMaze(start, end, useBFS);
            for (int[] coordinates : path) {
                int vertex = coordinates[0] * maze.getGraphMatrix().getMatAd()[0].length + coordinates[1];
                drawPath(vertex);
                try {
                    Thread.sleep(1000); // Esperar 1 segundo entre cada paso
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        solveThread.start();
    }

    private void drawLaberinto() {
        int numRows = maze.getGraphMatrix().getMatAd().length;
        int numCols = maze.getGraphMatrix().getMatAd()[0].length;
        double totalWidth = numCols * size;
        double totalHeight = numRows * size;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int value = maze.getGraphMatrix().getMatAd()[i][j];
                double x = j * size;
                double y = i * size;

                if (value == 0) {
                    gc.setFill(Color.BLACK);
                    gc.setStroke(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                }
                gc.fillRect(x, y, size, size);
                gc.strokeRect(x, y, size, size);
            }
        }
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, totalWidth , 2); // Línea superior
        gc.fillRect(0, 0, 2 , totalHeight ); // Línea izquierda
        gc.fillRect(0, totalHeight , totalWidth   ,2 ); // Línea inferior
        gc.fillRect(totalWidth , 0, 2 , totalHeight ); // Línea derecha
    }

    public void drawPath(int vertex) {
        int numRows = maze.getGraphMatrix().getMatAd().length;
        int numCols = maze.getGraphMatrix().getMatAd()[0].length;

        int x = vertex % numCols;
        int y = vertex / numCols;

        double rectX = x * size;
        double rectY = y * size;

        gc.setFill(Color.RED); // Color para resaltar el camino
        gc.setStroke(Color.RED);

        gc.fillRect(rectX, rectY, size, size);
        gc.strokeRect(rectX, rectY, size, size);
    }

}