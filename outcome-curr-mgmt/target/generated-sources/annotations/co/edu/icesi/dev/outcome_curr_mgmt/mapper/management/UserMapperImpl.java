package co.edu.icesi.dev.outcome_curr_mgmt.mapper.management;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.management.LoginOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.management.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2024-12-03T03:43:20-0500",
=======
    date = "2024-12-03T02:19:20-0500",
>>>>>>> 34859dd2f40d4816f89d6612e1fc3ade8fcc4d72
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromLoginOutDTO(LoginOutDTO loginOutDTO) {
        if ( loginOutDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        return user.build();
    }
}
