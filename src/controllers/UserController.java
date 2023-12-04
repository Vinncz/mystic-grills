package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.User;
import repositories.UserRepository;

public class UserController {

    private UserRepository userRepo;

    public UserController () {
        this.userRepo = new UserRepository();

    }

    public User authenticateUser (String _userEmail, String _userPassword) {
        return userRepo.authenticateUser(_userEmail, _userPassword);

    }

    public Optional<User> getUserByEmail(String _userEmail) {
        return userRepo.getUserByEmail(_userEmail);

    }

    public Optional<User> getById (Integer _id) {
        return userRepo.getById(_id);
        
    }

    public ArrayList<User> getAll () {
        return userRepo.getAll();

    }

    public User post (User _objectToBeInserted) {
        return userRepo.post(_objectToBeInserted);

    }

    public Boolean put (User _replacementObject) {
        return userRepo.put(_replacementObject);

    }

    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        return userRepo.delete(_idOfAnObjectToBeDeleted);

    }
}
