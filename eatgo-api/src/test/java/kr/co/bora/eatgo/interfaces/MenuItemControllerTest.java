package kr.co.bora.eatgo.interfaces;

import kr.co.bora.eatgo.application.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith
@SpringBootTest
//@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired
    private  MenuItemController menuItemController;

    private MockMvc mvc;

    @MockBean
    private MenuItemService menuItemService;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders.standaloneSetup(menuItemController).build();
    }

    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.patch("/restaurants/1/menuitems")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[]"))
                    .andExpect(status().isOk());

        verify(menuItemService).bulkUpdate(any());
    }
}