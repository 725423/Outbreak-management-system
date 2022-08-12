package cn.zb.project.controller;

import cn.zb.project.entity.ProvinceData;
import cn.zb.project.service.IndexService;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.ProvinceDataVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ChinaDataAdminController {

    @Autowired
    private IndexService indexService;


    /**
     * 模糊查询，带有分页
     * @return
     */
    @GetMapping("/listDataByPage")
    public DataView listDataByPage(ProvinceDataVo provinceDataVo){
        //创建分页的对象  当前页  每页限制大小
        /**
         * 分页构造函数
         * Params:
         * current – 当前页
         * size – 每页显示条数,即每页限制大小
         * 疑问：如何获得provinceDataVo.getPage(),provinceDataVo.getLimit()，即当前页面和每页显示条数
         */
        IPage<ProvinceData> page = new Page<>(provinceDataVo.getPage(),provinceDataVo.getLimit());

        //创建模糊查询条件    ,  通过value降序排列
        QueryWrapper<ProvinceData> queryWrapper = new QueryWrapper<>();
        //如果名字不为空，就按降序排列
        queryWrapper.like(!(provinceDataVo.getName() == null),"name",provinceDataVo.getName())
                .orderByDesc("value");

        //查询数据库
        /**
         * Params:
         * page – 分页查询条件（可以为 RowBounds.DEFAULT）
         * queryWrapper – 实体对象封装操作类（可以为 null）
         */
        indexService.page(page,queryWrapper);

        //返回数据封装
        DataView dataView = new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }


    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("/china/deleteById")
    public DataView deleteById(Integer id){
        indexService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除成功！");
        return dataView;
    }


    /**
     * 新增或者修改【id】
     * id：有值就是修改，无值就是新增
     * update provice_data set name = "" where id = ""
     * @param provinceData
     * @return
     */
    @PostMapping("/china/addOrUpdateChina")
    public DataView addOrUpdateChina(ProvinceData provinceData){
        boolean save = indexService.saveOrUpdate(provinceData);
        DataView dataView = new DataView();
        if(save){
            dataView.setCode(200);
            dataView.setMsg("添加或修改成功");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("添加或修改失败");
        return dataView;
    }


    /**
     * Excel的拖拽或者点击上传
     * 1.前天页面发送请求，上传文件MultipartFile HTTP
     * 2.Controller ，上传文件MultipartFile参数
     * 3.POI 解析文件 ，里面的数据一行一行全部解析出来
     * 4.每条数据出入数据库
     */
    @RequestMapping("/excelImportChina")
    public DataView excelImportChina(@RequestParam("file")MultipartFile file) throws IOException {
        DataView dataView = new DataView();

        //1.文件不能为空
        if (file.isEmpty()){
           dataView.setMsg("文件为空，不能上传");
        }

        //2.POI获取Excel解析数据, HSSFWorkbook是解析出来excel 2007 以前版本的，后缀名为xls的。
        //XSSFWorkbook是解析excel 2007 版的，后缀名为xlsx。
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = wb.getSheetAt(0);
//        HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
//        HSSFSheet sheet = wb.getSheetAt(0);

        //3.定义一个程序集合 接受 文件中的数据
        List<ProvinceData> list = new ArrayList<>();
        XSSFRow row = null;


        //4.解析数据库，装到集合里面
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            //定义实体
            ProvinceData provinceData = new ProvinceData();

            //每一行的数据，放到实体类中
            row = sheet.getRow(i);

            //解析数据
            provinceData.setName(row.getCell(0).getStringCellValue());
            provinceData.setValue((int)row.getCell(1).getNumericCellValue());

            //添加list集合
            list.add(provinceData);

        }
            //插入数据库
            indexService.saveBatch(list);

            dataView.setCode(200);

            dataView.setMsg("excel已经插入成功");

            return dataView;

    }


    /**
     * 导出中国疫情数据
     * 1.查询数据库
     * 2.建立Excel对象，封装数据
     * 3.建立输出流，输出文件
     */
    @RequestMapping("/excelOutPortChina")
    public void excelOutPortChina(HttpServletResponse response){
        //查询所有数据
        List<ProvinceData> list = indexService.list();

        //建立Excel对象，封装数据
        response.setCharacterEncoding("UTF-8");

        //创建Excel对象
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建sheet对象
        XSSFSheet sheet = wb.createSheet("中国疫情数据sheet1");

        //创建表头
        XSSFRow xssfRow = sheet.createRow(0);
        xssfRow.createCell(0).setCellValue("城市名称");
        xssfRow.createCell(1).setCellValue("确诊数量");

        //遍历数据，封装Excel工作对象
        for(ProvinceData data : list){
            XSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(data.getName());
            dataRow.createCell(1).setCellValue(data.getValue());
        }

        //建立输出流，输出浏览器文件
        OutputStream os = null;

        //设置Excel名字,输出类型编码
        try{
            response.setContentType("application/octet-stream;chartset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=" + new String("中国疫情数据表".getBytes(),"iso-8859-1")+".xlsx");

            //输出文件
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (os != null){
                    os.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
