package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.User;
import repositories.UserRepository;
import values.strings.ValidationState;

public class UserController {

    private UserRepository userRepo;
    private ValidationController validator;

    public UserController () {
        this.userRepo = new UserRepository();
        this.validator = new ValidationController();

    }

    /**
     * Authenticates a user by validating the provided email and password.
     *
     * @param _userEmail • The email of the user to authenticate.
     * @param _userPassword • The password associated with the user's account.
     * @return An instance of {@code AuthenticationReturnDatatype}, which indicates the whether the validation is successfully done or not through its state; and whether there are any associated account if the state is null.
     */
    public UserRepository.AuthenticationReturnDatatype authenticateUser (String _userEmail, String _userPassword) {
        UserRepository.AuthenticationReturnDatatype returnObject = new UserRepository.AuthenticationReturnDatatype();

        if ( validator.isBlank(_userEmail) || validator.isBlank(_userPassword) ) {
            if ( validator.isBlank(_userEmail) )
                returnObject.setState(ValidationState.EMPTY_EMAIL);
            else if ( validator.isBlank(_userPassword) )
                returnObject.setState(ValidationState.EMPTY_PASSWORD);

            return returnObject;
        }

        if ( validator.lessThanNCharacters(_userPassword, 6) ) {
            returnObject.setState(ValidationState.INVALID_PASSWORD_LENGTH);

            return returnObject;
        }

        return userRepo.authenticateUser(_userEmail, _userPassword);

    }

    /**
     * Communicates with the {@code UserRepository} to bring you the associated account for the given email (if any)
     * @param _userEmail
     */
    public Optional<User> getUserByEmail(String _userEmail) {
        return userRepo.getUserByEmail(_userEmail);

    }

    /**
     * Communicates with the {@code UserRepository} to bring you the associated account for the given email (if any)
     * @param _userEmail
     */
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
