package library.books.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserForm {

    @NotEmpty(message = "id는 필수 입니다.")
    private String id;

    private String pw;
    private String name;
    private String email;

}
