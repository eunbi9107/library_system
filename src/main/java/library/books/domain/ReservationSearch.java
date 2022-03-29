package library.books.domain;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationSearch {

    private String userId;
    private ReservationStatus reservationStatus; //예약 상태[RESERVATION, (CANCEL)]
}
