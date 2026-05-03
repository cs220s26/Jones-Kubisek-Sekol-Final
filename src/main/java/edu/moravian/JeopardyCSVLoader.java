package edu.moravian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JeopardyCSVLoader {
    static List<Category> categories = new ArrayList<>();

    public JeopardyCSVLoader() {
        String csvPath = "JeopardyQuestions.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                Category c = new Category();
                c.setCategoryName(row[0]);

                c.addQuestionSet(row[1],  Integer.parseInt(row[2]),  row[3]);
                c.addQuestionSet(row[5],  Integer.parseInt(row[6]),  row[7]);
                c.addQuestionSet(row[9],  Integer.parseInt(row[10]), row[11]);
                c.addQuestionSet(row[13], Integer.parseInt(row[14]), row[15]);

                categories.add(c);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
