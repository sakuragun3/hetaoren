package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.entity.dto.ActiveDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Active;
import cn.edu.cqrk.hetaoren.service.ActiveService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/active")
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    // 获取所有患教活动信息，带分页功能
    @GetMapping("/findAll")
    public Map<String, Object> findAllActivities(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int limit) {
        IPage<Active> activityPage = new Page<>(page, limit);  // 分页对象
        IPage<Active> result = activeService.page(activityPage);  // 分页查询结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);  // 返回码，0表示成功
        response.put("data", result.getRecords());  // 返回查询的活动记录
        response.put("total", result.getTotal());  // 总记录数
        return response;
    }

    // 新增患教活动
    @PostMapping("/add")
    public Map<String, Object> addActivity(@RequestBody ActiveDto activeDto) {
        Active active = new Active();
        active.setActivityName(activeDto.getActivityName());  // 设置活动名称
        active.setDoctorName(activeDto.getDoctorName());  // 设置医生名称
        active.setPatientName(activeDto.getPatientName());  // 设置患者名称
        active.setStartTime(activeDto.getStartTime());  // 设置活动开始时间
        active.setEndTime(activeDto.getEndTime());  // 设置活动结束时间
        active.setParticipantCount(activeDto.getParticipantCount());  // 设置参与人数

        boolean isSaved = activeService.save(active);  // 保存活动信息
        Map<String, Object> response = new HashMap<>();
        response.put("code", isSaved ? 0 : -1);  // 成功返回0，失败返回-1
        response.put("msg", isSaved ? "添加成功" : "添加失败");  // 提示信息
        return response;
    }

    // 更新患教活动信息
    @PostMapping("/update")
    public Map<String, Object> updateActivity(@RequestBody ActiveDto dto) {
        System.out.println("Updating Active ID: " + dto.getId());  // 输出更新的活动ID

        if (dto.getId() == null) {  // 检查活动ID是否为空
            Map<String, Object> response = new HashMap<>();
            response.put("code", -1);  // 错误码
            response.put("msg", "活动ID不能为空");  // 提示信息
            return response;
        }

        Active active = new Active();
        active.setId(dto.getId());  // 设置活动ID
        active.setActivityName(dto.getActivityName());  // 设置活动名称
        active.setDoctorName(dto.getDoctorName());  // 设置医生名称
        active.setPatientName(dto.getPatientName());  // 设置患者名称
        active.setStartTime(dto.getStartTime());  // 设置活动开始时间
        active.setEndTime(dto.getEndTime());  // 设置活动结束时间
        active.setParticipantCount(dto.getParticipantCount());  // 设置参与人数

        boolean isUpdated = activeService.updateById(active);  // 更新活动信息

        Map<String, Object> response = new HashMap<>();
        if (isUpdated) {
            response.put("code", 0);  // 成功返回0
            response.put("msg", "更新成功");  // 提示更新成功
        } else {
            response.put("code", -1);  // 失败返回-1
            response.put("msg", "更新失败");  // 提示更新失败
        }
        return response;
    }

    // 根据ID获取患教活动信息
    @GetMapping("/getById")
    public Map<String, Object> getActivityById(@RequestParam Integer id) {
        Active active = activeService.getById(id);  // 根据ID获取活动信息

        Map<String, Object> response = new HashMap<>();
        if (active != null) {
            response.put("code", 0);  // 成功返回0
            response.put("data", active);  // 返回活动数据
        } else {
            response.put("code", -1);  // 失败返回-1
            response.put("msg", "获取患教活动信息失败");  // 提示获取失败
        }
        return response;
    }

    // 删除患教活动
    @PostMapping("/delete")
    public Map<String, Object> deleteActivity(@RequestParam Integer id) {
        boolean isDeleted = activeService.removeById(id);  // 根据ID删除活动
        Map<String, Object> response = new HashMap<>();
        response.put("code", isDeleted ? 0 : -1);  // 成功返回0，失败返回-1
        response.put("msg", isDeleted ? "删除成功" : "删除失败");  // 提示信息
        return response;
    }

    // 批量删除患教活动
    @PostMapping("/batchDelete")
    public Map<String, Object> batchDeleteActivities(@RequestBody List<Integer> ids) {
        boolean isDeleted = activeService.removeByIds(ids);  // 批量删除活动
        Map<String, Object> response = new HashMap<>();
        response.put("code", isDeleted ? 0 : -1);  // 成功返回0，失败返回-1
        response.put("msg", isDeleted ? "批量删除成功" : "批量删除失败");  // 提示信息
        return response;
    }

    // 搜索患教活动
    @PostMapping("/search")
    public Map<String, Object> searchActivities(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int limit,
                                                @RequestParam(required = false) String activityName) {
        IPage<Active> activityPage = new Page<>(page, limit);  // 分页对象

        // 创建查询条件
        QueryWrapper<Active> queryWrapper = new QueryWrapper<>();
        if (activityName != null && !activityName.isEmpty()) {
            queryWrapper.like("activity_name", activityName);  // 按活动名称进行模糊查询
        }

        IPage<Active> result = activeService.page(activityPage, queryWrapper);  // 查询结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);  // 成功返回0
        response.put("data", result.getRecords());  // 返回活动记录
        response.put("total", result.getTotal());  // 返回总记录数
        return response;
    }
}
