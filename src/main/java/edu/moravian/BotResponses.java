package edu.moravian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static edu.moravian.BotResponder.board;

public class BotResponses {
    static List<Player> players = new ArrayList<>();

    public static String alreadyJoined(String username) {
        return username + ", you have already joined the game!";
    }

    public static String joinGame() {
        List<String> names = players.stream().map(Player::getName).toList();
        return "Type ``j.start`` to start the game!\n Players: " + String.join(", ", names);
    }

    public static String startGame(String server, String channel) {
        String message = "From the " + channel + " channel at " + server + ", this is the Jeopardy!\nLet's meet today's contestants.";
        List<String> location = new ArrayList<>();
        Collections.addAll(location, "Apocalypse Peaks, Antarctica", "Bacon, Indiana, USA", " Bat Cave, North Carolina, USA", "Batman, Turkey", "Bear, Delaware, USA", "Beaver Dam, New Brunswick, Canada", "Bee Lick, Kentucky, USA", "Bigfoot, Texas, USA", "Big Rock Candy Mountain, Vermont, USA", "Bird-in-Hand, Pennsylvania, USA", "Boring, Oregon, USA", "Brainy Borough, New Jersey, USA", "Bugscuffle, Tennessee, USA", "Burning Well, Pennsylvania, USA", "Buttermilk, Kansas, USA", "Celebration, Florida, USA", "Cheesequake, New Jersey, USA", "Chicken, Alaska, USA", "Christmas Pie, Surrey, England", "Dead Chinaman, Gulf, Papua New Guinea", "Dinosaur, Colorado, USA", "Disappointment Island, Auckland Islands, New Zealand", "Dog Walk, Kentucky, USA", "Eek, Alaska, USA", "Friendly, West Virginia, USA", "George, Washington, USA", "Gogogogo, Madagascar", "Goose Pimple Junction, Virginia, USA", "Greasy, Oklahoma, USA", "Happy Adventure, Newfoundland and Labrador, Canada", "Hungry Horse, Montana, USA", "Left Hand, West Virginia, USA", "Looneyville, Texas, USA", "Lost, Scotland", "Magic City, Idaho, USA", "Moosejaw, Saskatchewan, Canada", "Normal, Illinois, USA", "North Pole, Alaska, USA", "Nowhere Else, Tasmania, Australia", "Okay, Oklahoma, USA", "Oniontown, Pennsylvania, USA", "Peculiar, Missouri, USA", "Pie Town, New Mexico, USA", "Plain City, Utah, USA", "Rabbit Shuffle, North Carolina, USA", "Sandwich, Massachusetts, USA", "Smileyberg, Kansas, USA", "Squirrel Hill, Pennsylvania, USA", "Sugar City, Idaho, USA", "Tea, South Dakota, USA", "Truth Or Consequences, New Mexico, USA", "Two Egg, Florida, USA", "Useless Loop, Western Australia, Australia", "Utopia, Florida, USA", "Westward Ho!, United Kingdom", "Why, Arizona, USA", "Whynot, North Carolina, USA");
        Random random = null;
        for (Player p : players) {
            random = new Random();
            int randomIndex = random.nextInt(location.size());
            String randomLocation = location.get(randomIndex);
            message += "\nFrom " + randomLocation + ", ***" + p.getName() + "***!";
        }
        assert random != null;
        message += "\nAnd now, here is the host of Jeopardy, kubisekBot!\n\nGood luck players! Your categories are:\n " + board.toString() + "\n\nAnyone can pick a category and a value to start the game! (type ``a for 200``, for example)";
        return message;
    }

    public static String hasNotJoined(String username) {
        return username + ", you can only start the game if you join it first!";
    }

    public static String pickQuestion(String categoryName, int value, String question) {
        return "**" + categoryName + " for $" + value + "**:\n" + question + "\nType: ``what is <your answer>``";
    }

    public static String invalidQuestion() {
        return "This is an invalid question! Please check and make sure the category letter and/or question value is valid!";
    }

    public static String shouldntHappen() {
        return "this shouldn't happen";
    }

    public static String correctAnswer(String name, int value) {
        String playerStats = "\n\n";
        for  (Player p : players) {
            playerStats += p.toString() + "\n";
        }
        return name + ", you are correct! " + value + " points have been added to your score!\n\n Anyone may now pick a new question\n\n" + board.toString() + playerStats ;
    }

    public static String incorrectAnswer(String name, int value) {
        return name + ", you are incorrect! " + value + " points have been removed from your score!";
    }

    public static String alreadyAnswered() {
        return "This question has already been answered! Please pick a different question.";
    }

    public static String endGame(List<Player> players) {
        List<Player> ranked = BotResponder.getRankedPlayers(players);
        StringBuilder sb = new StringBuilder();
        if(board.allCategoriesComplete(board.chosenCategories)){
            sb.append("All of the questions have been answered and the game is over! Here are the scores:\n\n");
        }else{
            sb.append("You have ended the game early! Here are the scores:\n\n");
        }
        int rank = 1;
        for (Player p : ranked) {
            sb.append(rank).append(". ").append(p.getName()).append(" — ").append(p.getScore()).append(" points\n");
            rank++;
        }
        return sb.toString();
    }

    public static String help() {
        return "- `j.help` :: displays this help message of all possible commands!\n- `j.join` :: allows you to join a game before it starts\n- `j.start` :: starts the game with the players that have joined\n- `__ for __` :: format for choosing a question, with the first blank for the category letter, and the second blank being for the question value\n- `what is ___` :: format to answer a question\n- `j.quit` :: ends the game before it is officially over";
    }

    public static String canNotJoinGameInProgress(String username) {
        return "Sorry " + username + ", the game has already started, please join before the game has started!";
    }

    public static String needPlayerToStart() {
        return "The game can not start until at least one person has joined! Join the game with `j.join`";
    }

    public static String canNotQuitIfNoGame() {
        return "The game can not be quit if it has not started yet!";
    }

    public static String quitBeforeStarted() {
        return "The game has been quit before it started. Please rejoin the game if you want to play. Join the game with `j.join`";
    }

    public static String noGameInProgress() {
        return "Sorry! This action can not be completed as no game has started yet!";
    }

    public static String questionAlreadyInPlay() {
        return "Sorry! The question is already in play! Please answer it before you pick a new question.";
    }

    public static String playerAlreadyAnswered(String username) {
        return "Sorry " + username + ", you already attempted this question!";
    }

    public static String noCorrectAnswer() {
        return "No one got the answer right, the answer is " + BotResponder.answer + "!\nAnyone can pick a new question";
    }

    public static String canNotStartGameInProgress(String name) {
        return "Sorry " + name + ", the game has already started, you can not start a new one!";
    }
}
