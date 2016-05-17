package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import com.leoni.q_gate.animations.FadeInUpTransition;
import com.leoni.q_gate.beans.Faute;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.FauteRepository;
import com.leoni.q_gate.task.Q_GateTask;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class FauteCtrl implements Initializable {
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private AnchorPane paneFaute;
	@FXML
	private TextField txtFaute;
	@FXML
	private TextField txtDesigantion;
	@FXML
	private CheckBox txtOK;
	@FXML
	private CheckBox txtNOK;
	@FXML
	private TableView<Faute> tableKSK;
	@FXML
	private TableColumn<Object, Boolean> colAction;
	@FXML
	private TableColumn<Faute, String> colDesignation;
	@FXML
	private TableColumn<Faute, String> colFaute;
	@FXML
	private TableColumn<Faute, String> colOK;
	@FXML
	private TableColumn<Faute, String> colNOK;
	@FXML
	private BorderPane paneTabel;
	@FXML
	private ProgressBar bar;
	@FXML
	private Button btnSaveFaute;
	@FXML
	private Button btnNewFaute;
	@FXML
	private Button btnBack;
	private FauteRepository fauteRepository;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			fauteRepository = new FauteRepository();
			new FadeInUpTransition(paneTabel).play();
			Config.setModelColumn(colDesignation, "designation");
			Config.setModelColumn(colFaute, "faute");
			Config.setModelColumn(colOK, "OK");
			Config.setModelColumn(colNOK, "NOK");
			colAction
					.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
						@Override
						public ObservableValue<Boolean> call(
								TableColumn.CellDataFeatures<Object, Boolean> p) {
							return new SimpleBooleanProperty(
									p.getValue() != null);
						}
					});
			colAction
					.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
						@Override
						public TableCell<Object, Boolean> call(
								TableColumn<Object, Boolean> p) {
							return new ButtonCell(tableKSK);
						}
					});
			load();
		});

	}

	private class ButtonCell extends TableCell<Object, Boolean> {
		final Hyperlink cellButtonDelete = new Hyperlink("Supprimer");
		final Hyperlink cellButtonMofifier = new Hyperlink("Modifier");
		final HBox hb = new HBox(cellButtonDelete, cellButtonMofifier);

		ButtonCell(final TableView<Faute> tblView) {
			hb.setSpacing(4);
			cellButtonDelete.setOnAction((ActionEvent t) -> {
				int row = getTableRow().getIndex();
				tableKSK.getSelectionModel().select(row);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
						"Vous étes sur(e) de supprimer cette employer ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					fauteRepository.deleteFC(tableKSK.getSelectionModel()
							.getSelectedItem().getFaute());
					load();
				} else {

				}
			});
			cellButtonMofifier.setOnAction((ActionEvent event) -> {
				int row = getTableRow().getIndex();
				tableKSK.getSelectionModel().select(row);
				clearTextFields();
				setTxtFileds(tableKSK.getSelectionModel().getSelectedItem());
				paneTabel.setOpacity(0);
				new FadeInUpTransition(paneFaute).play();
			});
		}

		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(hb);
			} else {
				setGraphic(null);
			}
		}
	}

	@FXML
	private void aksiBack(ActionEvent event) {
		paneFaute.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		load();
	}

	@FXML
	private void aksiNewFaute(ActionEvent event) {
		paneTabel.setOpacity(0);
		new FadeInUpTransition(paneFaute).play();
		Platform.runLater(() -> {
			clearTextFields();
		});
	}

	/**
	 * INSERT NEW FAUTE
	 * 
	 * @param e
	 */
	@FXML
	private void saveFaute(ActionEvent e) {
		if (txtFaute.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Entrer n° faute");
			txtFaute.requestFocus();
		} else if (txtDesigantion.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Entrer designation");
			txtDesigantion.requestFocus();
		} else if (!txtOK.isSelected() && !txtNOK.isSelected()) {
			Config.dialog(Alert.AlertType.ERROR, "Cocher OK ou NOK");
			txtOK.requestFocus();
		} else {
			HashMap<Integer, Object> row = new HashMap<Integer, Object>();
			row.put(1, txtFaute.getText());
			row.put(2, txtDesigantion.getText());
			row.put(3, txtOK.isSelected() ? txtOK.isSelected() : false);
			row.put(4, txtNOK.isSelected() ? txtNOK.isSelected() : false);
			fauteRepository.saveFC(row);
			clearTextFields();
			aksiBack(null);

		}

	}

	/**
	 * LOAD FAUTES FROM DATA BASE
	 */
	public void load() {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		tableKSK.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableKSK.setItems(fauteRepository.findAllFCS());
	}

	@FXML
	private void search(KeyEvent e) {
		Platform.runLater(() -> {
			if (!txtSearch.getText().isEmpty()) {
				tableKSK.setItems(fauteRepository.findAllFCSByMC(txtSearch
						.getText()));
			} else {
				tableKSK.setItems(fauteRepository.findAllFCS());
			}
		});
	}

	/**
	 * SET TEXT FIELDS EMPTY
	 */
	private void clearTextFields() {
		txtFaute.setText("");
		txtDesigantion.setText("");
		txtOK.setSelected(false);
		txtNOK.setSelected(false);
	}

	private void setTxtFileds(Faute f) {
		txtFaute.setText(f.getFaute());
		txtDesigantion.setText(f.getDesignation());
		txtOK.setSelected(f.getOK() == 1 ? true : false);
		txtNOK.setSelected(f.getNOK() == 1 ? true : false);
	}
}
