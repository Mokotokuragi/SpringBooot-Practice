package com.springboot.Springbootpracticerestapi.service.impl;

import com.springboot.Springbootpracticerestapi.entity.Post;
import com.springboot.Springbootpracticerestapi.exception.ResourceNotFoundException;
import com.springboot.Springbootpracticerestapi.payload.PostDTO;
import com.springboot.Springbootpracticerestapi.payload.PostResponse;
import com.springboot.Springbootpracticerestapi.repository.PostRepository;
import com.springboot.Springbootpracticerestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository repo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert DTO to entity
//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        Post post = mapToEntity(postDTO);

        Post newPost = repo.save(post);

        //convert entity to DTO
        PostDTO postResponse = mapToDto(newPost);
//        PostDTO postResponse = new PostDTO();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());

        return postResponse;
    }

    @Override
    public  PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // if sort direction is ascending order it will display ascending if it is not the descending will display
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = repo.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List <PostDTO> content = listOfPosts.stream().map(post ->
                mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        // get post by id from the database
        Post post = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = repo.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        repo.delete(post);
    }

    // convert Entity into DTO

    private PostDTO mapToDto(Post post) {
        PostDTO postDTO = mapper.map(post, PostDTO.class);
//        postDTO.getComments();


//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);
//        post.setCommentSet(post.getCommentSet());

//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;
    }
}
