module mystic_grills {
	opens application_starter;
	opens controllers;
    opens database_access;
    opens models;
    opens views;

	requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.base;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.fxml;
	requires java.sql;
}
