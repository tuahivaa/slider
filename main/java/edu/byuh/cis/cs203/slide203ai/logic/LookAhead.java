package edu.byuh.cis.cs203.slide203ai.logic;

public class LookAhead {

    private int moveCount = 0;
    private char[] options = {'1', '2', '3', '4', '5', 'A', 'B', 'C', 'D', 'E'};

    public static final char NULL = '0';

    public void reset() {
        moveCount = 0;
    }

    public char getSuggestedMove(Player[][] grid, float difficulty) {
        int maxScore = Integer.MIN_VALUE;
        char bestMove = NULL;
        Node root = new Node(grid, "", NULL, 0, GameBoard.HUMAN, 2);
        for (Node l1 : root.getChildren()) {
            if (l1.getScore() == 5) {
                return l1.getFirstStepInPath();
            } else {
                int childScore = l1.getMinimumScoreOfChildren();
                if (childScore > maxScore) {
                    maxScore = childScore;
                    bestMove = l1.getFirstStepInPath();
                    //Main.say("For the move " + l1.move + l1.getMoveForMinimumScore() + " the best the human can do is " + childScore);
                }
            }
        }
        if (bestMove == NULL || moveCount++ < 2 || Math.random() > difficulty) {
            bestMove = options[(int)(Math.random() * options.length)];
        }
        //Main.say("Thus my suggested move is " + bestMove);
        return bestMove;
    }


    public Player[][] copyGrid(Player[][] grid) {
        Player[][] copy = new Player[GameBoard.DIM][GameBoard.DIM];
        for (int i=0; i<GameBoard.DIM; ++i) {
            for (int j=0; j<GameBoard.DIM; ++j) {
                copy[i][j] = grid[i][j];
            }
        }

        return copy;
    }

    public static void copyGrid(Player[][] src, Player[][] dst) {
        //dst = new Player[GameEngine.DIM][GameEngine.DIM];
        for (int i=0; i<GameBoard.DIM; ++i) {
            for (int j=0; j<GameBoard.DIM; ++j) {
                dst[i][j] = src[i][j];
            }
        }
    }

    public static int getScore(Player[][] matrix, Player p) {
        int max = -1;
        //rows
        for (int i=0; i<GameBoard.DIM; ++i) {
            int score = 0;
            for (int j=0; j<GameBoard.DIM; ++j) {
                if (matrix[i][j] == p) {
                    ++score;
                }
            }
            if (score > max) {
                max = score;
            }
        }
        //columns
        for (int i=0; i<GameBoard.DIM; ++i) {
            int score = 0;
            for (int j=0; j<GameBoard.DIM; ++j) {
                if (matrix[j][i] == p) {
                    ++score;
                }
            }
            if (score > max) {
                max = score;
            }
        }
        //diagonal \
        int score = 0;
        for (int i=0; i<GameBoard.DIM; ++i) {
            if (matrix[i][i] == p) {
                ++score;
            }
        }
        if (score > max) {
            max = score;
        }
        //diagonal /
        score = 0;
        for (int i=0; i<GameBoard.DIM; ++i) {
            if (matrix[i][GameBoard.DIM-i-1] == p) {
                ++score;
            }
        }
        if (score > max) {
            max = score;
        }
        return max;
    }
}

