package ru.example.eduplatform.service;

import ru.example.eduplatform.entity.Course;
import ru.example.eduplatform.entity.Module;

import java.util.List;

public interface CourseService {

    Course createCourse(String title, String description, Long categoryId, Long teacherId);

    Course getCourseById(Long courseId);

    Course getCourseWithModules(Long courseId);

    List<Course> getAllCourses();

    List<Course> getCoursesByCategory(Long categoryId);

    List<Course> getCoursesByTeacher(Long teacherId);

    Course updateCourse(Long courseId, String title, String description);

    void deleteCourse(Long courseId);

    Module addModuleToCourse(Long courseId, String title, Integer orderIndex);

    List<Module> getCourseModules(Long courseId);

    void addTagToCourse(Long courseId, Long tagId);
}
