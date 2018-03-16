import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import java.sql.SQLException;

class test {
    UserDao userDao;


    @BeforeEach
    public void setup(){
        userDao = new UserDao();
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
        int id = userDao.insert(user); //id물론 알고 잇지만 auto increment라고 했을 때 id는 받아서 가져와야함
        User insertedUser = userDao.get(id);

        assertThat(insertedUser.getId(), is(id));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }
}