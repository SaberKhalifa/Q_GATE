/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leoni.q_gate;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author Saber Ben Khalifa
 */
public class SplashScreen extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/leoni/q_gate/views/splash.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		InputStream is = new BufferedInputStream(new FileInputStream("img/logo.png"));
		stage.getIcons().add(new Image(is));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
