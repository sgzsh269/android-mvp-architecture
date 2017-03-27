
package com.sagarnileshshah.carouselmvp.data.models.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
