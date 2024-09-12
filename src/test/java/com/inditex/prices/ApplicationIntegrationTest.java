package com.inditex.prices;

import com.inditex.prices.dto.PriceDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class ApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testGetPrice(LocalDateTime dateTime, Double expectedPrice) {
        // Prepare the URL for the request
        String url = String.format(
                "http://localhost:%d/api/prices?date=%s&productId=35455&brandId=1",
                port, dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        );

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
        assertEquals(expectedPrice, response.getBody().getPrice());
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> provideTestCases() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(
                        LocalDateTime.of(2020, 6, 14, 10, 0), 35.50),
                org.junit.jupiter.params.provider.Arguments.of(
                        LocalDateTime.of(2020, 6, 14, 16, 0), 25.45),
                org.junit.jupiter.params.provider.Arguments.of(
                        LocalDateTime.of(2020, 6, 14, 21, 0), 35.50),
                org.junit.jupiter.params.provider.Arguments.of(
                        LocalDateTime.of(2020, 6, 15, 10, 0), 30.50),
                org.junit.jupiter.params.provider.Arguments.of(
                        LocalDateTime.of(2020, 6, 16, 21, 0), 38.95)
        );
    }
}