package com.inditex.prices.controller;

import com.inditex.prices.dto.PriceDTO;
import com.inditex.prices.service.PriceService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// webmvc test is used instead of springtest because its more isolated and it doesnt load all the project
@WebMvcTest(PriceController.class)
public class PriceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PriceService priceService;

	@ParameterizedTest
	@MethodSource("provideTestCases")
	public void testGetPrice(LocalDateTime dateTime, PriceDTO expectedPriceDTO) throws Exception {
		when(priceService.getApplicablePrice(anyInt(), anyInt(), any())).thenReturn(expectedPriceDTO);

		mockMvc.perform(get("/api/prices")
						.param("date", dateTime.toString())
						.param("productId", "35455")
						.param("brandId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.price").value(expectedPriceDTO.getPrice()))
				.andExpect(jsonPath("$.brandId").value(expectedPriceDTO.getBrandId()))
				.andExpect(jsonPath("$.productId").value(expectedPriceDTO.getProductId()))
				.andExpect(jsonPath("$.priceList").value(expectedPriceDTO.getPriceList()))
				.andExpect(jsonPath("$.priority").value(expectedPriceDTO.getPriority()))
				// It might happen that seconds are removed if they are 00 so the format has to be specified
				.andExpect(jsonPath("$.startDate").value(expectedPriceDTO.getStartDate()
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
				.andExpect(jsonPath("$.endDate").value(expectedPriceDTO.getEndDate()
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
	}

	private static Stream<org.junit.jupiter.params.provider.Arguments> provideTestCases() {
		return Stream.of(
				org.junit.jupiter.params.provider.Arguments.of(
						LocalDateTime.of(2020, 6, 14, 10, 0),
						PriceDTO.builder()
								.brandId(1)
								.startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
								.endDate(LocalDateTime.of
										(2020, 12, 31, 23, 59, 59))
								.priceList(1)
								.productId(35455)
								.priority(0)
								.price(35.50)
								.build()
				),
				org.junit.jupiter.params.provider.Arguments.of(
						LocalDateTime.of(2020, 6, 14, 16, 0),
						PriceDTO.builder()
								.brandId(1)
								.startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
								.endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
								.priceList(2)
								.productId(35455)
								.priority(1)
								.price(25.45)
								.build()
				),
				org.junit.jupiter.params.provider.Arguments.of(
						LocalDateTime.of(2020, 6, 14, 21, 0),
						PriceDTO.builder()
								.brandId(1)
								.startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
								.endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
								.priceList(2)
								.productId(35455)
								.priority(1)
								.price(25.45)
								.build()
				),
				org.junit.jupiter.params.provider.Arguments.of(
						LocalDateTime.of(2020, 6, 15, 10, 0),
						PriceDTO.builder()
								.brandId(1)
								.startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
								.endDate(LocalDateTime.of(2020, 6, 15, 11, 0))
								.priceList(3)
								.productId(35455)
								.priority(1)
								.price(30.50)
								.build()
				),
				org.junit.jupiter.params.provider.Arguments.of(
						LocalDateTime.of(2020, 6, 16, 21, 0),
						PriceDTO.builder()
								.brandId(1)
								.startDate(LocalDateTime.of(2020, 6, 15, 16, 0))
								.endDate(LocalDateTime.of
										(2020, 12, 31, 23, 59, 59))
								.priceList(4)
								.productId(35455)
								.priority(1)
								.price(38.95)
								.build()
				)
		);
	}
}
