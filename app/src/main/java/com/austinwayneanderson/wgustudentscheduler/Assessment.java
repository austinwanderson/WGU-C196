package com.austinwayneanderson.wgustudentscheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "start_date")
    private String startDate;

    @NonNull
    @ColumnInfo(name = "end_date")
    private String endDate;

    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseId;

    @Ignore
    public Assessment(@NonNull String title, @NonNull String startDate, @NonNull String endDate, @NonNull int courseId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    public Assessment(@NonNull String title, @NonNull String startDate, @NonNull String endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = 0; //  UPDATE WHEN FIXING FOR COURSE ID
    }

    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getStartDate() { return this.startDate; }
    public String getEndDate() { return this.endDate; }
    public int getCourseId() { return this.courseId; }
    public String getAssessment() { return this.id + ": " + this.title; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setStartDate(String id) { this.startDate = startDate; }
    public void setEndDate(String id) { this.endDate = endDate; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}