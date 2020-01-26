package kr.co.bora.eatgo.application;

import kr.co.bora.eatgo.domain.Reservation;
import kr.co.bora.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepositoy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        reservationService = new ReservationService(reservationRepositoy);
    }

    @Test
    public void getReservation() {
        long restaurantId = 1004L;

        List<Reservation> reservations =
                reservationService.getReservation(restaurantId);

        verify(reservationRepositoy).findAllByRestaurantId(restaurantId);
    }
}