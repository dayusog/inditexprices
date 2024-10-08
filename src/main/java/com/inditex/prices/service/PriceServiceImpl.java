package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDTO;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for handling price-related business logic.
 * <p>
 * This class provides methods to interact with price data.
 * </p>
 */
@Service
public class PriceServiceImpl implements PriceService{

    @Autowired
    private PriceRepository priceRepository;

    /**
     * Retrieves the applicable price for a given product and brand at a specific date.
     * <p>
     * This method queries the repository to find prices that match the provided product ID, brand ID,
     * and fall within the date range specified. It returns the price with the highest priority.
     * </p>
     *
     * @param productId       The identifier of the product for which the price is to be retrieved.
     * @param brandId         The identifier of the brand to which the product belongs.
     * @param applicationDate The date and time for which the applicable price is to be determined.
     * @return A {@link PriceDTO} containing the details of the applicable price, or {@code null}
     *         if no applicable price is found.
     */
    public PriceDTO getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        Optional<Price> price = priceRepository.findProductByProductIdBrandIdAndDate(
                productId, brandId, applicationDate);

        return price.map(this::mapToPriceDTO).orElse(null);
    }

    /**
     * Maps the Price entity to a PriceDTO.
     *
     * @param price the Price entity
     * @return the corresponding PriceDTO
     */
    private PriceDTO mapToPriceDTO(Price price) {
        return PriceDTO.builder()
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPriceAmount())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .build();
    }
}
