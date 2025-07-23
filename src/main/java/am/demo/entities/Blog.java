package am.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private String category;
    private String title;
    private String cover;
    private String content;
    private int timeToRead;

    @ManyToOne
    private Author authorId;

    public Blog(String category, String title, String content, String cover, Author author) {
        Random random = new Random();
        this.category = category;
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.timeToRead = random.nextInt(1, 10);
        this.authorId = author;
    }
}
