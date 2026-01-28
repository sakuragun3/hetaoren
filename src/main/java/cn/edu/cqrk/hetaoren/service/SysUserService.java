package cn.edu.cqrk.hetaoren.service;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.entity.dto.*;
import cn.edu.cqrk.hetaoren.entity.pojo.SysUser;
import cn.edu.cqrk.hetaoren.entity.vo.SysUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
    SysUserVo add(SysUserAddDto dto) throws BizException;
    SysUserVo login(SysUserLoginDto dto) throws BizException;
    boolean deleteById(Integer id) throws BizException;
    SysUserVo update(SysUserUpdateDto dto) throws BizException;
    boolean batchDelete(List<Integer> userIds) throws BizException;
    List<SysUserVo> search(SysUserSearchDto dto) throws BizException;
}
