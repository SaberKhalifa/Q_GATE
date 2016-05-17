package com.leoni.q_gate.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.leoni.q_gate.animations.FadeInUpTransition;
import com.leoni.q_gate.beans.Groupe;
import com.leoni.q_gate.data.repository.GroupeRepository;
import com.leoni.q_gate.data.repository.StatistiquesRepository;
import com.leoni.q_gate.printer.NodePrinter;
import com.leoni.q_gate.statistiques.Q_GateGraph;
import com.leoni.q_gate.task.Q_GateTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class StatistiquesCtrl implements Initializable {
	@FXML
	private BorderPane paneMain;
	@FXML
	private BorderPane paneStat;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnStatMois;
	@FXML
	private Button btnStatSemaine;
	@FXML
	private Button btnPrint;
	@FXML
	private Button btnPDF;
	@FXML
	private ProgressBar bar;
	@FXML
	private DatePicker txtDateDebut;
	@FXML
	private DatePicker txtDateFin;
	@FXML
	private HBox hBox;
	private StatistiquesRepository statRepository;
	private NodePrinter printer = new NodePrinter();
	private PrinterJob job;
	private Rectangle printRectangle;
	private GroupeRepository grRepository;
	public Node nodeFaute, nodeGroupe;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			statRepository = new StatistiquesRepository();
			grRepository = new GroupeRepository();
			new FadeInUpTransition(paneMain).play();
			load();
			setGroupe();
		});
	}

	/**
	 * LOAD FAUTES FROM DATA BASE
	 */
	public void load() {
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		nodeFaute = Q_GateGraph.graphFautes(statRepository.getXFautes());
		nodeGroupe = Q_GateGraph.graphPieGroupes(statRepository.getXGroues());
		paneMain.setRight(Q_GateGraph.graphFautes(statRepository.getXFautes()));
		paneMain.setCenter(Q_GateGraph.graphPieGroupes(statRepository.getXGroues()));
	}

	@FXML
	private void aksiBack(ActionEvent event) {
		paneStat.setOpacity(0);
		txtDateDebut.setValue(null);
		txtDateFin.setValue(null);
		new FadeInUpTransition(paneMain).play();
		load();
	}

	@FXML
	private void aksiStat(ActionEvent event) {
		txtDateDebut.setValue(null);
		txtDateFin.setValue(null);
		paneMain.setOpacity(0);
		new FadeInUpTransition(paneStat).play();
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		paneStat.setRight(Q_GateGraph.graphFautes(statRepository.getXFautes()));
		paneStat.setCenter(Q_GateGraph.graphPieGroupes(statRepository.getXGroues()));

	}

	@SuppressWarnings("deprecation")
	@FXML
	public void OK(ActionEvent event) {
		if (txtDateDebut.getValue() != null && txtDateFin.getValue() != null) {
			LocalDate LocalDateDebut = txtDateDebut.getValue();
			Date dateDebut = new Date();
			dateDebut.setYear(LocalDateDebut.getYear() - 1900);
			dateDebut.setMonth(LocalDateDebut.getMonth().getValue() - 1);
			dateDebut.setDate(LocalDateDebut.getDayOfMonth());
			LocalDate LocalDateFin = txtDateFin.getValue();
			Date dateFin = new Date();
			dateFin.setYear(LocalDateFin.getYear() - 1900);
			dateFin.setMonth(LocalDateFin.getMonth().getValue() - 1);
			dateFin.setDate(LocalDateFin.getDayOfMonth());
			System.out.println(new java.sql.Date(dateDebut.getTime()) + "," + new java.sql.Date(dateFin.getTime()));
			nodeFaute = Q_GateGraph.graphFautes(statRepository.getXFautesByDate(new java.sql.Date(dateDebut.getTime()),
					new java.sql.Date(dateFin.getTime())));
			nodeGroupe = Q_GateGraph.graphPieGroupes(statRepository
					.getXGrouesByDate(new java.sql.Date(dateDebut.getTime()), new java.sql.Date(dateFin.getTime())));
			paneStat.setRight(Q_GateGraph.graphFautes(statRepository
					.getXFautesByDate(new java.sql.Date(dateDebut.getTime()), new java.sql.Date(dateFin.getTime()))));
			paneStat.setCenter(Q_GateGraph.graphPieGroupes(statRepository
					.getXGrouesByDate(new java.sql.Date(dateDebut.getTime()), new java.sql.Date(dateFin.getTime()))));
		} else {
			paneStat.setRight(Q_GateGraph.graphFautes(statRepository.getXFautes()));
			paneStat.setCenter(Q_GateGraph.graphPieGroupes(statRepository.getXGroues()));
		}
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
	}

	@FXML
	private void print(ActionEvent event) {
		BorderPane pane = new BorderPane();
		pane.setTop(nodeFaute);
		pane.setCenter(nodeGroupe);
		pane.setMaxHeight(600);
		pane.setMaxWidth(450);
		PrinterJob job = PrinterJob.createPrinterJob();
		JobSettings jobSettings = job.getJobSettings();
		Printer printer = Printer.getDefaultPrinter();
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
				Printer.MarginType.DEFAULT);
		jobSettings.setPageLayout(pageLayout);
		if (job != null) {
			boolean success = job.printPage(pane);
			if (success) {
				job.endJob();
			}
		}

	}

	@FXML
	private void exportPDF(ActionEvent event) {
		BorderPane pane = new BorderPane();
		pane.setTop(nodeFaute);
		pane.setCenter(nodeGroupe);
		pane.setMaxHeight(600);
		pane.setMaxWidth(450);
		printer.setScale(3);
		printer.setPrintRectangle(getPrintRectangle());
		job = PrinterJob.createPrinterJob();
		boolean success = printer.print(job, true, pane);
		if (success) {
			job.endJob();
		}

	}

	private void setGroupe() {
		ObservableList<Groupe> list = grRepository.findAll();
		for (int i = 0; i < list.size(); i++) {
			CheckBox box = new CheckBox(list.get(i).getType());
			box.setPadding(new Insets(15));
			box.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
					loadStatByGroupe();
				}
			});
			hBox.getChildren().add(box);
		}
	}

	private void loadStatByGroupe() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < hBox.getChildren().size(); i++) {
			CheckBox box = (CheckBox) hBox.getChildren().get(i);
			if (box.isSelected()) {
				list.add(box.getText());
			}
		}
		Service<Integer> service = Q_GateTask.getService();
		service.start();
		bar.progressProperty().bind(service.progressProperty());
		nodeFaute = Q_GateGraph.graphFautes(statRepository.getXFautesByGroupe(list));
		nodeGroupe = Q_GateGraph.graphPieGroupes(statRepository.getXGrouesByGroupe(list));
		paneMain.setRight(
				Q_GateGraph.graphFautes(statRepository.getXFautesByGroupe(list)));
		paneMain.setCenter(
				Q_GateGraph.graphPieGroupes(statRepository.getXGrouesByGroupe(list)));
	}

	private Rectangle getPrintRectangle() {
		if (printRectangle == null) {
			printRectangle = new Rectangle(210, 297, null);
			printRectangle.setStroke(Color.BLACK);
		}
		return printRectangle;
	}
}
