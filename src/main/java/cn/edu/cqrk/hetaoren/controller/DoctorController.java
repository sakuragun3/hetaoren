package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.entity.dto.DoctorDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Doctor;
import cn.edu.cqrk.hetaoren.service.DoctorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin // 允许跨域请求
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    // 通过构造函数注入 DoctorService，避免使用 @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // 获取所有医生，支持分页
    @GetMapping("/findAll")
    public Map<String, Object> findAllDoctors(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int limit) {
        IPage<Doctor> doctorPage = new Page<>(page, limit); // 创建分页对象
        IPage<Doctor> result = doctorService.page(doctorPage); // 查询医生信息分页结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 成功状态码
        response.put("data", result.getRecords()); // 当前页的数据记录
        response.put("total", result.getTotal()); // 总记录数

        return response;
    }

    // 根据医生ID获取医生信息
    @GetMapping("/getById")
    public Map<String, Object> getDoctorById(@RequestParam Integer doctorId) {
        Doctor doctor = doctorService.getById(doctorId); // 根据ID查询医生

        Map<String, Object> response = new HashMap<>();
        if (doctor != null) {
            response.put("code", 0); // 成功状态码
            response.put("data", doctor); // 返回医生对象
        } else {
            response.put("code", -1); // 错误状态码
            response.put("msg", "获取医生信息失败");
        }
        return response;
    }

    // 添加新医生
    @PostMapping("/add")
    public Map<String, Object> addDoctor(@RequestBody DoctorDto dto) {
        // DTO 转换为实体类
        Doctor doctor = new Doctor();
        doctor.setDoctorName(dto.getDoctorName());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setHospitalId(dto.getHospitalId());

        boolean isSaved = doctorService.save(doctor); // 保存医生记录

        Map<String, Object> response = new HashMap<>();
        response.put("code", isSaved ? 0 : -1); // 返回状态码，0 表示成功，-1 表示失败
        response.put("msg", isSaved ? "添加成功" : "添加失败"); // 成功或失败的消息
        return response;
    }

    // 更新医生信息
    @PostMapping("/update")
    public Map<String, Object> updateDoctor(@RequestBody DoctorDto dto) {
        // 根据 DTO 转换为实体类
        Doctor doctor = new Doctor();
        doctor.setDoctorId(dto.getDoctorId());
        doctor.setDoctorName(dto.getDoctorName());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setHospitalId(dto.getHospitalId());

        boolean isUpdated = doctorService.updateById(doctor); // 根据 ID 更新医生记录

        Map<String, Object> response = new HashMap<>();
        response.put("code", isUpdated ? 0 : -1); // 更新结果状态码
        response.put("msg", isUpdated ? "更新成功" : "更新失败"); // 更新结果消息
        return response;
    }

    // 根据医生ID删除医生
    @PostMapping("/delete")
    public Map<String, Object> deleteDoctor(@RequestParam Integer doctorId) {
        boolean isDeleted = doctorService.removeById(doctorId); // 根据 ID 删除医生记录

        Map<String, Object> response = new HashMap<>();
        response.put("code", isDeleted ? 0 : -1); // 删除结果状态码
        response.put("msg", isDeleted ? "删除成功" : "删除失败"); // 删除结果消息
        return response;
    }

    // 批量删除医生
    @PostMapping("/batchDelete")
    public Map<String, Object> batchDeleteDoctors(@RequestBody List<Integer> doctorIds) {
        boolean isDeleted = doctorService.removeByIds(doctorIds); // 批量删除医生

        Map<String, Object> response = new HashMap<>();
        response.put("code", isDeleted ? 0 : -1); // 删除结果状态码
        response.put("msg", isDeleted ? "批量删除成功" : "批量删除失败"); // 删除结果消息
        return response;
    }

    // 根据条件搜索医生
    @GetMapping("/search")
    public Map<String, Object> searchDoctors(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int limit,
                                             @RequestParam(required = false) String doctor_name) {
        IPage<Doctor> doctorPage = new Page<>(page, limit); // 创建分页对象

        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        // 如果传入医生姓名，使用 like 模糊查询
        if (doctor_name != null && !doctor_name.isEmpty()) {
            queryWrapper.lambda().like(Doctor::getDoctorName, doctor_name);
        }

        IPage<Doctor> result = doctorService.page(doctorPage, queryWrapper); // 执行分页查询

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 成功状态码
        response.put("data", result.getRecords()); // 返回医生列表
        response.put("total", result.getTotal()); // 返回总记录数
        return response;
    }
}
