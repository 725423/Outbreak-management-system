package cn.zb.project.mapper;

import cn.zb.project.entity.ProvinceData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 22906
* @description 针对表【province_data】的数据库操作Mapper
* @createDate 2022-06-26 22:49:28
* @Entity cn.zb.project.entity.Data
*/
public interface IndexMapper extends BaseMapper<ProvinceData> {


    @Select("select * from province_data order by id desc limit 34")
    List<ProvinceData> listOrderByIdLimit34();
}




