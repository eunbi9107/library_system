package library.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_book")
@Getter @Setter
public class BorrowBook {

    @Id @GeneratedValue
    @Column(name = "borrow_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    private int count;

    //private LocalDateTime borrowDate;
    private int delayCnt;
    private LocalDateTime returnDate; //반납 예정일
    //private LocalDateTime returnBookDate; //반납 일시

    //== 생성 메서드 ==//
    public static BorrowBook createBorrowBook(Book book, int count){
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBook(book);
        borrowBook.setCount(count);

        //borrowBook.setBorrowDate(LocalDateTime.now());
        borrowBook.setReturnDate(LocalDateTime.now().plusDays(7));
        borrowBook.setDelayCnt(0);

        book.removeStock(count);

        return borrowBook;
    }

    //== 비즈니스 로직 ==//
    /* 연장 */
    public void delay(){
        this.delayCnt = delayCnt+1;

        if(this.delayCnt >= 4){
            throw new IllegalStateException("연장이 불가능합니다.");
        }

        this.returnDate = returnDate.plusDays(5);
    }

    public void returnBook(){
        getBook().addStock(count);
    }
}
