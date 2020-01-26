package kr.co.bora.eatgo.interfaces;

import kr.co.bora.eatgo.application.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void list() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwMjAsIm5hbWUiOiJPd25lciIsInJlc3RhdXJhbnRJZCI6MTAwNH0.a5n4PWJ2-3yVyMaLGG0HSPXtH_mgpOvofpQ1OFkgDOQ";

        mvc.perform(get("/reservations")
                .header("Authorization","Bearer " + token))
                .andExpect(status().isOk());

        verify(reservationService).getReservation(1004L);
//        Long userId = 1004L;
//        String name = "John";
//        String date = "2019-12-25";
//        String time = "20:00";
//        Integer partySize = 20;
    }
}