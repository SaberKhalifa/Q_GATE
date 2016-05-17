package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.leoni.q_gate.animations.FadeInUpTransition;
import com.leoni.q_gate.beans.Faute;
import com.leoni.q_gate.beans.Groupe;
import com.leoni.q_gate.data.repository.FauteRepository;
import com.leoni.q_gate.data.repository.GroupeRepository;
import com.leoni.q_gate.data.repository.ScannageRepository;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class PopupCtrl implements Initializable {
	@FXML
	private TextField txtFaute;
	@FXML
	private ListView<String> listFautes;
	@FXML
	private Button btnOK;
	private ScannageRepository scRepository;
	private GroupeRepository grRepository;
	private FauteRepository fauteRepository;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		scRepository = new ScannageRepository();
		grRepository = new GroupeRepository();
		fauteRepository = new FauteRepository();
		new FadeInUpTransition(txtFaute).play();
		Timeline lblTimeLine = new Timeline(new KeyFrame(Duration.seconds(2),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if (!txtFaute.getText().isEmpty()) {
							listFautes.getItems().add(txtFaute.getText());
							if (listFautes.getItems().size() > 0) {
								Groupe gr = grRepository
										.findByType(ScannageCtrl.getGroupe());
								Faute f = fauteRepository.getFauteByMC(txtFaute
										.getText());
								if (f != null) {
									HashMap<Integer, Object> row = new HashMap<Integer, Object>();
									row.put(1, gr.getNumType());
									row.put(2, txtFaute.getText());
									row.put(3,
											new java.sql.Date(new Date()
													.getTime()));
									row.put(4, ScannageCtrl.getNumKsk());
									row.put(5, null);
									scRepository.save(row);
									if (f.getOK() == 1) {
										((Stage) txtFaute.getScene()
												.getWindow()).close();
										ScannageCtrl.getScannageCtrl().init();
									}
								} else {
									listFautes.getItems().remove(txtFaute.getText());
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Message ");
									alert.setHeaderText("ATTENTION : N° DU FAUTE INTROUVABLE ");
									alert.show();
								}

							}
						}
						txtFaute.setText("");
					}

				}));
		lblTimeLine.setCycleCount(Timeline.INDEFINITE);
		lblTimeLine.play();
	}
}
