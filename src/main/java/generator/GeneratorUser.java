package generator;
import model.user.User;
import model.user.WithoutEmailUser;
import model.user.WithoutNameUser;
import model.user.WithoutPasswordUser;
import org.apache.commons.lang3.RandomStringUtils;

public class GeneratorUser {
    public User createUser() {
        return new User(
                createUniqueEmail(),
                createPassword(),
                createName());
    }
    public WithoutNameUser createUserWithoutName() {
        return new WithoutNameUser(
                createUniqueEmail(),
                createPassword());
    }


    public WithoutPasswordUser createUserWithoutPassword() {
        return new WithoutPasswordUser(
                createUniqueEmail(),
                createName());
    }


    public WithoutEmailUser createUserWithoutEmail() {
        return new WithoutEmailUser(
                createName(),
                createPassword());
    }


    private String createUniqueEmail() {
        return String.format("%s@%s.ru", RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(3));
    }

    private String createPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    private String createName() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}
