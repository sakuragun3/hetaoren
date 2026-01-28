package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.common.BizExceptionCode;
import cn.edu.cqrk.hetaoren.entity.dto.CaseSearchDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Case;
import cn.edu.cqrk.hetaoren.mapper.CaseMapper;
import cn.edu.cqrk.hetaoren.service.CaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

    /**
     * 添加病例
     * @param caseInfo 病例信息
     * @return 返回添加的病例信息
     * @throws BizException 业务异常，例如症状字段不合法等
     */
    @Override
    public Case add(Case caseInfo) throws BizException {
        // 校验必填字段
        if (caseInfo.getSymptoms() == null || caseInfo.getSymptoms().isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_SYMPTOMS); // 抛出非法症状异常
        }

        // 设置创建时间
        caseInfo.setCreated(new Date());

        // 保存病例信息
        boolean save = save(caseInfo);
        if (!save) {
            throw new BizException(BizExceptionCode.FAILED_ADD); // 抛出添加失败异常
        }

        return caseInfo; // 返回添加的病例信息
    }

    /**
     * 根据ID删除病例
     * @param id 病例ID
     * @return 返回删除操作的结果
     * @throws BizException 业务异常，例如病例未找到等
     */
    @Override
    public boolean deleteById(Integer id) throws BizException {
        Case existingCase = this.getById(id); // 查找病例
        if (existingCase == null) {
            throw new BizException(BizExceptionCode.CASE_NOT_FOUND); // 抛出病例未找到异常
        }
        return this.removeById(id); // 删除病例
    }

    /**
     * 更新病例信息
     * @param caseInfo 病例信息
     * @return 返回更新后的病例信息
     * @throws BizException 业务异常，例如病例未找到等
     */
    @Override
    public Case update(Case caseInfo) throws BizException {
        // 检查病例是否存在
        Case existingCase = this.getById(caseInfo.getId());
        if (existingCase == null) {
            throw new BizException(BizExceptionCode.CASE_NOT_FOUND); // 抛出病例未找到异常
        }

        // 更新病例信息，避免覆盖未修改的字段
        caseInfo.setUpdated(new Date());

        boolean updateSuccess = this.updateById(caseInfo);
        if (!updateSuccess) {
            throw new BizException(BizExceptionCode.FAILED_UPDATE); // 抛出更新失败异常
        }

        return caseInfo; // 返回更新后的病例信息
    }

    /**
     * 批量删除病例
     * @param caseIds 病例ID列表
     * @return 返回批量删除操作的结果
     * @throws BizException 业务异常，例如ID不合法或病例未找到等
     */
    @Override
    public boolean batchDelete(List<Integer> caseIds) throws BizException {
        if (caseIds == null || caseIds.isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_ID); // 抛出非法ID异常
        }

        List<Case> existingCases = this.listByIds(caseIds);
        if (existingCases.size() != caseIds.size()) {
            throw new BizException(BizExceptionCode.CASE_NOT_FOUND); // 抛出病例未找到异常
        }

        boolean deleteSuccess = this.removeByIds(caseIds);
        if (!deleteSuccess) {
            throw new BizException(BizExceptionCode.FAILED_DELETE); // 抛出删除失败异常
        }

        return true; // 返回删除成功
    }

    /**
     * 根据症状搜索病例
     * @param dto 包含症状信息的搜索传输对象
     * @return 返回符合条件的病例列表
     * @throws BizException 业务异常，例如症状字段不合法等
     */
    @Override
    public List<Case> search(CaseSearchDto dto) throws BizException {
        String symptoms = dto.getSymptoms();

        // 校验症状字段是否合法
        if (symptoms == null || symptoms.isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_SYMPTOMS); // 抛出非法症状异常
        }

        // 构建查询条件，仅按症状字段进行模糊查询
        LambdaQueryWrapper<Case> queryWrapper = Wrappers.lambdaQuery(Case.class);
        queryWrapper.like(Case::getSymptoms, symptoms); // 只根据症状模糊查询

        // 执行查询并返回结果
        return this.list(queryWrapper); // 返回查询结果
    }
}
