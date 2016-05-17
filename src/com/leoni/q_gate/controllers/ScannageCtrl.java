package com.leoni.q_gate.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

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
public class ScannageCtrl implements Initializable {
	@FXML
	private AnchorPane paneFaute;
	@FXML
	private BorderPane paneStatFaute;
	@FXML
	private BorderPane paneStatGroupe;
	@FXML
	private TextField txtKSK;
	@FXML
	private TextField txtGroupe;
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
	private Button btnSave;
	@FXML
	private Label lblOK;
	@FXML
	private Label lblNOK;
	private ScannageRepository scRepository;
	private StatistiquesRepository statRepository;
	private static String numKsk, groupe;
	private static ScannageCtrl scannageCtrl;
	private Timeline lblTimeLine;
	private GroupeRepository grRepository;

	public ScannageCtrl() {
		super();
		ScannageCtrl.setScannageCtrl(this);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
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
									return new SimpleBooleanProperty(p
											.getValue() != null);
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
					lblTimeLine = new Timeline(new KeyFrame(
							Duration.seconds(2),
							new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									if (!txtKSK.getText().isEmpty()
											&& !txtGroupe.getText().isEmpty()) {
										Groupe gr = grRepository
												.findByType(txtGroupe.getText());
										if (gr != null) {
											lblTimeLine.stop();
											setNumKsk(txtKSK.getText());
											setGroupe(txtGroupe.getText());
											Stage stage = new Stage();
											Pane root;
											try {
												root = FXMLLoader
														.load(getClass()
																.getResource(
																		"/com/leoni/q_gate/views/popup.fxml"));
												stage.setScene(new Scene(root));
												stage.setTitle("Controle Q_GATE");
												stage.initModality(Modality.APPLICATION_MODAL);
												stage.initOwner(txtGroupe
														.getScene().getWindow());
												stage.show();
												stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
													public void handle(
															WindowEvent we) {
														init();
													}
												});

											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										} else {
											lblTimeLine.stop();
											Alert alert = new Alert(
													AlertType.ERROR);
											alert.setTitle("Message ");
											alert.setHeaderText("ATTENTION : N° GROUPE INTROUVABLE ");
											alert.show();
											String txt = txtKSK.getText();
											init();
											txtKSK.setText(txt);

										}

									} else if (!txtKSK.getText().isEmpty()
											&& txtGroupe.getText().isEmpty()) {
										txtGroupe.requestFocus();
									} else {
										txtKSK.requestFocus();
									}
								}
							}));
					lblTimeLine.setCycleCount(Timeline.INDEFINITE);
					lblTimeLine.play();
				});

			}
		}).start();
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
		paneStatGroupe.setOpacity(0);
		new FadeInUpTransition(paneTabel).play();
		load();
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
		paneStatFaute.setCenter(Q_GateGraph.graphFautes(statRepository
				.getXFautes()));
	}

	public void init() {
		new FadeInUpTransition(paneTabel).play();
		txtKSK.setText("");
		txtGroupe.setText("");
		lblTimeLine.play();
		load();
	}

	public static String getNumKsk() {
		return numKsk;
	}

	public static void setNumKsk(String numKsk) {
		ScannageCtrl.numKsk = numKsk;
	}

	public static String getGroupe() {
		return groupe;
	}

	public static void setGroupe(String groupe) {
		ScannageCtrl.groupe = groupe;
	}

	public static ScannageCtrl getScannageCtrl() {
		return scannageCtrl;
	}

	public static void setScannageCtrl(ScannageCtrl scannageCtrl) {
		ScannageCtrl.scannageCtrl = scannageCtrl;
	}
}
