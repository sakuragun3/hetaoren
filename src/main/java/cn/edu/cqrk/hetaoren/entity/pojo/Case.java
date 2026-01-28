package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_case")
public class Case implements Serializable {
    @TableId(value = "case_id",type = IdType.AUTO)
    private Integer id;

    @TableField("case_symptoms")
    private String symptoms;

    @TableField("case_diagnosis")
    private String diagnosis;

    @TableField("case_treatment_plan")
    private String plan;

    @TableField("CREATED")
    private Date created;

    @TableField("UPDATED")
    private Date updated;
}
