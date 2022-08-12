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
 * @TableName health_clock
 */
@TableName(value ="health_clock")
@Data
public class HealthClock implements Serializable {
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
    private String sex;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String morningTemp;

    /**
     * 
     */
    private String afternoonTemp;

    /**
     * 
     */
    private String nightTemp;

    /**
     * 
     */
    private String feverAndCough;

    /**
     * 
     */
    private String recentHome;

    /**
     * 
     */
    private String riskZone;

    /**
     * 
     */
    private String recentZone;

    /**
     * 
     */
    private String healthStatus;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}