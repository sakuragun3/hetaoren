package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_patient")
public class Patient implements Serializable {

    @TableId(value = "pat_id",type = IdType.AUTO)
    private Integer id;

    @TableField("pat_account")
    private String account;

    @TableField("pat_name")
    private String name;

    @TableField("pat_phone")
    private String phone;

    @TableField("pat_sex")
    private String sex;

    @TableField("pat_status")
    private String status;

    @TableField("CREATED")
    private Date created;

    @TableField("UPDATED")
    private Date updated;

}
