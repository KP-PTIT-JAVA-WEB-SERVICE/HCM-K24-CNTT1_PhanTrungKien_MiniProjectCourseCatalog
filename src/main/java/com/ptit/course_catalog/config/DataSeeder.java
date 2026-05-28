package com.ptit.course_catalog.config;

import com.ptit.course_catalog.entity.Course;
import com.ptit.course_catalog.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CourseRepository courseRepository;

    public DataSeeder(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() == 0) {
            courseRepository.save(new Course(null, "Khóa học Java", "Học Java vui lắm", 190_000.0, null));
            courseRepository.save(new Course(null, "Khóa học ReactJS", "Phải OCD học mới giỏi", 599_000.0, null));
            courseRepository.save(new Course(null, "Học về cách giao tiếp và ứng xử xã hội", "Bắt buộc phải học", 1_999_000.0, null));
        }
    }
}
