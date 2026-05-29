package com.ptit.course_catalog.service;

import com.ptit.course_catalog.dto.request.CoursePatchRequest;
import com.ptit.course_catalog.dto.request.CoursePostRequest;
import com.ptit.course_catalog.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService {
    Page<CourseResponse> getAll(Pageable pageable);

    CourseResponse getById(Long id);

    CourseResponse create(CoursePostRequest request);

    CourseResponse update(Long id, CoursePostRequest request);

    CourseResponse patch(Long id, CoursePatchRequest request);

    void delete(Long id);

    CourseResponse uploadImage(Long id, MultipartFile file);

    void deleteImage(Long id);
}
