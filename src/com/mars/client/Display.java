package com.mars.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Display {
    private static String path;

    // getters and setters
    public static void setPath(String path) {
        Display.path = path;
    }

    public static String getPath() {
        return path;
    }
    // end getters and setters

    /**
     * method to pass String of text file contents
     * @param name
     * @return String
     */
    public static String showTextFile(String name) {
        return findFile(name);
    }

    /**
     * helper method to retrieve correct file
     * @param name
     * @return String
     */
    private static String findFile(String name) {
        switch (name) {
            case "Help":
                setPath("data/text/help.txt");
                break;
            case "Intro":
                setPath("data/text/game_info.txt");
                break;
            case "Win":
                setPath("data/text/win.txt");
                break;
            case "Solar Array":
                setPath("data/text/solarIntro.txt");
                break;
            case "Reactor":
                setPath("data/text/reactorIntro.txt");
                break;
            case "Environmental Control Room":
                setPath("data/text/environmentalIntro.txt");
                break;
            case "Green House":
                setPath("data/text/greenHouseIntro.txt");
                break;
            case "Hydro Control Room":
                setPath("data/text/hydroIntro.txt");
                break;
            default:
                break;
        }
        return getPathReturn(getPath());
    }

    /**
     * helper method to retrieve Files
     * @param path
     * @return String
     */
    private static String getPathReturn(String path) {
        StringBuilder sb = new StringBuilder();
        if (Files.exists(Path.of(path))) {
            try {
                List<String> lines = Files.readAllLines(Path.of(path));
                for (String line : lines) {
                    sb.append(line);
                }
            } catch (IOException e) {
                return "There was trouble reading this data file.";
            }
        }
        return sb.toString();
    }
}
