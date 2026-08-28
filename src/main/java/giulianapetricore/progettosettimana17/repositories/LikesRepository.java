package giulianapetricore.progettosettimana17.repositories;

import giulianapetricore.progettosettimana17.entities.Like;
import giulianapetricore.progettosettimana17.entities.Post;
import giulianapetricore.progettosettimana17.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikesRepository extends JpaRepository<Like, UUID> {
    Optional<Like> findByUserAndPost(User user, Post post);
    boolean existsByUserAndPost(User user, Post post);

    @Transactional
    void deleteByUserAndPost(User user, Post post);
}