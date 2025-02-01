package com.crud.Rest.api.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false, referencedColumnName = "dept_id")
    private Department dept;

    @NotBlank(message = "Address is required")
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, referencedColumnName = "city_id")
    private City city;

    @NotNull(message = "Date of Joining is required")
    private java.time.LocalDate date_Of_Joining;

    @NotBlank(message = "Job Title is required")
    private String job_Title;

    @NotNull(message = "Salary is required")
    @Min(value = 1, message = "Salary must be greater than 0")
    private Double salary;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    @Column(unique = true, nullable = false)
    private String mob;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;
}
