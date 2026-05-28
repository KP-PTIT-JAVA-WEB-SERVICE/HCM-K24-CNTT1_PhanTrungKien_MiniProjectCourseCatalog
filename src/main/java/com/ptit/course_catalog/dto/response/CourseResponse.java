package com.ptit.course_catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseResponse {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
}
