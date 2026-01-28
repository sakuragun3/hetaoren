import cn.edu.cqrk.hetaoren.MainApp;
import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.entity.dto.SysUserLoginDto;
import cn.edu.cqrk.hetaoren.entity.dto.SysUserUpdateDto;
import cn.edu.cqrk.hetaoren.entity.pojo.SysUser;
import cn.edu.cqrk.hetaoren.entity.vo.SysUserVo;
import cn.edu.cqrk.hetaoren.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = MainApp.class) // 指定Spring Boot的主类
@RunWith(SpringRunner.class) // 使用SpringRunner运行测试
public class SysUserServiceTest {

    @Autowired
    SysUserService sysUserService; // 自动注入SysUserService

    @Test
    public void testSave() {
        SysUser sysUser = new SysUser(); // 创建SysUser对象
        sysUser.setName("张三"); // 设置用户名
        sysUser.setPhone("17623392517"); // 设置电话
        sysUser.setMail("sakuragun3@gmailcom"); // 设置邮件（注意：此处缺少@）
        sysUser.setPassword("123456"); // 设置密码
//        sysUser.setAvatar("/img/test.png"); // 设置头像路径
        sysUser.setCreated(new Date()); // 设置创建时间
        sysUserService.save(sysUser); // 保存用户信息
    }

    @Test
    public void testRemove() {
        boolean b = sysUserService.removeById(5); // 根据ID删除用户
        System.out.println("b: " + b); // 输出删除结果
    }

    @Test
    public void testGet() {
        SysUser byId = sysUserService.getById(12); // 根据ID获取用户信息
        System.out.println(byId); // 输出用户信息
    }

    @Test
    public void testUpdate() {
        SysUser byId = sysUserService.getById(12); // 获取要更新的用户信息
        byId.setName("李四"); // 修改用户名
        boolean b = sysUserService.updateById(byId); // 更新用户信息
        System.out.println("b: " + b); // 输出更新结果
        System.out.println(byId.getName());
    }

    @Test
    public void testLogin() throws BizException {
        SysUserLoginDto dto = new SysUserLoginDto(); // 创建登录DTO
        dto.setPhone("17623392517"); // 设置电话
        dto.setPassword("123456"); // 设置密码
        SysUserVo login = sysUserService.login(dto); // 调用登录方法
        System.out.println(login); // 输出登录结果
    }

    @Test
    public void testList() {
        List<SysUser> list = sysUserService.list(); // 获取所有用户列表
        list.forEach(System.out::println); // 输出每个用户
    }

    @Test
    public void testListWrapper() {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class); // 创建查询包装器
        queryWrapper.like(SysUser::getPhone, 176); // 设置查询条件：电话号码包含176
        List<SysUser> list = sysUserService.list(queryWrapper); // 根据条件获取用户列表

        for (SysUser sysUser : list) {
            System.out.println(sysUser); // 输出每个用户
        }
    }

    @Test
    public void listByIds() {
        List<Integer> idList = new ArrayList<>(); // 创建ID列表
        idList.add(3); // 添加用户ID
        idList.add(3); // 再添加同一用户ID

        List<SysUser> sysUsers = sysUserService.listByIds(idList); // 根据ID列表获取用户信息

        for (SysUser sysUser : sysUsers) {
            System.out.println(sysUser); // 输出每个用户信息
        }
    }

    @Test
    public void testPage() {
        IPage<SysUser> iPage = new Page<>(2, 2); // 创建分页对象，设置当前页和每页记录数

        // 不带条件，传iPage对象
        IPage<SysUser> result = sysUserService.page(iPage); // 获取分页结果

        List<SysUser> records = result.getRecords(); // 获取记录列表
        records.forEach(System.out::println); // 输出每个用户

        long total = result.getTotal(); // 获取总记录数
        System.out.println("总记录数：" + total); // 输出总记录数
    }

    @Test
    public void testUpdateA() throws BizException {
        // 创建更新DTO
        SysUserUpdateDto updateDto = new SysUserUpdateDto();
        updateDto.setId(12); // 设置要更新的用户ID
        updateDto.setName("王小麻"); // 修改用户名
        updateDto.setPhone("17623392517"); // 更新电话
        updateDto.setMail("updated@example.com"); // 更新邮件
        updateDto.setDept("技术部"); // 更新所属部门
        // 可以根据需要添加其他字段

        // 调用更新方法
        SysUserVo updatedUser = sysUserService.update(updateDto); // 假设update方法返回更新后的用户视图对象

        // 输出更新结果
        System.out.println(updatedUser); // 输出更新后的用户信息


        // 验证更新是否成功
        SysUser afterUpdate = sysUserService.getById(12); // 获取更新后的用户信息
        assertEquals("王小麻", afterUpdate.getName()); // 验证用户名
        assertEquals("17623392517", afterUpdate.getPhone()); // 验证电话
        assertEquals("updated@example.com", afterUpdate.getMail()); // 验证邮件
        assertEquals("技术部", afterUpdate.getDept()); // 验证所属部门

        System.out.println(afterUpdate.getName());
    }


}
