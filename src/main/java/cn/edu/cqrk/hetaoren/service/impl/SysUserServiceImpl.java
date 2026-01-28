package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.common.BizExceptionCode;
import cn.edu.cqrk.hetaoren.entity.dto.*;
import cn.edu.cqrk.hetaoren.entity.pojo.SysUser;
import cn.edu.cqrk.hetaoren.entity.vo.SysUserVo;
import cn.edu.cqrk.hetaoren.mapper.SysUserMapper;
import cn.edu.cqrk.hetaoren.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 添加系统用户
     *
     * @param dto 用户传输对象，包含用户信息
     * @return 返回成功添加后的用户视图对象
     * @throws BizException 业务异常，例如手机号已存在或添加失败
     */
    @Override
    public SysUserVo add(SysUserAddDto dto) throws BizException {
        // 手机号格式校验
        String phone = dto.getPhone();
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            // 抛出手机号不合法的异常
            throw new BizException(BizExceptionCode.ILLEGAL_PHONE);
        }

        // 构建查询条件，按手机号查询用户
        LambdaQueryWrapper<SysUser> lambdaQuery = Wrappers.lambdaQuery(SysUser.class);
        lambdaQuery.eq(SysUser::getPhone, dto.getPhone());

        // 查询是否存在相同手机号的用户
        SysUser one = this.getOne(lambdaQuery);

        // 如果用户已存在，抛出异常
        if (one != null) {
            throw new BizException(BizExceptionCode.EXIST_PHONE);
        }

        // 创建新的用户对象
        SysUser sysUser = new SysUser();

        // 将dto中的属性复制到用户实体对象中
        BeanUtils.copyProperties(dto, sysUser);

        // 设置创建时间为当前时间
        sysUser.setCreated(new Date());

        // 保存用户到数据库
        boolean save = save(sysUser);

        // 如果保存成功，复制属性并返回用户视图对象
        if (save) {
            SysUserVo vo = new SysUserVo();
            BeanUtils.copyProperties(sysUser, vo);
            return vo;
        }

        // 保存失败，抛出添加失败的业务异常
        throw new BizException(BizExceptionCode.FAILED_ADD);
    }

    /**
     * 用户登录
     *
     * @param dto 登录传输对象，包含手机号和密码
     * @return 返回登录成功后的用户视图对象
     * @throws BizException 登录失败的异常
     */
    @Override
    public SysUserVo login(SysUserLoginDto dto) throws BizException {
        // 获取登录用户的手机号
        String phone = dto.getPhone();

        // 校验手机号格式，必须为中国大陆的合法手机号
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new BizException(BizExceptionCode.ILLEGAL_PHONE);  // 手机号格式不合法，抛出异常
        }

        // 构建查询条件，根据手机号和密码进行匹配查询
        LambdaQueryWrapper<SysUser> lambdaQuery = Wrappers.lambdaQuery(SysUser.class);
        lambdaQuery.eq(SysUser::getPhone, dto.getPhone());  // 根据手机号查询
        lambdaQuery.eq(SysUser::getPassword, dto.getPassword());  // 根据密码查询

        // 根据查询条件查找用户
        SysUser one = this.getOne(lambdaQuery);

        // 如果用户不存在，抛出登录失败异常
        if (one == null) {
            throw new BizException(BizExceptionCode.FAILED_LOGIN);
        }

        // 创建一个 SysUserVo 对象用于返回用户数据
        SysUserVo vo = new SysUserVo();
        BeanUtils.copyProperties(one, vo);  // 将 SysUser 对象的属性复制到 SysUserVo 对象

        // 返回用户信息
        return vo;
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除操作的结果
     */
    @Override
    public boolean deleteById(Integer id) {
        // 检查用户是否存在
        SysUser user = this.getById(id);
        if (user == null) {
            return false; // 用户不存在，返回false
        }
        // 执行删除操作
        return this.removeById(id); // 返回删除结果
    }

    /**
     * 更新用户信息
     *
     * @param dto 更新传输对象，包含用户更新信息
     * @return 返回更新后的用户视图对象
     * @throws BizException 更新过程中可能抛出的异常
     */
    @Override
    public SysUserVo update(SysUserUpdateDto dto) throws BizException {
        // 校验手机号格式
        String phone = dto.getPhone();
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new BizException(BizExceptionCode.ILLEGAL_PHONE);
        }

        // 检查用户是否存在
        SysUser existingUser = this.getById(dto.getId());
        if (existingUser == null) {
            throw new BizException(BizExceptionCode.USER_NOT_FOUND);
        }

        // 更新用户信息，避免覆盖未修改的字段
        if (!dto.getPhone().equals(existingUser.getPhone())) {
            // 检查手机号是否已存在
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class);
            queryWrapper.eq(SysUser::getPhone, dto.getPhone());
            if (this.count(queryWrapper) > 0) {
                throw new BizException(BizExceptionCode.EXIST_PHONE);
            }
        }

        // 将更新的数据复制到现有用户对象中
        BeanUtils.copyProperties(dto, existingUser, "id", "created", "password"); // 忽略 id, created, password 字段

        // 更新用户的修改时间
        existingUser.setUpdated(new Date());

        // 保存更新后的用户对象
        boolean updateSuccess = this.updateById(existingUser);
        if (!updateSuccess) {
            throw new BizException(BizExceptionCode.FAILED_UPDATE);
        }

        // 返回更新后的用户视图对象
        SysUserVo vo = new SysUserVo();
        BeanUtils.copyProperties(existingUser, vo);
        return vo;
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID列表
     * @return 删除成功的结果
     * @throws BizException 业务异常，例如ID列表不合法
     */
    @Override
    public boolean batchDelete(List<Integer> userIds) throws BizException {
        // 检查用户ID列表是否为空
        if (userIds == null || userIds.isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_ID);
        }

        // 检查用户是否存在，返回存在的用户数量
        List<SysUser> existingUsers = this.listByIds(userIds);
        if (existingUsers.size() != userIds.size()) {
            throw new BizException(BizExceptionCode.USER_NOT_FOUND); // 存在用户不存在的情况
        }

        // 执行批量删除操作
        boolean deleteSuccess = this.removeByIds(userIds);
        if (!deleteSuccess) {
            throw new BizException(BizExceptionCode.FAILED_DELETE); // 删除失败的异常处理
        }

        return true; // 返回删除成功的结果
    }

    /**
     * 根据条件搜索用户
     *
     * @param dto 搜索传输对象，包含搜索条件
     * @return 返回符合条件的用户视图对象列表
     * @throws BizException 业务异常，例如用户名格式不合法
     */
    @Override
    public List<SysUserVo> search(SysUserSearchDto dto) throws BizException {
        String username = dto.getUsername();

        // 校验用户名格式
        if (username == null || username.isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_USERNAME);
        }

        // 构建查询条件
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class);
        queryWrapper.like(SysUser::getUsername, username); // 模糊查询

        // 查询用户列表
        List<SysUser> users = this.list(queryWrapper);

        // 转换为视图对象列表
        List<SysUserVo> userVoList = new ArrayList<>();
        for (SysUser user : users) {
            SysUserVo vo = new SysUserVo();
            BeanUtils.copyProperties(user, vo);
            userVoList.add(vo);
        }

        return userVoList;
    }
}
