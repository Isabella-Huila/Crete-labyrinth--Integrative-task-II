import com.example.demo.GraphMatrix;
import com.example.demo.Maze;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    private Maze maze;
    public void setupStage1(){
        maze= new Maze();

        int[][] graphMatrix = {
                {1, 0, 1},
                {1, 1, 1},
                {0, 0, 1}
        };

        GraphMatrix<Integer> graphMatrix1= new GraphMatrix<>(3, false, false, false);
        graphMatrix1.setMatAd(graphMatrix);
        maze.setGraphMatrix(graphMatrix1);
    }
    
    @Test
    void testSolveMazeWithBFS() {
        setupStage1();
        int[] start = {0, 0};
        int[] end = {2, 2};
        boolean useBFS = true;

        List<int[]> expectedPath = Arrays.asList(
                new int[]{0, 0},
                new int[]{1, 0},
                new int[]{1, 1},
                new int[]{1, 2},
                new int[]{2, 2}
        );

        List<int[]> actualPath = maze.solveMaze(start, end, useBFS);

        for (int i = 0; i < expectedPath.size(); i++) {
            assertArrayEquals(expectedPath.get(i), actualPath.get(i));
        }

    }

    @Test
    void testSolveMazeWithDFS() {
        setupStage1();
        int[] start = {0, 0};
        int[] end = {2, 2};
        boolean useBFS = false;

        List<int[]> expectedPath = Arrays.asList(
                new int[]{0, 0},
                new int[]{1, 0},
                new int[]{1, 1},
                new int[]{1, 2},
                new int[]{2, 2}
        );

        List<int[]> actualPath = maze.solveMaze(start, end, useBFS);

        for (int i = 0; i < expectedPath.size(); i++) {
            assertArrayEquals(expectedPath.get(i), actualPath.get(i));
        }
    }

}
