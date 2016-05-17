package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import com.leoni.q_gate.animations.FadeInUpTransition;
import com.leoni.q_gate.beans.Faute;
import com.leoni.q_gate.beans.Groupe;
import com.leoni.q_gate.beans.KSKFaute;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.GroupeRepository;
import com.leoni.q_gate.data.repository.ScannageRepository;
import com.leoni.q_gate.data.repository.StatistiquesRepository;
import com.leoni.q_gate.statistiques.Q_GateGraph;
import com.leoni.q_gate.task.Q_GateTask;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class ControleCtrl implements Initializable {
	@FXML
	private AnchorPane paneFaute;
	@FXML
	private BorderPane paneStatFaute;
	@FXML
	private BorderPane paneStatGroupe;
	@FXML
	private ComboBox<Groupe> txtGroupe;
	@FXML
	private TableView<KSKFaute> tableFaute;
	@FXML
	private TableColumn<Object, Boolean> colAction;
	@FXML
	private TableColumn<Faute, String> colKSK;
	@FXML
	private TableColumn<Faute, String> colGroupe;
	@FXML
	private TableColumn<Faute, String> colDesignation;
	@FXML
	private TableColumn<Faute, String> colFaute;
	@FXML
	private TableColumn<Faute, String> colOK;
	@FXML
	private TableColumn<Faute, String> colNOK;
	@FXML
	public BorderPane paneTabel;
	@FXML
	private ProgressBar bar;
	@FXML
	private Button btnSaveFaute;
	@FXML
	private Button btnNewFaute;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnStatFaute;
	@FXML
	private Button btnStatGroupe;
	@FXML
	private Button btnSave;
	private ScannageRepository scRepository;
	private StatistiquesRepository statRepository;
	private GroupeRepository grRepository;
	@FXML
	private Label lblOK;
	@FXML
	private Label lblNOK;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			scRepository = new ScannageRepository();
			statRepository = new StatistiquesRepository();
			grRepository = new GroupeRepository();
			new FadeInUpTransition(paneTabel).play();
			Config.setModelColumn(colKSK, "ksk");
			Config.setModelColumn(colGroupe, "groupe");
			Config.setModelColumn(colDesignation, "designationGroupe");
			Config.setModelColumn(colFaute, "faute");
			Config.setModelColumn(colOK, "ok");
			Config.setModelColumn(colNOK, "nok");
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
							return new ButtonCell(tableFaute);
						}
					});
			load();
			setGroupe();
		});
	}

	private class ButtonCell extends TableCell<Object, Boolean> {
		final Hyperlink cellButtonDelete = new Hyperlink("Supprimer");
		final HBox hb = new HBox(cellButtonDelete);

		ButtonCell(final TableView<KSKFaute> tblView) {
			hb.setSpacing(4);
			cellButtonDelete.setOnAction((ActionEvent t) -> {
				int row = getTableRow().getIndex();
				tableFaute.getSelectionModel().select(row);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
						"Vous étes sur(e) de supprimer cette employer ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					scRepository.delete(tableFaute.getSelectionModel()
							.getSelectedItem().getIdFaute());
					load();
				} else {

				}
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
		paneStatFaute.setOpacity(0);
		paneStatGroupe.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		load();
	}

	@FXML
	private void aksiStatFaute(ActionEvent event) {
		paneTabel.setOpacity(0);
		paneStatGroupe.setOpacity(0);
		paneStatFaute.setCenter(Q_GateGraph.graphFautes(statRepository
				.getXFautes()));
		new FadeInUpTransition(paneStatFaute).play();
	}

	@FXML
	private void aksiStatGroupe(ActionEvent event) {
		paneTabel.setOpacity(0);
		paneStatFaute.setOpacity(0);
		paneStatGroupe.setCenter(Q_GateGraph.graphGroupes(statRepository
				.getXGroues()));
		new FadeInUpTransition(paneStatGroupe).play();
	}

	/**
	 * LOAD FAUTES FROM DATA BASE
	 */
	public void load() {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		tableFaute.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableFaute.setItems(scRepository.findAll());
		lblOK.setText(String.valueOf(scRepository.getKSKOK()));
		lblNOK.setText(String.valueOf(scRepository.getKSKNOK()));
	}

	@FXML
	public void loadByGroupe(ActionEvent event) {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		new FadeInUpTransition(paneTabel).play();
		tableFaute.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableFaute.setItems(scRepository.findAllByGroupe(txtGroupe
				.getSelectionModel().getSelectedItem().getNumType()));
		lblOK.setText(String.valueOf(scRepository.getKSKOKByGroupe(txtGroupe
				.getSelectionModel().getSelectedItem().getNumType())));
		lblNOK.setText(String.valueOf(scRepository.getKSKNOKByGroupe(txtGroupe
				.getSelectionModel().getSelectedItem().getNumType())));
	}

	private void setGroupe() {
		Service<ObservableList<Groupe>> service = new Service<ObservableList<Groupe>>() {
			@Override
			protected Task<ObservableList<Groupe>> createTask() {
				return new Task<ObservableList<Groupe>>() {
					@Override
					protected ObservableList<Groupe> call() throws Exception {
						ObservableList<Groupe> listTask = FXCollections
								.observableArrayList();
						txtGroupe.getItems().clear();
						if (listTask == null) {
							listTask = FXCollections
									.observableArrayList(grRepository.findAll());
						} else {
							listTask.clear();
							listTask.addAll(grRepository.findAll());
						}
						txtGroupe.setItems(listTask);
						return listTask;
					}
				};
			}
		};
		service.start();
	}
}
