package cn.edu.cqrk.hetaoren.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserVo implements Serializable {

    private Integer id;
    private String username;
    private String name;
    private String phone;
    private String dept;
    private String sex;
    private String mail;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updated;

}
