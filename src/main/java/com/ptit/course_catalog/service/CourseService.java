package com.ptit.course_catalog.service;

import com.ptit.course_catalog.dto.response.CourseResponse;
import com.ptit.course_catalog.mapper.CourseMapper;
import com.ptit.course_catalog.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository courseRepository, CourseMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream().map(CourseMapper::toResponse).toList();
    }
}
