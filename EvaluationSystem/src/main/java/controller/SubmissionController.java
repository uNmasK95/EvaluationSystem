package controller;

import exception.InvalidAnswerException;
import exception.InvalidQuestionException;
import exception.NonExistentEntityException;
import model.Answer;
import model.Question;
import model.Submission;
import org.orm.PersistentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.JwtService;
import service.AnswerService;
import service.ExamService;
import service.QuestionService;
import service.SubmissionService;
import wrapper.ErrorWrapper;
import wrapper.SubmissionExamWrapper;
import wrapper.SubmissionWrapper;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static controller.ErrorMessages.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "api/submissions")
public class SubmissionController {

    private JwtService jwtService;
    private SubmissionService submissionService;
    private ExamService examService;
    private QuestionService questionService;
    private AnswerService answerService;

    public SubmissionController(JwtService jwtService, SubmissionService submissionService,
                                ExamService examService, QuestionService questionService, AnswerService answerService) {
        this.jwtService = jwtService;
        this.submissionService = submissionService;
        this.examService = examService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @RequestMapping(value = "/{submissionID:[\\d]+}", method = GET)
    public ResponseEntity<Object> getSubmission(@PathVariable int submissionID, HttpServletRequest request){
        // TODO validar utilizador
        try {
            Submission submission = submissionService.getSubmissionByID(submissionID);
            boolean hideAnswers = !examService.examHasFinished(submission.get_exam());
            return new ResponseEntity<Object>(new SubmissionExamWrapper(submission, false, hideAnswers), OK);
        } catch (PersistentException e){
            return new ResponseEntity<Object>(new ErrorWrapper(INTERNAL_ERROR), INTERNAL_SERVER_ERROR);
        } catch (NonExistentEntityException e) {
            return new ResponseEntity<Object>(new ErrorWrapper(NO_SUCH_SUBMISSION), NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{submissionID:[\\d]+}", method = PUT)
    public ResponseEntity<Object> updateSubmission(@PathVariable int submissionID,
                                                   @RequestBody Map<Integer, Integer> answersMap,
                                                   HttpServletRequest request){
        // TODO validar utilizador
        try {
            Submission submission = submissionService.getSubmissionByID(submissionID);
            Map<Question, Answer> answers = getAnswers(answersMap);
            submission = submissionService.updateSubmission(submission, answers);
            return new ResponseEntity<Object>(new SubmissionWrapper(submission, false, true), OK);
        } catch (PersistentException e){
            return new ResponseEntity<Object>(new ErrorWrapper(INTERNAL_ERROR), INTERNAL_SERVER_ERROR);
        } catch (NonExistentEntityException e) {
            return new ResponseEntity<Object>(new ErrorWrapper(NO_SUCH_SUBMISSION), NOT_FOUND);
        } catch (InvalidAnswerException e) {
            return new ResponseEntity<Object>(new ErrorWrapper(INVALID_ANSWER), NOT_ACCEPTABLE);
        } catch (InvalidQuestionException e) {
            return new ResponseEntity<Object>(new ErrorWrapper(INVALID_QUESTION), NOT_ACCEPTABLE);
        }
    }
    private Map<Question, Answer> getAnswers(Map<Integer, Integer> answersMap) throws PersistentException, NonExistentEntityException {
        Map<Question, Answer> answers = new HashMap<>();
        for(int qid: answersMap.keySet()){
            Question question = questionService.getQuestionByID(qid);
            Answer answer = answerService.getAnswerByID(answersMap.get(qid));
            answers.put(question, answer);
        }
        return answers;
    }
}