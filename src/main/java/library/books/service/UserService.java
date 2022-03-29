package library.books.service;

import library.books.domain.User;
import library.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
