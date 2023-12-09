package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.User;
import models.User.UserRole;
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
     * Validates user registration data. If everything's clean, proceed to make and then return the new account.
     *
     * @param _username • The username of the user to validate.
     * @param _userEmail • The email of the user to validate.
     * @param _userPassword • The password of the user to validate.
     * @param _userConfirmPassword • The confirmation password of the user to validate.
     * @return An instance of {@code AuthenticationReturnDatatype}, which indicates the whether the validation is successfully done or not through its state;
     */
    public UserRepository.AuthenticationReturnDatatype validateRegistration(String _username, String _email, String _password, String _confirmationPassword) {
        UserRepository.AuthenticationReturnDatatype returnObject = new UserRepository.AuthenticationReturnDatatype();

        if (validator.isBlank(_username)) {
            returnObject.setState(ValidationState.EMPTY_USERNAME);
            return returnObject;
        }

        if (validator.isBlank(_email)) {
            returnObject.setState(ValidationState.EMPTY_EMAIL);
            return returnObject;
        }

        Optional<User> target = userRepo.getUserByEmail(_email);
        if (target.isPresent()) {
            returnObject.setState(ValidationState.DUPLICATE_EMAIL);
            return returnObject;
        }

        if (validator.isBlank(_password)) {
            returnObject.setState(ValidationState.EMPTY_PASSWORD);
            return returnObject;
        }

        if (validator.lessThanNCharacters(_password, 6)) {
            returnObject.setState(ValidationState.INVALID_PASSWORD_LENGTH);
            return returnObject;
        }

        if (validator.isBlank(_confirmationPassword)) {
            returnObject.setState(ValidationState.EMPTY_CONFIRMATION_PASSWORD);
            return returnObject;
        }

        if (!_password.equals(_confirmationPassword)) {
            returnObject.setState(ValidationState.INCORRECT_CONFIRMATION_PASSWORD);
            return returnObject;
        }

        /* if the code manages to reach this point, then the data is assumed to be sterile */
        User u = new User();
        u.setUserName(_username);
        u.setUserEmail(_email);
        u.setUserPassword(_confirmationPassword);
        u.setUserRole(UserRole.CUSTOMER);

        u = userRepo.post(u);

        returnObject.setAssociatedUser(u);

        return returnObject;
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
