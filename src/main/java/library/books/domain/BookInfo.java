package library.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class BookInfo {

    @Id @GeneratedValue
    @Column(name = "book_info_id")
    private Long id;

    private String isbn;

    private String name;

    private String author;

    private String publisher;

    @OneToMany(mappedBy = "bookInfo")
    private List<Book> books = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_division_id")
    private BookDivision bookDivision;

    private LocalDateTime publishDate;
}
