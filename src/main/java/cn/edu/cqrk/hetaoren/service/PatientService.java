package cn.edu.cqrk.hetaoren.service;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.entity.dto.PatientSearchDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PatientService extends IService<Patient> {
    Patient add(Patient patient) throws BizException;
    boolean deleteById(Integer id) throws BizException;
    Patient update(Patient patient) throws BizException;
    boolean batchDelete(List<Integer> patientIds) throws BizException;
    List<Patient> search(PatientSearchDto dto) throws BizException;

}
