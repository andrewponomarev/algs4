import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ponomarevandrew on 10.09.17.
 */
public class Solver {

    private static final Comparator<SearchNode> ByHamming = (v, w) -> {
        if (v.calculateHammingPriority() > w.calculateHammingPriority()) return 1;
        if (v.calculateHammingPriority() == w.calculateHammingPriority()) return 0;
        return -1;
    };

    private static final Comparator<SearchNode> ByManhattan = (v, w) -> {
        if (v.calculateManhattanPriority() > w.calculateManhattanPriority()) return 1;
        if (v.calculateManhattanPriority() == w.calculateManhattanPriority()) return 0;
        return -1;
    };

    private boolean isSolvable;

    private Iterable<Board> solution;

    private SearchNode solutionNode;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        long start = System.currentTimeMillis();

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> pq = new MinPQ<>(ByHamming);
        MinPQ<SearchNode> twinPq = new MinPQ<>(ByHamming);

        SearchNode node = new SearchNode(initial, null);
        SearchNode twinNode = new SearchNode(initial.twin(), null);

        pq.insert(node);
        twinPq.insert(twinNode);

        SearchNode dequedNode = pq.delMin();
        SearchNode twinDequedNode = twinPq.delMin();

        while (!dequedNode.getBoard().isGoal() && !twinDequedNode.getBoard().isGoal()) {
            insertNeighbors(dequedNode, pq);
            insertNeighbors(twinDequedNode, twinPq);
            dequedNode = pq.delMin();
            twinDequedNode = twinPq.delMin();
        }

        if (dequedNode.getBoard().isGoal()) {
            isSolvable = true;
        }

        solutionNode = dequedNode;
        this.solution = buildSolution(solutionNode);


        long finish = System.currentTimeMillis();
        System.out.println(finish - start);

    }

    private Iterable<Board> buildSolution(SearchNode solutionNode) {
        Stack s = new Stack<>();
        while (solutionNode != null) {
            s.push(solutionNode.board);
            solutionNode = solutionNode.getPreviousNode();
        }
        if (s.isEmpty()) s = null;
        return s;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable) {
            return -1;
        }
        return solutionNode.getNumOfMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
    }

    private void insertNeighbors(SearchNode searchNode, MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = searchNode.getBoard().neighbors();
        for (Board n : neighbors) {
            if (searchNode.getPreviousNode() == null || !n.equals(searchNode.getPreviousNode().getBoard())) {
                pq.insert(new SearchNode(n, searchNode));
            }
        }
    }

    private class SearchNode {

        private Board board;

        private int numOfMoves;

        private SearchNode previousSearchNode;

        private int priority;

        SearchNode(Board board, SearchNode previousSearchNode) {
            this.board = board;
            this.previousSearchNode = previousSearchNode;
            this.numOfMoves = calculateNumOfMoves(previousSearchNode);
            this.priority = calculateHammingPriority();
        }

        public Board getBoard() {
            return board;
        }

        public int getNumOfMoves() {
            return numOfMoves;
        }

        public SearchNode getPreviousNode() {
            return previousSearchNode;
        }

        private int calculateHammingPriority() {
            return board.hamming() + numOfMoves;
        }

        private int calculateManhattanPriority() {
            return board.manhattan() + numOfMoves;
        }

        private int calculateNumOfMoves(SearchNode previousSearchNode) {
            if (previousSearchNode == null)
                return 0;
            return previousSearchNode.numOfMoves + 1;
        }

    }
}
