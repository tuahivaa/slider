package edu.byuh.cis.cs203.slide203ai.logic;

/**
 * The GameBoard class is the "brains" of this program. It maintains the state
 * of the gameboard, handles moves, and decides who wins. The only thing it
 * does NOT do is handle user input/output. That part is handled by the Main class.
 * @author draperg
 *
 */
public class GameBoard {

    /**
     * grid is a two-dimensional (5x5) array representing the actual gameboard
     */
    private Player[][] grid;

    /**
     * DIM is set to 5. Change this if you want a bigger/smaller gameboard.
     * The "true" Slide game uses a 5x5 board.
     */
    public static final int DIM = 5;

    /**
     * keeps track of whose turn it is (either Player.X or Player.O)
     */
    private Player currentPlayer;

    /**
     * AI algorithm for the computer opponent
     */
    private LookAhead ai;

    //In the original Slide game, the computer was X
    //and the human was O, so I've kept that here.
    //But it doesn't really matter.
    public static final Player COMPUTER = Player.X;
    public static final Player HUMAN = Player.O;


    /**
     * Constructor for our GameBoard class. Initializes the gameboard to blank.
     * Arbitrarily sets O as the first player.
     */
    public GameBoard() {
        grid = new Player[DIM][DIM];
        currentPlayer = Player.O;
        ai = new LookAhead();
        clear();
    }

    /**
     * Just "blanks out" the gameboard.
     */
    public void clear() {
        for (int i=0; i<DIM; i++) {
            for (int j=0; j<DIM; j++) {
                grid[i][j] = Player.BLANK;
            }
        }
        ai.reset();
    }

    /**
     * This is a generalization of the submitMove method.
     * It is stateless; it works for arbitrary grids and players.
     * It is used as a helper routine by the AI algorithm to
     * test possible scenarios.
     * @param move which move to try (ABCDE12345)
     * @param p which player is submitting the move
     * @param matrix the current gameboard
     */
    public static void doOneMove(char move, Player p, Player[][] matrix) {
        if (move >= '1' && move <= '5') {
            //vertical move, move stuff down
            int col = Integer.parseInt(""+move)-1;
            Player newVal = p;
            for (int i=0; i<GameBoard.DIM; ++i) {
                if (matrix[i][col] == Player.BLANK) {
                    matrix[i][col] = newVal;
                    break;
                } else {
                    Player tmp = matrix[i][col];
                    matrix[i][col] = newVal;
                    newVal = tmp;
                }
            }

        } else { //A-E
            //horizontal move, move stuff right
            int row = (int)(move - 'A');
            Player newVal = p;
            for (int i=0; i<DIM; ++i) {
                if (matrix[row][i] == Player.BLANK) {
                    matrix[row][i] = newVal;
                    break;
                } else {
                    Player tmp = matrix[row][i];
                    matrix[row][i] = newVal;
                    newVal = tmp;
                }
            }
        }

    }

    /**
     * This method processes a single move for the current player. The input parameter is the row or column the user selected.
     * Tokens are "inserted" into the top of the grid (for vertical moves) or into the left side (for horizontal moves).
     * Tokens move from top to bottom (for vertical moves) or from left to right (for horizontal moves).
     * Existing tokens slide down (or right) to make way for the new tokens. If a column is full, the bottommost token
     * is removed. If a row is full, the rightmost token is removed.
     * At the conclusion of a move, the currentPlayer variable is toggled (X to O, or O to X).
     * @param move one of the characters '1', '2', '3', '4', '5' (for vertical moves) or 'A', 'B', 'C', 'D', 'E' (for horizontal moves). Any other values will result in buggy results.
     */
    public void submitMove(char move) {
        doOneMove(move, currentPlayer, grid);
        currentPlayer = otherPlayer(currentPlayer);
    }

    public static Player otherPlayer(Player p) {
        if (p == Player.X) {
            return Player.O;
        } else {
            return Player.X;
        }
    }



    /**
     * Checks all rows, columns and the two diagonals for five matching tokens in a row.
     * I'll explain the logic for rows. The logic for columns and diagonals are analogous.
     * For each of the five rows, check the value of the leftmost element. If it's not blank,
     * loop through the remaining four elements to see if they match the first one. If
     * they do, stop and declare that player the winner. But if any does not match the
     * first one, skip that row and search the next row for matches in the same manner.
     * @return the value of the winning player, X or O or TIE. Returns BLANK if no one has yet won (the most common state).
     */
    public Player checkForWin() {
        Player winner = Player.BLANK;
        Player tmpWinner;

        //check all rows
        for (int i=0; i<DIM; ++i) {
            if (grid[i][0] != Player.BLANK) {
                tmpWinner = grid[i][0];
                for (int j=0; j<DIM; ++j) {
                    if (grid[i][j] != tmpWinner) {
                        tmpWinner = Player.BLANK;
                        break;
                    }
                }
                if (tmpWinner != Player.BLANK) {
                    if (winner == Player.BLANK) {
                        winner = tmpWinner;
                    } else {
                        return Player.TIE;
                    }
                }
            }
        }

        //check all columns
        tmpWinner = Player.BLANK;
        for (int i=0; i<DIM; ++i) {
            if (grid[0][i] != Player.BLANK) {
                tmpWinner = grid[0][i];
                for (int j=0; j<DIM; ++j) {
                    if (grid[j][i] != tmpWinner) {
                        tmpWinner = Player.BLANK;
                        break;
                    }
                }
                if (tmpWinner != Player.BLANK) {
                    if (winner == Player.BLANK) {
                        winner = tmpWinner;
                    } else {
                        return Player.TIE;
                    }
                }
            }
        }

        //at this point, either there's a tie, or there's not.
        //You can't have a tie with diagonals.
        if (winner != Player.BLANK) {
            return winner;
        }

        //check top-left -> bottom-right diagonal
        if (grid[0][0] != Player.BLANK) {
            winner = grid[0][0];
            for (int i=0; i<DIM; ++i) {
                if (grid[i][i] != winner) {
                    winner = Player.BLANK;
                    break;
                }
            }
            if (winner != Player.BLANK) {
                return winner; //5 in a diagonal!
            }
        }

        //check bottom-left -> top-right diagonal
        if (grid[DIM-1][0] != Player.BLANK) {
            winner = grid[DIM-1][0];
            for (int i=0; i<DIM; ++i) {
                if (grid[DIM-1-i][i] != winner) {
                    winner = Player.BLANK;
                    break;
                }
            }
            if (winner != Player.BLANK) {
                return winner; //5 in a diagonal!
            }
        }

        return winner;
    }

    /**
     * returns the value of the current player (X or O).
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Ask the AI object for the computer's next move, and return it.
     * @return the char representing a possible move
     */
    public char suggestNextMove() {
        return ai.getSuggestedMove(grid, 1);
    }


}
