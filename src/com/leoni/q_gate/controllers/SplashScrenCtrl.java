package com.leoni.q_gate.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.leoni.q_gate.animations.FadeInLeftTransition;
import com.leoni.q_gate.animations.FadeInRightTransition;
import com.leoni.q_gate.animations.FadeInTransition;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.Q_GateRepository;

/**
 *
 * @author Saber Ben Khalifa
 */
public class SplashScrenCtrl implements Initializable {
	@FXML
	private Text lblWelcome;
	@FXML
	private Text lblRudy;
	@FXML
	private VBox vboxBottom;
	@FXML
	private Label lblClose;
	Stage stage;
	@FXML
	private ImageView imgLoading;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		longStart();
		lblClose.setOnMouseClicked((MouseEvent event) -> {
			Platform.exit();
			System.exit(0);
		});
		// TODO
	}

	private void longStart() {
		new FadeInLeftTransition(lblWelcome).play();
		new FadeInRightTransition(lblRudy).play();
		new FadeInTransition(vboxBottom).play();
		Config config = new Config();
		Q_GateRepository.getConnection();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					synchronized (this) {
						try {
							wait(500);
							if (i == 10) {
								Platform.runLater(new Runnable() {
									public void run() {
										config.newStage(stage, lblClose, "/com/leoni/q_gate/views/login.fxml", "G_GATE",
												true, StageStyle.UNDECORATED, false);
									}
								});

							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}).start();

	}
}
