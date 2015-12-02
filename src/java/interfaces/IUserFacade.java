package interfaces;

import entity.User;
import exception.UserAlreadyExistException;
import java.util.List;

public interface IUserFacade {

    public User getUserByUserId(String id) throws Exception;

    public List<String> authenticateUser(String userName, String password);

    public User createUser(User u) throws UserAlreadyExistException;
    
    public void deleteUserById(String id) throws Exception;
}
