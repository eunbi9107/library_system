package library.books.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BorrowSearch {

    private String userId;
    private BorrowStatus borrowStatus; //대여 상태[BORROW, DELAY, (RETURN)]


}
