package com.jjsj.mall.user.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
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
public class UserSignMonthVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "月")
    private String month;
    @ApiModelProperty(value = "签到列表")
    private List<UserSignVo> list;

    public UserSignMonthVo() {
    }

    public UserSignMonthVo(String month, List<UserSignVo> list) {
        this.month = month;
        this.list = list;
    }
}
