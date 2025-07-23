package am.demo.controllers;

import am.demo.entities.Blog;
import am.demo.payloads.BlogPayload;
import am.demo.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog addBlog(@RequestBody BlogPayload payload) {
        return this.blogService.saveBlog(payload);
    }

    @GetMapping
    public Page<Blog> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "id") String sort) {
        return this.blogService.getAll(pageNumber, pageSize, sort);
    }

    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable UUID blogId) {
        return this.blogService.findBlogById(blogId);
    }

    @PutMapping("/{blogId}")
    public Blog modifyBlog(@RequestBody BlogPayload payload, @PathVariable UUID blogId) {
        return this.blogService.modifyBlog(payload, blogId);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable UUID blogId) {
        this.blogService.deleteBlog(blogId);
    }

}
