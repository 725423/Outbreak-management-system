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
 * @TableName china_total
 */
@TableName(value ="china_total")
@Data
public class ChinaTotal implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer confirm;

    /**
     * 
     */
    private Integer input;

    /**
     * 
     */
    private Integer severe;

    /**
     * 
     */
    private Integer heal;

    /**
     * 
     */
    private Integer dead;

    /**
     * 
     */
    private Integer suspect;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}