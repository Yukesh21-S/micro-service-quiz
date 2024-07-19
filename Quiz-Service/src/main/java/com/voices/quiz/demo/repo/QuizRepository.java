package com.voices.quiz.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voices.quiz.demo.models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}