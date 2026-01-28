package cn.edu.cqrk.hetaoren.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserLoginDto implements Serializable {
    private String phone;
    private String password;
}
