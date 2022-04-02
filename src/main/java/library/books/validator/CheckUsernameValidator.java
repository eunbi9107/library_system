package library.books.validator;

import library.books.domain.User;
import library.books.repository.UserRepository;
import library.books.web.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<UserForm>{

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserForm dto, Errors errors) {
        List<User> users = userRepository.findById(dto.getId());

        if(!users.isEmpty()){
            errors.rejectValue("id","아이디 중복 오류","이미 사용중인 아이디입니다.");
        }
    }
}
