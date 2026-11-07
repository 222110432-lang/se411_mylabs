package edu.psu.se411.lab14.model;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.*;

public record Course(
 @Id @PositiveOrZero Long id,
 @NotBlank @Size(max=100) String code,
 @NotBlank @Size(min=2,max=100) String name,
 @Size(max=100) String description,
 @NotNull @Min(1) @Max(10) Integer credits
) {}
