package com.leoni.q_gate.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.leoni.q_gate.animations.FadeInLeftTransition;
import com.leoni.q_gate.animations.FadeInLeftTransition1;
import com.leoni.q_gate.animations.FadeInRightTransition;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.LoginRepository;

/**
 *
 * @author Saber Ben Khalifa
 */
public class LoginCtrl implements Initializable {
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Text lblWelcome;
	@FXML
	private Text lblUserLogin;
	@FXML
	private Text lblUsername;
	@FXML
	private Text lblPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Text lblRudyCom;
	@FXML
	private Label lblClose;
	Stage stage;
	private int numUser;
	public Parent root;
	public LoginRepository repository;
	private Config config;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Platform.runLater(() -> {
			repository = new LoginRepository();
			config = new Config();
			new FadeInRightTransition(lblUserLogin).play();
			new FadeInLeftTransition(lblWelcome).play();
			new FadeInLeftTransition1(lblPassword).play();
			new FadeInLeftTransition1(lblUsername).play();
			new FadeInLeftTransition1(txtUsername).play();
			new FadeInLeftTransition1(txtPassword).play();
			new FadeInRightTransition(btnLogin).play();
			lblClose.setOnMouseClicked((MouseEvent event) -> {
				Platform.exit();
				System.exit(0);
			});
			txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						aksiLogin(null);
					}
				}
			});
		});
	}

	@FXML
	private void aksiLogin(ActionEvent event) {
		if (!txtUsername.getText().isEmpty()
				&& !txtPassword.getText().isEmpty()) {
			numUser = repository.login(txtUsername.getText(),
					txtPassword.getText());
			if (numUser > 0) {
				config.newStage(stage, lblClose,
						"/com/leoni/q_gate/views/app.fxml", "G_GATE", true,
						StageStyle.UNDECORATED, false);
				AppCtrl.setNumUser(numUser);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Mauvaise authentification");
				alert.setHeaderText("Le nom d'utilisateur ou le mot de passe est incorrect ?");
				alert.show();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur saisie");
			alert.setHeaderText("Le nom d'utilisateur et  le mot de passe sont obligatoire ?");
			alert.show();
		}

	}
}
