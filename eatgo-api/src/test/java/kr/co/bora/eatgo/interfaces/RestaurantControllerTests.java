package kr.co.bora.eatgo.interfaces;

import kr.co.bora.eatgo.application.RestaurantService;
import kr.co.bora.eatgo.domain.MenuItem;
import kr.co.bora.eatgo.domain.Restaurant;
import kr.co.bora.eatgo.domain.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
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
        restaurants.add(new Restaurant(1004L, "test zip", "Seoul"));
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
                .andExpect(content().string(containsString("Bob zip")));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = new Restaurant( 1004L,"test zip", "Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));

        Restaurant restaurant2 = new Restaurant( 2020L,"test food", "Seoul");
        restaurant2.addMenuItem(new MenuItem("Kimchi"));

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
    public void create() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"bora\",\"address\":\"Seoul\"}"))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("location","/restaurants/1234"))
            .andExpect(MockMvcResultMatchers.content().string("{}"));

        verify(restaurantService).addRestaurant(ArgumentMatchers.any());
    }
}