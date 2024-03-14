package com.dayrain.wms.domain.entity;

import com.dayrain.wms.common.exception.DomainErrorCode;
import com.dayrain.wms.common.exception.DomainException;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.dayrain.wms.common.utils.DecimalUtils.is;

@Data
public class Stock {

    private String id;

    private String sku;

    private BigDecimal number;

    private BigDecimal weight;

    private BigDecimal price;

    private Integer binId;

    private LocalDateTime createTime;

    private StockAttribute stockAttribute;

    private List<Sn> sns;

    public void addNumber(BigDecimal addedNumber) {
        number = number.add(addedNumber);
    }

    public void addWeight(BigDecimal addedWeight) {
        weight = weight.add(addedWeight);
    }

    public void addPrice(BigDecimal addedPrice) {
        price = price.add(addedPrice);
    }

    public void minusNumber(BigDecimal minusNumber) {
        if(is(minusNumber).gt(number)) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存数量不足");
        }
        number = number.subtract(minusNumber);
    }

    public void minusWeight(BigDecimal minusWeight) {
        if(is(minusWeight).gt(weight)) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存重量不足");
        }
        weight = weight.subtract(minusWeight);
    }

    public void minusPrice(BigDecimal minusPrice) {
        if(is(minusPrice).gt(price)) {
            throw new DomainException(DomainErrorCode.STOCK_ERROR, "库存价格不足");
        }
        price = price.subtract(minusPrice);
    }
}
