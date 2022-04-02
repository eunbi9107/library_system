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
public class CheckEmailValidator extends AbstractValidator<UserForm> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserForm dto, Errors errors) {
        List<User> users = userRepository.findByEmail(dto.getEmail());

        if(!users.isEmpty()){
            errors.rejectValue("email","이메일 중복 오류","이미 등록된된 이메일입니다");
        }
    }
}
