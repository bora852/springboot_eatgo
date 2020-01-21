package kr.co.bora.eatgo.interfaces;

import kr.co.bora.eatgo.application.RestaurantService;
import kr.co.bora.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(
            @RequestParam("region") String region,
            @RequestParam("category") Long categoryId

    ) {
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);

        return restaurant;
    }

//    @PostMapping("/restaurants")
//    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
//            throws URISyntaxException {
//        String name = resource.getName();
//        String address = resource.getAddress();
//
//        Restaurant restaurant = restaurantService.addRestaurant(
//                    Restaurant.builder()
//                    .name(name)
//                    .address(address)
//                    .build());
//
//        URI location = new URI("/restaurants/" + restaurant.getId());
//        return ResponseEntity.created(location).body("{}");
//    }
//
//    @PatchMapping("/restaurants/{id}")
//    public String update(@PathVariable("id") Long id,
//                         @Valid @RequestBody Restaurant resource){
//
//        String name = resource.getName();
//        String address = resource.getAddress();
//        restaurantService.updateRestaurant(id, name, address);
//        return "{}";
//    }

}
