package com.ptit.course_catalog.controller;

import com.ptit.course_catalog.dto.request.CoursePatchRequest;
import com.ptit.course_catalog.dto.request.CoursePostRequest;
import com.ptit.course_catalog.dto.response.ApiResponse;
import com.ptit.course_catalog.dto.response.CourseResponse;
import com.ptit.course_catalog.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CourseResponse>>> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Lấy danh sách khóa học thành công",
                courseService.getAll(pageable)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                String.format("Lấy khóa học có ID %d thành công", id),
                courseService.getById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CoursePostRequest request) {
        CourseResponse response = courseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Tạo khóa học mới thành công!",
                response
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CoursePostRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Cập nhật khóa học thành công",
                courseService.update(id, request)
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> patchCourse(
            @PathVariable Long id,
            @RequestBody CoursePatchRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Cập nhật một phần khóa học thành công",
                courseService.patch(id, request)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<ApiResponse<CourseResponse>> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Upload ảnh cho khóa học thành công",
                courseService.uploadImage(id, file)
        ));
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long id) {
        courseService.deleteImage(id);
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Xóa ảnh của khóa học thành công",
                null
        ));
    }
}
