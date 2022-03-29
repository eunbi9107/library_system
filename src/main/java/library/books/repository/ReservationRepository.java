package library.books.repository;

import library.books.domain.Book;
import library.books.domain.Reservation;
import library.books.domain.ReservationSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    public Reservation findOne(Long id) {
        return em.find(Reservation.class, id);
    }

    //검색기능?
    public List<Reservation> findAll(ReservationSearch reservationSearch) {

        String jpql = "select r from Reservation r join r.user u";
        boolean isFirstCondition = true;

        //예약 상태 검색
        if (reservationSearch.getReservationStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " r.status = :status";
        }

        //이름 검색
        if (StringUtils.hasText(reservationSearch.getUserId())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " u.id like :id";
        }

        TypedQuery<Reservation> query = em.createQuery(jpql, Reservation.class)
                .setMaxResults(1000);

        if (reservationSearch.getReservationStatus() != null) {
            query = query.setParameter("status", reservationSearch.getReservationStatus());
        }

        if (StringUtils.hasText(reservationSearch.getUserId())) {
            query = query.setParameter("id", reservationSearch.getUserId());
        }

        return query.getResultList();
    }
}
