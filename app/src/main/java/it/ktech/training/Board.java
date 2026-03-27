package it.ktech.training;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.stream.Stream;

import it.ktech.training.exceptions.GameOverException;
import it.ktech.training.exceptions.IllegalBoardSizeException;
import it.ktech.training.exceptions.IllegalMoveException;
import it.ktech.training.exceptions.SquareOccupiedException;

/**
 * The Gomoku game board.
 *
 * The game is played on a square board of intersecting lines (7-19
 * left-to-right lines, 7-19 top-to-bottom lines, up to 361 intersections in
 * all). Players place their markers on these intersections.
 *
 * Internally, since intersections on a Gomoku board are akin to squares on a
 * chessboard, they are referred to as "squares".
 *
 * @author Diego Paolicelli
 */
public class Board {
    // size constraints
    private static final int SIZE_MIN = 7;
    private static final int SIZE_MAX = 19;
    private int size;

    // board state - we provide empty squares, callers provide the markers
    private static final char EMPTY_SQUARE = '0';
    private char[][] squares;

    private Board(int size) throws IllegalBoardSizeException {
        if (size < SIZE_MIN || size > SIZE_MAX) {
            throw new IllegalBoardSizeException();
        }
        this.size = size;
        this.squares = new char[size][size];
        for (char[] row : this.squares) {
            Arrays.fill(row, EMPTY_SQUARE);
        }
    }

    /**
     * Prompts the user for the board's size, then creates a new {@code Board}
     * of the given size.
     *
     * This method keeps trying until a valid size is given.
     */
    public static Board create() {
        while (true) {
            try {
                int size = Terminal.scanInt("What is your board size");
                return new Board(size);
            } catch (IllegalBoardSizeException | InputMismatchException _) {
                Terminal.println("I said, the minimum is " + SIZE_MIN
                        + ", the maximum is " + SIZE_MAX + ".");
            }
        }
    }

    /**
     * Adds the given {@code marker} to the board at the given {@code position}.
     *
     * @param marker   The marker to place.
     * @param position Where to place the marker.
     * @throws IllegalMoveException    The marker would be placed out of bounds.
     * @throws SquareOccupiedException A marker has already been placed at that
     *                                 position.
     * @throws GameOverException       The caller requested a specific position
     *                                 that signals the end of the game.
     */
    public void placeMarker(char marker, Position position)
            throws IllegalMoveException, SquareOccupiedException, GameOverException {
        switch (position) {
            // game over
            case Position pos when pos.equals(new Position(-1, -1)) ->
                throw new GameOverException();
            // out of bounds
            case Position pos when isPositionOutOfBounds(pos) ->
                throw new IllegalMoveException();
            // square is occupied
            case Position pos when !isSquareEmpty(pos) ->
                throw new SquareOccupiedException();
            // square is valid and empty, put the marker here
            default -> setMarkerAt(marker, position);
        }
    }

    /**
     * Adds the given {@code marker} to the board in a random empty square.
     *
     * @param marker The marker to place.
     */
    public void placeMarkerRandomly(char marker) {
        boolean placed = false;
        while (!placed) {
            try {
                this.placeMarker(marker, Position.createRandom(this.size));
                placed = true;
            } catch (Exception _) {
                // retry until found
                continue;
            }
        }
    }

    /**
     * Returns a {@code Stream} of valid positions immediately around the given
     * {@code center}.
     *
     * @param center The target {@code Position}.
     * @return A stream of {@code Position}s.
     */
    public Stream<Position> getSquaresAround(Position center) {
        return center.getAdjacentPositions().filter(pos -> !isPositionOutOfBounds(pos));
    }

    /**
     * Prints the current state of the board.
     */
    public void print() {
        Terminal.println("");
        for (char[] row : this.squares) {
            Terminal.println(new String(row).replace("", " "));
        }
        Terminal.println("");
    }

    /**
     * Returns whether the square in the given {@code position} is empty.
     *
     * @param position The target position.
     * @return {@code true} if the square is empty, {@code false} otherwise.
     */
    public boolean isSquareEmpty(Position position) {
        return getMarkerAt(position) == EMPTY_SQUARE;
    }

    /**
     * Returns the marker placed at the given position.
     *
     * @param position The target position.
     * @return The marker set at the given position, or an {@code EMPTY} marker
     *         if no marker has been placed there.
     */
    public char getMarkerAt(Position position) {
        // NOTE: ArrayIndexOutOfBoundsExceptions may happen - if so, it's a bug!
        return this.squares[position.rowIndex()][position.columnIndex()];
    }

    private boolean isPositionOutOfBounds(Position position) {
        return position.x() <= 0
                || position.x() > this.size
                || position.y() <= 0
                || position.y() > this.size;
    }

    private void setMarkerAt(char marker, Position position) {
        // NOTE: ArrayIndexOutOfBoundsExceptions may happen - if so, it's a bug!
        this.squares[position.rowIndex()][position.columnIndex()] = marker;
    }
}
