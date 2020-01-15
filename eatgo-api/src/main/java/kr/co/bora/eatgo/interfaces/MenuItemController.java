package kr.co.bora.eatgo.interfaces;

import kr.co.bora.eatgo.application.MenuItemService;
import kr.co.bora.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public String bulkUpdate(
            @RequestBody List<MenuItem> menuItems
    ) {
        menuItemService.bulkUpdate(menuItems);

        return "";
    }
}
