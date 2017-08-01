package com.chablis.lilosoft.model;

import java.util.ArrayList;

/**
 * Created by chablis on 2017/3/13.
 */

public class Question {
    /**
     * vote_c_id : 1349a34251f5499e88f5108ab899d6a5
     * vote_title : 你认为1
     * select_type : 0
     * indagate_id : b9598ae5fc624b6b915b164851e7a3b3
     */

    private String vote_c_id;
    private String vote_title;
    private String select_type;
    private String indagate_id;

    private ArrayList<Answer> answers;

    public String getVote_c_id() {
        return vote_c_id;
    }

    public void setVote_c_id(String vote_c_id) {
        this.vote_c_id = vote_c_id;
    }

    public String getVote_title() {
        return vote_title;
    }

    public void setVote_title(String vote_title) {
        this.vote_title = vote_title;
    }

    public String getSelect_type() {
        return select_type;
    }

    public void setSelect_type(String select_type) {
        this.select_type = select_type;
    }

    public String getIndagate_id() {
        return indagate_id;
    }

    public void setIndagate_id(String indagate_id) {
        this.indagate_id = indagate_id;
    }



    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "vote_c_id='" + vote_c_id + '\'' +
                ", vote_title='" + vote_title + '\'' +
                ", select_type='" + select_type + '\'' +
                ", indagate_id='" + indagate_id + '\'' +
                ", answers=" + answers +
                '}';
    }
}
