package cn.edu.cqrk.hetaoren.controller;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.common.BizExceptionCode;
import cn.edu.cqrk.hetaoren.common.R;
import cn.edu.cqrk.hetaoren.entity.dto.CaseIdDto;
import cn.edu.cqrk.hetaoren.entity.dto.CaseIdsDto;
import cn.edu.cqrk.hetaoren.entity.dto.CaseSearchDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Case;
import cn.edu.cqrk.hetaoren.service.CaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/case")
public class CaseController implements Serializable {

    @Autowired
    private CaseService caseService;

    // 获取所有病例信息，支持分页
    @GetMapping("/findCaseAll")
    public Map<String, Object> findCaseAll(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int limit) {
        IPage<Case> casePage = new Page<>(page, limit);  // 分页对象
        IPage<Case> result = caseService.page(casePage);  // 查询病例的分页结果

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);  // 成功状态码
        response.put("data", result.getRecords());  // 病例数据
        response.put("total", result.getTotal());  // 总记录数
        return response;
    }

    // 添加病例
    @PostMapping("/add")
    public R add(@RequestBody Case caseInfo) throws BizException {
        Case newCase = caseService.add(caseInfo);  // 调用服务层添加病例
        return R.success(newCase);  // 返回添加成功结果
    }

    // 根据ID删除病例
    @PostMapping("/deleteById")
    public R deleteById(@RequestBody CaseIdDto dto) throws BizException {
        Integer id = dto.getId();
        if (id <= 0) {
            throw new BizException(BizExceptionCode.ILLEGAL_ID);  // 非法ID异常
        }
        boolean isDeleted = caseService.deleteById(id);  // 删除病例
        if (isDeleted) {
            return R.success("病例删除成功");  // 删除成功提示
        } else {
            throw new BizException(BizExceptionCode.CASE_NOT_FOUND);  // 病例未找到异常
        }
    }

    // 更新病例信息
    @PostMapping("/update")
    public R update(@RequestBody Case caseInfo) throws BizException {
        Case updatedCase = caseService.update(caseInfo);  // 更新病例信息
        return R.success(updatedCase);  // 返回更新成功结果
    }

    // 批量删除病例
    @PostMapping("/batchDelete")
    public R batchDelete(@RequestBody CaseIdsDto dto) throws BizException {
        List<Integer> caseIds = dto.getCaseIds();  // 获取要删除的病例ID列表
        boolean isDeleted = caseService.batchDelete(caseIds);  // 批量删除病例
        if (isDeleted) {
            return R.success("病例批量删除成功");  // 批量删除成功提示
        } else {
            throw new BizException(BizExceptionCode.FAILED_DELETE);  // 删除失败异常
        }
    }

    // 根据症状搜索病例
    @GetMapping("/search")
    public R search(@RequestParam String symptoms) throws BizException {
        CaseSearchDto dto = new CaseSearchDto();
        dto.setSymptoms(symptoms);  // 设置搜索的症状条件
        List<Case> caseList = caseService.search(dto);  // 搜索病例
        return R.success(caseList);  // 返回搜索结果
    }
}
