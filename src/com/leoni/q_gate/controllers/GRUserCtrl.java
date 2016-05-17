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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import com.leoni.q_gate.animations.FadeInUpTransition;
import com.leoni.q_gate.beans.Groupe;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.GroupeRepository;
import com.leoni.q_gate.task.Q_GateTask;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class GRUserCtrl implements Initializable {
	@FXML
	private BorderPane paneTabel;
	@FXML
	private AnchorPane paneGroupe;
	@FXML
	private TextField txtGrp;
	@FXML
	private TextField txtDesignation;
	@FXML
	private TextField txtServiceGr;
	@FXML
	private TableView<Groupe> tableGroupes;
	@FXML
	private TableColumn<Object, Boolean> colActionGR;
	@FXML
	private TableColumn<Groupe, String> colService;
	@FXML
	private TableColumn<Groupe, String> colGroupe;
	@FXML
	private TableColumn<Groupe, String> colDesigantion;
	@FXML
	private Button btnNewGroupe;
	@FXML
	private Button btnSaveGroupe;
	@FXML
	private Button btnBackGroupe;
	@FXML
	private ProgressBar bar;
	private GroupeRepository grRepository;
	private int numGroupe;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		grRepository = new GroupeRepository();
		new FadeInUpTransition(paneTabel).play();
		Platform.runLater(() -> {
			Config.setModelColumn(colGroupe, "type");
			Config.setModelColumn(colDesigantion, "designation");
			Config.setModelColumn(colService, "service");
			colActionGR.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
				@Override
				public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
					return new SimpleBooleanProperty(p.getValue() != null);
				}
			});
			colActionGR.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
				@Override
				public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
					return new ButtonCell(tableGroupes);
				}
			});
		});
		loadGroupes();

	}

	private class ButtonCell extends TableCell<Object, Boolean> {
		final Hyperlink cellButtonDelete = new Hyperlink("Supprimer");
		final Hyperlink cellButtonEdit = new Hyperlink("Modifier");
		final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

		ButtonCell(final TableView<Groupe> tblView) {
			hb.setSpacing(4);
			cellButtonDelete.setOnAction((ActionEvent t) -> {
				int row = getTableRow().getIndex();
				tableGroupes.getSelectionModel().select(row);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vous étes sur(e) de supprimer cette groupe ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					grRepository.delete(tableGroupes.getSelectionModel().getSelectedItem().getNumType());
					loadGroupes();
				} else {

				}

			});
			cellButtonEdit.setOnAction((ActionEvent event) -> {
				int row = getTableRow().getIndex();
				tableGroupes.getSelectionModel().select(row);
				setTxtFieldsEmpty();
				setTxtFiledsGroupe(tableGroupes.getSelectionModel().getSelectedItem());
				paneTabel.setOpacity(0);
				new FadeInUpTransition(paneGroupe).play();
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
	private void aksiBackGroupe(ActionEvent event) {
		paneGroupe.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		loadGroupes();
	}

	@FXML
	private void aksiNewGroupe(ActionEvent event) {
		paneTabel.setOpacity(0);
		new FadeInUpTransition(paneGroupe).play();
		Platform.runLater(() -> {
			setNumGroupe(0);
			setTxtFieldsEmpty();
		});
	}

	/**
	 * INSERT NEW GROUPE
	 * 
	 * @param event
	 */
	@FXML
	private void saveGroupe(ActionEvent event) {
		if (txtGrp.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Groupe non vide");
			txtGrp.requestFocus();
		} else if (txtDesignation.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Désignation non vide");
			txtDesignation.requestFocus();
		} else if (txtServiceGr.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Service non vide");
			txtServiceGr.requestFocus();
		} else {
			HashMap<Integer, Object> row = new HashMap<Integer, Object>();
			row.put(1, txtGrp.getText());
			row.put(2, txtDesignation.getText());
			row.put(3, txtServiceGr.getText());
			grRepository.saveOrUpdate(row, getNumGroupe());
			aksiBackGroupe(null);
		}
	}

	/**
	 * LOAD ALL USER FROM DATABASE
	 */
	public void loadGroupes() {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		tableGroupes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableGroupes.setItems(grRepository.findAll());

	}

	private void setTxtFiledsGroupe(Groupe g) {
		System.out.println(g.getService());
		setNumGroupe(g.getNumType());
		txtGrp.setText(g.getType());
		txtDesignation.setText(g.getDesignation());
		txtServiceGr.setText(g.getService());
	}

	private void setTxtFieldsEmpty() {
		txtGrp.setText("");
		txtDesignation.setText("");
		txtServiceGr.setText("");
	}

	public int getNumGroupe() {
		return numGroupe;
	}

	public void setNumGroupe(int numGroupe) {
		this.numGroupe = numGroupe;
	}

}
