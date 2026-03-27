package it.ktech.training;

import it.ktech.training.exceptions.IllegalMoveException;
import it.ktech.training.exceptions.SquareOccupiedException;
import it.ktech.training.exceptions.GameOverException;

/**
 * The Gomoku game runner.
 *
 * @author Diego Paolicelli
 */
public class Gomoku {
    private Board board;

    private static final char HUMAN_MARKER = '1';
    private static final char CPU_MARKER = '2';

    public Gomoku() {
        this.board = Board.create();
    }

    /**
     * Plays the game until completion.
     */
    public void play() {
        while (true) {
            Position lastHumanMove;
            try {
                lastHumanMove = humanMove();
            } catch (IllegalMoveException e) {
                Terminal.println("Illegal move, try again...");
                continue;
            } catch (SquareOccupiedException e) {
                Terminal.println("Square occupied, try again...");
                continue;
            } catch (GameOverException e) {
                break;
            }

            // the CPU player will always make a valid move
            cpuMove(lastHumanMove);

            this.board.print();
        }

        Terminal.println("\n");
        Terminal.println("Thanks for the game!!");
    }

    private Position humanMove()
            throws IllegalMoveException, SquareOccupiedException, GameOverException {
        String coordinates = Terminal.scan("Your play (i,j)");
        Position move = Position.fromString(coordinates);
        board.placeMarker(HUMAN_MARKER, move);
        return move;
    }

    private void cpuMove(Position lastHumanMove) {
        // make an "intelligent" move: try to occupy a square around the human
        // player's last move
        var candidates = board.getSquaresAround(lastHumanMove)
                .filter(position -> board.getMarkerAt(position) == HUMAN_MARKER)
                .toList();
        for (Position candidate : candidates) {
            // pick a square where the player already put a marker and see if
            // we can place the CPU's marker opposite of that, so we can block
            // a potential line
            Position opposite = candidate.oppositeOf(lastHumanMove);
            try {
                board.placeMarker(CPU_MARKER, opposite);
                return;
            } catch (Exception _) {
                // we can't place the marker here - try the next position
                continue;
            }
        }

        // if we're here, we can't be "smart"
        board.placeMarkerRandomly(CPU_MARKER);
    }
}
