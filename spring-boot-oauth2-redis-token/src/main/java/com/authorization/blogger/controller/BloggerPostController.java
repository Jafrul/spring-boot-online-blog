package com.authorization.blogger.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.blogger.service.BloggerService;
import com.authorization.common.messages.BaseResponse;
import com.authorization.common.utils.StatusValue;
import com.authorization.user.model.dto.BlogDTO;

@RestController
@RequestMapping(value = "/blogger")
@Validated
public class BloggerPostController {

	@Autowired
	private BloggerService bloggerService;

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/post/create")
	public ResponseEntity<BaseResponse> createBlogPostByBlogger(@Valid @RequestBody BlogDTO blogDTO) {
		BaseResponse response = bloggerService.createBlogPostByBlogger(blogDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('USER')")
	@DeleteMapping(value = "/delete/{userId}/{blogId}")
	public ResponseEntity<BaseResponse> deleteOwnBlogPostById(@Valid @NotNull(message = "userId must not be null") @PathVariable("userId") int userId,
															@Valid @NotNull(message = "blogId must not be null") @PathVariable("blogId") Long blogId) {
		BaseResponse response = bloggerService.deleteOwnBlogPostById(userId, blogId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/find")
	public ResponseEntity<List<BlogDTO>> findAllApproedBloggerPost() {
		List<BlogDTO> list = bloggerService.findAllApproedBloggerPost(StatusValue.ACTIVE.getValue(), StatusValue.ACTIVE.getValue());
		return new ResponseEntity<List<BlogDTO>>(list, HttpStatus.OK);
	}
	
}
