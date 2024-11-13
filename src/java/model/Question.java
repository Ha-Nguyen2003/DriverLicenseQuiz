package model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private int questionID;
    private int quizID;
    private String questionText;
     private List<Answer> answers;  // Add a List to store answers

    public Question(int questionID, String questionText) {
       this.questionID = questionID;
       this.questionText = questionText;
    }

    // Constructor and other methods...

    // Add this method to add answers to the list
    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
    }

}
