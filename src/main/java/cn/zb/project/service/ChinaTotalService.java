package cn.zb.project.service;

import cn.zb.project.entity.ChinaTotal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 22906
* @description 针对表【china_total】的数据库操作Service
* @createDate 2022-07-01 11:18:59
*/
public interface ChinaTotalService extends IService<ChinaTotal> {

    Integer maxID();
}
