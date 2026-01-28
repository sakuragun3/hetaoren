package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.entity.pojo.Department;
import cn.edu.cqrk.hetaoren.mapper.DepartmentMapper;
import cn.edu.cqrk.hetaoren.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
