package library.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reservation_book")
@Getter @Setter
public class ReservationBook {

    @Id @GeneratedValue
    @Column(name = "reservation_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private int count;

    //== 생성 메서드 ==//
    public static ReservationBook createReservationBook(Book book, int count){
        ReservationBook reservationBook = new ReservationBook();
        reservationBook.setBook(book);
        reservationBook.setCount(count);

        book.addReservationStock(count);

        return reservationBook;
    }

    //== 비즈니스 로직 ==//
    /* 예약 취소 */
    public void cancel(){
        getBook().removeReservationStock(count);
    }

}
