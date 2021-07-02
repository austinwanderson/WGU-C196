package com.austinwayneanderson.wgustudentscheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "courses")
public class Course {

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
    @ColumnInfo(name = "status")
    private String status;

    @NonNull
    @ColumnInfo(name = "term_id")
    private int termId;

    @Ignore
    private List<Instructor> instructors;

    @Ignore
    private List<Assessment> assessments;

    public Course(@NonNull String title, @NonNull String startDate, @NonNull String endDate, @NonNull String status, List<Instructor> instructors, List<Assessment> assessments) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructors = instructors;
        this.assessments = assessments;
        this.status = status;
    }

    public Course(@NonNull String title, @NonNull String startDate, @NonNull String endDate, @NonNull String status, @NonNull int termId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termId = termId;
    }

    public int getId() { return this.id; }
    public int getTermId() { return this.termId; }
    public String getTitle() { return this.title; }
    public String getStartDate() { return this.startDate; }
    public String getEndDate() { return this.endDate; }
    public List<Instructor> getInstructors() { return this.instructors; }
    public List<Assessment> getAssessments() { return this.assessments; }
    public String getStatus() { return this.status; }
    public void addInstructor(Instructor i) {
        //TODO
    }
    public void removeInstructor(Instructor i) {
        //TODO
    }
    public void addAssessment(Assessment a) {
        //TODO
    }
    public void removeAssessment(Assessment a) {
        //TODO
    }

    public void setId(int id) { this.id = id; }
    public void setTermId(int termId) { this.termId = termId; }
    public void setTitle(String title) { this.title = title; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setStatus(String status) { this.status = status; }
}