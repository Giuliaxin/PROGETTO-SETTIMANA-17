package giulianapetricore.progettosettimana17.services;

import giulianapetricore.progettosettimana17.entities.Role;
import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.BadRequestException;
import giulianapetricore.progettosettimana17.exceptions.NotFoundException;
import giulianapetricore.progettosettimana17.payloads.AssignRoleDTO;
import giulianapetricore.progettosettimana17.payloads.NewUserDTO;
import giulianapetricore.progettosettimana17.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder bcrypt;

    public UsersService(UsersRepository usersRepository, PasswordEncoder bcrypt) {
        this.usersRepository = usersRepository;
        this.bcrypt = bcrypt;
    }

    public List<User> findAll() {
        return this.usersRepository.findAll();
    }

    public User create(NewUserDTO payload) {
        if (this.usersRepository.findByEmail(payload.email()).isPresent()) {
            throw new BadRequestException("L'email " + payload.email() + " è già in uso");
        }
        if (this.usersRepository.findByUsername(payload.username()).isPresent()) {
            throw new BadRequestException("L'username " + payload.username() + " è già in uso");
        }

        User newUser = new User(
                payload.username(),
                payload.fullName(),
                payload.email(),
                bcrypt.encode(payload.password())
        );
        return this.usersRepository.save(newUser);
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdateRole(UUID userId, AssignRoleDTO body) {
        User userFromDB = this.findById(userId);
        try {
            userFromDB.setRole(Role.valueOf(body.role().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ruolo non valido. Usa MEMBER o MODERATOR.");
        }
        return this.usersRepository.save(userFromDB);
    }
}