package library.books.web;

import library.books.domain.User;
import library.books.service.UserService;
import library.books.validator.CheckUsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CheckUsernameValidator checkUsernameValidator;

    /*커스텀 유효성 검증을 위해 추가*/
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkUsernameValidator);
    }

    @GetMapping(value = "/users/new")
    public String createForm(Model model){
        model.addAttribute("userForm", new UserForm());

        return "users/createUserForm";
    }

    @PostMapping(value = "/users/new")
    public String create(@Valid UserForm form, Errors errors, BindingResult result, Model model){

        if(result.hasErrors()){
            Map<String, String> validatorResult = userService.validateHandling(result);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }

            return "users/createUserForm";
        }

        User user = new User();
        user.setId(form.getId());
        user.setPw(form.getPw());
        user.setName(form.getName());
        user.setEmail(form.getEmail());

        userService.join(user);

        return "redirect:/";
    }


    @GetMapping(value = "/users")
    public String list(Model model){
        List<User> users = userService.findUsers();

        model.addAttribute("users", users);

        return "users/userList";
    }
}
