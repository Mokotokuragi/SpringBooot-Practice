package com.springboot.Springbootpracticerestapi.repository;

import com.springboot.Springbootpracticerestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
