package facadetest;

import deploy.DeploymentConfiguration;
import entity.User;
import facades.UserFacade;
import interfaces.IUserFacade;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserFacadeTest {

    private IUserFacade facade;

    public UserFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DeploymentConfiguration.setTestModeOn();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        facade = new UserFacade();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateUserWithNonExistingUsername() throws Exception {
        User user = new User("ole", "test");
        facade.createUser(user);
        assertEquals("ole", facade.getUserByUserId(user.getUserName()).getUserName());
        facade.deleteUserById("ole");
    }
}
