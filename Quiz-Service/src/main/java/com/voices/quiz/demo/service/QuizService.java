package com.voices.quiz.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.voices.quiz.demo.feign.QuizInterface;
import com.voices.quiz.demo.models.QuestionW;
import com.voices.quiz.demo.models.Quiz;
import com.voices.quiz.demo.models.Response;
import com.voices.quiz.demo.repo.QuizRepository;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionW>> getQuizQuestions(Integer id) {
          Quiz quiz = quizDao.findById(id).get();
          List<Integer> questionIds = quiz.getQuestionIds();
          ResponseEntity<List<QuestionW>> questions = quizInterface.getQuestionsFromId(questionIds);
          return questions;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }

    public ResponseEntity<String> deleteQuiz(Integer id) {
        try {
            quizDao.deleteById(id);
            return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete quiz", HttpStatus.BAD_REQUEST);
        }
    }
}