package ru.example.eduplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.eduplatform.entity.*;
import ru.example.eduplatform.entity.Module;
import ru.example.eduplatform.repository.*;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ModuleRepository moduleRepository;

    private final TagRepository tagRepository;

    public CourseServiceImpl(final CourseRepository courseRepository,
                             final CategoryRepository categoryRepository,
                             final UserRepository userRepository,
                             final ModuleRepository moduleRepository,
                             final TagRepository tagRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Course createCourse(final String title, final String description, final Long categoryId, final Long teacherId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        final User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Преподаватель не найден"));

        final Course course = new Course(title, description, category, teacher);
        return courseRepository.save(course);
    }

    public Course getCourseById(final Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
    }

    public Course getCourseWithModules(final Long courseId) {
        return getCourseById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByCategory(final Long categoryId) {
        return courseRepository.findByCategoryId(categoryId);
    }

    public List<Course> getCoursesByTeacher(final Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    public Course updateCourse(final Long courseId, final String title, final String description) {
        final Course course = getCourseById(courseId);
        course.setTitle(title);
        course.setDescription(description);
        return courseRepository.save(course);
    }

    public void deleteCourse(final Long courseId) {
        final Course course = getCourseById(courseId);
        courseRepository.delete(course);
    }

    public Module addModuleToCourse(final Long courseId, final String title, final Integer orderIndex) {
        final Course course = getCourseById(courseId);
        final Module module = new Module(course, title, orderIndex);
        return moduleRepository.save(module);
    }

    public List<Module> getCourseModules(final Long courseId) {
        return moduleRepository.findByCourseIdOrderByOrderIndex(courseId);
    }

    @Transactional
    public void addTagToCourse(final Long courseId, final Long tagId) {
        final Course course = getCourseById(courseId);
        final Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Тег не найден"));
        course.getTags().add(tag);
        courseRepository.save(course);
    }
}
