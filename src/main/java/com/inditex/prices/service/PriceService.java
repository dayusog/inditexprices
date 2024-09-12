package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDTO;
import java.time.LocalDateTime;

public interface PriceService {
    PriceDTO getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate);
}


