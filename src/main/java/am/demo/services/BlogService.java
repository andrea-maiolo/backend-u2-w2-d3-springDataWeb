package am.demo.services;

import am.demo.entities.Author;
import am.demo.entities.Blog;
import am.demo.exceptions.NotfoundException;
import am.demo.payloads.BlogPayload;
import am.demo.repositories.AuthorRepo;
import am.demo.repositories.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private AuthorRepo authorRepo;

    public Blog saveBlog(BlogPayload payloadb) {
        Author blogAuthor = this.authorRepo.findById(payloadb.getAuthorId()).orElseThrow(() ->
                new NotfoundException("author not found"));
        Blog blogToSave = new Blog(payloadb.getCategory(), payloadb.getTitle(), payloadb.getContent(),
                "https://picsum.photos/200/300", blogAuthor);
        this.blogRepo.save(blogToSave);
        return blogToSave;
    }

    public Page<Blog> getAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber,
                pageSize, Sort.by(sortBy).descending());
        return this.blogRepo.findAll(pageable);
    }


    public Blog findBlogById(UUID blogId) {
        return this.blogRepo.findById(blogId).orElseThrow(() ->
                new NotfoundException("blog not found"));

    }

    public Blog modifyBlog(BlogPayload payload, UUID blogId) {
        Blog found = this.findBlogById(blogId);
        found.setCategory(payload.getCategory());
        found.setTitle(payload.getTitle());
        found.setContent(payload.getContent());
        return found;
    }

    public void deleteBlog(UUID blogId) {
        Blog found = findBlogById(blogId);
        blogRepo.delete(found);
    }

}
