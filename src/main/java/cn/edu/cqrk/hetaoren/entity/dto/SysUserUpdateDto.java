package cn.edu.cqrk.hetaoren.entity.dto;

import lombok.Data;

@Data
public class SysUserUpdateDto {
    private Integer id;
    private String username;
    private String name;
    private String phone;
    private String dept;
    private String sex;
    private String mail;
    private String status;
}
