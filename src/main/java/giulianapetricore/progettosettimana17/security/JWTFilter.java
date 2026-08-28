package giulianapetricore.progettosettimana17.security;

import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.UnauthorizedException;
import giulianapetricore.progettosettimana17.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UsersService usersService;

    public JWTFilter(JWTTools jwtTools, UsersService usersService) {
        this.jwtTools = jwtTools;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire il token nell'header");
        }

        String accessToken = header.replace("Bearer ", "");
        jwtTools.verifyToken(accessToken);

        UUID currentUserId = jwtTools.extractIdFromToken(accessToken);
        User currentUser = this.usersService.findById(currentUserId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/api/auth/**", request.getServletPath());
    }
}