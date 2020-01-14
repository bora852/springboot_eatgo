package kr.co.bora.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryImplTest {

    @Test
    public void save() {
        RestaurantRepository repository = new RestaurantRepositoryImpl();

        int oldCnt = repository.findAll().size();

        Restaurant restaurant = new Restaurant("bora", "Seoul");
        repository.save(restaurant);

        assertThat(restaurant.getId()).isEqualTo(1234L);

        int newCnt = repository.findAll().size();

        assertThat(newCnt - oldCnt).isEqualTo(1);

    }
}