
package com.sagarnileshshah.carouselmvp.data.models.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.sagarnileshshah.carouselmvp.data.local.LocalDatabase;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;


@Table(database = LocalDatabase.class, allFields = true)
public class Comment extends BaseModel {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("author_is_deleted")
    @Expose
    private int authorIsDeleted;
    @SerializedName("authorname")
    @Expose
    private String authorname;
    @SerializedName("iconserver")
    @Expose
    private String iconserver;
    @SerializedName("iconfarm")
    @Expose
    private int iconfarm;
    @SerializedName("datecreate")
    @Expose
    private String datecreate;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("path_alias")
    @Expose
    private String pathAlias;
    @SerializedName("realname")
    @Expose
    private String realname;
    @SerializedName("_content")
    @Expose
    private String content;

    @ForeignKey(stubbedRelationship = true)
    private Photo photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorIsDeleted() {
        return authorIsDeleted;
    }

    public void setAuthorIsDeleted(int authorIsDeleted) {
        this.authorIsDeleted = authorIsDeleted;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getIconserver() {
        return iconserver;
    }

    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    public int getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(int iconfarm) {
        this.iconfarm = iconfarm;
    }

    public String getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(String datecreate) {
        this.datecreate = datecreate;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPathAlias() {
        return pathAlias;
    }

    public void setPathAlias(String pathAlias) {
        this.pathAlias = pathAlias;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Photo getPhoto() {
        return photo;
    }

}
