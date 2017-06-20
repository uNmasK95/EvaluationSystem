import dao.ClassCriteria;
import dao.ClassDAO;
import dao.UserCriteria;
import dao.UserDAO;
import exception.ExistentEntityException;
import exception.InvalidUserTypeException;
import exception.MissingInformationException;
import exception.NonExistentEntityException;
import model.Class;
import model.Teacher;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.ClassService;
import service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ClassTest {

    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ClassDAO classDAO;

    private Class cl;
    private String clUniqueName = "AA@GetClassTest";
    private Teacher teacher;
    private String teacherUniqueEmail = "john@GetClassTest";

    @Before
    public void addClass() throws PersistentException, MissingInformationException, InvalidUserTypeException {
        this.cl = new Class();
        cl.setAbbreviation("AA");
        cl.setName(clUniqueName);

        this.teacher = new Teacher();
        teacher.setEmail(teacherUniqueEmail);
        teacher.setPassword("password");
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        try {
            teacher = (Teacher) userService.signup(teacher, "teacher");
        }
        catch (ExistentEntityException e){
            try {
                teacher = (Teacher) userService.getUserByEmail(teacherUniqueEmail);
            } catch (NonExistentEntityException e1) {}
        }

        cl.set_teacher(teacher);
        classService.addClass(cl);
    }

    @Test
    public void get_Class() throws PersistentException {
        Class clGet = null;
        try {
            clGet = classService.getClassByID(cl.getID());
        } catch (NonExistentEntityException e) {}
        Assert.assertNotNull(clGet);

        Class clGetNotExistent = null;
        try {
            clGetNotExistent = classService.getClassByID(9999999);
        } catch (NonExistentEntityException e) {}
        Assert.assertNull(clGetNotExistent);
    }

    @After
    public void cleanup() throws PersistentException {
        classService.delete(cl);
        userService.delete(teacher);

    }
}
