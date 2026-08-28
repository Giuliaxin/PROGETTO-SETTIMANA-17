package giulianapetricore.progettosettimana17.services;

import giulianapetricore.progettosettimana17.entities.Like;
import giulianapetricore.progettosettimana17.entities.Post;
import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.BadRequestException;
import giulianapetricore.progettosettimana17.repositories.LikesRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostsService postsService;

    public LikesService(LikesRepository likesRepository, PostsService postsService) {
        this.likesRepository = likesRepository;
        this.postsService = postsService;
    }

    public Like addLike(UUID postId, User currentUser) {
        Post post = postsService.findById(postId);
        if (likesRepository.existsByUserAndPost(currentUser, post)) {
            throw new BadRequestException("Hai già messo like a questo post!");
        }
        Like newLike = new Like(currentUser, post);
        return likesRepository.save(newLike);
    }

    public void removeLike(UUID postId, User currentUser) {
        Post post = postsService.findById(postId);
        Like like = likesRepository.findByUserAndPost(currentUser, post)
                .orElseThrow(() -> new BadRequestException("Non hai ancora messo like a questo post!"));
        likesRepository.delete(like);
    }
}