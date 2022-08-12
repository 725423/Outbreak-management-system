package cn.zb.project.service;

import cn.zb.project.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22906
* @description 针对表【news】的数据库操作Service
* @createDate 2022-07-18 15:57:26
*/
public interface NewsService extends IService<News> {

    List<News> listNewsLimit5();
}
