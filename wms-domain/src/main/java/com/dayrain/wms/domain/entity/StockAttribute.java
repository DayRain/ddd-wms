package com.dayrain.wms.domain.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class StockAttribute {

    private String wmsBatch;

    private String productBatch;

    private String asnCode;

    private Integer stockStatus;

    private LocalDate madeDate;

    private LocalDate inDate;

    private LocalDate expireDate;
}
