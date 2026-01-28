package cn.edu.cqrk.hetaoren.entity.dto;

import lombok.Data;

@Data
public class DepartmentDto {
    private Integer departmentId;     // 部门ID
    private String departmentName;    // 部门名称
    private String managerName;       // 部门负责人
    private String managerPhone;      // 负责人联系电话
    private Integer employeeCount;    // 部门员工人数
}
