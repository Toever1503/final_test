package com.controller.api;

import com.entity.dto.ResponseDto;
import com.entity.model.CommentModel;
import com.repository.OrderLineRepository;
import com.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentResources {

    private final CommentService commentService;

    public CommentResources(CommentService commentService ) {
        this.commentService = commentService;
    }

    @CrossOrigin("*")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Object addRating(@RequestBody CommentModel commentModel) {
        System.out.println(commentModel);
        commentModel.setId(null);
        return ResponseDto.of(commentService.add(commentModel), "Added comment");
    }
}
