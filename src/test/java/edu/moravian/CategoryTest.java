package edu.moravian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("History");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("History", category.getCategoryName());
        assertEquals(0, category.getSize());
    }

    @Test
    void testSetCategoryName() {
        category.setCategoryName("Geography");
        assertEquals("Geography", category.getCategoryName());
    }

    @Test
    void testAddQuestionSet() {
        category.addQuestionSet("Who was the first president?", 100, "George Washington");
        assertEquals(1, category.getSize());
        assertEquals("Who was the first president?", category.getQuestion(0));
        assertEquals(100, category.getValue(0));
        assertEquals("George Washington", category.getAnswer(0));
        assertFalse(category.isAnswered(0));
    }

    @Test
    void testSetQuestionValueAnswer() {
        category.addQuestionSet("Q", 200, "A");
        category.setQuestion(0, "Updated Question");
        category.setValue(0, 300);
        category.setAnswer(0, "Updated Answer");

        assertEquals("Updated Question", category.getQuestion(0));
        assertEquals(300, category.getValue(0));
        assertEquals("Updated Answer", category.getAnswer(0));
    }

    @Test
    void testMarkAnswered() {
        category.addQuestionSet("Q", 100, "A");
        assertFalse(category.isAnswered(0));
        category.markAnswered(0);
        assertTrue(category.isAnswered(0));
    }

    @Test
    void testMultipleQuestions() {
        category.addQuestionSet("Q1", 100, "A1");
        category.addQuestionSet("Q2", 200, "A2");
        category.addQuestionSet("Q3", 300, "A3");

        assertEquals(3, category.getSize());
        assertEquals("Q1", category.getQuestion(0));
        assertEquals("Q2", category.getQuestion(1));
        assertEquals("Q3", category.getQuestion(2));

        assertEquals(100, category.getValue(0));
        assertEquals(200, category.getValue(1));
        assertEquals(300, category.getValue(2));

        assertEquals("A1", category.getAnswer(0));
        assertEquals("A2", category.getAnswer(1));
        assertEquals("A3", category.getAnswer(2));
    }

    @Test
    void testToString() {
        assertEquals("History", category.toString());
    }

    @Test
    void testGetSizeEmptyCategory() {
        Category emptyCategory = new Category("Empty");
        assertEquals(0, emptyCategory.getSize());
    }
}
