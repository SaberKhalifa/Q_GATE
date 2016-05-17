package com.leoni.q_gate.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.leoni.q_gate.beans.Employe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class EmployeRepository extends Q_GateRepository {

	/**
	 * FIND ALL EMPLOYES
	 * 
	 * @return Collections EMPLOYES
	 */
	public Employe findEmploye(Object matricule) {
		String sql = "SELECT e.matricule, e.idGroupe,g.groupe, e.nom, e.prenom, e.adresse, e.codePostale, e.ville, e.tel, e.email, e.service FROM employes as  e,groupes as g WHERE e.idGroupe= g.idGroupe  and e.matricule="
				+ matricule;
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				Employe emp = new Employe(rs.getInt("matricule"),
						rs.getInt("idGroupe"), rs.getString("groupe"),
						rs.getString("nom"), rs.getString("prenom"),
						rs.getString("adresse"), rs.getString("codePostale"),
						rs.getString("ville"), rs.getString("tel"),
						rs.getString("email"), rs.getString("service"));
				return emp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FIND ALL EMPLOYES
	 * 
	 * @return Collections EMPLOYES
	 */
	public ObservableList<Employe> findAll() {
		ObservableList<Employe> data = FXCollections.observableArrayList();
		String sql = "SELECT e.matricule,e.idGroupe, g.groupe, e.nom, e.prenom, e.adresse, e.codePostale, e.ville, e.tel, e.email, e.service FROM employes as  e,groupes as g WHERE e.idGroupe= g.idGroupe ORDER BY e.matricule";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				Employe emp = new Employe(rs.getInt("matricule"),
						rs.getInt("idGroupe"), rs.getString("groupe"),
						rs.getString("nom"), rs.getString("prenom"),
						rs.getString("adresse"), rs.getString("codePostale"),
						rs.getString("ville"), rs.getString("tel"),
						rs.getString("email"), rs.getString("service"));
				data.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * INSERT NEW EMPLOYE
	 * 
	 * @param row
	 */
	public void saveOrUpdate(HashMap<Integer, Object> row) {
		String[] cols = new String[] { "matricule", "idGroupe", "nom",
				"prenom", "adresse", "codePostale", "ville", "tel", "email",
				"service" };
		if (findEmploye(row.get(1)) == null) {
			this.save("employes", cols, row);
		} else {
			this.update("employes", cols, row, "matricule", row.get(1));
		}

	}

	/**
	 * DELETE EMPLOYE
	 * 
	 * @param matricule
	 */
	public void delete(Object matricule) {
		this.delete("employes", "matricule", matricule);
	}

	public static void main(String[] args) {
		EmployeRepository repository = new EmployeRepository();
		System.out.println(repository.findEmploye(307));
	}
}
