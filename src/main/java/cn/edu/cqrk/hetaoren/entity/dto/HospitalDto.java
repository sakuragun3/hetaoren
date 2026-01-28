package cn.edu.cqrk.hetaoren.entity.dto;

import lombok.Data;

@Data
public class HospitalDto {
    private Integer hospitalId; // 添加此字段
    private String hospitalName;
    private String phone;
    private String hospitalAddress;
    private String deanName;
}
