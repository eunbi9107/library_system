package library.books.service;

import library.books.domain.*;
import library.books.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired ReservationService reservationService;
    @Autowired ReservationRepository reservationRepository;

    @Test
    public void 도서예약() throws Exception{

        //Given
        User user = createUser();
        Book book = createBook(5,0);

        int reservationCount = 1;

        //When
        Long reservationId = reservationService.reservation(user.getUser_id(), book.getId(), reservationCount);

        //Then
        Reservation getReservation = reservationRepository.findOne(reservationId);

        assertEquals("도서 예약시 상태는 RESERVATION", ReservationStatus.RESERVATION,getReservation.getStatus());
        assertEquals("예약한 도서 수가 정확해야 한다.", 1, getReservation.getReservationBooks().size());
        assertEquals("예약 수량 만큼 예약 수가 증가해야 한다.", 1, book.getReservationQuantity());

    }

    @Test
    public void 예약취소(){

        //Given
        User user = createUser();
        Book book = createBook(0,1);
        int reservationCount = 1;

        Long reservationId = reservationService.reservation(user.getUser_id(), book.getId(), reservationCount);

        //When
        reservationService.cancelReservation(reservationId);

        //Then
        Reservation getReservation = reservationRepository.findOne(reservationId);

        assertEquals("예약 취소시 상태는 CANCEL 이다.", ReservationStatus.CANCEL, getReservation.getStatus());
        assertEquals("예약 취소한 만큼 예약 수량이 감소해야 한다.", 1, book.getReservationQuantity());

    }

    private User createUser(){
        User user = new User();

        user.setId("a123");
        user.setPw("1234");
        user.setName("Kim");
        user.setEmail("a123@g.com");

        em.persist(user);

        return user;
    }

    private Book createBook(int stockQuantity, int reservationQuantity) {
        BookInfo bookInfo = new BookInfo();

        bookInfo.setIsbn("123L");
        bookInfo.setName("가나다");
        bookInfo.setAuthor("작가");
        bookInfo.setPublisher("출판사");

        Book book = new Book();

        book.setStockQuantity(stockQuantity);
        book.setReservationQuantity(reservationQuantity);
        book.setBookInfo(bookInfo);

        em.persist(book);

        return book;
    }
}
