package edu.moravian;

import com.github.fppt.jedismock.RedisServer;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BotResponderRedisTest {

    private static RedisServer server;
    private static Jedis jedis;

    private BotResponder bot;
    private Player player;

    @BeforeAll
    static void setupRedisServer() throws Exception {
        server = RedisServer.newRedisServer();
        server.start();
    }

    @AfterAll
    static void stopRedisServer() throws IOException {
        server.stop();
    }

    @BeforeEach
    void setUp() {
        jedis = new Jedis(server.getHost(), server.getBindPort());
        jedis.flushAll();

        BotResponder.board = null;
        BotResponder.state = BotState.NO_GAME;
        BotResponses.players.clear();

        BotResponder.store = new RedisStorage(server.getHost(), server.getBindPort());
        bot = new BotResponder();
        player = new Player("Alice");
    }

    @AfterEach
    void tearDown() {
        jedis.close();
    }

    @Test
    void testJoinAndStartGame() {
        String joinResp = bot.respond(player, "j.join", "server1", "channel1");
        assertEquals(BotResponses.joinGame(), joinResp);
        assertTrue(BotResponses.players.contains(player));
        String startResp = bot.respond(player, "j.start", "server1", "channel1");
        assertNotNull(startResp);
        assertEquals(BotState.IN_PROGRESS, BotResponder.state);
        assertNotNull(BotResponder.board);
    }


    @Test
    void testPickAndAnswerQuestionCorrectly() {
        bot.respond(player, "j.join", "", "");
        bot.respond(player, "j.start", "", "");

        String pick = pickFirstAvailableQuestion();
        assertNotNull(pick);
        assertTrue(bot.activeQuestion);

        String answer = answerCurrentQuestionCorrectly();
        assertTrue(answer.contains(player.getName()));
        assertTrue(player.getScore() > 0);
        assertFalse(player.hasAnswered());
    }

    @Test
    void testIncorrectAnswerPenalizesPlayer() {
        bot.respond(player, "j.join", "", "");
        bot.respond(player, "j.start", "", "");

        pickFirstAvailableQuestion();
        String resp = bot.respond(player, "what is definitely wrong", "", "");

        assertTrue(resp.contains(player.getName()));
        assertTrue(player.getScore() < 0);
        assertTrue(player.hasAnswered());
    }

    @Test
    void testHelpCommand() {
        assertEquals(BotResponses.help(), bot.respond(player, "j.help", "", ""));
    }

    @Test
    void testQuitBeforeGameStarted() {
        bot.respond(player, "j.join", "", "");
        assertEquals(BotResponses.quitBeforeStarted(), bot.respond(player, "j.quit", "", ""));
        assertEquals(BotState.NO_GAME, BotResponder.state);
    }

    @Test
    void testMultipleQuestionFlow() {
        bot.respond(player, "j.join", "", "");
        bot.respond(player, "j.start", "", "");

        int initialScore = player.getScore();

        for (int i = 0; i < 5; i++) {
            pickFirstAvailableQuestion();
            answerCurrentQuestionCorrectly();
        }

        assertTrue(player.getScore() > initialScore);
    }

    private String pickFirstAvailableQuestion() {
        for (char c = 'a'; c <= 'd'; c++) {
            for (int value = 200; value <= 800; value += 200) {
                String resp = bot.respond(player, c + " for " + value, "", "");
                if (resp != null && resp.toLowerCase().contains("question")) {
                    return resp;
                }
            }
        }
        fail("No available questions on board");
        return null;
    }

    private String answerCurrentQuestionCorrectly() {
        return bot.respond(player,
                "what is " + BotResponder.answer,
                "", "");
    }

}