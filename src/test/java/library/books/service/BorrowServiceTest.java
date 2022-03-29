package library.books.service;

import library.books.domain.*;
import library.books.exception.NotEnoughStockException;
import library.books.repository.BorrowRepository;
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
public class BorrowServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired BorrowService borrowService;
    @Autowired BorrowRepository borrowRepository;

    @Test
    public void 도서_대여() throws Exception {

        //Given
        User user = createUser();
        Book book = createBook(5);

        int borrowCount = 1;

        //When
        Long borrowId = borrowService.borrow(user.getUser_id(), book.getId(), borrowCount);

        //Then
        Borrow getBorrow = borrowRepository.findOne(borrowId);

        assertEquals("도서 대여시 상태는 BORROW", BorrowStatus.BORROW, getBorrow.getStatus());
        assertEquals("대여한 도서 수가 정확해야 한다.", 1, getBorrow.getBorrowBooks().size());
        assertEquals("대여 수량만큼 재고가 줄어야 한다.", 4, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 도서대여_재고초과() throws Exception{

        //Given
        User user = createUser();
        Book book = createBook(0);

        int borrowCnt = 1;

        //When
        borrowService.borrow(user.getUser_id(), book.getId(), borrowCnt);

        //Then
        fail("재고 수량 부족 예외가 발생해야 한다.");
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

    private Book createBook(int stockQuantity) {
        BookInfo bookInfo = new BookInfo();

        bookInfo.setIsbn("123L");
        bookInfo.setName("가나다");
        bookInfo.setAuthor("작가");
        bookInfo.setPublisher("출판사");

        Book book = new Book();

        book.setStockQuantity(stockQuantity);
        book.setBookInfo(bookInfo);

        em.persist(book);

        return book;
    }

}
