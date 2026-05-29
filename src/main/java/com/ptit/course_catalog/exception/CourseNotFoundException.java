package com.ptit.course_catalog.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Không tìm thấy khóa học có id: " + id);
    }
}
