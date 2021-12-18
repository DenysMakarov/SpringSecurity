package youtube.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
@Getter
public class Developer {
    private long id;
    private String firstName;
    private String lastName;
}
