package service;

import dao.GroupDAO;
import dao.GroupStudentDAO;
import exception.*;
import model.persistent.*;
import model.persistent.Class;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GroupServiceImpl implements GroupService{

    GroupDAO groupDAO;
    GroupStudentDAO groupStudentDAO;
    UserService userService;
    StudentService studentService;
    NotificationService notificationService;
    ClassService classService;
    ExamService examService;

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }
    @Autowired
    public void setClassService(ClassService classService) {
        this.classService = classService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public GroupServiceImpl(GroupDAO groupDAO, GroupStudentDAO groupStudentDAO) {
        this.groupDAO = groupDAO;
        this.groupStudentDAO = groupStudentDAO;
    }

    @Override
    public boolean userHasAccess(Group group, User user) {
        if(user instanceof Teacher)
            return user.getID() == group.get_class().get_teacher().getID();
        Student student = (Student) user;
        return studentInGroup(student,group);
    }

    @Override
    public boolean exists(int ID) throws PersistentException {
        return this.groupDAO.exists(ID);
    }

    @Override
    public boolean exists(Class cl, String name) throws PersistentException {
        return this.groupDAO.exists(cl, name);
    }

    @Override
    public Group getGroupByID(int ID) throws PersistentException, NonExistentEntityException {
        if(!this.exists(ID))
            throw new NonExistentEntityException();

        else return groupDAO.loadGroupByORMID(ID);
    }

    @Override
    public Group getGroupByName(Class cl, String name) throws PersistentException, NonExistentEntityException {
        if(!this.exists(cl,name))
            throw new NonExistentEntityException();

        else return groupDAO.loadGroupByName(cl, name);
    }

    @Override
    public Group addGroup(Group group) throws PersistentException {
        this.groupDAO.save(group);
        return group;
    }

    @Override
    public void delete(Group group) throws PersistentException {
        Class cl = group.get_class();
        cl._groups.remove(group);
        this.groupDAO.delete(group);
    }

    @Override
    public List<GroupStudent> getGroupStudents(Group group) {
        return Arrays.asList(group._students.toArray());
    }

    @Override
    public GroupStudent addStudentToGroupByEmail(Group group, String email)
            throws PersistentException, InvalidUserTypeException, ExistentEntityException {
        Student student = null;
        if(!userService.existsActive(email)){
            if(!userService.exists(email)) {
                try {
                    student = studentService.createStudent();
                    student.setEmail(email);
                    student = studentService.addStudent(student, false);
                } catch (MissingInformationException e) {
                    e.printStackTrace(); // Nunca acontece
                } catch (ExistentEntityException e) {
                    e.printStackTrace();
                    throw new PersistentException(); // Nunca deve acontecer
                }
            }
            else{
                try {
                    student = studentService.getStudentByEmail(email);
                } catch (NonExistentEntityException e) {
                    e.printStackTrace(); //Nunca deve acontecer
                }
            }
        }
        else{
            if(studentService.existsActive(email)) {
                try {
                    student = studentService.getStudentByEmail(email);
                } catch (NonExistentEntityException e) {
                    e.printStackTrace(); // Nunca deve acontecer
                }
            }
            else
                throw new InvalidUserTypeException();
        }

        if(groupStudentDAO.exists(group.getID(), student.getID()))
            throw new ExistentEntityException();
        return this.addStudentToGroup(group,student);
    }

    private GroupStudent addStudentToGroup(Group group, Student student) throws PersistentException {
        GroupStudent groupStudent = this.createGroupStudent(group,student);
        this.groupStudentDAO.save(groupStudent);
        this.notificationService.addGroupInvitation(group, student);
        return groupStudent;
    }

    @Override
    public void removeStudentFromGroup(Group group, Student student) throws PersistentException, NonExistentEntityException {
        GroupStudent groupStudent = this.groupStudentDAO.loadGroupStudentByGroupAndStudent(
                group.getID(), student.getID());
        if(groupStudent == null)
            throw new NonExistentEntityException();

        group._students.remove(groupStudent);
        this.groupStudentDAO.delete(groupStudent);
        GroupInvitation groupInvitation = notificationService.getGroupInvitation(group,student);
        notificationService.removeGroupInvitation(groupInvitation);
    }

    private GroupStudent createGroupStudent(Group group, Student student){
        GroupStudent groupStudent = new GroupStudent();
        groupStudent.set_group(group);
        groupStudent.set_student(student);
        groupStudent.setAccepted(false);
        return groupStudent;
    }

    @Override
    public boolean studentInGroup(Student student, Group group) {
        for(GroupStudent groupStudent: group._students.toArray()) {
            if (groupStudent.get_student().getID() == student.getID())
                return true;
        }
        return false;
    }

    @Override
    public boolean questionInExams(Group group, Question question) throws PersistentException {
        for(Exam exam: group._exams.toArray()){
            if(examService.examContainsQuestion(exam,question))
                return true;
        }
        return false;
    }

    @Override
    public List<Question> listAvailableQuestions(Group group) throws PersistentException {
        List<Question> classQuestions = classService.listClassQuestions(group.get_class());
        List<Question> availableQuestions = new ArrayList<>();
        for(Question q: classQuestions){
            if(!this.questionInExams(group,q))
                availableQuestions.add(q);
        }
        return availableQuestions;
    }

    @Override
    public Map<String, Map<Integer, List<Question>>> getAvailableQuestionsByCategoryAndDifficulty(Group group)
            throws PersistentException {
        List<Question> questions = this.listAvailableQuestions(group);
        Map<String, Map<Integer, List<Question>>> categoriesMap = new TreeMap<>();
        for(Question question: questions){
            String category = question.getCategory();
            int difficulty = question.getDifficulty();
            if(!categoriesMap.containsKey(category))
                categoriesMap.put(category, new TreeMap<Integer, List<Question>>());
            Map<Integer, List<Question>> difficultiesMap = categoriesMap.get(category);
            if(!difficultiesMap.containsKey(difficulty))
                difficultiesMap.put(difficulty, new ArrayList<Question>());

            categoriesMap.get(category).get(difficulty).add(question);
        }
        return categoriesMap;
    }

    @Override
    public List<Question> generateExamQuestions(Group group, List<String> categories, List<Integer> difficulties)
            throws PersistentException, InvalidInputException, InsufficientQuestionsException {
        if(categories.size() != difficulties.size())
            throw new InvalidInputException("Categories and dificulties lists must be the same size");

        Map<String, Map<Integer, List<Question>>> categoriesMap = getAvailableQuestionsByCategoryAndDifficulty(group);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i<categories.size(); i++){
            String category = categories.get(i);
            int difficulty = difficulties.get(i);
            if(!categoriesMap.containsKey(category))
                throw new InsufficientQuestionsException();
            Map<Integer, List<Question>> difficultiesMap = categoriesMap.get(category);
            if(!difficultiesMap.containsKey(difficulty))
                throw new InsufficientQuestionsException();
            List<Question> questions = difficultiesMap.get(difficulty);
            if(questions.isEmpty())
                throw new InsufficientQuestionsException();

            int randomQuestionIndex =  ThreadLocalRandom.current().nextInt(0, questions.size());
            Question selectedQuestion = questions.get(randomQuestionIndex);
            questions.remove(randomQuestionIndex);

            res.add(selectedQuestion);
        }

        return res;
    }

}