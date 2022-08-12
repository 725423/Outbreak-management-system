package cn.zb.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.ChinaTotal;
import cn.zb.project.service.ChinaTotalService;
import cn.zb.project.mapper.ChinaTotalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 22906
* @description 针对表【china_total】的数据库操作Service实现
* @createDate 2022-07-01 11:18:59
*/
@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChinaTotalMapper, ChinaTotal>
    implements ChinaTotalService{

    @Autowired
    private ChinaTotalMapper chinaTotalMapper;

    @Override
    public Integer maxID() {
        return chinaTotalMapper.maxID();
    }
}




