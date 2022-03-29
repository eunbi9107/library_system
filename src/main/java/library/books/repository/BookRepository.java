package library.books.repository;

import library.books.domain.Book;
import library.books.domain.BookInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public void saveInfo(BookInfo bookInfo){
        if(bookInfo.getId() == null){
            em.persist(bookInfo);
        } else{
            em.merge(bookInfo);
        }
    }

    public void save(Book book){
        if(book.getId() == null){
            em.persist(book);
        } else{
            em.merge(book);
        }
    }

    public Book findOne(Long id){
        return em.find(Book.class, id);
    }

    //bookInfo findOne
    public BookInfo findOneInfo(Long id){
        return em.find(BookInfo.class, id);
    }

    public List<Book> findAll(){
//          String jpql = "select b from Book b join fetch b.bookInfo i";
//        String jpql = "select b from Book b";
//
//        TypedQuery<Book> query = em.createQuery(jpql, Book.class)
//                .setParameter("book_info_id", );
//
//        return em.createQuery(jpql,Book.class).getResultList();

        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }


    public List<BookInfo> findInfoAll(){
        String jpql = "select i from BookInfo i join fetch i.books b";

        return em.createQuery(jpql, BookInfo.class).getResultList();

//        return em.createQuery("select i from BookInfo i", BookInfo.class)
//                .getResultList();
    }

}
