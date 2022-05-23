package com.mars.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Puzzle {
    private static List<Puzzle> puzzleList;
    private String question;
    private List<String> choices;
    private String correctAnswer;
    private static Puzzle instance = new Puzzle();

    private Puzzle() {}

    public static Puzzle getInstance() {
        instance.setPuzzleList();
        return instance;
    }

    // getters and setters
    public void setPuzzleList() {
        this.puzzleList = loadPuzzleFromJson();
    }

    public static List<Puzzle> getPuzzleList() {
        return puzzleList;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    // end getters and setters

    /**
     * helper method to display question
     */
    public String askQuestion() {
        return getQuestion();
    }

    public Vector<String> getAnswers() {
        Vector<String> answers = new Vector<>();
        for (String ans : getChoices()) {
            answers.add(ans);
        }
        return answers;
    }

    /**
     * helper method to check is answer is correct
     * @param inputAnswer
     * @return boolean
     */
    public boolean checkAnswer(String inputAnswer) {
        boolean isCorrect = false;
        if (this.getCorrectAnswer().equals(inputAnswer)) {
            isCorrect = true;
        }
        return isCorrect;
    }

    /**
     * helper method to create List of Puzzle from json File
     * @return List<Puzzle>
     */
    public static List<Puzzle> loadPuzzleFromJson() {
        List<Puzzle> puzzleList= new ArrayList<>();

        Map<String, Object> map = JsonParser.parseJson("data/json/questions.json");
        List<Map<String, Object>> puzzles = (List)map.get("results");

        for (Map<String, Object> puzzleMap: puzzles) {
            Puzzle puzzle = new Puzzle();
            String ques = puzzleMap.get("question").toString();
            puzzle.setQuestion(ques);
            List<String> choices = (List<String>)puzzleMap.get("incorrect_answers");
            puzzle.setChoices(choices);
            String correct = puzzleMap.get("correct_answer").toString();
            puzzle.setCorrectAnswer(correct);
            puzzleList.add(puzzle);
        }
        return puzzleList;
    }
}
