package library.books.service;

import library.books.domain.User;
import library.books.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception {

        //Given
        User user = new User();
        user.setId("a12");

        //When
        Long saveId = userService.join(user);

        //Then
        assertEquals(user, userRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {

        //Given
        User user1 = new User();
        user1.setId("a12");

        User user2 = new User();
        user2.setId("a12");

        //When
        userService.join(user1);
        userService.join(user2);

        //Then
        fail("예외가 발생해야 한다.");
    }
}
