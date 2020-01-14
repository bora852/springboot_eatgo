package kr.co.bora.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.List;

public interface MenuItemRepository {
    List<MenuItem> findAllByRestaurantId(Long restaurantId);
}
