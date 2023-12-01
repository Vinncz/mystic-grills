package views.guidelines;

import javafx.scene.Scene;

public interface PageDeclarationGuideline_v1 {

    /**
     * This is the default course of action when setting up a new JavaFX scene.
     */
    public default void initializeScene () {
        initializeControls();
        configureElements();
        initializeEventListeners();
        assembleLayout();
        setupScene();
    }

    /**
     * Initialize your scene's various elements here.
     */
    public void initializeControls();

    /**
     * Customize your initialized control elements here.
     * <br></br>
     * The recommended actions may include:
     * <ul>
     *     <li>Styling up your declared control elements</li>
     *     <li>Set up custom properties for a particular control elements</li>
     * </ul>
     */
    public void configureElements();

    /**
     * Attach any event listeners to your control element of choice, here.
     * <br></br>
     * These event listeners serve to determine what action does a particular element is expected to do when interacted with.
     */
    public void initializeEventListeners();

    /**
     * Attach your declared-and-configured control elements to your root component here.
     */
    public void assembleLayout();

    /**
     * Set up your scene's root element here.
     * Idealy, there can only be ONE root element for any given scene.
     */
    public void setupScene();

    /**
     * Replaces whichever Scene the PrimaryStage is showing you with the passed argument.
     * @param _targetScene
     */
    public void redirectTo(Scene _targetScene);

}
