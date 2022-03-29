package library.books.web;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;
    private Long infoId;

    private String isbn;
    private String name;

    private String author;
    private String publisher;

    private int stockQuantity;
    private int reservationQuantity;

}
