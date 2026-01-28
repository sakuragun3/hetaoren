package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.common.BizExceptionCode;
import cn.edu.cqrk.hetaoren.common.R;
import cn.edu.cqrk.hetaoren.entity.dto.*;
import cn.edu.cqrk.hetaoren.entity.pojo.SysUser;
import cn.edu.cqrk.hetaoren.entity.vo.SysUserVo;
import cn.edu.cqrk.hetaoren.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sysuser")
public class SysUserController implements Serializable {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加系统用户
     * @param dto 系统用户添加的传输对象
     * @return 返回成功响应及添加的用户信息
     * @throws BizException 业务异常，例如手机号已存在等
     */
    @PostMapping("/add")
    public R add(@RequestBody SysUserAddDto dto) throws BizException {

        // 调用服务层添加用户
        SysUserVo vo = sysUserService.add(dto);

        // 返回成功响应，附带新添加的用户信息
        return R.success(vo);
    }

    /**
     * 用户登录
     * @param dto 用户登录的传输对象
     * @return 返回成功响应及登录的用户信息
     * @throws BizException 业务异常，例如用户名或密码错误
     */
    @PostMapping("/login")
    public R login(@RequestBody SysUserLoginDto dto) throws BizException {

        // 调用服务层进行用户登录
        SysUserVo login = sysUserService.login(dto);

        return R.success(login);
    }

    /**
     * 查询所有系统用户并进行分页
     * @param page 当前页数
     * @param limit 每页显示的记录数
     * @return 返回用户数据及总记录数
     */
    @GetMapping("/findSysUserAll")
    public Map<String, Object> findSysUserAll(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int limit) {
        IPage<SysUser> userPage = new Page<>(page, limit); // 创建分页对象
        IPage<SysUser> result = sysUserService.page(userPage); // 获取分页结果

        // 转换为 SysUserVo 列表
        List<SysUserVo> voList = result.getRecords().stream()
                .map(user -> {
                    SysUserVo vo = new SysUserVo();
                    BeanUtils.copyProperties(user, vo); // 属性复制
                    return vo;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 设置成功状态码
        response.put("data", voList); // 返回用户数据
        response.put("total", result.getTotal()); // 返回总记录数

        return response; // 返回结果
    }

    /**
     * 根据ID删除系统用户
     * @param dto 包含用户ID的传输对象
     * @return 返回删除成功的响应
     * @throws BizException 业务异常，例如非法ID或用户未找到
     */
    @PostMapping("/deleteById")
    public R deleteById(@RequestBody SysUserIdDto dto) throws BizException {

        Integer id = dto.getId();
        // 检查ID是否合法
        if (id <= 0) {
            throw new BizException(BizExceptionCode.ILLEGAL_ID); // 抛出非法ID异常
        }

        // 调用服务层删除用户
        boolean isDeleted = sysUserService.deleteById(id);

        if (isDeleted) {
            return R.success("用户删除成功"); // 删除成功
        } else {
            throw new BizException(BizExceptionCode.USER_NOT_FOUND); // 如果用户不存在
        }
    }

    /**
     * 更新系统用户信息
     * @param dto 系统用户更新的传输对象
     * @return 返回成功响应及更新后的用户信息
     * @throws BizException 业务异常，例如用户未找到等
     */
    @PostMapping("/update")
    public R update(@RequestBody SysUserUpdateDto dto) throws BizException {

        // 调用服务层更新用户信息
        SysUserVo vo = sysUserService.update(dto);

        return R.success(vo); // 返回更新后的用户信息
    }

    /**
     * 批量删除系统用户
     * @param dto 包含多个用户ID的传输对象
     * @return 返回批量删除成功的响应
     * @throws BizException 业务异常，例如删除失败等
     */
    @PostMapping("/batchDelete")
    public R batchDelete(@RequestBody SysUserIdsDto dto) throws BizException {
        List<Integer> userIds = dto.getUserIds(); // 获取待删除用户ID列表
        // 调用服务层批量删除用户
        boolean isDeleted = sysUserService.batchDelete(userIds);

        if (isDeleted) {
            return R.success("用户批量删除成功"); // 批量删除成功
        } else {
            throw new BizException(BizExceptionCode.FAILED_DELETE); // 如果删除失败，抛出异常
        }
    }

    /**
     * 根据用户名搜索系统用户
     * @param username 用户名
     * @return 返回符合条件的用户列表
     * @throws BizException 业务异常，例如搜索失败等
     */
    @GetMapping("/search")
    public R search(@RequestParam String username) throws BizException {
        SysUserSearchDto dto = new SysUserSearchDto(); // 创建搜索DTO对象
        dto.setUsername(username); // 设置搜索条件：用户名
        List<SysUserVo> userVoList = sysUserService.search(dto); // 调用服务层搜索方法
        return R.success(userVoList); // 返回搜索结果
    }
}
