package cn.zb.project.mapper;

import cn.zb.project.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 22906
* @description 针对表【news】的数据库操作Mapper
* @createDate 2022-07-18 15:57:26
* @Entity cn.zb.project.entity.News
*/
public interface NewsMapper extends BaseMapper<News> {

    @Select("select * from news order by create_time desc limit 5")
    List<News> listNewsLimit5();
}




