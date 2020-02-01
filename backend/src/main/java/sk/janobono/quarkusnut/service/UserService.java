package sk.janobono.quarkusnut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.janobono.quarkusnut.domain.User;
import sk.janobono.quarkusnut.mapper.UserMapper;
import sk.janobono.quarkusnut.repository.UserRepository;
import sk.janobono.quarkusnut.so.UserSO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    UserRepository userRepository;

    @Inject
    KafkaProducerService kafkaProducerService;

    public Set<UserSO> getUsers() {
        LOGGER.debug("getUsers()");
        List<User> userList = userRepository.findAll();
        Set<UserSO> result = new HashSet<>();
        userList.forEach(u -> result.add(userMapper.userToUserSO(u)));
        LOGGER.debug("getUsers()={}", result);
        return result;
    }

    public boolean userExists(Long id) {
        LOGGER.debug("userExists({})", id);
        return userRepository.existsById(id);
    }

    public UserSO getUser(Long id) {
        LOGGER.debug("getUser({})", id);
        UserSO result = null;
        User user = userRepository.findById(id);
        if (user != null) {
            result = userMapper.userToUserSO(user);
        }
        return result;
    }

    @Transactional
    public UserSO addUser(UserSO userSO) {
        LOGGER.debug("addUser({})", userSO);
        if (userRepository.existsByUsername(userSO.getUsername().toLowerCase())) {
            throw new RuntimeException("User with username exists!");
        }
        if (userRepository.existsByEmail(userSO.getEmail().toLowerCase())) {
            throw new RuntimeException("User with email exists!");
        }
        UserSO result = userMapper.userToUserSO(userRepository.save(userMapper.userSOToUser(userSO)));
        try {
            kafkaProducerService.sendMessage(objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Transactional
    public UserSO setUser(UserSO userSO) {
        LOGGER.debug("setUser({})", userSO);
        User user = userRepository.findById(userSO.getId());
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        user.setUsername(userSO.getUsername());
        user.setEmail(userSO.getEmail());
        user.setEnabled(userSO.getEnabled());
        user.setLocked(userSO.getLocked());
        userRepository.save(user);
        return userMapper.userToUserSO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        LOGGER.debug("deleteUser({})", id);
        userRepository.deleteById(id);
    }
}
