package com.ptit.course_catalog.controller;

import com.ptit.course_catalog.dto.response.ApiResponse;
import com.ptit.course_catalog.dto.response.CourseResponse;
import com.ptit.course_catalog.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllProducts() {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Lấy toàn bộ khóa học thành công", courseService.getAll()));
    }
}
