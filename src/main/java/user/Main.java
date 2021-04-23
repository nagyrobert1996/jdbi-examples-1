package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        Fake faker = new Fake(new Locale("hu"));
        List<User> users = new ArrayList<>();
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();
            users.add(faker.getFakeUser());
            dao.insertUser(users.get(0));
            users.add(faker.getFakeUser());
            dao.insertUser(users.get(1));
            users.add(faker.getFakeUser());
            dao.insertUser(users.get(2));
            dao.listUser().forEach(System.out::println);
            dao.deleteUser(users.get(1));
            dao.listUser().forEach(System.out::println);
            users = dao.listUser();
            dao.getUserById(users.get(0).getId()).ifPresent(System.out::println);
            dao.getUserByUsername(users.get(0).getUsername()).ifPresent(System.out::println);
        }
    }
}
