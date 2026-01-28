package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.entity.pojo.Doctor;
import cn.edu.cqrk.hetaoren.mapper.DoctorMapper;
import cn.edu.cqrk.hetaoren.service.DoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
}
