package library.books.domain;

import library.books.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_info_id")
    private BookInfo bookInfo;

    //private List<BookDivision> bookDivisions = new ArrayList<>();

    //도서재고
    private int stockQuantity;

    //예약 수
    private int reservationQuantity;

    //== 비즈니스 로직 ==//
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;

        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }

    public void addReservationStock(int quantity){
        int restStock = this.stockQuantity;

        if (restStock > 0) {
            throw new NotEnoughStockException("해당 도서는 대여 가능합니다.");
        }

        //예약을 했을 때는 예약 수량 증가
        int stock = this.reservationQuantity + quantity;

        this.reservationQuantity = stock;
    }

    public void removeReservationStock(int quantity){
        //예약 취소 시 예약 수량 감소
        this.reservationQuantity -= quantity;
    }
}
