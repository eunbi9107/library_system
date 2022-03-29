package library.books.web;

import library.books.domain.*;
import library.books.service.BookService;
import library.books.service.ReservationService;
import library.books.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping(value = "/reservation")
    public String createForm(Model model){

        List<User> users = userService.findUsers();
        List<Book> books = bookService.findBooks();
        List<BookInfo> bookInfos = bookService.findBookInfos();

        model.addAttribute("users", users);
        model.addAttribute("books", books);
        model.addAttribute("bookInfos", bookInfos);

        return "reservation/reservationForm";
    }

    @PostMapping(value = "/reservation")
    public String reservation(@RequestParam("userId") Long userId,
                              @RequestParam("bookId") Long bookId,
                              @RequestParam("count") int count){

        reservationService.reservation(userId, bookId, count);

        return "redirect:/reservations";
    }

    @GetMapping(value = "/reservations")
    public String reservationList(@ModelAttribute("reservationSearch") ReservationSearch reservationSearch, Model model){

        List<Reservation> reservations = reservationService.findReservations(reservationSearch);
        model.addAttribute("reservations", reservations);

        return "reservation/reservationList";
    }

    @PostMapping(value = "/reservations/{reservationId}/cancel")
    public String cancelReservation(@PathVariable("reservationId") Long reservationId){

        reservationService.cancelReservation(reservationId);

        return "redirect:/reservations";
    }
}
