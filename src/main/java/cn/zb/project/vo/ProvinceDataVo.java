package cn.zb.project.vo;

import cn.zb.project.entity.ProvinceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProvinceDataVo extends ProvinceData {
    private Integer page;
    private Integer limit;
}
