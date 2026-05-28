package com.ptit.course_catalog.mapper;

import com.ptit.course_catalog.dto.response.CourseResponse;
import com.ptit.course_catalog.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public static CourseResponse toResponse(Course course) {
        return new CourseResponse(course.getName(), course.getDescription(), course.getPrice(), course.getImageUrl());
    }
}
