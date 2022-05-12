package com.practice.seckill.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询条件DTO")
@Builder
@Data
public class MemberQueryConditionDTO {
    @ApiModelProperty(value="锁定状态")
    private Integer lockStatus;
    @ApiModelProperty(value="时间左界")
    private LocalDateTime startTime;
    @ApiModelProperty(value="时间右界")
    private LocalDateTime endTime;
    @ApiModelProperty(value="用户昵称")
    private String nickname;
    @ApiModelProperty(value="手机号")
    private String phoneNumber;
    @ApiModelProperty(value="pageNo")
    private Integer pageNo;
    @ApiModelProperty(value="pageSize")
    private Integer pageSize;
}
