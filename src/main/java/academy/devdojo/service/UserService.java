package academy.devdojo.service;

import academy.devdojo.exception.NotFoundException;
import academy.devdojo.domain.User;
import academy.devdojo.repository.UserHardCodedRepository;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodedRepository repository;
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {

        var userToDelete = findById(id);

        repository.delete(userToDelete);
    }

    public void update(User userToUpdate) {

        AssertUserExists(userToUpdate);

        repository.update(userToUpdate);
    }

    private void AssertUserExists(User user) {
        findById(user.getId());
    }

}
