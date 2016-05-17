package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
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
import com.leoni.q_gate.beans.Employe;
import com.leoni.q_gate.beans.TypeUser;
import com.leoni.q_gate.beans.User;
import com.leoni.q_gate.config.Config;
import com.leoni.q_gate.data.repository.EmployeRepository;
import com.leoni.q_gate.data.repository.UserRepository;
import com.leoni.q_gate.md5.MD5Q_GATE;
import com.leoni.q_gate.task.Q_GateTask;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class UserCtrl implements Initializable {
	/****************************************************
	 *
	 ***************************************************/
	@FXML
	private BorderPane paneTabel;
	@FXML
	private AnchorPane paneUser;
	@FXML
	private TableView<User> tableUsers;
	@FXML
	private TableColumn<Object, Boolean> colActionUser;
	@FXML
	private TableColumn<User, String> colMatriculeUser;
	@FXML
	private TableColumn<User, String> colTypeUser;
	@FXML
	private TableColumn<User, String> colUsername;
	@FXML
	private TableColumn<User, String> colPassword;
	@FXML
	private TableColumn<User, Boolean> colActived;

	@FXML
	private Button btnNewUser;
	@FXML
	private Button btnSaveUser;
	@FXML
	private Button btnBackUser;
	@FXML
	private ComboBox<Employe> txtMatEmp;
	@FXML
	private ComboBox<TypeUser> txtTypeUser;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private CheckBox txtActived;
	@FXML
	private CheckBox txtNonActived;
	private UserRepository userRepository;
	private EmployeRepository employeRepository;
	@FXML
	private ProgressBar bar;
	private int numUser;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		userRepository = new UserRepository();
		employeRepository = new EmployeRepository();
		new FadeInUpTransition(paneTabel).play();
		Platform.runLater(() -> {
			Config.setModelColumn(colMatriculeUser, "matricule");
			Config.setModelColumn(colTypeUser, "typeUtilisateur");
			Config.setModelColumn(colUsername, "username");
			Config.setModelColumn(colPassword, "password");
			Config.setModelColumn(colActived, "actived");
			colActionUser.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
				@Override
				public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
					return new SimpleBooleanProperty(p.getValue() != null);
				}
			});
			colActionUser.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
				@Override
				public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
					return new ButtonCell(tableUsers);
				}
			});
		});
		loadUsers();
		setEmpsMatricule();
		setTypeUser();
	}

	private class ButtonCell extends TableCell<Object, Boolean> {
		final Hyperlink cellButtonDelete = new Hyperlink("Supprimer");
		final Hyperlink cellButtonEdit = new Hyperlink("Modifier");
		final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

		ButtonCell(final TableView<User> tblView) {
			hb.setSpacing(4);
			cellButtonDelete.setOnAction((ActionEvent t) -> {
				int row = getTableRow().getIndex();
				tableUsers.getSelectionModel().select(row);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
						"Vous étes sur(e) de supprimer cette utilisateur ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					userRepository.delete(tableUsers.getSelectionModel().getSelectedItem().getNumUtilisateur());
					loadUsers();
				} else {

				}
			});
			cellButtonEdit.setOnAction((ActionEvent event) -> {
				int row = getTableRow().getIndex();
				tableUsers.getSelectionModel().select(row);
				clearTxtFieldUser();
				setTxtFiledsUser(tableUsers.getSelectionModel().getSelectedItem());
				paneTabel.setOpacity(0);
				new FadeInUpTransition(paneUser).play();
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
	private void aksiBackUser(ActionEvent event) {
		paneUser.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		loadUsers();
	}

	@FXML
	private void aksiNewUser(ActionEvent event) {
		setNumUser(0);
		paneTabel.setOpacity(0);
		new FadeInUpTransition(paneUser).play();
		Platform.runLater(() -> {
			clearTxtFieldUser();
		});
	}

	/**
	 * INSERT NEW USER
	 * 
	 * @param event
	 */
	@FXML
	private void saveUser(ActionEvent event) {
		if (txtMatEmp.getValue() == null) {
			Config.dialog(Alert.AlertType.ERROR, "Matricule non vide");
			txtMatEmp.requestFocus();
		} else if (txtTypeUser.getValue() == null) {
			Config.dialog(Alert.AlertType.ERROR, "Type d'utilisateur non vide");
			txtTypeUser.requestFocus();
		} else if (txtUsername.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Nom d'utilisateur non vide");
			txtUsername.requestFocus();
		} else if (txtPassword.getText().isEmpty()) {
			Config.dialog(Alert.AlertType.ERROR, "Mot de passe non vide");
			txtPassword.requestFocus();
		} else if (!txtActived.isSelected() && !txtNonActived.isSelected()) {
			Config.dialog(Alert.AlertType.ERROR, "Cocher activer ou non");
			txtPassword.requestFocus();
		} else {
			try {
				System.out.println(txtMatEmp.getValue().getMatricule());
				HashMap<Integer, Object> row = new HashMap<Integer, Object>();
				row.put(1, txtMatEmp.getValue().getMatricule());
				row.put(2, txtTypeUser.getValue().getNumType());
				row.put(3, txtUsername.getText());

				row.put(4, new MD5Q_GATE().encrypt(txtPassword.getText()));

				row.put(5, txtActived.isSelected() ? txtActived.isSelected() : false);
				userRepository.saveOrUpdate(row, getNumUser());
				aksiBackUser(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * LOAD ALL USER FROM DATABASE
	 */
	public void loadUsers() {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		tableUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableUsers.setItems(userRepository.findAll());

	}

	/**
	 * LOAD ALL TYPE OF USER
	 */
	private void setEmpsMatricule() {
		Service<ObservableList<Employe>> service = new Service<ObservableList<Employe>>() {
			@Override
			protected Task<ObservableList<Employe>> createTask() {
				return new Task<ObservableList<Employe>>() {
					@Override
					protected ObservableList<Employe> call() throws Exception {
						ObservableList<Employe> listTask = FXCollections.observableArrayList();
						txtMatEmp.getItems().clear();
						if (listTask == null) {
							listTask = FXCollections.observableArrayList(employeRepository.findAll());
						} else {
							listTask.clear();
							listTask.addAll(employeRepository.findAll());
						}
						txtMatEmp.setItems(listTask);
						return listTask;
					}
				};
			}
		};
		service.start();
	}

	/**
	 * LOAD ALL TYPE OF USER
	 */
	private void setTypeUser() {
		Service<ObservableList<TypeUser>> service = new Service<ObservableList<TypeUser>>() {
			@Override
			protected Task<ObservableList<TypeUser>> createTask() {
				return new Task<ObservableList<TypeUser>>() {
					@Override
					protected ObservableList<TypeUser> call() throws Exception {
						ObservableList<TypeUser> listTask = FXCollections.observableArrayList();
						txtTypeUser.getItems().clear();
						if (listTask == null) {
							listTask = FXCollections.observableArrayList(userRepository.findAllType());
						} else {
							listTask.clear();
							listTask.addAll(userRepository.findAllType());
						}
						txtTypeUser.setItems(listTask);
						return listTask;
					}
				};
			}
		};
		service.start();
	}

	/**
	 * SET TEXT FILED DATA FROM USER SELECTED FROM TABLE
	 * 
	 * @param u
	 */
	public void setTxtFiledsUser(User u) {
		try {
			setNumUser(u.getNumUtilisateur());
			txtMatEmp.setValue(employeRepository.findEmploye(u.getMatricule()));
			txtTypeUser.setValue(userRepository.getTypeById(u.getIdTypeUtilisateur()));
			txtUsername.setText(u.getUsername());
			txtPassword.setText(new MD5Q_GATE().decrypt(u.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (u.isActived())
			txtActived.setSelected(true);
		else
			txtNonActived.setSelected(true);
	}

	/**
	 * SET TEXT FILED EMPTY
	 */
	public void clearTxtFieldUser() {
		txtMatEmp.setValue(null);
		txtTypeUser.setValue(new TypeUser());
		txtUsername.setText("");
		txtPassword.setText("");
		txtActived.setSelected(false);
		txtNonActived.setSelected(false);
	}

	public int getNumUser() {
		return numUser;
	}

	public void setNumUser(int numUser) {
		this.numUser = numUser;
	}

}
