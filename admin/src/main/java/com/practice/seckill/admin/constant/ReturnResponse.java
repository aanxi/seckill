package com.practice.seckill.admin.constant;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class ReturnResponse<T> implements Serializable {

    @ApiModelProperty(value = "HttpStatus", hidden = true)
    @JSONField(serialize = false)
    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;

    @ApiModelProperty(value = "请求结果码，由业务自定义，默认值：200, 500, 400, 401, 403", example = "200")
    private Integer code;

    @ApiModelProperty(value = "提示信息，默认200:成功 500:失败 400:参数异常 401:未登录 403:无权限", example = "操作成功")
    private String message = "OK";

    @ApiModelProperty(value = "数据")
    private T data;

    /**
     * 处理完成正常返回数据，code为200，message为"OK"
     */
    public static <T> ReturnResponse<T> ok(T data) {
        return response(HttpStatus.OK, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 向前端返回HTTP状态为200，附自定义code与message时使用
     */
    public static <T> ReturnResponse<T> ok(Integer code, String message) {
        return response(HttpStatus.OK, code, message, null);
    }

    /**
     * 向前端返回HTTP状态为200，附自定义数据、code与message时使用
     */
    public static <T> ReturnResponse<T> ok(Integer code, String message, T data) {
        return response(HttpStatus.OK, code, message, data);
    }

    /**
     * （不推荐）懒人直接返回code为200，message为"OK"
     */
    public static <T> ReturnResponse<T> ok() {
        return response(HttpStatus.OK, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
    }

    /**
     * 自定义REST Response
     */
    public static <T> ReturnResponse<T> response(HttpStatus httpStatus, Integer code, String message, T data) {
        return new ReturnResponse<T>()
                .setHttpStatus(httpStatus)
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

//    /**
//     * 无需data返回值时，应规范使用常量返回值ConstResultEnum
//     */
//    public static <T> ReturnResponse<T> response(ConstResultEnum result) {
//        return response(result.status, result.code, result.msg, null);
//    }
//
//    public static <T> ReturnResponse<T> fail(String message) {
//        message = Optional.ofNullable(message).orElse("操作失败");
//        return response(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
//    }
//
//    /**
//     * 常量返回值ConstResultEnum搭配自定义data使用
//     */
//    public static <T> ReturnResponse<T> response(ConstResultEnum result, T data) {
//        return response(result.status, result.code, result.msg, data);
//    }

    public static <T> ReturnResponse<T> response200(Integer code, String message, T data) {
        return ok(code, message, data);
    }

    public static <T> ReturnResponse<T> makeOkResponse() {
        return ok();
    }

    public static <T> ReturnResponse<T> makeOkResponse(T data) {
        return ok(data);
    }

    public static <T> ReturnResponse<T> makeFailResponse(String message, T data) {
        message = Optional.ofNullable(message).orElse("操作失败");
        return response(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, data);
    }

    public static <T> ReturnResponse<T> makeFailResponse(String message) {
        message = Optional.ofNullable(message).orElse("操作失败");
        return response(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static <T> ReturnResponse<T> makeResponse(int code, String message) {
        return ok(code, message);
    }

    public static <T> ReturnResponse<T> makeResponse(int code, String message, T data) {
        return ok(code, message, data);
    }

    public static <T> ReturnResponse<T> makeResponse(HttpStatus status, Integer code, String msg) {
        return response(status, code, msg, null);
    }

    public static <T> ReturnResponse<T> makeOkResponse(String msg, int code, T data) {
        return ok(code, msg, data);
    }

}
