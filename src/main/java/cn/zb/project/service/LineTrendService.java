package cn.zb.project.service;

import cn.zb.project.entity.LineTrend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22906
* @description 针对表【line_trend】的数据库操作Service
* @createDate 2022-06-27 11:22:04
*/
public interface LineTrendService extends IService<LineTrend> {

    List<LineTrend> findSevenData();
}
