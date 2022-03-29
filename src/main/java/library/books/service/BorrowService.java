package library.books.service;

import library.books.domain.*;
import library.books.repository.BookRepository;
import library.books.repository.BorrowRepository;
import library.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BorrowService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;

    /* 대여 */
    @Transactional
    public Long borrow(Long userId, Long bookId, int count){

        //엔티티 조회
        User user = userRepository.findOne(userId);
        Book book = bookRepository.findOne(bookId);

        //예약정보 생성


        //대여도서 생성
        BorrowBook borrowBook = BorrowBook.createBorrowBook(book, count);

        //대여 생성
        Borrow borrow = Borrow.createBorrow(user,borrowBook);

        //대여 저장
        borrowRepository.save(borrow);

        return borrow.getId();
    }

    /* 반납 */
    @Transactional
    public void returnBook(Long borrowId){

        //대여 엔티티 조회
        Borrow borrow = borrowRepository.findOne(borrowId);

        //반납
        borrow.returnBook();
    }

    /* 연장 */
    @Transactional
    public void delay(Long borrowId){

        //대여 엔티티 조회
        Borrow borrow = borrowRepository.findOne(borrowId);

        //연장
        borrow.delay();
    }

    /* 대여 검색 */
    public List<Borrow> findBorrows(BorrowSearch borrowSearch){
        return borrowRepository.findAll(borrowSearch);
    }
}
