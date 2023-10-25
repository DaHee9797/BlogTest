package com.example.BlogTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    private PostRepository postRepository;

    @RequestMapping("/")
    public String main(Model model) {
        List<Post> postList = postRepository.findAll();

        if (postList.isEmpty()) {
            write();
            postList = postRepository.findAll();
        }
        return "redirect:/detail/" + postList.get(0).getId();
    }

    @RequestMapping("/write")
    public String write() {
        Post post = new Post();
        post.setTitle("new title..");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);

        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Post post = postRepository.getReferenceById(id);
        model.addAttribute("targetPost", post);
        model.addAttribute("postList", postRepository.findAll());

        return "main";
    }
    @RequestMapping("/update")
    public String update(Long id, String title, String content) {
        Post post = postRepository.getReferenceById(id);
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);

        return "redirect:/detail/" + id;
    }
}
