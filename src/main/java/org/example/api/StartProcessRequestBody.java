package org.example.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StartProcessRequestBody {
    @NotBlank
    private String initiator;
    @NotNull @Min(18)
    private Integer age;
}
