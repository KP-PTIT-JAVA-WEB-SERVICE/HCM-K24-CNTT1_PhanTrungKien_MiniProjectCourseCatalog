package com.ptit.course_catalog.service.impl;

import com.ptit.course_catalog.dto.request.CoursePatchRequest;
import com.ptit.course_catalog.dto.request.CoursePostRequest;
import com.ptit.course_catalog.dto.response.CourseResponse;
import com.ptit.course_catalog.entity.Course;
import com.ptit.course_catalog.exception.CourseNotFoundException;
import com.ptit.course_catalog.mapper.CourseMapper;
import com.ptit.course_catalog.repository.CourseRepository;
import com.ptit.course_catalog.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper mapper;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<CourseResponse> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        return mapper.toResponse(course);
    }

    @Override
    public CourseResponse create(CoursePostRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Tên khóa học không được bỏ trống");
        }
        if (request.getPrice() == null) {
            throw new IllegalArgumentException("Giá khóa học không được bỏ trống");
        }
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Giá khóa học phải lớn hơn 0");
        }

        Course course = mapper.toEntity(request);
        courseRepository.save(course);
        return mapper.toResponse(course);
    }

    @Override
    public CourseResponse update(Long id, CoursePostRequest request) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Tên khóa học không được bỏ trống");
        }
        if (request.getPrice() == null) {
            throw new IllegalArgumentException("Giá khóa học không được bỏ trống");
        }
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Giá khóa học phải lớn hơn 0");
        }

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        courseRepository.save(course);
        return mapper.toResponse(course);
    }

    @Override
    public CourseResponse patch(Long id, CoursePatchRequest request) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        if (request.getName() != null) {
            if (request.getName().isBlank()) {
                throw new IllegalArgumentException("Tên khóa học không được bỏ trống");
            }
            course.setName(request.getName());
        }

        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            if (request.getPrice() <= 0) {
                throw new IllegalArgumentException("Giá khóa học phải lớn hơn 0");
            }
            course.setPrice(request.getPrice());
        }

        courseRepository.save(course);
        return mapper.toResponse(course);
    }

    @Override
    public void delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        if (course.getImageUrl() != null) {
            deletePhysicalFile(course.getImageUrl());
        }
        courseRepository.delete(course);
    }

    @Override
    public CourseResponse uploadImage(Long id, MultipartFile file) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File không được trống");
        }

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        if (contentType == null || originalFilename == null) {
            throw new IllegalArgumentException("File không hợp lệ");
        }

        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new IllegalArgumentException("Sai định dạng file! Chỉ chấp nhận file JPG, JPEG, PNG.");
        }
        String extension = originalFilename.substring(dotIndex + 1).toLowerCase();
        boolean isValidFormat = extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
        boolean isValidContentType = contentType.equals("image/jpeg") || contentType.equals("image/png");

        if (!isValidFormat || !isValidContentType) {
            throw new IllegalArgumentException("Sai định dạng file! Chỉ chấp nhận file JPG, JPEG, PNG.");
        }

        try {
            // Ensure target directory exists
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // If there's an existing image, delete it physically
            if (course.getImageUrl() != null) {
                deletePhysicalFile(course.getImageUrl());
            }

            // Save new file
            String newFilename = UUID.randomUUID() + "." + extension;
            Path targetPath = Paths.get(uploadDir).resolve(newFilename).normalize();
            Files.copy(file.getInputStream(), targetPath);

            // Update course image URL
            course.setImageUrl("/uploads/" + newFilename);
            courseRepository.save(course);

            return mapper.toResponse(course);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi xảy ra khi lưu trữ file ảnh: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteImage(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        if (course.getImageUrl() == null || course.getImageUrl().isBlank()) {
            throw new IllegalArgumentException("Khóa học không có ảnh");
        }

        deletePhysicalFile(course.getImageUrl());
        course.setImageUrl(null);
        courseRepository.save(course);
    }

    private void deletePhysicalFile(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }
        try {
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Không thể xóa file ảnh vật lý: " + e.getMessage());
        }
    }
}
