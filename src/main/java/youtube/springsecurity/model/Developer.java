package youtube.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Getter
@Setter
public class Developer {
    private Long id;
    private String firstName;
    private String lastName;
}
