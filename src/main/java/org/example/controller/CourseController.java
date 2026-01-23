package org.example.controller;

import org.example.dto.CourseDto;
import org.example.dao.Course;
import org.example.dao.Module;
import org.example.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(final CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody final CourseDto courseDto) {
        Course course = courseService.createCourse(courseDto.title(), courseDto.description(), courseDto.categoryId(), courseDto.teacherId());
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable final Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/with-modules")
    public ResponseEntity<Course> getCourseWithModules(@PathVariable final Long id) {
        Course course = courseService.getCourseWithModules(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable final Long categoryId) {
        List<Course> courses = courseService.getCoursesByCategory(categoryId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesByTeacher(@PathVariable final Long teacherId) {
        List<Course> courses = courseService.getCoursesByTeacher(teacherId);
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description) {
        Course course = courseService.updateCourse(id, title, description);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable final Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Module> addModule(
            @PathVariable Long courseId,
            @RequestParam String title,
            @RequestParam Integer orderIndex) {
        Module module = courseService.addModuleToCourse(courseId, title, orderIndex);
        return ResponseEntity.ok(module);
    }

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<List<Module>> getCourseModules(@PathVariable final Long courseId) {
        List<Module> modules = courseService.getCourseModules(courseId);
        return ResponseEntity.ok(modules);
    }

    @PostMapping("/{courseId}/tags/{tagId}")
    public ResponseEntity<Void> addTagToCourse(@PathVariable final Long courseId, @PathVariable final Long tagId) {
        courseService.addTagToCourse(courseId, tagId);
        return ResponseEntity.ok().build();
    }
}
