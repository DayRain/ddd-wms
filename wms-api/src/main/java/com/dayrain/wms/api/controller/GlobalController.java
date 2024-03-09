package com.dayrain.wms.api.controller;

import com.dayrain.wms.common.exception.CommonErrorCode;
import com.dayrain.wms.common.exception.ErrorCode;
import com.dayrain.wms.common.exception.ServerException;
import com.dayrain.wms.common.web.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
/**
 * 全局异常捕捉
 * @author peng
 * @date 2024/3/10
 */
@RestControllerAdvice
public class GlobalController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R exceptionHandler(HttpServletRequest req, Exception e) {

        if (e instanceof ServerException) {
            ServerException serverException = (ServerException) e;
            return R.error(serverException.getErrorCode());
        }

        return R.error(CommonErrorCode.UNKNOWN_ERROR);
    }
}
