package am.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dob;
    private String avatar;

    public Author(String name, String surname, String email, String avatar, LocalDate dob) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = avatar;
        this.dob = dob;
    }

}
