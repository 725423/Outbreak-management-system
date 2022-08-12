package cn.zb.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer pid;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String permission;

    /**
     * 
     */
    private String icon;

    /**
     * 
     */
    private String href;

    /**
     * 
     */
    private Integer open;

    /**
     * 
     */
    private Integer ordernum;

    private Integer available;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}