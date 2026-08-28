package giulianapetricore.progettosettimana17.controllers;

import giulianapetricore.progettosettimana17.entities.Like;
import giulianapetricore.progettosettimana17.entities.Post;
import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.ValidationException;
import giulianapetricore.progettosettimana17.payloads.NewPostDTO;
import giulianapetricore.progettosettimana17.services.LikesService;
import giulianapetricore.progettosettimana17.services.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;
    private final LikesService likesService;

    public PostsController(PostsService postsService, LikesService likesService) {
        this.postsService = postsService;
        this.likesService = likesService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return this.postsService.findAll();
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable UUID postId) {
        return this.postsService.findById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody @Validated NewPostDTO body, BindingResult validationResult, @AuthenticationPrincipal User currentUser) {
        if (validationResult.hasErrors()) {
            String errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new ValidationException(errorsList);
        }
        return this.postsService.save(body, currentUser);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable UUID postId, @RequestBody @Validated NewPostDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new ValidationException(errorsList);
        }
        return this.postsService.findByIdAndUpdate(postId, body);
    }

    @PostMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public Like addLike(@PathVariable UUID postId, @AuthenticationPrincipal User currentUser) {
        return this.likesService.addLike(postId, currentUser);
    }

    @DeleteMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable UUID postId, @AuthenticationPrincipal User currentUser) {
        this.likesService.removeLike(postId, currentUser);
    }
}