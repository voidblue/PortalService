import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class User {
    private int id;
    private String name;
    private String password;


}
