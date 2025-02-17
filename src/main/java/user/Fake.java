package user;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.ZoneId;
import java.util.Locale;

public class Fake {

    private Faker faker;

    public Fake(Locale locale) {
        faker = new Faker(locale);
    }

    public User getFakeUser() {
        User user = User.builder()
                .username(faker.name().username())
                .password(DigestUtils.md5Hex(faker.internet().password()))
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(User.Gender.values()))
                .birthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .enabled(faker.bool().bool())
                .build();

        return user;
    }
}
