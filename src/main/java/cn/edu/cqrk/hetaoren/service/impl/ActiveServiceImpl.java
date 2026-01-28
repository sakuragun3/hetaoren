package cn.edu.cqrk.hetaoren.service.impl;

import cn.edu.cqrk.hetaoren.entity.pojo.Active;
import cn.edu.cqrk.hetaoren.mapper.ActiveMapper;
import cn.edu.cqrk.hetaoren.service.ActiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ActiveServiceImpl extends ServiceImpl<ActiveMapper, Active> implements ActiveService {
}
