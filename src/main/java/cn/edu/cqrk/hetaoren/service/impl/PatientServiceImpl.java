package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.common.BizExceptionCode;
import cn.edu.cqrk.hetaoren.entity.dto.PatientSearchDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Patient;
import cn.edu.cqrk.hetaoren.mapper.PatientMapper;
import cn.edu.cqrk.hetaoren.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    /**
     * 添加患者信息
     * @param patient 患者对象
     * @return 返回新添加的患者对象
     * @throws BizException 业务异常，例如手机号不合法、手机号已存在等
     */
    @Override
    public Patient add(Patient patient) throws BizException {
        // 手机号格式校验
        String phone = patient.getPhone();
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            // 抛出手机号不合法的异常
            throw new BizException(BizExceptionCode.ILLEGAL_PHONE);
        }

        // 构建查询条件，按手机号查询患者
        LambdaQueryWrapper<Patient> lambdaQuery = Wrappers.lambdaQuery(Patient.class);
        lambdaQuery.eq(Patient::getPhone, phone);
        Patient existingPatient = this.getOne(lambdaQuery);

        // 如果患者已存在，抛出异常
        if (existingPatient != null) {
            throw new BizException(BizExceptionCode.EXIST_PHONE);
        }

        // 设置创建时间为当前时间
        patient.setCreated(new Date());

        // 保存患者到数据库
        boolean save = save(patient);

        // 如果保存失败，抛出添加失败的业务异常
        if (!save) {
            throw new BizException(BizExceptionCode.FAILED_ADD);
        }

        return patient; // 返回新添加的患者对象
    }

    /**
     * 根据ID删除患者
     * @param id 患者ID
     * @return 返回删除操作的结果
     */
    @Override
    public boolean deleteById(Integer id) {
        // 检查用户是否存在
        Patient patient = this.getById(id);
        if (patient == null) {
            return false; // 用户不存在，返回false
        }
        // 执行删除操作
        return this.removeById(id); // 返回删除结果
    }

    /**
     * 更新患者信息
     * @param patient 患者对象
     * @return 返回更新后的患者对象
     * @throws BizException 业务异常，例如手机号不合法、患者未找到等
     */
    @Override
    public Patient update(Patient patient) throws BizException {
        // 校验手机号格式
        String phone = patient.getPhone();
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new BizException(BizExceptionCode.ILLEGAL_PHONE);
        }

        // 检查患者是否存在
        Patient existingPatient = this.getById(patient.getId());
        if (existingPatient == null) {
            throw new BizException(BizExceptionCode.USER_NOT_FOUND);
        }

        // 更新患者信息，避免覆盖未修改的字段
        if (!patient.getPhone().equals(existingPatient.getPhone())) {
            // 检查手机号是否已存在
            LambdaQueryWrapper<Patient> queryWrapper = Wrappers.lambdaQuery(Patient.class);
            queryWrapper.eq(Patient::getPhone, patient.getPhone());
            if (this.count(queryWrapper) > 0) {
                throw new BizException(BizExceptionCode.EXIST_PHONE);
            }
        }

        // 将更新的数据复制到现有患者对象中
        BeanUtils.copyProperties(patient, existingPatient, "id", "created"); // 忽略 id 和 created 字段

        // 更新患者的修改时间
        existingPatient.setUpdated(new Date());

        // 保存更新后的患者对象
        boolean updateSuccess = this.updateById(existingPatient);
        if (!updateSuccess) {
            throw new BizException(BizExceptionCode.FAILED_UPDATE);
        }

        // 返回更新后的患者对象
        return existingPatient;
    }

    /**
     * 批量删除患者
     * @param patientIds 患者ID列表
     * @return 返回批量删除操作的结果
     * @throws BizException 业务异常，例如ID不合法或患者未找到等
     */
    @Override
    public boolean batchDelete(List<Integer> patientIds) throws BizException {
        // 检查用户ID列表是否为空
        if (patientIds == null || patientIds.isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_ID);
        }

        // 检查用户是否存在，返回存在的用户数量
        List<Patient> existingUsers = this.listByIds(patientIds);
        if (existingUsers.size() != patientIds.size()) {
            throw new BizException(BizExceptionCode.USER_NOT_FOUND); // 存在用户不存在的情况
        }

        // 执行批量删除操作
        boolean deleteSuccess = this.removeByIds(patientIds);
        if (!deleteSuccess) {
            throw new BizException(BizExceptionCode.FAILED_DELETE); // 删除失败的异常处理
        }

        return true; // 返回删除成功的结果
    }

    /**
     * 根据姓名搜索患者
     * @param dto 包含姓名信息的搜索传输对象
     * @return 返回符合条件的患者列表
     * @throws BizException 业务异常，例如姓名不合法等
     */
    @Override
    public List<Patient> search(PatientSearchDto dto) throws BizException {
        String name = dto.getName();

        // 校验姓名格式
        if (name == null || name.isEmpty()) {
            throw new BizException(BizExceptionCode.ILLEGAL_NAME);
        }

        // 构建查询条件
        LambdaQueryWrapper<Patient> queryWrapper = Wrappers.lambdaQuery(Patient.class);
        queryWrapper.like(Patient::getName, name); // 模糊查询

        // 查询患者列表
        List<Patient> patients = this.list(queryWrapper);

        return patients; // 直接返回患者列表
    }
}
