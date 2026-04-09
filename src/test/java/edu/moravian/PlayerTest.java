package edu.moravian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Alice");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Alice", player.getName());
        assertEquals(0, player.getScore());
        assertFalse(player.hasAnswered());
    }

    @Test
    void testSetName() {
        player.setName("Bob");
        assertEquals("Bob", player.getName());
    }

    @Test
    void testSetScore() {
        player.setScore(50);
        assertEquals(50, player.getScore());
    }

    @Test
    void testAddPoints() {
        player.addPoints(100);
        assertEquals(100, player.getScore());
        player.addPoints(50);
        assertEquals(150, player.getScore());
    }

    @Test
    void testSubtractPoints() {
        player.setScore(200);
        player.subtractPoints(50);
        assertEquals(150, player.getScore());
        player.subtractPoints(200);
        assertEquals(-50, player.getScore());
    }

    @Test
    void testHasAnswered() {
        assertFalse(player.hasAnswered());
        player.setHasAnswered(true);
        assertTrue(player.hasAnswered());
        player.setHasAnswered(false);
        assertFalse(player.hasAnswered());
    }

    @Test
    void testToString() {
        player.setScore(300);
        assertEquals("Alice: 300 points", player.toString());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(player, player);
    }

    @Test
    void testEqualsDifferentObjectSameName() {
        Player other = new Player("alice"); // different case
        assertEquals(player, other);
    }

    @Test
    void testEqualsDifferentObjectDifferentName() {
        Player other = new Player("Bob");
        assertNotEquals(player, other);
    }

    @Test
    void testEqualsNullAndOtherClass() {
        assertNotEquals(null, player);
        assertNotEquals("Alice", player);
    }

    @Test
    void testHashCodeConsistency() {
        Player other = new Player("ALICE");
        assertEquals(player.hashCode(), other.hashCode());
    }
}
