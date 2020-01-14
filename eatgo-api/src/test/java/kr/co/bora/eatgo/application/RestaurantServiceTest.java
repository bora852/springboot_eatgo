package kr.co.bora.eatgo.application;

import kr.co.bora.eatgo.domain.MenuItem;
import kr.co.bora.eatgo.domain.MenuItemRepository;
import kr.co.bora.eatgo.domain.Restaurant;
import kr.co.bora.eatgo.domain.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
//        restaurantService = new RestaurantService(
//                restaurantRepository, menuItemRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));

        given(restaurantRepository.findAll())
                .willReturn(restaurants);
        given(restaurantRepository.findById(1004L))
                .willReturn(new Restaurant(1004L, "Bob zip", "Seoul"));
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Kimchi"));

        given(menuItemRepository.findAllByRestaurantId(1004L))
                .willReturn(menuItems);
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        assertThat(restaurants.get(0).getId()).isEqualTo(1004L);
    }

    @Test
    public void getRestaurant() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId()).isEqualTo(1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName()).isEqualTo("Kimchi");
    }

    @Test
    public void addRestaurant() {
        Restaurant restaurant = new Restaurant("bora", "Seoul");
        Restaurant saved = new Restaurant(1234L, "bora", "Seoul");

        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant create = restaurantService.addRestaurant(restaurant);

        assertThat(create.getId()).isEqualTo(1234L);
    }
}