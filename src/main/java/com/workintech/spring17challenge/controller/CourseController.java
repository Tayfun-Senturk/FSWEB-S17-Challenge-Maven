package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.entity.*;
import com.workintech.spring17challenge.exceptions.ApiException;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/courses")
public class CourseController {
    private Map<Integer,Course> courses;
    LowCourseGpa lowCourse;
    HighCourseGpa highCourse;
    MediumCourseGpa mediumCourse;
    @PostConstruct
    public void init(){
        courses= new HashMap<>();
    }

    @Autowired
    public CourseController(LowCourseGpa lowCourse , MediumCourseGpa mediumCourse , HighCourseGpa highCourse){
        this.lowCourse=lowCourse;
        this.highCourse=highCourse;
        this.mediumCourse=mediumCourse;

    }

    @GetMapping
    public List<Course> allCourses(){
        return courses.values().stream().toList();
    }
    @GetMapping("/{name}")
    public Course getCourse(@PathVariable String name){
        for(Course course:courses.values()){
            if(course.getName().equalsIgnoreCase(name)){
                return course;
            }
        }
        throw new ApiException("Notfound",HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<CustomResponse> addCourse(@RequestBody Course course){
        if(course.getGrade()==null||course.getName()==null||course.getId()==null){
            throw new ApiException("Null",HttpStatus.BAD_REQUEST);
        }
        if((course.getCredit() > 4) || (course.getCredit() < 0)){
            throw new  ApiException("kredi değeri 0 ila 4 arasında olmalı ",HttpStatus.BAD_REQUEST);
        }
        courses.put(course.getId(), course);
        return new ResponseEntity<>(new CustomResponse(course,calculateGpa(course)),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCourse(@PathVariable Integer id,@RequestBody Course course){
        courses.put(id,course);
        return new ResponseEntity<>(new CustomResponse(course,calculateGpa(course)),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable Integer id){
       Course c= courses.get(id);
       courses.remove(id);
       return c;
    }

    public double calculateGpa(Course course){
        double totalGpa = 0;
        if(course.getCredit()<=2) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourse.getGpa();
        } else if (course.getCredit()<=3) {
            totalGpa= course.getGrade().getCoefficient() * course.getCredit() * mediumCourse.getGpa();
        } else if (course.getCredit()<=4) {
            totalGpa=course.getGrade().getCoefficient() * course.getCredit() * highCourse.getGpa();
        }
        return totalGpa;
    }
}
