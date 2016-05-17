package com.leoni.q_gate.beans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Menu {
	public static ObservableList<String> menuAdmin() {
		ObservableList<String> menus = FXCollections.observableArrayList();
		menus.addAll("Utilisateurs","Employes","Groupes", "Fautes","Controle","Statistiques");
		return menus;
	}
	public static ObservableList<String> menuUser() {
		ObservableList<String> menus = FXCollections.observableArrayList();
		menus.addAll("Scannage","Statistiques");
		return menus;
	}
}
