package library.books.service;

import library.books.domain.User;
import library.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /*
    * 회원 가입
     */
    @Transactional
    public Long join(User user){
        validateDuplicateUser(user);
        validateDuplicateEmail(user);

        userRepository.save(user);
        return user.getUser_id();
    }

    private void validateDuplicateUser(User user){
        //List<User> findUsers = userRepository.findByName(user.getName());
        List<User> findUsers = userRepository.findById(user.getId());

        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private void validateDuplicateEmail(User user){
        List<User> findUsers = userRepository.findByEmail(user.getEmail());

        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    /*회원가입 시, 유효성 및 중복 검사*/
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 및 중복 검사에 실패한 필드 목록을 받음 */
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s",error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    /*
    * 전체 회원 조회
    */
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User findOne(Long userId){
        return userRepository.findOne(userId);
    }
}
