package com.workintech.spring17challenge.entity;

public class CustomResponse {
    private Course course;
    private double totalGpa;

    public CustomResponse(Course course, double totalGpa) {
        this.course = course;
        this.totalGpa = totalGpa;
    }

    public Course getCourse() {
        return course;
    }

    public double getTotalGpa() {
        return totalGpa;
    }
}
