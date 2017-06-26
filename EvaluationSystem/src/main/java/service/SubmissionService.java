package service;

import exception.ExistentEntityException;
import exception.InvalidAnswerException;
import exception.InvalidQuestionException;
import exception.NonExistentEntityException;
import model.persistent.*;
import org.orm.PersistentException;

import java.util.Map;

public interface SubmissionService {

    Submission getSubmissionByID(int ID) throws PersistentException, NonExistentEntityException;
    QuestionSubmission getQuestionSubmission(Submission submission, Question question) throws NonExistentEntityException;
    boolean exists(int ID) throws PersistentException;
    boolean exists(Student student, Exam exam) throws PersistentException;

    Submission submit(Student student, Exam exam, Map<Question, Answer> answers) throws ExistentEntityException, PersistentException, InvalidQuestionException, InvalidAnswerException;
}