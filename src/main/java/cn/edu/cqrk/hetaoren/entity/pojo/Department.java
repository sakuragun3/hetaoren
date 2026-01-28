package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_department")
public class Department implements Serializable {
    @TableId(value = "department_id",type = IdType.AUTO)
    private Integer departmentId;

    @TableField("department_name")
    private String departmentName;  // 部门名称

    @TableField("manager_name")
    private String managerName;     // 部门负责人

    @TableField("manager_phone")
    private String managerPhone;    // 负责人电话

    @TableField("employee_count")
    private Integer employeeCount;  // 员工人数
}
