package com.imxushuai.jrs303.entity;

import com.imxushuai.jrs303.valid.AddGroup;
import com.imxushuai.jrs303.valid.ListValue;
import com.imxushuai.jrs303.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class User {

    /**
     * ID
     */
    @Null(message = "新增时, ID必须为空", groups = {AddGroup.class})
    @NotNull(message = "更新时, ID不能为空", groups = {UpdateGroup.class})
    private String id;

    /**
     * 姓名
     */
    @NotBlank(message = "用户姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 年龄
     */
    @NotNull(message = "用户年龄不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "用户年龄必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer age;

    /**
     * 住址
     */
    private String address;

    /**
     * 性别，0：男性；1：女性；2：未知
     */
    @ListValue(values = {1, 2, 3}, groups = {AddGroup.class, UpdateGroup.class})
    private Integer gender;

}
