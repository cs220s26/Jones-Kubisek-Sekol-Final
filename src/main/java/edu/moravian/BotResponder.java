package edu.moravian;
import java.util.ArrayList;
import java.util.List;
import static edu.moravian.BotResponses.players;

public class BotResponder {
    static Board board;
    static String answer;
    static int value;
    static int currentCategoryIndex;
    static int currentQuestionIndex;
    List<Player> endPlayers;
    static BotState state = BotState.NO_GAME;
    boolean activeQuestion = false;
    static RedisStorage store = new RedisStorage("localhost", 6379);

    public BotResponder() {
        board = new Board();
        state = store.loadState();
        board = store.loadBoard();
        players.clear();
        players.addAll(store.loadPlayers());
        RedisStorage.Active a = store.loadActive();
        if (a != null) {
            currentCategoryIndex = a.category;
            currentQuestionIndex = a.question;
            answer = a.answer;
            value = a.value;
            activeQuestion = a.active;
        }
    }


    public String respond(Player player, String message, String server, String channel) {
        if (message.equalsIgnoreCase("j.join")) {
            return respondJoin(player);
        }else if(message.equalsIgnoreCase("j.start")){
            return respondStart(player, server, channel);
        }else if(message.equalsIgnoreCase("j.quit")){
            return respondQuit(player);
        }else if(message.equalsIgnoreCase("j.help")){
            return respondHelp();
        }else if(message.contains(" for ")) {
            return respondPickQuestion(message, player);
        }else if(message.toLowerCase().startsWith("what is") || message.toLowerCase().startsWith("when is") || message.toLowerCase().startsWith("who is") || message.toLowerCase().startsWith("where is") || message.toLowerCase().startsWith("what are") || message.toLowerCase().startsWith("when are") || message.toLowerCase().startsWith("who are") || message.toLowerCase().startsWith("where are")){
            return respondAnswer(message, player);
        }else{
            return "";
        }
    }

    //checks if a player can join depending on the BotState and if the user has already joined or not.
    private String respondJoin(Player player) {
        if (state != BotState.IN_PROGRESS) {
            if(players.contains(player)){
                return BotResponses.alreadyJoined(player.getName());
            }else{
                state = BotState.STARTING;
                players.add(player);
                store.savePlayers(players);
                store.saveState(state);
                return BotResponses.joinGame();
            }
        }else{
            return BotResponses.canNotJoinGameInProgress(player.getName());
        }
    }

    //starts the game depending on the BotState and if the player has joined
    private String respondStart(Player player, String server, String channel) {
        if (!players.contains(player)) {
            return BotResponses.canNotJoinGameInProgress(player.getName());
        }
        if (state == BotState.STARTING) {
            if (players.contains(player)) {
                board = new Board();
                state = BotState.IN_PROGRESS;
                store.saveBoard(board);
                store.savePlayers(players);
                store.saveState(state);
                return BotResponses.startGame(server, channel);
            } else {
                return BotResponses.hasNotJoined(player.getName());
            }
        } else if(state == BotState.IN_PROGRESS){
            return BotResponses.canNotStartGameInProgress(player.getName());
        }else{
            return BotResponses.needPlayerToStart();
        }
    }

    //quits the game depending on BotState
    private String respondQuit(Player player) {
        if(!players.contains(player)){
            return BotResponses.canNotJoinGameInProgress(player.getName());
        }
        if(state == BotState.NO_GAME){
            return BotResponses.canNotQuitIfNoGame();
        }else if(state == BotState.STARTING){
            resetGame();
            state = BotState.NO_GAME;
            return BotResponses.quitBeforeStarted();
        }else{
            endPlayers = players;
            String end = BotResponses.endGame(getRankedPlayers(endPlayers));
            resetGame();
            return end;
        }
    }

    //displays help command
    private String respondHelp() {
        return BotResponses.help();
    }

