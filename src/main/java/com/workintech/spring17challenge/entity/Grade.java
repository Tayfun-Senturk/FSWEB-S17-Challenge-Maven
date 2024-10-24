package com.workintech.spring17challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@Data
@AllArgsConstructor
public class Grade {
    private Integer coefficient;
    private String note;
}
