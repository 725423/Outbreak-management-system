package cn.zb.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.News;
import cn.zb.project.service.NewsService;
import cn.zb.project.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 22906
* @description 针对表【news】的数据库操作Service实现
* @createDate 2022-07-18 15:57:26
*/
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
    implements NewsService{

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> listNewsLimit5() {
        return newsMapper.listNewsLimit5();
    }
}




