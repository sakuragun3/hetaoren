package cn.edu.cqrk.hetaoren.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserAddDto implements Serializable {

    private String username;
    private String name;
    private String password;
    private String phone;
    private String dept;
    private String sex;
    private String mail;
    private String status;
}
