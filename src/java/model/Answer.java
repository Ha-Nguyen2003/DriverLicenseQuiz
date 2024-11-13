package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private int answerID;
    private int questionID;
    private String answerText;
    private boolean correct;
    private boolean selected;

    public Answer(int answerID, String answerText, boolean isCorrect) {
        this.answerID = answerID;
        this.answerText = answerText;
        this.correct = isCorrect;
    }
}
