package cn.zb.project.service.impl;

import cn.zb.project.mapper.GlobalDataMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.LineTrend;
import cn.zb.project.service.LineTrendService;
import cn.zb.project.mapper.LineTrendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 22906
* @description 针对表【line_trend】的数据库操作Service实现
* @createDate 2022-06-27 11:22:04
*/
@Service
public class LineTrendServiceImpl extends ServiceImpl<LineTrendMapper, LineTrend>
    implements LineTrendService{

    @Autowired
    private LineTrendMapper lineTrendMapper;


    @Override
    public List<LineTrend> findSevenData() {
      List<LineTrend> list =  lineTrendMapper.findSevenData();
        return list;
    }
}




