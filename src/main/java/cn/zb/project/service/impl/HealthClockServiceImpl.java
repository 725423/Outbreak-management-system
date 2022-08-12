package cn.zb.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.HealthClock;
import cn.zb.project.service.HealthClockService;
import cn.zb.project.mapper.HealthClockMapper;
import org.springframework.stereotype.Service;

/**
* @author 22906
* @description 针对表【health_clock】的数据库操作Service实现
* @createDate 2022-06-30 15:50:38
*/
@Service
public class HealthClockServiceImpl extends ServiceImpl<HealthClockMapper, HealthClock>
    implements HealthClockService{

}




