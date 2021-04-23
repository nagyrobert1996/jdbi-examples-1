package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {



    @SqlUpdate("""
        CREATE TABLE user (
            id IDENTITY PRIMARY KEY,
            username VARCHAR,
            password VARCHAR,
            name VARCHAR,
            email VARCHAR,
            gender ENUM('FEMALE','MALE'),
            birthDate DATE,
            enabled BIT
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, name, email, gender, birthDate, enabled) VALUES (:username, :password, :name, :email, :gender, :birthDate, :enabled)")
    @GetGeneratedKeys
    long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> getUserById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> getUserByUsername(@Bind("username") String username);

    @SqlUpdate("DELETE FROM user WHERE username = :username")
    void deleteUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user")
    List<User> listUser();
}
