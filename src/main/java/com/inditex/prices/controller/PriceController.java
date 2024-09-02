package com.inditex.prices.controller;

import com.inditex.prices.dto.PriceDTO;
import com.inditex.prices.service.PriceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for managing price queries.
 * <p>
 * Provides endpoints to query for prices based on product ID, brand ID, and a date.
 * </p>
 */
@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;

    /**
     * Constructs a new {@link PriceController} with the specified {@link PriceService}.
     *
     * @param priceService the service to fetch price information
     */
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * Retrieves the applicable price for a product based on the given parameters.
     *
     * @param date      the date and time to check the price for, formatted as ISO 8601 date-time
     * @param productId the ID of the product
     * @param brandId   the ID of the brand
     * @return a {@link ResponseEntity} containing the {@link PriceDTO} with the price information, or 404 if no price is found
     */
    @GetMapping
    public ResponseEntity<PriceDTO> getPrice(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId) {
        PriceDTO priceDTO = priceService.getApplicablePrice(productId, brandId, date);
        return ResponseEntity.ok(priceDTO);
    }
}
