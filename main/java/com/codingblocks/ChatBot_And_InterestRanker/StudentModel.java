package com.codingblocks.ChatBot_And_InterestRanker;

/**
 * Created by Sachin on 9/3/2016.
 */
public class StudentModel {
    int id;
    String student_name;
    String batch_name;
    String user_name;


    public StudentModel(String student_name,String user_name, String batch_name)
    {
        this.student_name=student_name;
        this.user_name=user_name;
        this.batch_name=batch_name;
    }

    public StudentModel(int id,String student_name,String user_name, String batch_name)
    {
        this.id=id;
        this.student_name=student_name;
        this.user_name=user_name;
        this.batch_name=batch_name;
    }
    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(int batch_id) {
        this.batch_name = batch_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
