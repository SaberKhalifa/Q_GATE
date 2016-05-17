package com.leoni.q_gate.statistiques;
import java.util.HashMap;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;


/**
 * 
 * @author SABER KHALIFA
 *
 */
public class Q_GateGraph {
	/**
	 * Cette méthode pour afficher un graphique (FAUTES) sous forme BarChart
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BarChart<String, Number> graphFautes(List<HashMap<String, String>> data) {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setCategoryGap(1);
		barChart.setBarGap(1);
		xAxis.setLabel("FAUTE");
		yAxis.setLabel("NB FAUTE");
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Les fautes les plus apparue");
		double somme = 0;
		for (int i = 0; i < data.size(); i++) {
			somme += Double.parseDouble(data.get(i).get("nb"));
		}
		for (int i = 0; i < data.size(); i++) {
			double percent = 100.0 * Double.parseDouble(data.get(i).get("nb")) / somme;
			String fmt = String.format(data.get(i).get("faute") + "(%d - %.02f%%)",
					Integer.parseInt(data.get(i).get("nb")), percent);
			series1.getData().add(new XYChart.Data<String, Number>(fmt,
					Integer.parseInt(data.get(i).get("nb"))));
		}
		barChart.getData().addAll(series1);
		barChart.setAnimated(false);
		return barChart;

	}

	/**
	 * Cette méthode pour afficher un graphique (GROUPE) sous forme BarChart
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BarChart<String, Number> graphGroupes(List<HashMap<String, String>> data) {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setStyle("-fx-border-color:#323232;");
		barChart.setCategoryGap(1);
		barChart.setBarGap(1);
		xAxis.setLabel("GROUPE");
		yAxis.setLabel("NB FOIS");
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Les groupes les plus fautifs");
		double somme = 0;
		for (int i = 0; i < data.size(); i++) {
			somme += Double.parseDouble(data.get(i).get("nb"));
		}
		for (int i = 0; i < data.size(); i++) {
			double percent = 100.0 * Double.parseDouble(data.get(i).get("nb")) / somme;
			String fmt = String.format(data.get(i).get("groupe") + "(%d - %.02f%%)",
					Integer.parseInt(data.get(i).get("nb")), percent);
			series1.getData().add(new XYChart.Data<String, Number>(fmt,
					Integer.parseInt(data.get(i).get("nb"))));
		}
		barChart.getData().addAll(series1);
		barChart.setAnimated(false);
		return barChart;

	}

	/**
	 * Cette méthode pour afficher un graphique (GROUPE) sous forme pieChart
	 * 
	 * @param data
	 * @return
	 */
	public static PieChart graphPieGroupes(List<HashMap<String, String>> data) {
		final PieChart piaChart = new PieChart();
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Les groupes les plus fautifs");
		double somme = 0;
		for (int i = 0; i < data.size(); i++) {
			somme += Double.parseDouble(data.get(i).get("nb"));
		}
		for (int i = 0; i < data.size(); i++) {
			double percent = 100.0 * Double.parseDouble(data.get(i).get("nb")) / somme;
			String fmt = String.format(data.get(i).get("groupe") + "(%d - %.02f%%)",
					Integer.parseInt(data.get(i).get("nb")), percent);
			Data d = new Data(fmt, Double.parseDouble(data.get(i).get("nb")));
			piaChart.getData().add(d);
		}
		return piaChart;

	}
}
