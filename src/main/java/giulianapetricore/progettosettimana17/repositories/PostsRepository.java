package giulianapetricore.progettosettimana17.repositories;

import giulianapetricore.progettosettimana17.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostsRepository extends JpaRepository<Post, UUID> {}