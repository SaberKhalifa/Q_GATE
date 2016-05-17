package com.leoni.q_gate.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.leoni.q_gate.beans.Groupe;

/***
 * 
 * @author SABER KHALIFA
 *
 */
public class GroupeRepository extends Q_GateRepository {
	/**
	 * FIND GROUEP BY ID
	 * 
	 * @param num
	 * @return
	 */
	public Groupe findById(Object num) {
		String sql = "SELECT idGroupe, groupe, designation,service FROM groupes WHERE idGroupe="
				+ num;
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				Groupe temp = new Groupe(rs.getInt("idGroupe"),
						rs.getString("groupe"), rs.getString("designation"),
						rs.getString("service"));
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FIND GROUPE BY TYPE
	 * 
	 * @param num
	 * @return
	 */
	public Groupe findByType(Object num) {
		String sql = "SELECT idGroupe, groupe, designation,service FROM groupes WHERE groupe='"
				+ num + "'";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				Groupe temp = new Groupe(rs.getInt("idGroupe"),
						rs.getString("groupe"), rs.getString("designation"),
						rs.getString("service"));
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param num
	 * @return
	 */
	public ObservableList<Groupe> findAll() {
		ObservableList<Groupe> grs = FXCollections.observableArrayList();
		String sql = "SELECT idGroupe, groupe, designation,service FROM groupes";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				Groupe temp = new Groupe(rs.getInt("idGroupe"),
						rs.getString("groupe"), rs.getString("designation"),
						rs.getString("service"));
				grs.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grs;
	}

	/**
	 * INSERT NEW USER
	 * 
	 * @param row
	 */
	public void saveOrUpdate(HashMap<Integer, Object> row, int id) {
		String[] cols = new String[] { "groupe", "designation", "service" };
		if (id == 0) {
			this.save("groupes", cols, row);
		} else {
			this.update("groupes", cols, row, "idGroupe", id);
		}

	}

	/**
	 * DELETE USER
	 * 
	 * @param idUtilisateur
	 */
	public void delete(Object idUtilisateur) {
		this.delete("groupes", "idGroupe", idUtilisateur);
	}
}
