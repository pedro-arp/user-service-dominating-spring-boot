package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user) {
        assertEmailIsUnique(user.getEmail(), user.getId());
        return repository.save(user);
    }

    public void delete(Long id) {

        var userToDelete = findById(id);

        repository.delete(userToDelete);
    }

    public void update(User userToUpdate) {

        assertUserExists(userToUpdate);

        assertEmailIsUnique(userToUpdate.getEmail(), userToUpdate.getId());

        repository.save(userToUpdate);
    }

    private void assertUserExists(User user) {
        findById(user.getId());
    }

    private void assertEmailIsUnique(String email, Long userId) {
        repository.findByEmail(email)
                .ifPresent(userFound -> {
                    if (!userFound.getId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email '%s' is already in use".formatted(email));
                    }

                });

    }
}
