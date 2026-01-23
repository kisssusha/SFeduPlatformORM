package org.example.service;

import org.example.dao.Category;
import org.example.dao.Course;
import org.example.dao.User;
import org.example.dao.enums.UserRole;
import org.example.repository.CategoryRepository;
import org.example.repository.CourseRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    private Category category;
    private User teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        category = new Category("Programming");
        category.setId(1L);

        teacher = new User("Teacher", "teacher@test.com", UserRole.TEACHER);
        teacher.setId(1L);

        course = new Course("Java Basics", "Learn Java", category, teacher);
        course.setId(1L);
    }

    @Test
    void shouldCreateCourse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(userRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course created = courseServiceImpl.createCourse("Java Basics", "Learn Java", 1L, 1L);

        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Java Basics");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseServiceImpl.createCourse("Java", "Learn", 999L, 1L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Category not found");
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseServiceImpl.createCourse("Java", "Learn", 1L, 999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("The teacher was not found");
    }

    @Test
    void shouldGetCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course found = courseServiceImpl.getCourseById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getTitle()).isEqualTo("Java Basics");
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseServiceImpl.getCourseById(999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Course not found");
    }

    @Test
    void shouldDeleteCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseServiceImpl.deleteCourse(1L);

        verify(courseRepository).delete(course);
    }

    @Test
    void shouldUpdateCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course updated = courseServiceImpl.updateCourse(1L, "New Title", "New Description");

        assertThat(updated.getTitle()).isEqualTo("New Title");
        assertThat(updated.getDescription()).isEqualTo("New Description");
        verify(courseRepository).save(course);
    }
}

