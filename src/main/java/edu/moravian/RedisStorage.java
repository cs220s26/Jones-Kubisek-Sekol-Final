package edu.moravian;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisStorage {

    private final Jedis redis;
    public RedisStorage(String host, int port) {
        this.redis = new Jedis(host, port);
    }

    public void saveState(BotState state) {
        redis.hset("jeopardy:state", "state", state.name());
    }

    public void saveActiveQuestion(int cat, int q, String answer, int value, boolean active) {
        redis.hset("jeopardy:active", Map.of("category", String.valueOf(cat), "question", String.valueOf(q), "answer", answer == null ? "" : answer, "value", String.valueOf(value), "active", String.valueOf(active)));

    }

    public void savePlayers(List<Player> players) {
        Set<String> keys = redis.keys("jeopardy:players:*");
        if (!keys.isEmpty()) {
            redis.del(keys.toArray(new String[0]));
        }
        for (Player p : players) {
            redis.hset("jeopardy:players:" + p.getName(), Map.of("score", String.valueOf(p.getScore()), "answered", String.valueOf(p.hasAnswered())));
        }
    }

    public void saveBoard(Board board) {
        if (board == null) {
            return;
        }
        Set<String> keys = redis.keys("jeopardy:board:*");
        if (!keys.isEmpty()) {
            redis.del(keys.toArray(new String[0]));
        }
        for (int c = 0; c < board.chosenCategories.size(); c++) {
            Category cat = board.getCategory(c);
            redis.hset("jeopardy:board:category:" + c, "name", cat.getCategoryName());
            for (int q = 0; q < 4; q++) {
                redis.hset("jeopardy:board:category:" + c + ":q:" + q, Map.of("question", cat.getQuestion(q), "answer", cat.getAnswer(q), "value", String.valueOf(cat.getValue(q)), "answered", String.valueOf(cat.isAnswered(q))));
            }
        }
    }

    public BotState loadState() {
        String s = redis.hget("jeopardy:state", "state");
        return s == null ? BotState.NO_GAME : BotState.valueOf(s);
    }

    public Active loadActive() {
        Map<String, String> map = redis.hgetAll("jeopardy:active");
        if (map.isEmpty()) {
            return null;
        }
        return new Active(Integer.parseInt(map.get("category")), Integer.parseInt(map.get("question")), map.get("answer"), Integer.parseInt(map.get("value")), Boolean.parseBoolean(map.get("active")));
    }

    public List<Player> loadPlayers() {
        Set<String> keys = redis.keys("jeopardy:players:*");
        List<Player> players = new ArrayList<>();
        for (String k : keys) {
            Map<String, String> data = redis.hgetAll(k);
            String name = k.substring("jeopardy:players:".length());
            Player p = new Player(name);
            p.setScore(Integer.parseInt(data.get("score")));
            p.setHasAnswered(Boolean.parseBoolean(data.get("answered")));
            players.add(p);
        }
        return players;
    }

    public Board loadBoard() {
        Set<String> catKeys = redis.keys("jeopardy:board:category:*");
        if (catKeys.isEmpty()) {
            return null;
        }
        List<Category> categories = new ArrayList<>();
        for (int c = 0; ; c++) {
            String catKey = "jeopardy:board:category:" + c;
            if (!redis.exists(catKey)) {
                break;
            }
            Map<String, String> catData = redis.hgetAll(catKey);
            String catName = catData.getOrDefault("name", "Category " + c);
            Category cat = new Category(catName);
            for (int q = 0; q < 4; q++) {
                String qKey = catKey + ":q:" + q;
                if (!redis.exists(qKey)) {
                    continue;
                }
                Map<String, String> qData = redis.hgetAll(qKey);
                String questionText = qData.getOrDefault("question", "No question available");
                String answerText   = qData.getOrDefault("answer", "");
                int value = 0;
                try {
                    value = Integer.parseInt(qData.getOrDefault("value", "0"));
                } catch (NumberFormatException e) {
                    value = 0;
                }
                cat.addQuestionSet(questionText, value, answerText);
                boolean answered = Boolean.parseBoolean(qData.getOrDefault("answered", "false"));
                if (answered) {
                    cat.markAnswered(q);
                }
            }
            categories.add(cat);
        }
        return new Board(categories);
    }

    public void clearAll() {
        Set<String> all = redis.keys("jeopardy:*");
        if (!all.isEmpty()) {
            redis.del(all.toArray(new String[0]));
        }
    }

    public static class Active {
        public int category;
        public int question;
        public String answer;
        public int value;
        public boolean active;
        public Active(int c, int q, String a, int v, boolean act) {
            category = c;
            question = q;
            answer = a;
            value = v;
            active = act;
        }
    }
}
