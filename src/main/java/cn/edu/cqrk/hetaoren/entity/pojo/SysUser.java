package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_sysuser")
public class SysUser implements Serializable {

    @TableId(value = "sysuser_id",type = IdType.AUTO)
    private Integer id;

    @TableField("sysuser_username")
    private String username;

    @TableField("sysuser_name")
    private String name;

    @TableField("sysuser_phone")
    private String phone;

    @TableField("sysuser_mail")
    private String mail;

    @TableField("sysuser_password")
    private String password;

//    @TableField("sysuser_avatar")
//    private String avatar;

    @TableField("sysuser_sex")
    private String sex;

    @TableField("sysuser_dept")
    private String dept;

    @TableField("sysuser_status")
    private String status;

    @TableField("CREATED")
    private Date created;

    @TableField("UPDATED")
    private Date updated;

}
