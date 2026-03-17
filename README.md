# Java and Spring Boot training session 1

This repo contains the slides (in Markdown form) and the assignment for the first Java and Spring Boot training session.

[Presenterm](https://mfontanini.github.io/presenterm/) is required to look at the slides as a presentation in your terminal.

## Topics covered in the slides

* Intro to Java
* The current state of the Java ecosystem
* Setting up a project
* Assignment: `GOMOKO`

## The assignment

**Port the `GOMOKO` game from GW-BASIC to Java.**

The game first appeared in [David H. Ahl's "BASIC Computer Games" book](https://en.wikipedia.org/wiki/BASIC_Computer_Games), published in 1973.

* [Source code](https://raw.githubusercontent.com/coding-horror/basic-computer-games/refs/heads/main/40_Gomoko/gomoko.bas) (also included in this repo's root).

* Try it out on [Jeff Atwood (Coding Horror)'s dedicated GitHub page](https://coding-horror.github.io/basic-computer-games/40_Gomoko/javascript/gomoko.html).

From the readme on Coding Horror's repo:

> GOMOKO or GOMOKU is a traditional game of the Orient. It is played by two people on a board of intersecting lines (19 left-to-right lines, 19 top-to-bottom lines, 361 intersections in all). Players take turns. During his turn, a player may cover one intersection with a marker; (one player uses white markers; the other player uses black markers). The object of the game is to get five adjacent markers in a row, horizontally, vertically or along either diagonal.
>
> Unfortunately, this program does not make the computer a very good player. It does not know when you are about to win or even who has won. But some of its moves may surprise you.

### You MUST...

* **Implement all the features of the GW-BASIC program.** The final program must look and work exactly like its BASIC counterpart.
* **Initialize and structure the game properly.** Initialize your project with Gradle, use classes wisely.
* **Document your classes' public API.**

### You MUST NOT...

* **Make a straight, line-by-line port.** You are not programming in BASIC - Java is way more powerful, so use that power well. Give the game the structure and design it deserves :^)
* **Use AI for coding, not even for completions.** You are learning, and learning is done best without AI assistance. ***Turn Copilot off!***
* **CHEAT** by looking at the existing port on GitHub. I **will** notice.

### You CAN...

* **Add features (if you have time to spare).** For instance, you can add the check for winning conditions that the original BASIC program lacks, or restrictions on the first moves to make it fairer to the second player - look up _Gomoku_ on Wikipedia.
* **Use AI to understand the BASIC program.** You're learning Java, not BASIC :^)
* **Write tests.** We'll see JUnit later, but you are welcome to try it :^)

### You SHOULD...

* **Ask for help if you're stuck.**
* ***HAVE FUN.***
    * ... if you can.
