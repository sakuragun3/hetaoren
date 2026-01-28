package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.entity.pojo.Hospital;
import cn.edu.cqrk.hetaoren.mapper.HospitalMapper;
import cn.edu.cqrk.hetaoren.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {
}
