
package com.sagarnileshshah.carouselmvp.data.models.comment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments {

    @SerializedName("photo_id")
    @Expose
    private String photoId;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = null;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

}
