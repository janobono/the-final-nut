package sk.janobono.quarkusnut.repository;

import sk.janobono.quarkusnut.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserRepository {

    @Inject
    EntityManager em;

    public boolean existsById(Long id) {
        return ((Long) em.createQuery("SELECT count(u.id) FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult()) == 1;
    }

    public boolean existsByUsername(String username) {
        return ((Long) em.createQuery("SELECT count(u.id) FROM User u WHERE u.username LIKE :username")
                .setParameter("username", username)
                .getSingleResult()) == 1;
    }

    public boolean existsByEmail(String email) {
        return ((Long) em.createQuery("SELECT count(u.id) FROM User u WHERE u.email LIKE :email")
                .setParameter("email", email)
                .getSingleResult()) == 1;
    }

    public User findById(Long id) {
        return (User) em.createQuery(
                "SELECT u FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<User> findAll() {
        String qlString = "SELECT u FROM User u ORDER BY u.id";
        Query query = em.createQuery(qlString);
        return query.getResultList();
    }

    @Transactional
    public User save(User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
