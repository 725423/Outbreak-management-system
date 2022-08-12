package cn.zb.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String sex;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String img;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private Integer card;

    /**
     * 
     */

    /**
     * 
     */
    private Integer banJiId;

    /**
     * 
     */
    private Integer xueYuanId;

    /**
     * 
     */
    private Integer teacherId;

    /**
     * 非数据库列
     */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String banJiName;

    @TableField(exist = false)
    private String xueYuanName;

    @TableField(exist = false)
    private String teacherName;
}