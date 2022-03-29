package library.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class BookDivision {

    @Id @GeneratedValue
    @Column(name = "book_division_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "bookDivision")
    private List<BookInfo> bookInfos = new ArrayList<>();
}
