package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import com.leoni.q_gate.animations.FadeInUpTransition;
import com.leoni.q_gate.beans.Employe;
import com.leoni.q_gate.beans.Groupe;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.EmployeRepository;
import com.leoni.q_gate.data.repository.GroupeRepository;
import com.leoni.q_gate.task.Q_GateTask;

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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class EmployeCtrl implements Initializable {
	/****************************************************
	 *
	 ***************************************************/
	@FXML
	private TableView<Employe> tableEmployes;
	@FXML
	private TableColumn<Object, Boolean> colAction;
	@FXML
	private TableColumn<Employe, String> colMatricule;
	@FXML
	private TableColumn<Employe, String> colNom;
	@FXML
	private TableColumn<Employe, String> colPrenom;
	@FXML
	private TableColumn<Employe, String> colAdresse;
	@FXML
	private TableColumn<Employe, String> colCodePostale;
	@FXML
	private TableColumn<Employe, String> colTel;
	@FXML
	private TableColumn<Employe, String> colEmail;
	@FXML
	private TableColumn<Employe, String> colService;

	@FXML
	private Button btnNewEmploye;
	@FXML
	public BorderPane paneTabel;
	@FXML
	public AnchorPane paneEmploye;
	@FXML
	private TextField txtMatricule;
	@FXML
	private ComboBox<Groupe> txtGroupe;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextArea txtAddress;
	@FXML
	private TextField txtCodePostale;
	@FXML
	private TextField txtVille;
	@FXML
	private TextField txtTel;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtService;
	@FXML
	private TextField txtCredit;
	@FXML
	private Button btnSaveEmploye;
	@FXML
	private Button btnBack;
	Integer status;
	/********************************************************************
	 * 
	 *********************************************************************/
	@FXML
	private ImageView imgLoad;
	@FXML
	private ProgressBar bar;
	private EmployeRepository employeRepository;
	private GroupeRepository groupeRepository;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Platform.runLater(() -> {
			employeRepository = new EmployeRepository();
			groupeRepository = new GroupeRepository();
			new FadeInUpTransition(paneTabel).play();
			status = 0;
			Config.setModelColumn(colMatricule, "matricule");
			Config.setModelColumn(colNom, "nom");
			Config.setModelColumn(colPrenom, "prenom");
			Config.setModelColumn(colAdresse, "adresse");
			Config.setModelColumn(colCodePostale, "codePostale");
			Config.setModelColumn(colTel, "tel");
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
							return new ButtonCell(tableEmployes);
						}
					});
			load();
			setGroupe();
		});
		// TODO
	}

	private class ButtonCell extends TableCell<Object, Boolean> {
		final Hyperlink cellButtonDelete = new Hyperlink("Supprimer");
		final Hyperlink cellButtonEdit = new Hyperlink("Modifier");
		final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

		ButtonCell(final TableView<Employe> tblView) {
			hb.setSpacing(4);
			cellButtonDelete.setOnAction((ActionEvent t) -> {
				status = 1;
				int row = getTableRow().getIndex();
				tableEmployes.getSelectionModel().select(row);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
						"Vous étes sur(e) de supprimer cette employer ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					employeRepository.delete(tableEmployes.getSelectionModel()
							.getSelectedItem().getMatricule());
					load();
				} else {

				}
				status = 0;
			});
			cellButtonEdit.setOnAction((ActionEvent event) -> {
				status = 1;
				txtMatricule.setEditable(false);
				int row = getTableRow().getIndex();
				tableEmployes.getSelectionModel().select(row);
				clearTxtFieldEmp();
				setTxtFiledsEmp(tableEmployes.getSelectionModel()
						.getSelectedItem());
				paneTabel.setOpacity(0);
				new FadeInUpTransition(paneEmploye).play();
				status = 0;
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
		paneEmploye.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		load();
	}

	@FXML
	private void aksiNewEmploy(ActionEvent event) {
		paneTabel.setOpacity(0);
		new FadeInUpTransition(paneEmploye).play();
		Platform.runLater(() -> {
			txtMatricule.setEditable(true);
			clearTxtFieldEmp();
		});
	}

	@FXML
	private void saveEmploye(ActionEvent event) {
		if (txtMatricule.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Matricule non vide");
			txtMatricule.requestFocus();
		} else if (txtGroupe.getValue() == null) {
			Config.dialog(Alert.AlertType.ERROR, "Groupe non vide");
			txtGroupe.requestFocus();
		} else if (txtNom.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Nom non vide");
			txtNom.requestFocus();
		} else if (txtPrenom.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Prenom non vide");
			txtPrenom.requestFocus();
		} else if (txtAddress.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Adresse non vide");
			txtAddress.requestFocus();
		} else if (txtCodePostale.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Code Postale non vide");
			txtAddress.requestFocus();
		} else if (txtVille.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Ville non vide");
			txtVille.requestFocus();
		} else if (txtTel.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Tel non vide");
			txtTel.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Email non vide");
			txtEmail.requestFocus();
		} else if (txtService.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Service non vide");
			txtService.requestFocus();
		} else {
			HashMap<Integer, Object> row = new HashMap<Integer, Object>();
			row.put(1, txtMatricule.getText());
			row.put(2, txtGroupe.getValue().getNumType());
			row.put(3, txtNom.getText());
			row.put(4, txtPrenom.getText());
			row.put(5, txtAddress.getText());
			row.put(6, txtCodePostale.getText());
			row.put(7, txtVille.getText());
			row.put(8, txtTel.getText());
			row.put(9, txtEmail.getText());
			row.put(10, txtService.getText());
			employeRepository.saveOrUpdate(row);
			aksiBack(null);
		}
	}

	/**
	 * LOAD TABLE EMPLOYES FROM DATA BASE
	 */
	public void load() {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		tableEmployes
				.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableEmployes.setItems(employeRepository.findAll());
	}

	/**
	 * SET INF OF THE EMPLOYE INT THE TEXT FIELD
	 * 
	 * @param emp
	 */
	public void setTxtFiledsEmp(Employe emp) {
		txtMatricule.setText(String.valueOf(emp.getMatricule()));
		txtGroupe.setValue(groupeRepository.findById(emp.getIdGroupe()));
		txtNom.setText(emp.getNom());
		txtPrenom.setText(emp.getPrenom());
		txtAddress.setText(emp.getAdresse());
		txtCodePostale.setText(emp.getCodePostale());
		txtVille.setText(emp.getVille());
		txtTel.setText(emp.getTel());
		txtEmail.setText(emp.getEmail());
		txtService.setText(emp.getService());
	}

	/**
	 * CLEAR TEXT FIELD EMPLOYE
	 */
	public void clearTxtFieldEmp() {
		txtMatricule.setText("");
		txtGroupe.setValue(new Groupe());
		txtNom.setText("");
		txtPrenom.setText("");
		txtAddress.setText("");
		txtCodePostale.setText("");
		txtVille.setText("");
		txtTel.setText("");
		txtEmail.setText("");
		txtService.setText("");
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
									.observableArrayList(groupeRepository
											.findAll());
						} else {
							listTask.clear();
							listTask.addAll(groupeRepository.findAll());
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
