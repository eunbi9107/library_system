package library.books.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name="user_id")
    private Long user_id;

    private String id;

    private String pw;

    private String name;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Borrow> borrows = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();
}
