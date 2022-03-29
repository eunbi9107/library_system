package library.books.repository;

import library.books.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u",
                User.class).getResultList();
    }

    public List<User> findById(String id){
        return em.createQuery("select u from User u where u.id = :id",
                User.class).setParameter("id", id).getResultList();
    }

    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.name = :name",
                User.class).setParameter("name", name).getResultList();
    }

}
