package library.books.repository;

import library.books.domain.Borrow;
import library.books.domain.BorrowSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BorrowRepository {

    private final EntityManager em;

    public void save(Borrow borrow){
        em.persist(borrow);
    }

    public Borrow findOne(Long id){
        return em.find(Borrow.class, id);
    }

    public List<Borrow> findAll(BorrowSearch borrowSearch) {
        String jpql = "select b From Borrow b join b.user u";
        boolean isFirstCondition = true;

        //대여 상태 검색
        if (borrowSearch.getBorrowStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " b.status = :status";
        }

        //회원 이름 검색
        if(StringUtils.hasText(borrowSearch.getUserId())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            } else{
                jpql += " and";
            }
            jpql += " u.id like :id";
        }

        TypedQuery<Borrow> query = em.createQuery(jpql, Borrow.class)
                .setMaxResults(1000);

        if (borrowSearch.getBorrowStatus() != null) {
            query = query.setParameter("status", borrowSearch.getBorrowStatus());
        }

        if(StringUtils.hasText(borrowSearch.getUserId())){
            query = query.setParameter("id", borrowSearch.getUserId());
        }

        return query.getResultList();
    }
}
