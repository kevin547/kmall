package com.jjsj.mall.user.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 签到记录表 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_sign")
@ApiModel(value = "UserSign对象", description = "签到记录表")
public class UserSignVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "签到说明")
    private String title;

    ;
    @ApiModelProperty(value = "获得积分")
    private Integer number;
    @ApiModelProperty(value = "签到日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDay;

    public UserSignVo() {
    }

    public UserSignVo(String title, Integer number, Date createDay) {
        this.title = title;
        this.number = number;
        this.createDay = createDay;
    }
}
