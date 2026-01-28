package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.common.BizExceptionCode;
import cn.edu.cqrk.hetaoren.common.R;
import cn.edu.cqrk.hetaoren.entity.dto.PatientIdDto;
import cn.edu.cqrk.hetaoren.entity.dto.PatientIdsDto;
import cn.edu.cqrk.hetaoren.entity.dto.PatientSearchDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Patient;
import cn.edu.cqrk.hetaoren.service.PatientService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController implements Serializable {

    @Autowired
    private PatientService patientService;

    // 查询所有患者信息并分页
    @GetMapping("/findPatientAll")
    public Map<String, Object> findSysUserAll(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int limit) {
        IPage<Patient> patientPage = new Page<>(page, limit); // 创建分页对象
        IPage<Patient> result = patientService.page(patientPage); // 获取分页结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 设置成功状态码
        response.put("data", result.getRecords()); // 返回当前页的患者数据
        response.put("total", result.getTotal()); // 返回总记录数

        return response; // 返回分页数据
    }

    // 添加患者信息
    @PostMapping("/add")
    public R add(@RequestBody Patient patient) throws BizException {
        // 调用服务层方法添加患者
        Patient newPatient = patientService.add(patient);

        // 返回成功响应，附带新添加的患者信息
        return R.success(newPatient);
    }

    // 根据ID删除患者
    @PostMapping("/deleteById")
    public R deleteById(@RequestBody PatientIdDto dto) throws BizException {
        Integer id = dto.getId();
        // 检查ID是否合法
        if (id <= 0) {
            throw new BizException(BizExceptionCode.ILLEGAL_ID); // 抛出非法ID异常
        }

        // 调用服务层方法删除患者
        boolean isDeleted = patientService.deleteById(id);

        if (isDeleted) {
            return R.success("用户删除成功"); // 删除成功
        } else {
            throw new BizException(BizExceptionCode.USER_NOT_FOUND); // 如果用户未找到
        }
    }

    // 更新患者信息
    @PostMapping("/update")
    public R update(@RequestBody Patient patient) throws BizException {
        // 调用服务层方法更新患者信息
        Patient newPatient = patientService.update(patient);

        // 返回成功响应，附带更新后的患者信息
        return R.success(newPatient);
    }

    // 批量删除患者信息
    @PostMapping("/batchDelete")
    public R batchDelete(@RequestBody PatientIdsDto dto) throws BizException {
        List<Integer> patientIds = dto.getPatientIds(); // 获取待删除患者ID列表
        // 调用服务层方法批量删除患者
        boolean isDeleted = patientService.batchDelete(patientIds);

        if (isDeleted) {
            return R.success("用户批量删除成功"); // 批量删除成功
        } else {
            throw new BizException(BizExceptionCode.FAILED_DELETE); // 删除失败，抛出异常
        }
    }

    // 根据患者名称搜索患者
    @GetMapping("/search")
    public R search(@RequestParam String name) throws BizException {
        PatientSearchDto dto = new PatientSearchDto(); // 创建搜索DTO对象
        dto.setName(name); // 设置搜索条件：患者名称
        List<Patient> patientList = patientService.search(dto); // 调用服务层搜索方法
        return R.success(patientList); // 返回搜索结果
    }
}
