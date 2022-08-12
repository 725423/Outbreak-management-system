package cn.zb.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.ProvinceData;
import cn.zb.project.service.IndexService;
import cn.zb.project.mapper.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 22906
* @description 针对表【province_data】的数据库操作Service实现
* @createDate 2022-06-26 22:49:28
*/


@Service
public class IndexServiceImpl extends ServiceImpl<IndexMapper, ProvinceData>
    implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<ProvinceData> listOrderByIdLimit34() {
        return indexMapper.listOrderByIdLimit34();
    }
}




