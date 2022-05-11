package com.mars.locations;

import com.mars.objects.Inventory;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Puzzles {
    private static Scanner input = new Scanner(System.in);

    // TODO return statement
    public static boolean greenHousePuzzle() {
        return true;
    }

    // TODO return statement
    public static boolean hydroPuzzle() {
        return true;
    }

    public static boolean environmentalPuzzle() {
        return true;
    }

    public static boolean reactorPuzzle() {
        return true;
    }

    public static boolean solarPuzzle() {
        while (true) {
            System.out.print("Would you like to attempt to align the panels [y/n]? ");
            String option = input.nextLine();
            if (option.equalsIgnoreCase("y")) {
                // use gps unit and batteries to do something
                // answer random trivia question
            } else {
                System.out.println("OK, then...");
                return false;
            }
            // you notice that the array is not generating any power, would you like to take a look?
            if (option.equalsIgnoreCase("y")) {
                // use cable to fix array
                // answer random trivia question
            }
        }
    }
}

        /*
        Random rand = new Random();
                    int randInt = rand.nextInt(10);
                    if (randInt >= 5) {
                        System.out.println("Nicely done! You've aligned the panels");
                        isSolved = true;
                        break;
                    } else {
                        System.out.println("I'm sorry, you've failed. Please keep trying!");
                    }
         */