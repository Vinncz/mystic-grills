package views;

import java.util.ArrayList;
import java.util.stream.Collectors;

import application_starter.App;
import controllers.UserController;
import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.LabelValidationStrategy;
import design_patterns.strategy_pattern.TextfieldValidationStrategy;
import design_patterns.strategy_pattern.VanishingLabelValidationStrategy;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import repositories.UserRepository;
import values.strings.ValidationState;
import views.components.buttons.CTAButton;
import views.components.buttons.TextButton;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.textfields.DefaultTextfield;
import views.components.textfields.PasswordTextfield;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class RegisterPage extends BorderPane implements PageDeclarationGuideline_v1 {

	private ScrollPane scrollSupport;
	private HBox rootElement;
	private VBox container;

	private VBox pageIdentifierContainer;
	private Label brand, pageTitle;

	private VBox pageContent;
	private HBox userSectionContainer, passwordSectionContainer;
	private VBox userFieldContainer, emailFieldContainer, passwordFieldContainer, passwordConfirmationContainer;

	private Label usernameLabel, emailLabel, passwordLabel, passwordConfirmationLabel;
	private TextField usernameField, emailField, passwordField, passwordConfirmationField;
	private Label usernameWarnLabel, emailWarnLabel, passwordWarnLabel, passwordConfirmationWarnLabel;

	private VBox buttonContainer;
	private Button loginButton, registerButton;

	public RegisterPage() {
		initializeScene();
	}

	private ArrayList<ValidationState> errorToWatchForUsernameRelatedElements = new ArrayList<>() {
		{
			add(ValidationState.EMPTY_USERNAME);
		}
	};
	private ArrayList<ValidationState> errorToWatchForEmailRelatedElements = new ArrayList<>() {
		{
			add(ValidationState.DUPLICATE_EMAIL);
			add(ValidationState.EMPTY_EMAIL);
		}
	};
	private ArrayList<ValidationState> errorToWatchForPasswordRelatedElements = new ArrayList<>() {
		{
			add(ValidationState.EMPTY_PASSWORD);
			add(ValidationState.INVALID_PASSWORD_LENGTH);
		}
	};
	private ArrayList<ValidationState> errorToWatchForPasswordConfirmationRelatedElements = new ArrayList<>() {
		{
			add(ValidationState.EMPTY_CONFIRMATION_PASSWORD);
			add(ValidationState.INCORRECT_CONFIRMATION_PASSWORD);
		}
	};

	@Override
	public void initializeControls() {
		rootElement 	= new RootElement();
		container 		= new Container().centerContentHorizontally();
		scrollSupport   = new BaseScrollPane(rootElement);

		pageIdentifierContainer = new BaseVBox().withNoSpacing().centerContentHorizontally();
			brand     = new H1Label("Mystic Grills").withBoldFont().withAlternateFont();
			pageTitle = new H3Label("Registration Portal").withExtraBoldFont();

		pageContent = new BaseVBox().withNormalSpacing().centerContentHorizontally();
		userSectionContainer = new BaseHBox().withTightSpacing().centerContentHorizontally();
			userFieldContainer	= new BaseVBox().withTightSpacing().growsHorizontally();
				usernameLabel	= new H5Label("Username")
										.setStrategy(
												new LabelValidationStrategy()
													.setRegisteredErrorWatchList(errorToWatchForUsernameRelatedElements)
										);
				usernameField	= new DefaultTextfield("Username Here")
										.setStrategy(
												new TextfieldValidationStrategy()
													.setRegisteredErrorWatchList(errorToWatchForUsernameRelatedElements)
										);
				usernameWarnLabel = new H5Label("Warn")
										.setStrategy(
												new VanishingLabelValidationStrategy()
													.setRegisteredErrorWatchList(errorToWatchForUsernameRelatedElements)
										);

			emailFieldContainer = new BaseVBox().withTightSpacing().growsHorizontally();
				emailLabel		= new H5Label("Email")
										.setStrategy(
												new LabelValidationStrategy()
													.setRegisteredErrorWatchList(errorToWatchForEmailRelatedElements)
										);
				emailField		= new DefaultTextfield("Email here")
										.setStrategy(
												new TextfieldValidationStrategy()
													.setRegisteredErrorWatchList(errorToWatchForEmailRelatedElements)
										);
				emailWarnLabel	= new H5Label("warn")
										.setStrategy(
											new VanishingLabelValidationStrategy()
												.setRegisteredErrorWatchList(errorToWatchForEmailRelatedElements)
										);

		passwordSectionContainer = new BaseHBox().withTightSpacing().centerContentHorizontally();
			passwordFieldContainer = new BaseVBox().withTightSpacing().growsHorizontally();
				passwordLabel	= new H5Label("Password")
										.setStrategy(
											new LabelValidationStrategy()
												.setRegisteredErrorWatchList(errorToWatchForPasswordRelatedElements)
										);
				passwordField	= new PasswordTextfield("Password Here")
										.setStrategy(
											new TextfieldValidationStrategy()
												.setRegisteredErrorWatchList(errorToWatchForPasswordRelatedElements)
										);
				passwordWarnLabel = new H5Label("Warn")
										.setStrategy(
											new VanishingLabelValidationStrategy()
												.setRegisteredErrorWatchList(errorToWatchForPasswordRelatedElements)
										);

			passwordConfirmationContainer = new BaseVBox().withTightSpacing().growsHorizontally();
				passwordConfirmationLabel = new H5Label("Password Confirmation")
												.setStrategy(
													new LabelValidationStrategy()
														.setRegisteredErrorWatchList(errorToWatchForPasswordConfirmationRelatedElements)
												);
				passwordConfirmationField = new PasswordTextfield("Confirm Password Here")
												.setStrategy(
													new TextfieldValidationStrategy()
														.setRegisteredErrorWatchList(errorToWatchForPasswordConfirmationRelatedElements)
												);
				passwordConfirmationWarnLabel = new H5Label("Warn")
													.setStrategy(
														new VanishingLabelValidationStrategy()
															.setRegisteredErrorWatchList(errorToWatchForPasswordConfirmationRelatedElements)
													);

				buttonContainer = new BaseVBox().withTightSpacing().centerContentBothAxis();
					registerButton	= new CTAButton("Make my registration").withBoldFont();
					loginButton		= new TextButton("I have an account");
	}

	@Override
	public void configureElements() {
		ArrayList<Object> _errorToWatchForUsernameRelatedElements = new ArrayList<>(errorToWatchForUsernameRelatedElements.stream().map(ValidationState::value).collect(Collectors.toList()));
		ArrayList<Object> _errorToWatchForEmailRelatedElements = new ArrayList<>(errorToWatchForEmailRelatedElements.stream().map(ValidationState::value).collect(Collectors.toList()));
		ArrayList<Object> _errorToWatchForPasswordRelatedElements = new ArrayList<>(errorToWatchForPasswordRelatedElements.stream().map(ValidationState::value).collect(Collectors.toList()));
		ArrayList<Object> _errorToWatchForPasswordConfirmationRelatedElements = new ArrayList<>(errorToWatchForPasswordConfirmationRelatedElements.stream().map(ValidationState::value).collect(Collectors.toList()));

		App.preferences.subscribeToMany(
			_errorToWatchForUsernameRelatedElements,

			(Observer) usernameLabel,
			(Observer) usernameWarnLabel,
			(Observer) usernameField
		);

		App.preferences.subscribeToMany(
			_errorToWatchForEmailRelatedElements,

			(Observer) emailLabel,
			(Observer) emailField,
			(Observer) emailWarnLabel
		);

		App.preferences.subscribeToMany(
			_errorToWatchForPasswordRelatedElements,

			(Observer) passwordLabel,
			(Observer) passwordField,
			(Observer) passwordWarnLabel
		);

		App.preferences.subscribeToMany(
			_errorToWatchForPasswordConfirmationRelatedElements,

			(Observer) passwordConfirmationLabel,
			(Observer) passwordConfirmationWarnLabel,
			(Observer) passwordConfirmationField
		);

		userFieldContainer.setMaxWidth(500);
		emailFieldContainer.setMaxWidth(500);
		passwordFieldContainer.setMaxWidth(500);
		passwordConfirmationContainer.setMaxWidth(500);

		usernameWarnLabel.setVisible(false);
			usernameWarnLabel.setManaged(false);
		emailWarnLabel.setVisible(false);
			emailWarnLabel.setManaged(false);
		passwordWarnLabel.setVisible(false);
			passwordWarnLabel.setManaged(false);
		passwordConfirmationWarnLabel.setVisible(false);
			passwordConfirmationWarnLabel.setManaged(false);

		pageContent.getStyleClass().addAll("py-16");
		pageContent.setSpacing(24);
		buttonContainer.getStyleClass().addAll("pt-16");

	}

	@Override
	public void initializeEventListeners() {
		usernameField.setOnMouseClicked(e -> {
			for (ValidationState vs : errorToWatchForUsernameRelatedElements) {
				App.preferences.putValue(vs.value, false);
			}
		});

		emailField.setOnMouseClicked(e -> {
			for (ValidationState vs : errorToWatchForEmailRelatedElements) {
				App.preferences.putValue(vs.value, false);
			}
		});

		passwordField.setOnMouseClicked(e -> {
			for (ValidationState vs : errorToWatchForPasswordRelatedElements) {
				App.preferences.putValue(vs.value, false);
			}
		});

		passwordConfirmationField.setOnMouseClicked(e -> {
			for (ValidationState vs : errorToWatchForPasswordConfirmationRelatedElements) {
				App.preferences.putValue(vs.value, false);
			}
		});

		loginButton.setOnMouseClicked(e -> {
			resetErrors();
			App.redirectTo(App.sceneBuilder(new LoginPage()));
		});

		registerButton.setOnMouseClicked(e -> {
			String username             = usernameField.getText();
			String email                = emailField.getText();
			String password             = passwordField.getText();
			String passwordConfirmation = passwordConfirmationField.getText();

			UserController uc = new UserController();
			UserRepository.AuthenticationReturnDatatype registrationResult = uc.validateRegistration(username, email, password, passwordConfirmation);

			if (registrationResult.getState() != null) {
				App.preferences.putValue(registrationResult.getMessage(), true);

			} else {
				App.preferences.putValue(App.CURRENT_USER_KEY, registrationResult.getAssociatedUser());

				App.redirectTo(App.sceneBuilder(new CustomerDashboardPage()));
			}
		});
	}

	@Override
	public void assembleLayout() {
		pageIdentifierContainer.getChildren().addAll(
			brand,
			pageTitle
		);

		buttonContainer.getChildren().addAll(
			registerButton,
			loginButton
		);

		userFieldContainer.getChildren().addAll(
			usernameLabel,
			usernameField,
			usernameWarnLabel
		);

		emailFieldContainer.getChildren().addAll(
			emailLabel,
			emailField,
			emailWarnLabel
		);

		passwordFieldContainer.getChildren().addAll(
			passwordLabel,
			passwordField,
			passwordWarnLabel
		);

		passwordConfirmationContainer.getChildren().addAll(
			passwordConfirmationLabel,
			passwordConfirmationField,
			passwordConfirmationWarnLabel
		);

		userSectionContainer.getChildren().addAll(
			userFieldContainer,
			emailFieldContainer
		);

		passwordSectionContainer.getChildren().addAll(
			passwordFieldContainer,
			passwordConfirmationContainer
		);

		pageContent.getChildren().addAll(
			userSectionContainer,
			passwordSectionContainer
		);

		container.getChildren().addAll(
			pageIdentifierContainer,
			pageContent,
			buttonContainer
		);

		rootElement.getChildren().addAll(
			container
		);
	}

	@Override
	public void setupScene() {
		setCenter(scrollSupport);
	}

	private void resetErrors() {
		ArrayList<ValidationState> errors = new ArrayList<>() {{
			addAll(errorToWatchForUsernameRelatedElements);
			addAll(errorToWatchForEmailRelatedElements);
			addAll(errorToWatchForPasswordRelatedElements);
			addAll(errorToWatchForPasswordConfirmationRelatedElements);
		}};

		errors.forEach(f -> App.preferences.putValue(f.value, false));
	}

}
