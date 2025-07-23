package am.demo.services;

import am.demo.entities.Author;
import am.demo.exceptions.NotfoundException;
import am.demo.exceptions.ValidationException;
import am.demo.payloads.AuthorPayload;
import am.demo.repositories.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepo authorRepo;

    public Author saveAuthor(AuthorPayload payload) {
        this.authorRepo.findByEmail(payload.getEmail()).ifPresent(a -> {
            throw new ValidationException("user already registred");
        });

        Author authorToSave = new Author(payload.getName(), payload.getSurname(), payload.getEmail(),
                ("https://ui-avatars.com/api/?name=" + payload.getName() + "+" + payload.getSurname()), payload.getDob());
        authorRepo.save(authorToSave);
        return authorToSave;
    }

    public Page<Author> getAll(int pageNumber, int pageSize, String sort) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort).descending());
        return authorRepo.findAll(pageable);
    }

    public Author getByID(UUID authorId) {
        return this.authorRepo.findById(authorId).orElseThrow(() -> new NotfoundException(
                "user not found"
        ));
    }

    public Author modifyAuthor(AuthorPayload payload, UUID authorId) {
        Author found = getByID(authorId);
        if (!found.getEmail().equals(payload.getEmail()))
            this.authorRepo.findByEmail(payload.getEmail()).ifPresent(a -> {
                throw new ValidationException("invalid email");
            });
        found.setName(payload.getName());
        found.setSurname(payload.getSurname());
        found.setEmail(payload.getEmail());
        found.setDob(payload.getDob());
        return found;
    }

    public void deleteAuthor(UUID authorId) {
        Author found = getByID(authorId);
        this.authorRepo.delete(found);
    }
}
