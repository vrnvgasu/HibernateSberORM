package ru.edu;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.edu.config.MyConfig;
import ru.edu.dao.UserEntity;
import ru.edu.dao.UserRepository;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);

        UserRepository repository = applicationContext.getBean(UserRepository.class);
        UserEntity user1 = repository.getById("1");
        System.out.println("user1: " + user1);

        ///////////
        UserEntity newUser = new UserEntity();
        newUser.setId("2");
        newUser.setLastName("Testov");
        newUser.setFirstName("Test");
        newUser.setBirthDate("2000-01-01");

        if (repository.getById("2") == null) {
            repository.create(newUser);
        }

        UserEntity user2 = repository.getById("2");
        System.out.println("user2: " + user2);

        ///////////
        UserEntity updatedUser = new UserEntity();
        updatedUser.setId("2");
        updatedUser.setLastName("Sid");
        updatedUser.setFirstName("Ant");
        updatedUser.setBirthDate("2000-01-01");

        repository.update(updatedUser);
        System.out.println("updatedUser: " + repository.getById(updatedUser.getId()));


        ///////////
        repository.deleteById("2");
        System.out.println("user2: " + repository.getById("2"));

        ///////////
        List<UserEntity> users = repository.getAll();
        System.out.println("users: " + users);
    }
}
