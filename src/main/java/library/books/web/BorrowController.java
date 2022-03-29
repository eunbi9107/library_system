package library.books.web;

import library.books.domain.*;
import library.books.service.BookService;
import library.books.service.BorrowService;
import library.books.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping(value = "/borrow")
    public String createForm(Model model){

        List<User> users = userService.findUsers();
        List<Book> books = bookService.findBooks();
        List<BookInfo> bookInfos = bookService.findBookInfos();

        model.addAttribute("users", users);
        model.addAttribute("books", books);
        model.addAttribute("bookInfos", bookInfos);

        return "borrow/borrowForm";
    }

    @PostMapping(value = "/borrow")
    public String borrow(@RequestParam("userId") Long userId,
                     @RequestParam("bookId") Long bookId,
                     @RequestParam("count") int count){

        borrowService.borrow(userId, bookId, count);

        return "redirect:/borrow";
    }

    @GetMapping(value = "/borrows")
    public String borrowList(@ModelAttribute("borrowSearch")BorrowSearch borrowSearch, Model model){
        List<Borrow> borrows = borrowService.findBorrows(borrowSearch);
        model.addAttribute("borrows", borrows);
        model.addAttribute("returnBookDate", LocalDateTime.now());

        return "borrow/borrowList";
    }

    @PostMapping(value = "/borrows/{borrowId}/returnBook")
    public String returnBook(@PathVariable("borrowId") Long borrowId){


        borrowService.returnBook(borrowId);

        return "redirect:/borrows";
    }

    @PostMapping(value = "/borrows/{borrowId}/delay")
    public String delay(@PathVariable("borrowId") Long borrowId){

        borrowService.delay(borrowId);

        return "redirect:/borrows";
    }
}
