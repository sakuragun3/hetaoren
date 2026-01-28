package cn.edu.cqrk.hetaoren.entity.dto;

import lombok.Data;

@Data
public class DoctorDto {
    private Integer doctorId;
    private String doctorName;
    private String phone;
    private String specialty;
    private Integer hospitalId;
}
