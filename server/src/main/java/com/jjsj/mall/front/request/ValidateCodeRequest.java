package com.jjsj.mall.front.request;

import com.jjsj.constants.RegularConstants;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 验证码类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ValidateCodeRequest对象", description = "发送手机验证码类")
public class ValidateCodeRequest implements Serializable {

    @Pattern(regexp = RegularConstants.PHONE, message = "手机号码格式错误")
    private String phone;
}
