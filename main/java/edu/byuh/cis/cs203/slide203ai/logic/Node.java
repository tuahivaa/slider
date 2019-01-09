package edu.byuh.cis.cs203.slide203ai.logic;

import java.util.ArrayList;
import java.util.List;

public class Node {

    //private Player[][] myGrid;
    private Player currentPlayer;
    private int currentScore;
    private int cumulativeScore;//TODO do we need this?
    char move;
    private String path;
    private List<Node> children;

    private static char[] moves = {'1', '2', '3', '4', '5', 'A', 'B', 'C', 'D', 'E'};

    public Node(Player[][] grid, String path, char move, int score, Player p, int height) {
        Player[][] myGrid = new Player[GameBoard.DIM][GameBoard.DIM];
        LookAhead.copyGrid(grid, myGrid);
        currentPlayer = p;
        this.move = move;
        if (move != '0') {
            this.path = path + move;
            GameBoard.doOneMove(move, currentPlayer, myGrid);
            currentScore = LookAhead.getScore(myGrid, currentPlayer);
            if (currentPlayer == GameBoard.HUMAN) {
                currentScore = -currentScore;
            }
            cumulativeScore = score + currentScore;
        } else {
            this.path = ""; //special case for root
            cumulativeScore = 0;
        }
        if (height > 0) {
            children = new ArrayList<Node>(10);
            for (char c : moves) {
                children.add(new Node(myGrid, path, c, cumulativeScore, GameBoard.otherPlayer(currentPlayer), height-1));
            }
        }
    }

    public int getScore() {
        //return cumulativeScore;
        return currentScore;
    }

    public boolean hasChildren() {
        return (children != null);
    }

    public List<Node> getChildren() {
        return children;
    }

    public char getFirstStepInPath() {
        return path.charAt(0);
    }

    public int getMinimumScoreOfChildren() {
        //char c = '0';
        int min = Integer.MAX_VALUE;
        for (Node ch : children) {
            if (ch.getScore() < min) {
                min = ch.getScore();
                //c = ch.move;
            }
        }

        return min;
    }

    public char getMoveForMinimumScore() {
        char c = '0';
        int min = Integer.MAX_VALUE;
        for (Node ch : children) {
            if (ch.getScore() < min) {
                min = ch.getScore();
                c = ch.move;
            }
        }

        return c;
    }

}

