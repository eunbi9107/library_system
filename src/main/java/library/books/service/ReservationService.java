package library.books.service;

import library.books.domain.*;
import library.books.repository.BookRepository;
import library.books.repository.ReservationRepository;
import library.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    /* 예약 */
    @Transactional
    public Long reservation(Long userId, Long bookId, int count){

        //엔티티 조회
        User user = userRepository.findOne(userId);
        Book book = bookRepository.findOne(bookId);

        //예약 도서 생성
        ReservationBook reservationBook =
                ReservationBook.createReservationBook(book, count);

        //예약 생성
        Reservation reservation = Reservation.createReservation(user, reservationBook);

        //예약 저장
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    /* 예약 취소 */
    @Transactional
    public void cancelReservation(Long reservationId){

        //예약 엔티티 조회
        Reservation reservation = reservationRepository.findOne(reservationId);

        //예약 취소
        reservation.cancel();

    }

    /* 예약 검색 */
    public List<Reservation> findReservations(ReservationSearch reservationSearch){
        return reservationRepository.findAll(reservationSearch);
    }
}
