/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ADMIN
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    private int id;
    private int userId;
    private int quizId;
    private float score;
    private Date date;
    
    private String studentName;
    private String quizName;
    
}
