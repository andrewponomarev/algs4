import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ponomarevandrew on 04.07.17.
 */
public class Board {

    private int[][] blocks;

    private int dimension;

    private int hammingDistance;

    private int manhattanDistance;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        checkArgument(blocks);
        this.blocks    = blocks;
        this.dimension = blocks.length;
        this.hammingDistance = calculateHammingDistance();
        this.manhattanDistance = calculateManhattanDistace();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingDistance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int n = dimension * dimension;
        int i = 0;
        int j = 0;
        for (int k = 1; k < n; k++) {
            i = (k - 1) / dimension;
            j = (k - 1) % dimension;
            if (blocks[i][j] != k)
                return false;
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        // клонируем массив
        int[][] newBlocks = new int[dimension][dimension];
        copyToBlocks(newBlocks);

        // ищем индексы и значения ненулевых элементов
        int[] rows = new int[2];
        int[] cols = new int[2];
        int[] values = new int[2];
        int k = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (newBlocks[i][j] != 0) {
                    rows[k] = i;
                    cols[k] = j;
                    values[k++] =newBlocks[i][j];
                }
                if (k == 2) break;
            }
            if (k == 2) break;
        }
        // меняем элементы местами
        newBlocks[rows[0]][cols[0]] = values[1];
        newBlocks[rows[1]][cols[1]] = values[0];

        //создаем доску по новому массиву
        return new Board(newBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (getClass() != y.getClass())
            return false;
        Board other = (Board) y;
        if (this.dimension != other.dimension)
            return false;
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (this.blocks[i][j] != other.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<Board>();
        int null_row = 0;
        int null_column = 0;
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (blocks[i][j] == 0) {
                    null_row = i;
                    null_column = j;
                    break;
                }
            }
        }
        int[][] cloneBlocks = null;
        // Над нулем есть блок
        if (null_row > 0) {
            cloneBlocks = new int[dimension][dimension];
            copyToBlocks(cloneBlocks);
            exchange(cloneBlocks, null_row - 1, null_column, null_row, null_column );
            result.add(new Board(cloneBlocks));
        }
        if (null_row < dimension - 1) {
            cloneBlocks = new int[dimension][dimension];
            copyToBlocks(cloneBlocks);
            exchange(cloneBlocks, null_row + 1, null_column, null_row, null_column );
            result.add(new Board(cloneBlocks));
        }
        if (null_column > 0) {
            cloneBlocks = new int[dimension][dimension];
            copyToBlocks(cloneBlocks);
            exchange(cloneBlocks, null_row, null_column, null_row , null_column - 1);
            result.add(new Board(cloneBlocks));
        }
        if (null_column < dimension - 1) {
            cloneBlocks = new int[dimension][dimension];
            copyToBlocks(cloneBlocks);
            exchange(cloneBlocks, null_row, null_column, null_row, null_column + 1);
            result.add(new Board(cloneBlocks));
        }
        return result;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String result = dimension + "\n";
        for (int[] row : blocks) {
            String rowString = "";
            for (int block : row) {
                rowString += " " + block;
            }
            result += rowString + "\n";
        }
        result += "\n";
        return result;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        for (int i = 0; i < 10; i++) {
            Iterable<Board> nb = initial.neighbors();
            for (Board nn : nb) {
                System.out.println(nn);
            }
            System.out.println("-------");
        }
    }

    private int calculateHammingDistance() {
        return calculate_distance( (index, block, distance) -> {
                    if (block != 0 && index != block) {
                        return distance + 1;
                    }
                    return distance;
                }
        );
    }

    private int calculateManhattanDistace() {
        return calculate_distance( (index, block, distance) -> {
            if (block == 0) return distance;
            while (index != block) {
                if (index - block <= -dimension) {
                    index += dimension;
                    distance++;
                    continue;
                }
                if (index - block < 0) {
                    if ( (index - 1) / dimension == (block - 1) / dimension ) {
                        index += 1;
                    } else {
                        index += dimension;
                    }
                    distance++;
                    continue;
                }
                if (index - block < dimension) {
                    if ( (index - 1) / dimension == (block - 1) / dimension) {
                        index -= 1;
                    } else {
                        index -= dimension;
                    }
                    distance++;
                    continue;
                }
                else {
                    index -= dimension;
                    distance++;
                }
            }
            return distance;
        });
    }

    private void checkArgument(int[][] blocks) {
        int n = blocks.length;
        if (n == 1) {
            throw new IllegalArgumentException("Bad size of input blocks");
        }
        for (int[] row : blocks) {
            if (row.length != n) {
                throw new IllegalArgumentException("Bad size of input blocks");
            }
        }
    }

    // меняем элементы в двумерном массиве местами
    private void exchange(int[][] b, int first_row, int first_column, int second_row, int second_column) {
        int temp = b[first_row][first_column];
        b[first_row][first_column] = b[second_row][second_column];
        b[second_row][second_column] = temp;
    }

    // создаем копию 2-мерного массива
    private void copyToBlocks(int[][] copy) {
        for (int i = 0; i < dimension; ++i)
            for (int j = 0; j < dimension; ++j)
                copy[i][j] = blocks[i][j];
    }

    private interface DistanceFunction {
       int execute(int index, int block, int distance);
    }

    private int calculate_distance(DistanceFunction f) {
        int distance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension && j == dimension) {
                    continue;
                }
                int index = i * dimension + j + 1;
                int block = blocks[i][j];
                distance = f.execute(index, block, distance);
            }
        }
        return distance;
    }

}
