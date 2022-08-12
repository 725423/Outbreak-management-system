package cn.zb.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.GlobalData;
import cn.zb.project.service.GlobalDataService;
import cn.zb.project.mapper.GlobalDataMapper;
import org.springframework.stereotype.Service;

/**
* @author 22906
* @description 针对表【global_data】的数据库操作Service实现
* @createDate 2022-06-27 11:08:22
*/
@Service
public class GlobalDataServiceImpl extends ServiceImpl<GlobalDataMapper, GlobalData>
    implements GlobalDataService{

}




