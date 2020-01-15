package kr.co.bora.eatgo.interfaces;

import kr.co.bora.eatgo.application.RestaurantService;
import kr.co.bora.eatgo.domain.MenuItem;
import kr.co.bora.eatgo.domain.Restaurant;
import kr.co.bora.eatgo.domain.RestaurantNotFoundException;
import kr.co.bora.eatgo.domain.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {

    @Autowired
    RestaurantController restaurantController;

    @MockBean
    private RestaurantService restaurantService;

//    @SpyBean(RestaurantRepositoryImpl.class)
    @Autowired
    RestaurantRepository restaurantRepository;

    private MockMvc mvc;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                            .id(1004L)
                            .name("test zip")
                            .address("Seoul")
                            .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("\"name\":\"test zip\"")
                ))
                .andExpect(content().string(containsString("test zip")));
    }

    @Test
    public void detailWithExsited() throws Exception {
        Restaurant restaurant1 = Restaurant.builder()
                .id(1004L)
                .name("test zip")
                .address("Seoul")
                .build();

        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();
        restaurant1.setMenuItems(Arrays.asList(menuItem));

        Restaurant restaurant2 = Restaurant.builder()
                .id(2020L)
                .name("test food")
                .address("Seoul")
                .build();
//        restaurant2.setMenuItems(Arrays.asList(new MenuItem("Kimchi")));

        given(restaurantService.getRestaurant(1004L))
                .willReturn(restaurant1);
        given(restaurantService.getRestaurant(2020L))
                .willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("\"name\":\"test zip\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
                ));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("\"id\":2020")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("\"name\":\"test food\"")
                ));
    }

    @Test
    public void detailWithNotExsited() throws Exception {
       given(restaurantService.getRestaurant(404L))
               .willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    @Test
    public void createWithValidDate() throws Exception {
        given(restaurantService.addRestaurant(any()))
                .will(invocation -> {
                    Restaurant restaurant = invocation.getArgument(0);
                    return Restaurant.builder()
                        .id(1234L)
                        .name(restaurant.getName())
                        .address(restaurant.getAddress())
                        .build();
                });
        mvc.perform(
                MockMvcRequestBuilders.post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"bora\",\"address\":\"Seoul\"}"))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("location","/restaurants/1234"))
            .andExpect(MockMvcResultMatchers.content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidDate() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"name\":\"\",\"address\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"JOKER\",\"address\":\"Busan\"}"))
            .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "JOKER", "Busan");
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.patch("/restaurants/1004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}