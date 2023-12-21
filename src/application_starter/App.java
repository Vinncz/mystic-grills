package application_starter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import values.SYSTEM_PROPERTIES;
import values.SharedPreference;
import views.LoginPage;

public class App extends Application {

	public static final String CURRENT_USER_KEY = "currentlyLoggedInUser";
	public static final String PASSING_ID_CHANNEL_FOR_MODIFICATION = "channelWhichIsUsedToPassIdForModificationPage";
	public static final String PASSING_ITEM_ORDER_CHANNEL_FOR_CHECKOUT = "https://www.youtube.com/watch?v=wh9QLjk3M2k&t=92s";
	public static final String PASSING_ORDER_QUANTITY_CHANNEL_FOR_CHECKOUT = "https://www.youtube.com/watch?v=AWM5ZNdWlqw";
	public static final String PASSING_ORDER_CHANNEL_FOR_CHECK_ORDER_DETAIL = "https://www.youtube.com/watch?v=AYil1j5vS_4";

	private static Stage primaryStage;
	public  static SharedPreference preferences = new SharedPreference();

	public static Integer stagePadding = 60;

	public static Double STAGE_WIDTH  = -1.0;
	public static Double STAGE_HEIGHT = -1.0;
	public static Boolean IS_MAXIMIZED = false;

	public static void main (String [] args) {
		launch(args);
	}

	@Override
	public void start(Stage _primaryStage) throws Exception {
		primaryStage = _primaryStage;

		final Scene defaultStartupScene = attachStylesheet( sceneBuilder( new LoginPage() ) );
		primaryStage.setScene(defaultStartupScene);

		primaryStage.setMinHeight(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_HEIGHT.value) + (stagePadding * 2) );
		primaryStage.setMinWidth (Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value ) + (stagePadding * 2) );

		primaryStage.setResizable(true);
		primaryStage.setTitle("Mystic Grills");

		primaryStage.show();

		STAGE_HEIGHT = primaryStage.getHeight();
		STAGE_WIDTH = primaryStage.getWidth();
	}

	/**
	 * Attaches the global css file to the passed scene argument.
	 *
	 * @param _target
	 * @return The passed scene argument, with its stylesheet attribute linked to the global css file.
	 */
	private static Scene attachStylesheet (Scene _target) {
		_target.getStylesheets().add(
			App.class.getResource("/views/styles/global.css").toExternalForm()
		);

		return _target;
	}

	/**
     * Replaces whichever Scene the PrimaryStage is showing you, with the passed argument.
	 * <br></br>
	 * <b> DO NOTE </b> that the passed scene <b> MUST NOT </b> be the same instance as the scene which is shown.
	 *
     * @param _targetScene • The scene who will replace the scene that is currently shown.
     */
    public static void redirectTo (Scene _targetScene) {
		STAGE_HEIGHT = primaryStage.getHeight() >= 0 ? primaryStage.getHeight(): Double.parseDouble(SYSTEM_PROPERTIES.APPLICATION_TARGET_HEIGHT.value);
		STAGE_WIDTH  = primaryStage.getWidth()  >= 0 ? primaryStage.getWidth() : Double.parseDouble(SYSTEM_PROPERTIES.APPLICATION_TARGET_WIDTH.value);
		IS_MAXIMIZED = primaryStage.isMaximized();

		_targetScene = attachStylesheet(_targetScene);
		primaryStage.setScene(_targetScene);

		primaryStage.setWidth(STAGE_WIDTH);
		primaryStage.setHeight(STAGE_HEIGHT);

		primaryStage.setMaximized(IS_MAXIMIZED);
	}

	/**
	 * Simplifies the creation of a new scene, using the system properties' application scene target width/height.
	 *
	 * @param _page
	 * @return The created scene object
	 */
	public static Scene sceneBuilder (Pane _page) {

		if (STAGE_HEIGHT == -1 && STAGE_WIDTH == -1) {
			return new Scene (
				_page,
				Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_TARGET_WIDTH.value),
				Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_TARGET_HEIGHT.value)
			);

		} else {
			STAGE_HEIGHT = primaryStage.getHeight();
			STAGE_WIDTH  = primaryStage.getWidth();

			return new Scene (
				_page,
				STAGE_WIDTH,
				STAGE_HEIGHT
			);

		}
	}

}
