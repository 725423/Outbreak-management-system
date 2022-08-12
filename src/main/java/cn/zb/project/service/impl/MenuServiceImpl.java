package cn.zb.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.Menu;
import cn.zb.project.service.MenuService;
import cn.zb.project.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 22906
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2022-07-02 14:54:22
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




