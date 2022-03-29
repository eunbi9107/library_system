package library.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Reservation {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationBook> reservationBooks = new ArrayList<>();

    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; //enum[RESERVATION, CANCEL]

    //==연관 관계 메서드==//
    public void setUser(User user){
        this.user = user;
        user.getReservations().add(this);
    }

    public void addReservationBook(ReservationBook reservationBook){
        reservationBooks.add(reservationBook);
        reservationBook.setReservation(this);
    }

    //== 생성 메서드 ==//
    public static Reservation createReservation(User user, ReservationBook... reservationBooks){
        Reservation reservation = new Reservation();
        reservation.setUser(user);

        for(ReservationBook reservationBook : reservationBooks){
            reservation.addReservationBook(reservationBook);
        }

        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.RESERVATION);

        return reservation;
    }

    //== 비즈니스 로직 ==//
    /* 예약 취소 */
    public void cancel(){
        this.setStatus(ReservationStatus.CANCEL);
        for(ReservationBook reservationBook : reservationBooks){
            reservationBook.cancel();
        }
    }
}
