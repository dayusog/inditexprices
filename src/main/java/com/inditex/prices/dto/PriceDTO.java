package com.inditex.prices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for price information.
 * <p>
 * This DTO is used to transfer price data between the service layer and the presentation layer.
 * It contains information about the price of a product, including the applicable date range, priority, and currency.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDTO {

    /**
     * The identifier of the code of the product.
     */
    private Integer productId;

    /**
     * The identifier of the brand.
     * <p>
     * Example mappings:
     * <ul>
     *     <li>1 = ZARA</li>
     *     <li>2 = Pull&Bear</li>
     *     <li>3 = Massimo Dutti</li>
     *     <li>4 = Bershka</li>
     *     <li>5 = Stradivarius</li>
     *     <li>6 = Oysho</li>
     *     <li>7 = Zara Home</li>
     * </ul>
     * </p>
     */
    private Integer brandId;

    /**
     * Identifier of the applicable price list
     */
    private Integer priceList;

    /**
     * The start date and time when the price becomes applicable.
     */
    private LocalDateTime startDate;

    /**
     * The end date and time when the price is no longer applicable.
     */
    private LocalDateTime endDate;

    /**
     * Price application disambiguator. If two price lists overlap within a date range, the one with the higher priority
     * (higher numeric value) is applied
     */
    private Integer priority;

    /**
     * The final sell price.
     */
    private Double price;

    /**
     * The currency of the price value, represented as an ISO 4217 currency code (e.g., EUR).
     */
    private String currency;
}
