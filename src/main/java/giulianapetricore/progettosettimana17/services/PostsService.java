package giulianapetricore.progettosettimana17.services;

import giulianapetricore.progettosettimana17.entities.Post;
import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.NotFoundException;
import giulianapetricore.progettosettimana17.payloads.NewPostDTO;
import giulianapetricore.progettosettimana17.repositories.PostsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public List<Post> findAll() {
        return this.postsRepository.findAll();
    }

    public Post findById(UUID postId) {
        return this.postsRepository.findById(postId).orElseThrow(() -> new NotFoundException(postId));
    }

    public Post save(NewPostDTO body, User author) {
        Post newPost = new Post(body.text(), author);
        return this.postsRepository.save(newPost);
    }

    public Post findByIdAndUpdate(UUID postId, NewPostDTO body) {
        Post found = this.findById(postId);
        found.setText(body.text());
        return this.postsRepository.save(found);
    }
}