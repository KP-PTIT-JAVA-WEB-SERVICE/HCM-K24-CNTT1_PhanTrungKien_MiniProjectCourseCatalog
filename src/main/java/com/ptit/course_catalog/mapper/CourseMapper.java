package com.ptit.course_catalog.mapper;

import com.ptit.course_catalog.dto.request.CoursePostRequest;
import com.ptit.course_catalog.dto.response.CourseResponse;
import com.ptit.course_catalog.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseResponse toResponse(Course course) {
        return new CourseResponse(course.getId(), course.getName(), course.getDescription(), course.getPrice(), course.getImageUrl());
    }

    public Course toEntity(CoursePostRequest request) {
        return new Course(null, request.getName(), request.getDescription(), request.getPrice(), null);
    }
}
