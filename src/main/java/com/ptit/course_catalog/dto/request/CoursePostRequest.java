package com.ptit.course_catalog.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoursePostRequest {
    @NotBlank(message = "Tên khóa học không được bỏ trống")
    private String name;

    private String description;

    @NotNull(message = "Giá khóa học không được bỏ trống")
    @DecimalMin(value = "0.1", message = "Giá khóa học phải lớn hơn 0")
    private Double price;
}
