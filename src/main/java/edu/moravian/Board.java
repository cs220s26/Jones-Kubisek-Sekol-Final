package edu.moravian;

import java.util.ArrayList;
import java.util.List;

import static edu.moravian.JeopardyCSVLoader.categories;

public class Board {

    List<Category> chosenCategories = new ArrayList<>();

    public Board() {
        new JeopardyCSVLoader();
        for (int i = 0; i < 4; i++) {
            int randomIndex = (int) (Math.random() * categories.size());
            chosenCategories.add(categories.get(randomIndex));
            categories.remove(randomIndex);
        }
    }

    public Board(List<Category> categories) {
        this.chosenCategories = categories;
    }

    public Category getCategory(int index) {
        return chosenCategories.get(index);
    }

    @Override
    public String toString() {
        return formatCategory('a', chosenCategories.get(0)) + "\n\n" + formatCategory('b', chosenCategories.get(1)) + "\n\n" + formatCategory('c', chosenCategories.get(2)) + "\n\n" + formatCategory('d', chosenCategories.get(3));
    }

    private String formatCategory(char letter, Category cat) {
        StringBuilder sb = new StringBuilder();
        sb.append(letter).append(". ").append(cat.getCategoryName()).append("\n ");
        int size = cat.getSize();
        for (int i = 0; i < size; i++) {
            String val = cat.isAnswered(i) ? "X" : "$" + cat.getValue(i);
            sb.append("(").append(val).append(") ");
        }
        return sb.toString().trim();
    }

    public void markQuestionAnswered(int categoryIndex, int questionIndex) {
        Category cat = chosenCategories.get(categoryIndex);
        cat.markAnswered(questionIndex);
    }

    public boolean allCategoriesComplete(List<Category> categories) {
        for (Category category : categories) {
            for (int i = 0; i < category.getSize(); i++) {
                if (!category.isAnswered(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}