package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_doctor")
public class Doctor implements Serializable {
    @TableId(value = "doctor_id",type = IdType.AUTO)
    private Integer doctorId;
    @TableField("doctor_name")
    private String doctorName;
    @TableField("doctor_phone")
    private String phone;
    @TableField("doctor_specialty")
    private String specialty;

    private Integer hospitalId;
}
