package com.austinwayneanderson.wgustudentscheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "notes")
public class Note {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "contents")
    private String contents;

    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseId;

    public Note(@NonNull String title, @NonNull String contents, @NonNull int courseId) {
        this.title = title;
        this.contents = contents;
        this.courseId = courseId;
    }

    public int getId() { return this.id; }
    public int getCourseId() { return this.courseId; }
    public String getTitle() { return this.title; }
    public String getContents() { return this.contents; }

    public void setId(int id) { this.id = id; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setTitle(String title) { this.title = title; }
    public void setContents(String contents) { this.contents = contents; }
}