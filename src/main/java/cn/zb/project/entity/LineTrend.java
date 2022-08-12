package cn.zb.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName line_trend
 */
@TableName(value ="line_trend")
@Data
public class LineTrend implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 确证病例
     */
    private Integer confirm;

    /**
     *  隔离人数
     */
    private Integer isolation;

    /**
     * 治愈人数
     */
    private Integer cure;

    /**
     * 死亡人数
     */
    private Integer dead;

    /**
     * 疑似病例
     */
    private Integer similar;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}