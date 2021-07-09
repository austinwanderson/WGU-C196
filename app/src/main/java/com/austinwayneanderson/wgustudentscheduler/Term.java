package com.austinwayneanderson.wgustudentscheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {

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

    public Term(@NonNull String title, @NonNull String startDate, @NonNull String endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getStartDate() { return this.startDate; }
    public String getEndDate() { return this.endDate; }
    public String getTerm() { return this.id + ": " + this.title; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
