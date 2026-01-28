package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.entity.dto.HospitalDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Hospital;
import cn.edu.cqrk.hetaoren.service.HospitalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin // 允许跨域请求
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // 查询所有医院信息并分页
    @GetMapping("/findAll")
    public Map<String, Object> findAllHospitals(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int limit) {
        IPage<Hospital> hospitalPage = new Page<>(page, limit); // 分页查询对象
        IPage<Hospital> result = hospitalService.page(hospitalPage); // 获取分页结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 成功状态码
        response.put("data", result.getRecords()); // 当前页的数据
        response.put("total", result.getTotal()); // 数据总量

        return response;
    }

    // 根据条件搜索医院信息
    @GetMapping("/search")
    public Map<String, Object> searchHospitals(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam(required = false) String hospital_name) {
        IPage<Hospital> hospitalPage = new Page<>(page, limit); // 分页查询对象
        QueryWrapper<Hospital> queryWrapper = new QueryWrapper<>(); // 查询条件

        // 如果提供了医院名称，则使用 like 进行模糊查询
        if (hospital_name != null && !hospital_name.isEmpty()) {
            queryWrapper.lambda().like(Hospital::getHospitalName, hospital_name);
        }

        IPage<Hospital> result = hospitalService.page(hospitalPage, queryWrapper); // 获取分页查询结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 成功状态码
        response.put("data", result.getRecords()); // 当前页的数据
        response.put("total", result.getTotal()); // 数据总量

        return response;
    }

    // 添加医院信息
    @PostMapping("/add")
    public Map<String, Object> addHospital(@RequestBody HospitalDto dto) {
        Hospital hospital = new Hospital();
        hospital.setHospitalName(dto.getHospitalName()); // 设置医院名称
        hospital.setHospitalPhone(dto.getPhone()); // 设置医院电话
        hospital.setHospitalAddress(dto.getHospitalAddress()); // 设置医院地址
        hospital.setDeanName(dto.getDeanName()); // 设置院长姓名

        boolean isSaved = hospitalService.save(hospital); // 保存医院信息

        Map<String, Object> response = new HashMap<>();
        if (isSaved) {
            response.put("code", 0); // 成功状态码
            response.put("msg", "添加成功"); // 添加成功消息
        } else {
            response.put("code", -1); // 失败状态码
            response.put("msg", "添加失败"); // 添加失败消息
        }
        return response;
    }

    // 删除医院信息
    @PostMapping("/delete")
    public Map<String, Object> deleteHospital(@RequestParam Integer hospitalId) {
        boolean isDeleted = hospitalService.removeById(hospitalId); // 根据医院ID删除

        Map<String, Object> response = new HashMap<>();
        if (isDeleted) {
            response.put("code", 0); // 成功状态码
            response.put("msg", "删除成功"); // 删除成功消息
        } else {
            response.put("code", -1); // 失败状态码
            response.put("msg", "删除失败"); // 删除失败消息
        }
        return response;
    }

    // 批量删除医院信息
    @PostMapping("/batchDelete")
    public Map<String, Object> batchDeleteHospitals(@RequestBody List<Integer> hospitalIds) {
        boolean isDeleted = hospitalService.removeByIds(hospitalIds); // 批量删除医院

        Map<String, Object> response = new HashMap<>();
        if (isDeleted) {
            response.put("code", 0); // 成功状态码
            response.put("msg", "删除成功"); // 删除成功消息
        } else {
            response.put("code", -1); // 失败状态码
            response.put("msg", "删除失败"); // 删除失败消息
        }
        return response;
    }

    // 更新医院信息
    @PostMapping("/update")
    public Map<String, Object> updateHospital(@RequestBody HospitalDto dto) {
        System.out.println("Updating Hospital ID: " + dto.getHospitalId()); // 调试信息，打印医院ID

        // 如果 hospitalId 为空，返回错误信息
        if (dto.getHospitalId() == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", -1); // 失败状态码
            response.put("msg", "hospitalId 不能为空"); // 返回错误消息
            return response;
        }

        // 更新医院实体对象
        Hospital hospital = new Hospital();
        hospital.setHospitalId(dto.getHospitalId()); // 设置医院ID
        hospital.setHospitalName(dto.getHospitalName()); // 设置医院名称
        hospital.setHospitalPhone(dto.getPhone()); // 设置医院电话
        hospital.setHospitalAddress(dto.getHospitalAddress()); // 设置医院地址
        hospital.setDeanName(dto.getDeanName()); // 设置院长姓名

        boolean isUpdated = hospitalService.updateById(hospital); // 更新医院信息

        Map<String, Object> response = new HashMap<>();
        if (isUpdated) {
            response.put("code", 0); // 成功状态码
            response.put("msg", "更新成功"); // 更新成功消息
        } else {
            response.put("code", -1); // 失败状态码
            response.put("msg", "更新失败"); // 更新失败消息
        }
        return response;
    }

    // 根据医院 ID 获取医院信息
    @GetMapping("/getById")
    public Map<String, Object> getHospitalById(@RequestParam Integer hospitalId) {
        Hospital hospital = hospitalService.getById(hospitalId); // 根据医院ID查询

        Map<String, Object> response = new HashMap<>();
        if (hospital != null) {
            response.put("code", 0); // 成功状态码
            response.put("data", hospital); // 返回医院对象
        } else {
            response.put("code", -1); // 失败状态码
            response.put("msg", "获取医院信息失败"); // 获取失败消息
        }
        return response;
    }
}
