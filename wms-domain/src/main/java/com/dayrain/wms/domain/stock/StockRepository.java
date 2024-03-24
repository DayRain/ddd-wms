package com.dayrain.wms.domain.stock;

public interface StockRepository {

    void saveOrUpdate(Stock stock);

    Stock findById(String id);
}