    //picks question based on BotState and if question has already been answered. reads beginning and end of message to determine category and question
    private String respondPickQuestion(String message, Player player) {
        if(!players.contains(player)){
            return BotResponses.canNotJoinGameInProgress(player.getName());
        }
        if(activeQuestion){
            return BotResponses.questionAlreadyInPlay();
        }
        if(state != BotState.IN_PROGRESS){
            return BotResponses.noGameInProgress();
        }else{
            if (board == null) {
                return BotResponses.shouldntHappen();
            }
            if (message.length() < 2)
                return BotResponses.invalidQuestion();
            char letter = Character.toLowerCase(message.charAt(0));
            int categoryIndex = letter - 'a';
            if (categoryIndex < 0 || categoryIndex > 3)
                return BotResponses.invalidQuestion();
            String[] values = {"200", "400", "600", "800"};
            int questionIndex = -1;
            for (int i = 0; i < values.length; i++) {
                if (message.endsWith(values[i])) {
                    questionIndex = i;
                    break;
                }
            }
            if (questionIndex == -1)
                return BotResponses.invalidQuestion();
            Category cat = board.getCategory(categoryIndex);
            if (cat.isAnswered(questionIndex))
                return BotResponses.alreadyAnswered();
            currentCategoryIndex = categoryIndex;
            currentQuestionIndex = questionIndex;
            System.out.println(questionIndex);
            answer = cat.getAnswer(questionIndex).toLowerCase();
            value  = cat.getValue(questionIndex);
            activeQuestion = true;
            store.saveActiveQuestion(currentCategoryIndex, currentQuestionIndex, answer, value, true);
            store.saveState(state);
            return BotResponses.pickQuestion(cat.getCategoryName(), value, cat.getQuestion(questionIndex));
        }
    }

    //checks answer based on BotState and uses end of message to see if answer is equal to the question's answer
    private String respondAnswer(String message, Player player) {
        if(!players.contains(player)){
            return BotResponses.canNotJoinGameInProgress(player.getName());
        }
        if(player.hasAnswered()){
            return BotResponses.playerAlreadyAnswered(player.getName());
        }
        if(haveAllPlayersAnswered(players)){
            return BotResponses.noCorrectAnswer();
        }
        if(state != BotState.IN_PROGRESS){
            return BotResponses.noGameInProgress();
        }else{
            Player actualPlayer = findPlayer(player);
            String guess = message.toLowerCase().trim();
            String correct = answer.toLowerCase().trim();
            player.setHasAnswered(true);
            if (guess.contains(correct)) {
                for (Player p : players) {
                    p.setHasAnswered(false);
                }
                actualPlayer.addPoints(value);
                board.markQuestionAnswered(currentCategoryIndex, currentQuestionIndex);
                store.saveBoard(board);
                store.savePlayers(players);
                store.saveActiveQuestion(currentCategoryIndex, currentQuestionIndex, answer, value, false);
                store.saveState(state);
                if (board.allCategoriesComplete(board.chosenCategories)) {
                    endPlayers = players;
                    store.clearAll();
                    String end = BotResponses.endGame(getRankedPlayers(endPlayers));
                    resetGame();
                    return end;
                } else {
                    return BotResponses.correctAnswer(actualPlayer.getName(), value);
                }
            } else {
                actualPlayer.subtractPoints(value);
                store.savePlayers(players);
                store.saveActiveQuestion(currentCategoryIndex, currentQuestionIndex, answer, value, true);
                return BotResponses.incorrectAnswer(actualPlayer.getName(), value);
            }
        }
    }

    private Player findPlayer(Player p) {
        for (Player stored : players) {
            if (stored.equals(p)) {
                return stored;
            }
        }
        return p;
    }

    public static List<Player> getRankedPlayers(List<Player> players) {
        List<Player> ranked = new ArrayList<>(players);
        ranked.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return ranked;
    }

    private void resetGame() {
        board = null;
        answer = null;
        value = 0;
        currentCategoryIndex = -1;
        currentQuestionIndex = -1;
        players.clear();
        state = BotState.NO_GAME;
        store.clearAll();
    }

    public boolean haveAllPlayersAnswered(List<Player> players) {
        for (Player p : players) {
            if (!p.hasAnswered()) {
                return false;
            }
        }
        return true;
    }
}