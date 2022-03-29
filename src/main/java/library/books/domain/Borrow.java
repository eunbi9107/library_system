package library.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Borrow {

    @Id @GeneratedValue
    @Column(name="borrow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL)
    private List<BorrowBook> borrowBooks = new ArrayList<>();

    //private int delayCnt;

    private LocalDateTime borrowDate;
//    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    private BorrowStatus status; //대여 상태 [BORROW, DELAY ,RETURN]

    //== 연관 관계 메서드 ==//
    public void setUser(User user){
        this.user = user;
        user.getBorrows().add(this);
    }

    public void addBorrowBook(BorrowBook borrowBook){
        borrowBooks.add(borrowBook);
        borrowBook.setBorrow(this);
    }

    //== 생성 메서드 ==//
    public static Borrow createBorrow(User user, BorrowBook... borrowBooks){
        Borrow borrow = new Borrow();
        borrow.setUser(user);

        for(BorrowBook borrowBook : borrowBooks){
            borrow.addBorrowBook(borrowBook);
        }

        borrow.setStatus(BorrowStatus.BORROW);
        borrow.setBorrowDate(LocalDateTime.now());

        return borrow;
    }

    //== 비즈니스 로직 ==//
    /* 연장 */
    public void delay(){

        BorrowBook _borrowBook = new BorrowBook();
        int delayCnt = _borrowBook.getDelayCnt();
        if(delayCnt >= 3) {
            throw new IllegalStateException("연장이 불가능합니다.");
        }

        this.setStatus(BorrowStatus.DELAY);

        for(BorrowBook borrowBook : borrowBooks){
            borrowBook.delay();
        }
    }


    /* 반납 */
    public void returnBook(){
        this.setStatus(BorrowStatus.RETURN);

        for(BorrowBook borrowBook : borrowBooks){
            borrowBook.returnBook();
        }
    }

}
