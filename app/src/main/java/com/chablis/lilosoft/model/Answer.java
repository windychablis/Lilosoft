package com.chablis.lilosoft.model;

/**
 * Created by chablis on 2017/3/13.
 */

public class Answer {
    /**
     * vote_id : b981139a375a40299a9e24cfb37540da
     * opt_content : 满意？
     * vote_c_id : 1349a34251f5499e88f5108ab899d6a5
     * count_num : 4
     */

    private String vote_id;
    private String opt_content;
    private String vote_c_id;
    private int count_num;

    public String getVote_id() {
        return vote_id;
    }

    public void setVote_id(String vote_id) {
        this.vote_id = vote_id;
    }

    public String getOpt_content() {
        return opt_content;
    }

    public void setOpt_content(String opt_content) {
        this.opt_content = opt_content;
    }

    public String getVote_c_id() {
        return vote_c_id;
    }

    public void setVote_c_id(String vote_c_id) {
        this.vote_c_id = vote_c_id;
    }

    public int getCount_num() {
        return count_num;
    }

    public void setCount_num(int count_num) {
        this.count_num = count_num;
    }
}
