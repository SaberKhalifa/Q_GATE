/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.leoni.q_gate.beans.Menu;
import com.leoni.q_gate.beans.User;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.UserRepository;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Saber Ben Khalifa
 */
public class AppCtrl implements Initializable {
	@FXML
	private Button close;
	@FXML
	private Button maximize;
	@FXML
	private Button minimize;
	@FXML
	private Button resize;
	@FXML
	private Button fullscreen;
	@FXML
	private Label title;
	Stage stage;
	Rectangle2D rec2;
	Double w, h;
	@FXML
	private ListView<String> listMenu;
	@FXML
	private AnchorPane paneData;
	Config con = new Config();
	@FXML
	private Button btnLogout;
	private static int numUser;
	public UserRepository userRepository;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		userRepository = new UserRepository();
		rec2 = Screen.getPrimary().getVisualBounds();
		w = rec2.getWidth();
		h = rec2.getHeight();
		User user = userRepository.getUserById(getNumUser());
		title.setText(title.getText() + user.getUsername());
		if (user.getTypeUtilisateur().equals("Administrateur")) {
			listMenu.getItems().addAll(Menu.menuAdmin());
		} else if (user.getTypeUtilisateur().equals("Utilisateur")) {
			listMenu.getItems().addAll(Menu.menuUser());
		} else {
			listMenu.getItems().addAll("Scannage");
		}
		Platform.runLater(() -> {
			stage = (Stage) maximize.getScene().getWindow();
			stage.setMaximized(true);
			stage.setHeight(rec2.getHeight());
			maximize.getStyleClass().add("decoration-button-restore");
			resize.setVisible(false);
			listMenu.getSelectionModel().select(0);
			con.loadAnchorPane(paneData, listMenu.getSelectionModel()
					.getSelectedItem() + ".fxml");
			listMenu.requestFocus();
		});
	}

	@FXML
	private void aksiMaximized(ActionEvent event) {
		stage = (Stage) maximize.getScene().getWindow();
		if (stage.isMaximized()) {
			if (w == rec2.getWidth() && h == rec2.getHeight()) {
				stage.setMaximized(false);
				stage.setWidth(rec2.getWidth() - 200);
				stage.setHeight(rec2.getHeight() - 100);
				stage.centerOnScreen();
				maximize.getStyleClass().remove("decoration-button-restore");
				resize.setVisible(true);
			} else {
				stage.setMaximized(false);
				maximize.getStyleClass().remove("decoration-button-restore");
				resize.setVisible(true);
			}

		} else {
			stage.setMaximized(true);
			stage.setHeight(rec2.getHeight());
			maximize.getStyleClass().add("decoration-button-restore");
			resize.setVisible(false);
		}
	}

	@FXML
	private void aksiminimize(ActionEvent event) {
		stage = (Stage) minimize.getScene().getWindow();
		if (stage.isMaximized()) {
			w = rec2.getWidth();
			h = rec2.getHeight();
			stage.setMaximized(false);
			stage.setHeight(h);
			stage.setWidth(w);
			stage.centerOnScreen();
			Platform.runLater(() -> {
				stage.setIconified(true);
			});
		} else {
			stage.setIconified(true);
		}
	}

	@FXML
	private void aksiResize(ActionEvent event) {

	}

	@FXML
	private void aksifullscreen(ActionEvent event) {
		stage = (Stage) fullscreen.getScene().getWindow();
		if (stage.isFullScreen()) {
			stage.setFullScreen(false);
		} else {
			stage.setFullScreen(true);
		}
	}

	@FXML
	private void aksiClose(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void aksiKlikListMenu(MouseEvent event) {
		for (int i = 0; i < listMenu.getItems().size(); i++) {
			if (i == listMenu.getSelectionModel().getSelectedIndex()) {
				con.loadAnchorPane(paneData, listMenu.getSelectionModel()
						.getSelectedItem() + ".fxml");
			}
		}
	}

	@FXML
	private void aksiLogout(ActionEvent event) {
		Config config = new Config();
		config.newStage2(stage, btnLogout,
				"/com/leoni/q_gate/views/login.fxml", "Authentification", true,
				StageStyle.UNDECORATED, false);
		setNumUser(0);
	}

	public static int getNumUser() {
		return numUser;
	}

	public static void setNumUser(int numUser) {
		AppCtrl.numUser = numUser;
	}
}
