package edu.moravian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Category cat1;
    private Category cat2;
    private Category cat3;
    private Category cat4;
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        cat1 = new Category("Cat1");
        cat2 = new Category("Cat2");
        cat3 = new Category("Cat3");
        cat4 = new Category("Cat4");

        for (int i = 0; i < 4; i++) {
            cat1.addQuestionSet("Q1_" + i, (i + 1) * 100, "A1_" + i);
            cat2.addQuestionSet("Q2_" + i, (i + 1) * 100, "A2_" + i);
            cat3.addQuestionSet("Q3_" + i, (i + 1) * 100, "A3_" + i);
            cat4.addQuestionSet("Q4_" + i, (i + 1) * 100, "A4_" + i);
        }

        categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        categories.add(cat4);
    }

    @Test
    void testBoardConstructorWithList() {
        Board board = new Board(categories);
        assertEquals(4, board.chosenCategories.size());
        assertEquals("Cat1", board.getCategory(0).getCategoryName());
        assertEquals("Cat4", board.getCategory(3).getCategoryName());
    }

    @Test
    void testGetCategory() {
        Board board = new Board(categories);
        Category cat = board.getCategory(1);
        assertEquals("Cat2", cat.getCategoryName());
    }

    @Test
    void testMarkQuestionAnswered() {
        Board board = new Board(categories);
        board.markQuestionAnswered(0, 2);
        assertTrue(board.getCategory(0).isAnswered(2));
        assertFalse(board.getCategory(0).isAnswered(0));
        assertFalse(board.getCategory(0).isAnswered(1));
    }

    @Test
    void testAllCategoriesCompleteFalse() {
        Board board = new Board(categories);
        board.markQuestionAnswered(0, 0);
        board.markQuestionAnswered(1, 0);
        assertFalse(board.allCategoriesComplete(board.chosenCategories));
    }

    @Test
    void testAllCategoriesCompleteTrue() {
        Board board = new Board(categories);
        for (int c = 0; c < 4; c++) {
            for (int q = 0; q < 4; q++) {
                board.markQuestionAnswered(c, q);
            }
        }
        assertTrue(board.allCategoriesComplete(board.chosenCategories));
    }

    @Test
    void testToStringContainsCategoryNamesAndValues() {
        Board board = new Board(categories);
        String output = board.toString();
        assertTrue(output.contains("Cat1"));
        assertTrue(output.contains("Cat2"));
        assertTrue(output.contains("Cat3"));
        assertTrue(output.contains("Cat4"));
        assertTrue(output.contains("$100"));
        assertTrue(output.contains("$400"));
    }
}
