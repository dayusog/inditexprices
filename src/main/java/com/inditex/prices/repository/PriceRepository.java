package com.inditex.prices.repository;

import com.inditex.prices.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing {@link Price} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing basic CRUD operations
 * and custom query methods for {@link Price} entities.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Finds the list of {@link Price} entities applicable to a given product and brand
     * at a specific date, ordered by priority in descending order.
     * <p>
     * The query checks if the provided {@code applicationDate} falls within the
     * {@code startDate} and {@code endDate} range of the price records. It also ensures
     * that the price records belong to the specified {@code productId} and {@code brandId}.
     * The results are sorted by the {@code priority} field in descending order, so that
     * the highest priority price is returned first.
     * </p>
     *
     * @param productId       The identifier of the product.
     * @param brandId         The identifier of the brand.
     * @param applicationDate The date and time to check for applicable prices.
     * @return A list of {@link Price} entities matching the criteria, ordered by priority.
     */
    @Query("SELECT p FROM Price p WHERE p.productId = :productId " +
            "AND p.brandId = :brandId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC")
    List<Price> findProductByProductIdBrandIdAndDate(
            @Param("productId") Integer productId,
            @Param("brandId") Integer brandId,
            @Param("applicationDate") LocalDateTime applicationDate);
}
