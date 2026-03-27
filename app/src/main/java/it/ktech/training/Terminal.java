package it.ktech.training;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Retro-style terminal I/O.
 *
 * @author Diego Paolicelli
 */
public class Terminal {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * The width of a "retro" terminal.
     */
    private static final int WIDTH = 72;

    /**
     * Prints the given {@code message} in upper case.
     *
     * @param message The string to print.
     */
    public static void print(String message) {
        System.out.print(message.toUpperCase());
    }

    /**
     * Prints the given {@code message} in upper case and terminates the line.
     *
     * @param message The string to print.
     */
    public static void println(String message) {
        System.out.println(message.toUpperCase());
    }

    /**
     * Prints the given {@code message} in upper case, padded so that it looks
     * like it is displayed at the center of a 72 characters wide "retro"
     * display, then terminates the line.
     *
     * If the length of the message exceeds the width of the terminal, then the
     * message is printed in a single line, without padding or wrapping.
     *
     * This method does not treat newlines, tabs and multi-codepoint Unicode
     * symbols as special cases - include them in the message at your own risk.
     *
     * @param message The string to print.
     */
    public static void printCentered(String message) {
        // padding width can be negative, clamp to 0 so it's not illegal
        int computedPaddingWidth = (WIDTH - message.length()) / 2;
        int paddingWidth = Math.max(computedPaddingWidth, 0);

        Terminal.println(" ".repeat(paddingWidth) + message);
    }

    /**
     * Prints a prompt and waits for user input, then returns the latter as a
     * string.
     *
     * @param prompt The message to print, followed by {@code "? "}.
     */
    public static String scan(String prompt) {
        System.out.print(prompt.toUpperCase() + "? ");
        return scanner.nextLine();
    }

    /**
     * Prints a prompt and waits for user input, then attempts to parse the
     * input as an integer and returns it if successful.
     *
     * @param prompt The message to print, followed by {@code "? "}.
     */
    public static int scanInt(String prompt) throws InputMismatchException {
        System.out.print(prompt.toUpperCase() + "? ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            // NOTE: any other exception must be treated as a bug
            throw e;
        } finally {
            scanner.nextLine();
        }
    }
}
