package it.ktech.training;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.ktech.training.exceptions.IllegalMoveException;

/**
 * The coordinates of a move on the board.
 *
 * In the original BASIC game, contrary to plane geometry (and, arguably, more
 * intuitively), the X coordinate marks the <b>row</b> while the Y coordinate
 * marks the <b>column</b>.
 *
 * @author Diego Paolicelli
 */
public record Position(int x, int y) {
    /**
     * Assuming a 2D array representation of a board, return the index of the
     * row corresponding to the X coordinate.
     *
     * @return The row index.
     */
    public int rowIndex() {
        return this.x - 1;
    }

    /**
     * Assuming a 2D array representation of a board, return the index of the
     * column corresponding to the Y coordinate.
     *
     * @return The column index.
     */
    public int columnIndex() {
        return this.y - 1;
    }

    /**
     * Attempts to parse a string into a pair of coordinates and returns them
     * in a new {@code Position} if successful.
     *
     * @param coordinates The string containing the coordinates.
     * @return The position corresponding to the given coordinates.
     * @throws IllegalMoveException Coordinates parsing ended in failure.
     */
    public static Position fromString(String coordinates) throws IllegalMoveException {
        var splitCoordinates = coordinates.split(",", 2);
        try {
            int x = Integer.parseInt(splitCoordinates[0]);
            int y = Integer.parseInt(splitCoordinates[1]);
            return new Position(x, y);
        } catch (Exception _) {
            throw new IllegalMoveException();
        }
    }

    /**
     * Creates a new Position with pseudo-random coordinates.
     *
     * Coordinates range from 1 to the given {@code size}.
     *
     * @param bound The upper bound for coordinates generation.
     */
    public static Position createRandom(int bound) {
        var rng = new Random();
        return new Position(rng.nextInt(bound) + 1, rng.nextInt(bound) + 1);
    }

    /**
     * Returns a lazy stream of {@code Position}s adjacent to this
     * {@code Position} horizontally, vertically and along diagonals.
     *
     * Assuming "North" refers to the direction above the current
     * {@code Position} and "West" refers to the direction to the left hand
     * side of the current {@code Position}, the resulting {@code Position}s are
     * yielded in the following order:
     *
     * <ul>
     * <li>Northwest</li>
     * <li>North</li>
     * <li>Northeast</li>
     * <li>West</li>
     * <li>East</li>
     * <li>Southwest</li>
     * <li>South</li>
     * <li>Southeast</li>
     * </ul>
     *
     * @return A stream of {@code Position}s.
     */
    public Stream<Position> getAdjacentPositions() {
        // NOTE: sometime laziness is complicated...
        return IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap(deltaX -> IntStream.rangeClosed(-1, 1)
                        .boxed()
                        .filter(deltaY -> deltaX != 0 || deltaY != 0)
                        .map(deltaY -> new Position(this.x + deltaX, this.y + deltaY)));
    }

    /**
     * Returns the {@code Position} symmetrically opposite to {@code other},
     * using this {@code Position} as the center of symmetry.
     *
     * @param other A {@code Position}
     * @return The {@code Position} opposite of this one.
     */
    public Position oppositeOf(Position other) {
        int deltaX = other.x() - this.x;
        int deltaY = other.y() - this.y;
        return new Position(this.x - deltaX, this.y - deltaY);
    }
}
