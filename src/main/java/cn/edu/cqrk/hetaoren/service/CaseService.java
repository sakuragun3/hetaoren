package cn.edu.cqrk.hetaoren.service;

import cn.edu.cqrk.hetaoren.common.BizException;
import cn.edu.cqrk.hetaoren.entity.dto.CaseSearchDto;
import cn.edu.cqrk.hetaoren.entity.pojo.Case;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CaseService extends IService<Case> {
    Case add(Case caseInfo) throws BizException;

    boolean deleteById(Integer id) throws BizException;

    Case update(Case caseInfo) throws BizException;

    boolean batchDelete(List<Integer> caseIds) throws BizException;

    List<Case> search(CaseSearchDto dto) throws BizException;

}
