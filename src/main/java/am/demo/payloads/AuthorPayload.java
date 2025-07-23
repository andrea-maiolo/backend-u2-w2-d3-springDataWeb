package am.demo.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AuthorPayload {
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private LocalDate dob;
}
