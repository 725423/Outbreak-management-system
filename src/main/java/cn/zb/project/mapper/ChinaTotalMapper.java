package cn.zb.project.mapper;

import cn.zb.project.entity.ChinaTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 22906
* @description 针对表【china_total】的数据库操作Mapper
* @createDate 2022-07-01 11:18:59
* @Entity cn.zb.project.entity.ChinaTotal
*/
public interface ChinaTotalMapper extends BaseMapper<ChinaTotal> {

    @Select("select max(id) from china_total")
    Integer maxID();
}




