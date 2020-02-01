package sk.janobono.quarkusnut.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sk.janobono.quarkusnut.domain.User;
import sk.janobono.quarkusnut.so.UserSO;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    UserSO userToUserSO(User user);

    User userSOToUser(UserSO userSO);
}
