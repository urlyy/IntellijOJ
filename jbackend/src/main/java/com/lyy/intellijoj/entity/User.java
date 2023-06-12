package com.lyy.intellijoj.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user")
public class User {
    @Id
    private Integer id;
    @NotBlank(message = "name不能为空")
    private String name;

//    @Range(min = 10, max = 50)
//    private Integer age;

//    private LocalDate birthday;
}
