package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_hospital")
public class Hospital implements Serializable {
    @TableId(value = "hospital_id",type = IdType.AUTO)
    private Integer hospitalId;
    @TableField("hospital_name")
    private String hospitalName;
    @TableField("hospital_phone")
    private String hospitalPhone;
    @TableField("hospital_address")
    private String hospitalAddress;
    @TableField("dean_name")
    private String deanName;
}
