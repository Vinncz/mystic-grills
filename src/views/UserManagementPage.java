package views;

import java.util.ArrayList;
import java.util.Vector;

import controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import models.User;
import models.User.UserRole;
import values.SYSTEM_PROPERTIES;
import views.components.buttons.CTAButton;
import views.components.buttons.DestructiveButton;
import views.components.buttons.DisabledButton;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;


public class UserManagementPage extends BorderPane implements PageDeclarationGuideline_v1
{

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
    private Label pageTitle;

    private VBox pageContent;
    TableView<User> table;
	TableColumn<User,String> usernameColumn, emailColumn, passwordColumn; 
    TableColumn<User,UserRole> roleColumn;
    TableColumn deleteColumn;

    private HBox buttonContainer;
    private Button discardButton, saveButton;

    ArrayList<User> users;
    ArrayList<User> userToDeleteList;
    UserController uc;

    private ObservableList<UserRole> userRole;

    private boolean changesMade;

    @Override
    public void initializeScene () {
        initializeControls();
        configureElements();
        initializeEventListeners();
        assembleLayout();
        setupScene();
        select();
    }

    public UserManagementPage()
    {
        initializeScene();
    }
   
    @Override
    public void initializeControls()
    {
        rootElement    = new RootElement();
        container      = new Container().centerContentHorizontally();
        scrollSupport  = new BaseScrollPane(rootElement);  
        
        pageIdentifierContainer = new BaseVBox();
            pageTitle = new H1Label("Mystic Grills").withBoldFont().withAlternateFont();

        pageContent = new BaseVBox().withNormalSpacing();
            table = new TableView<>();
                usernameColumn = new TableColumn<>("Username");
                emailColumn = new TableColumn<>("Email");
                passwordColumn = new TableColumn<>("Password");
                roleColumn = new TableColumn<>("Role");
                deleteColumn = new TableColumn<>("Delete");

                usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
                passwordColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
                emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
                roleColumn.setCellValueFactory(new PropertyValueFactory<User, UserRole>("userRole"));
                
        buttonContainer = new BaseHBox().withTightSpacing();
            saveButton = new CTAButton("Save");
            discardButton = new DestructiveButton("Discard");

        uc = new UserController();
        userToDeleteList = new ArrayList<>();
        changesMade = false;

    }

    public class UserRoleStringConverter extends StringConverter<UserRole> {
        @Override
        public String toString(UserRole object) {
            return object == null ? null : object.toString();
        }
    
        @Override
        public UserRole fromString(String string) {
            return string == null ? null : UserRole.valueOf(string);
        }
    }

    @Override
    public void configureElements()
    {
        pageContent.setMaxWidth(1000);
        pageContent.setPrefHeight(500);
        pageContent.getStyleClass().addAll("py-16");
        pageContent.setSpacing(24);
        

        buttonContainer.getStyleClass().addAll("pt-16");
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        VBox.setMargin(buttonContainer, new Insets(0, 0, 20, 0));        

        table.setPrefWidth(1000);
        double columnWidth = (table.getPrefWidth()-5)/5;
        usernameColumn.setPrefWidth(columnWidth);
        emailColumn.setPrefWidth(columnWidth);
        passwordColumn.setPrefWidth(columnWidth);
        roleColumn.setPrefWidth(columnWidth);
        deleteColumn.setPrefWidth(columnWidth);

        
        BorderPane.setMargin(buttonContainer, new Insets(30, 30, 30, 30));
    }

    @Override
    public void initializeEventListeners()
    {
        usernameColumn.setCellFactory(col -> new TableCell<User, String>() {
            {
                setAlignment(Pos.CENTER);
            }
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    
        // Set alignment for emailColumn
        emailColumn.setCellFactory(col -> new TableCell<User, String>() {
            {
                setAlignment(Pos.CENTER);
            }
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    
        // Set alignment for passwordColumn
        passwordColumn.setCellFactory(col -> new TableCell<User, String>() {
            {
                setAlignment(Pos.CENTER);
            }
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    
        // Set alignment for roleColumn
        roleColumn.setCellFactory(col -> new TableCell<User, UserRole>() {
            {
                setAlignment(Pos.CENTER);
            }
    
            @Override
            protected void updateItem(UserRole item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    
        // Set alignment for deleteColumn
        deleteColumn.setCellFactory(col -> new TableCell<User, Void>() {

            private final Button deleteButton = new Button("Delete");
            {
                setAlignment(Pos.CENTER);
    
                // Add action to the delete button
                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    // uc.delete(user.getUserId());
                    if (users != null) {
                        userToDeleteList.add(user);
                        users.remove(user);
                        changesMade = true;
                        updateSaveButtonStyle();
                        table.getItems().clear();
		                table.getItems().addAll(users);
                    }               
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Set the button as the graphic for the cell
                    setGraphic(deleteButton);
                }
            }
        });

        saveButton.setOnMouseClicked(e->{
            for (User user : table.getItems()) {
                boolean success = uc.put(user);
        
                if (success) {
                    System.out.println("User with ID " + user.getUserId() + " updated successfully.");
                } else {
                    System.out.println("Failed to update user with ID " + user.getUserId());
                }
            }

            for (User user : userToDeleteList)
            {
                uc.delete(user.getUserId());
            }
            userToDeleteList.clear();

            changesMade = false;
            updateSaveButtonStyle();
        }); 

        discardButton.setOnMouseClicked(e->{
            userToDeleteList.clear();
            select();
        });

        table.getItems().addListener((ListChangeListener<User>) c -> {
            while (c.next()) {
                if (c.wasUpdated() || c.wasAdded() || c.wasRemoved()) {
                    changesMade = true;
                    break;
                }
            }

            updateSaveButtonStyle();
        });
    }



    private void updateSaveButtonStyle() {
        if(changesMade)
        System.out.println("Hello world");

        if (changesMade) {
            saveButton = new CTAButton("Save");
            saveButton.setDisable(false);  
        } else {
            saveButton = new DisabledButton("Save");
        }
    }

    @Override
    public void assembleLayout()
    {
        pageIdentifierContainer.getChildren().addAll(
            pageTitle
        );
        

        buttonContainer.getChildren().addAll(
            discardButton,    
            saveButton   
        );

        table.getColumns().addAll(
            usernameColumn,
            emailColumn,
            passwordColumn,
            roleColumn,
            deleteColumn
        );


        pageContent.getChildren().addAll(
            table
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

    public void select()
    {
        users = uc.getAll();
        table.getItems().clear();
		table.getItems().addAll(users);

        userRole = FXCollections.observableArrayList(User.UserRole.values());
        roleColumn.setCellFactory(column -> {
            ComboBoxTableCell<User, UserRole> cell = new ComboBoxTableCell<>(new UserRoleStringConverter(), userRole);
            cell.setComboBoxEditable(true); 
            return cell;
        });


        roleColumn.setOnEditCommit(event -> {
            int rowIndex = event.getTablePosition().getRow();
            User user = event.getTableView().getItems().get(rowIndex);
            user.setUserRole(event.getNewValue());
            
            changesMade = true;
            updateSaveButtonStyle();

            // // Update the database with the new UserRole
            // boolean success = uc.put(user);

            // // Check if the update was successful and handle accordingly
            // if (success) {
            //     System.out.println("Database updated successfully.");
            // } else {
            //     System.out.println("Failed to update database.");
            //   
            // }
        });

        roleColumn.setEditable(true);
        table.setEditable(true);
    }

    @Override
    public void setupScene()
    {
        setCenter(scrollSupport);
    }
}