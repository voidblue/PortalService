import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.support.NullValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
@SuppressWarnings("Duplicate")
class test {
    private UserDao userDao;
    private DaoFatory daoFactory;

    @BeforeEach
    public void setup(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFatory.class);
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }

    @Test
    public void getTest()  {
        User user = null;
        //내가 확신할 수 없는 에러는 throw로 해서 다음 사람이 처리할 수 있도록 할 것
        try {
            user = userDao.get(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("jaeyun"));
        assertThat(user.getPassword(), is("1234"));
    }
    @Test
    public void addTest() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("jaeyun2");
        user.setPassword("12345");
        int id = userDao.insert(user);
        User insertedUser = userDao.get(id);

        assertThat(insertedUser.getId(), is(id));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("jaeyun2");
        user.setPassword("12345");
        int id = userDao.insert(user);
        User insertedUser = userDao.get(id);

        user.setName("김재윤");
        user.setPassword("123456");
        userDao.update(user);

        User updatedUser = userDao.get(id);
        assertThat(updatedUser.getId(), is(updatedUser.getId()));
        assertThat(updatedUser.getName(), is(updatedUser.getName()));
        assertThat(updatedUser.getPassword(), is(updatedUser.getPassword()));
    }

    private Integer insertUserTest(User user) throws SQLException, ClassNotFoundException {
        user.setName("bye");
        user.setPassword("1234");
        return userDao.insert(user);
    }

    @Test void delete() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        userDao.delete(id);

        User deletedUser = userDao.get(id);
        assertThat(deletedUser, nullValue());
    }

}