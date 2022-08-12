package cn.zb.project.mapper;

import cn.zb.project.entity.LineTrend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 22906
* @description 针对表【line_trend】的数据库操作Mapper
* @createDate 2022-06-27 11:22:04
* @Entity cn.zb.project.entity.LineTrend
*/
public interface LineTrendMapper extends BaseMapper<LineTrend> {

    /**
     * 接口：只有方法定义
     * 实现类：
     * XML
     * @Select
     * @return
     */
    //根据创建时间升序排序
    @Select("select * from line_trend order by create_time limit 7")
    List<LineTrend> findSevenData();
}




