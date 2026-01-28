package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.entity.dto.DepartmentDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Department;
import cn.edu.cqrk.hetaoren.service.DepartmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // 分页获取所有部门信息
    @GetMapping("/findAll")
    public Map<String, Object> findAllDepartments(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int limit) {
        IPage<Department> departmentPage = new Page<>(page, limit);  // 分页参数
        IPage<Department> result = departmentService.page(departmentPage);  // 分页查询部门信息

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);  // layui 需要的成功码
        response.put("data", result.getRecords());  // 返回部门记录
        response.put("total", result.getTotal());   // 总记录数
        return response;
    }

    // 添加部门
    @PostMapping("/add")
    public Map<String, Object> addDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setManagerName(departmentDto.getManagerName());
        department.setManagerPhone(departmentDto.getManagerPhone());
        department.setEmployeeCount(departmentDto.getEmployeeCount());

        boolean isSaved = departmentService.save(department);  // 保存部门信息
        Map<String, Object> response = new HashMap<>();
        response.put("code", isSaved ? 0 : -1);
        response.put("msg", isSaved ? "添加成功" : "添加失败");
        return response;
    }

    // 更新部门信息
    @PostMapping("/update")
    public Map<String, Object> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = departmentService.getById(departmentDto.getDepartmentId());
        if (department != null) {
            department.setDepartmentName(departmentDto.getDepartmentName());
            department.setManagerName(departmentDto.getManagerName());
            department.setManagerPhone(departmentDto.getManagerPhone());
            department.setEmployeeCount(departmentDto.getEmployeeCount());

            boolean isUpdated = departmentService.updateById(department);  // 更新部门信息
            Map<String, Object> response = new HashMap<>();
            response.put("code", isUpdated ? 0 : -1);
            response.put("msg", isUpdated ? "更新成功" : "更新失败");
            return response;
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("code", -1);
            response.put("msg", "部门不存在");
            return response;
        }
    }

    // 删除部门
    @PostMapping("/delete")
    public Map<String, Object> deleteDepartment(@RequestParam Integer departmentId) {
        boolean isDeleted = departmentService.removeById(departmentId);  // 删除部门
        Map<String, Object> response = new HashMap<>();
        response.put("code", isDeleted ? 0 : -1);
        response.put("msg", isDeleted ? "删除成功" : "删除失败");
        return response;
    }

    // 根据部门 ID 获取部门信息
    @GetMapping("/getById")
    public Map<String, Object> getDepartmentById(@RequestParam Integer departmentId) {
        Department department = departmentService.getById(departmentId);  // 获取部门信息

        Map<String, Object> response = new HashMap<>();
        if (department != null) {
            response.put("code", 0);
            response.put("data", department);  // 返回部门信息
        } else {
            response.put("code", -1);
            response.put("msg", "获取部门信息失败");
        }
        return response;
    }

    // 搜索部门信息接口
    @GetMapping("/search")
    public Map<String, Object> searchDepartments(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(required = false) String departmentName) {
        IPage<Department> departmentPage = new Page<>(page, limit);
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();

        if (departmentName != null && !departmentName.isEmpty()) {
            queryWrapper.lambda().like(Department::getDepartmentName, departmentName);  // 根据部门名模糊查询
        }

        IPage<Department> result = departmentService.page(departmentPage, queryWrapper);  // 分页查询

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);  // 成功状态码
        response.put("data", result.getRecords());  // 返回搜索结果
        response.put("total", result.getTotal());  // 总记录数
        return response;
    }

    // 批量删除部门
    @PostMapping("/batchDelete")
    public Map<String, Object> batchDeleteDepartments(@RequestBody List<Integer> departmentIds) {
        Map<String, Object> response = new HashMap<>();

        if (departmentIds == null || departmentIds.isEmpty()) {
            response.put("code", -1);
            response.put("msg", "没有要删除的部门ID");
            return response;
        }

        boolean isDeleted = departmentService.removeByIds(departmentIds);  // 批量删除部门
        response.put("code", isDeleted ? 0 : -1);
        response.put("msg", isDeleted ? "批量删除成功" : "批量删除失败");
        return response;
    }
}
