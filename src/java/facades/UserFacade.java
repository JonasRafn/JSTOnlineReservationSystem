package facades;

import deploy.DeploymentConfiguration;
import entity.Role;
import entity.User;
import exception.UserAlreadyExistException;
import interfaces.IUserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.PasswordHash;

public class UserFacade implements IUserFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public UserFacade() {

    }

    @Override
    public User getUserByUserId(String id) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    @Override
    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user == null) {
                return null;
            }
            try {
                boolean authenticated = PasswordHash.validatePassword(password, user.getPassword());
                return authenticated ? user.getRolesAsStrings() : null;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } finally {
            em.close();
        }

    }

    @Override
    public User createUser(User u) throws UserAlreadyExistException {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, u.getUserName());

        if (user != null) {
            throw new UserAlreadyExistException("User with user-name: " + u.getUserName() + " already exists");
        } else {
            User newUser = new User();
            newUser.setUserName(u.getUserName());
            List<Role> roles = u.getRoles();
            for (Role role : roles) {
                Role newRole = em.find(Role.class, role.getRoleName());
                newUser.AddRole(newRole);
            }
            try {
                newUser.setPassword(PasswordHash.createHash(u.getPassword()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            em.getTransaction().begin();
            em.persist(newUser);
            em.getTransaction().commit();
        }
        return u;
    }

    @Override
    public void deleteUserById(String id) throws Exception {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

}
