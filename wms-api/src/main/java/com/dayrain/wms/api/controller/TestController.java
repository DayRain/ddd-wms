package com.dayrain.wms.api.controller;

import com.dayrain.wms.common.exception.CommonErrorCode;
import com.dayrain.wms.common.exception.ServerException;
import com.dayrain.wms.common.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/index")
    public R index() {
        log.info("test index request...");
        return R.success("index");
    }

    @RequestMapping("/error")
    public R error() {
        log.error("error index request...");
        throw new ServerException(CommonErrorCode.UNKNOWN_ERROR);
    }
}
