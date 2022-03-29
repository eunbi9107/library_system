package library.books.service;

import library.books.domain.Book;
import library.books.domain.BookInfo;
import library.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void saveBook(Book book, BookInfo bookInfo){
        bookRepository.save(book);
        bookRepository.saveInfo(bookInfo);
    }

    /*
     * 영속성 컨텍스트가 자동 변경
     */
    @Transactional
    public void updateBook(Long id,Long infoId, String isbn, String name, String author,
                           String publisher, int stockQuantity, int reservationQuantity){
        Book book = bookRepository.findOne(id);
        BookInfo bookInfo = bookRepository.findOneInfo(infoId);

        bookInfo.setIsbn(isbn);
        bookInfo.setName(name);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);

        book.setBookInfo(bookInfo);
        book.setStockQuantity(stockQuantity);
        book.setReservationQuantity(reservationQuantity);
    }

    public List<Book> findBooks(){
        return bookRepository.findAll();
    }

    public List<BookInfo> findBookInfos(){
        return bookRepository.findInfoAll();
    }

    public Book findOne(Long bookId){
        return bookRepository.findOne(bookId);
    }

    //bookInfo 찾기
    public BookInfo findOneInfo(Long bookInfoId){
        return bookRepository.findOneInfo(bookInfoId);
    }

}
