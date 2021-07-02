package com.austinwayneanderson.wgustudentscheduler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @NonNull
    @ColumnInfo(name = "email_address")
    private String emailAddress;

    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseId;

    public Instructor(@NonNull String name, @NonNull String phoneNumber, @NonNull String emailAddress, @NonNull int courseId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.courseId = courseId;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getEmailAddress() { return this.emailAddress; }
    public int getCourseId() { return this.courseId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
}
