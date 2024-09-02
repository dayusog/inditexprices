package com.inditex.prices;

import com.inditex.prices.dto.PriceDTO;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional // Ensure each test runs in a transaction that is rolled back after the test
class ApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    public void setUp() {
        // Clear existing data and insert test data
        priceRepository.deleteAll();

        Price price = Price.builder()
                .brandId(1)
                .priceAmount(25.45)
                .priceList(2)
                .priority(1)
                .productId(35455)
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .currency("EUR")
                .build();
        priceRepository.save(price);
    }

    @Test
    void testGetPrice() {
        // Prepare the URL for the request
        String url = String.format
                ("http://localhost:%d/api/prices?date=2020-06-14T17:00:00&productId=35455&brandId=1", port);

        // Perform the request
        ResponseEntity<PriceDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                PriceDTO.class
        );

        // Validate the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(25.45, response.getBody().getPrice());
    }
}
