package library.books.web;

import library.books.domain.Book;
import library.books.domain.BookInfo;
import library.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/books/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());

        return "books/createBookForm";
    }

    @PostMapping(value = "/books/new")
    public String create(BookForm form){
        Book book = new Book();

        BookInfo bookInfo = new BookInfo();
        bookInfo.setIsbn(form.getIsbn());
        bookInfo.setName(form.getName());
        bookInfo.setAuthor(form.getAuthor());
        bookInfo.setPublisher(form.getPublisher());

        book.setStockQuantity(form.getStockQuantity());
        book.setReservationQuantity(form.getReservationQuantity());

        book.setBookInfo(bookInfo);

        bookService.saveBook(book, bookInfo);

        return "redirect:/books";
    }

    @GetMapping(value = "/books")
    public String list(Model model){

        List<Book> books = bookService.findBooks();

        for(Book book : books){
            System.out.println("book = " + book );
        }

        List<BookInfo> bookInfos = bookService.findBookInfos();

        model.addAttribute("books", books);
        model.addAttribute("bookInfos", bookInfos);

        return "books/bookList";
    }

    //도서 수정 폼
    @GetMapping(value = "/books/{bookId}/edit")
    public String updateBookForm(@PathVariable("bookId") Long bookId, Model model){

        Book book = (Book) bookService.findOne(bookId);

        Long bookInfoId = book.getBookInfo().getId();
        BookInfo bookInfo = (BookInfo) bookService.findOneInfo(bookInfoId);

        BookForm form = new BookForm();
        form.setInfoId(bookInfo.getId());
        form.setIsbn(bookInfo.getIsbn());
        form.setName(bookInfo.getName());
        form.setAuthor(bookInfo.getAuthor());
        form.setPublisher(bookInfo.getPublisher());

        form.setId(book.getId());
        form.setStockQuantity(book.getStockQuantity());

        model.addAttribute("form", form);
        return "books/updateBookForm";
    }

    //도서 수정
    @PostMapping(value = "/books/{bookId}/edit")
    public String updateBook(@ModelAttribute("form") BookForm form){

//        Book book = new Book();
//
//        BookInfo bookInfo = new BookInfo();
//        bookInfo.setId(form.getInfoId());
//        bookInfo.setIsbn(form.getIsbn());
//        bookInfo.setName(form.getName());
//        bookInfo.setAuthor(form.getAuthor());
//        bookInfo.setPublisher(form.getPublisher());
//
//        book.setId(form.getId());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setReservationQuantity(form.getReservationQuantity());
//
//        book.setBookInfo(bookInfo);
//
//        bookService.saveBook(book, bookInfo);

        bookService.updateBook(form.getId(),form.getInfoId(),form.getIsbn(), form.getName(),form.getAuthor(),form.getPublisher(),form.getStockQuantity(), form.getReservationQuantity());

        return "redirect:/books";
    }

}
