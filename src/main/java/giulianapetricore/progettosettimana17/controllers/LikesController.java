package giulianapetricore.progettosettimana17.controllers;

import giulianapetricore.progettosettimana17.entities.Like;
import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.services.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikesController {

    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Like addLike(@PathVariable UUID postId, @AuthenticationPrincipal User currentUser) {
        return this.likesService.addLike(postId, currentUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable UUID postId, @AuthenticationPrincipal User currentUser) {
        this.likesService.removeLike(postId, currentUser);
    }
}