package com.example.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Question {
    private String question;
    private String[] answers;
    private String correctAnswer; // Thêm thuộc tính để lưu đáp án đúng

    public Question(String question, String[] answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    // Phương thức để lấy ký tự đại diện của đáp án đúng (A, B, C, D)
    public String getCorrectAnswer() {
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].equals(correctAnswer)) {
                switch (i) {
                    case 0: return "A";
                    case 1: return "B";
                    case 2: return "C";
                    case 3: return "D";
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy đáp án đúng
    }


    public static List<Question> loadQuestionsFromFile(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> currentQuestion = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.equals("//")) {
                    if (!currentQuestion.isEmpty()) {
                        String questionText = currentQuestion.get(0);
                        List<String> answersList = new ArrayList<>();
                        String correctAnswer = null;

                        // Kiểm tra từng đáp án
                        for (String answer : currentQuestion.subList(1, currentQuestion.size())) {
                            if (answer.startsWith("&")) {
                                correctAnswer = answer.substring(1); // Bỏ ký tự "&" để lấy đáp án đúng
                                answersList.add(correctAnswer);
                            } else {
                                answersList.add(answer);
                            }
                        }

                        // Tạo đối tượng Question với đáp án đúng
                        questions.add(new Question(questionText, answersList.toArray(new String[0]), correctAnswer));
                        currentQuestion.clear();
                    }
                } else {
                    currentQuestion.add(line);
                }
            }

            // Thêm câu hỏi cuối nếu file không kết thúc bằng "//"
            if (!currentQuestion.isEmpty()) {
                String questionText = currentQuestion.get(0);
                List<String> answersList = new ArrayList<>();
                String correctAnswer = null;

                for (String answer : currentQuestion.subList(1, currentQuestion.size())) {
                    if (answer.startsWith("&")) {
                        correctAnswer = answer.substring(1);
                        answersList.add(correctAnswer);
                    } else {
                        answersList.add(answer);
                    }
                }

                questions.add(new Question(questionText, answersList.toArray(new String[0]), correctAnswer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static Question getRandomQuestion(List<Question> questions) {
        Collections.shuffle(questions); // Trộn danh sách câu hỏi
        return questions.get(0); // Lấy câu hỏi đầu tiên sau khi trộn
    }
}
