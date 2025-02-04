package com.service.ordering.orders.clients;

import com.service.ordering.orders.dto.RequestDto.InventoryAdjustmentRequest;
import com.service.ordering.orders.dto.RequestDto.InventoryStockRequest;
import com.service.ordering.orders.dto.ResponseDto.InventoryStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Change url later on
@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryServiceClient {

    @PostMapping("/inventory/check-stock")
    InventoryStockResponse checkStock(@RequestBody InventoryStockRequest request);

    @PostMapping("/inventory/adjust-stock")
    void adjustStock(@RequestBody InventoryAdjustmentRequest request);
}
