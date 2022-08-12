package cn.zb.project.service;

import cn.zb.project.entity.ProvinceData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22906
* @description 针对表【province_data】的数据库操作Service
* @createDate 2022-06-26 22:49:28
*/
public interface IndexService extends IService<ProvinceData> {

    List<ProvinceData> listOrderByIdLimit34();
}
