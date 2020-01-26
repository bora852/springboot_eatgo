package kr.co.bora.eatgo.application;

import kr.co.bora.eatgo.domain.Reservation;
import kr.co.bora.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservation(long restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }
}
