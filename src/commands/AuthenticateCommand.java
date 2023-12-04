package commands;

import controllers.UserController;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.User;

public class AuthenticateCommand extends Command {

    private UserController userControl;

    public AuthenticateCommand(Pane _context) {
        super(_context);
        this.userControl = new UserController();

    }

    public User execute () {
        TextField email = (TextField) this.contextReference.lookup("#email");
        TextField passw = (TextField) this.contextReference.lookup("#password");

        String emailValue = email.getText();
        String passwValue = passw.getText();

        return userControl.authenticateUser (
            emailValue,
            passwValue
        );

    }

}
