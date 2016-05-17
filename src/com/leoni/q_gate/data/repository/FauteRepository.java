package com.leoni.q_gate.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.leoni.q_gate.beans.Faute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class FauteRepository extends Q_GateRepository {
	/**
	 * FIND ALL FAUTES
	 * 
	 * @return
	 */
	public ObservableList<Faute> findAllFCS() {
		ObservableList<Faute> data = FXCollections.observableArrayList();
		StringBuilder sql = new StringBuilder("SELECT * FROM fautes");
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql.toString());
			while (rs.next()) {
				Faute c = new Faute(rs.getString("cbFaute"),
						rs.getString("designation"), rs.getBoolean("ok") ? 1
								: 0, rs.getBoolean("nok") ? 1 : 0);
				data.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * GET FAUTE BY MC
	 * 
	 * @param mc
	 * @return
	 */
	public Faute getFauteByMC(Object mc) {
		StringBuilder sql = new StringBuilder(
				"SELECT * FROM fautes WHERE cbFaute='" + mc + "'");
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql.toString());
			while (rs.next()) {
				Faute c = new Faute(rs.getString("cbFaute"),
						rs.getString("designation"), rs.getBoolean("ok") ? 1
								: 0, rs.getBoolean("nok") ? 1 : 0);
				return c;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * FIND ALL FAUTES BY MC (Mot clé)
	 * 
	 * @param mc
	 * @return
	 */
	public ObservableList<Faute> findAllFCSByMC(String mc) {
		ObservableList<Faute> data = FXCollections.observableArrayList();
		StringBuilder sql = new StringBuilder("SELECT * FROM fautes").append(
				" WHERE cbFaute LIKE ").append("'%" + mc + "%'");
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql.toString());
			while (rs.next()) {
				Faute c = new Faute(rs.getString("cbFaute"),
						rs.getString("designation"), rs.getBoolean("ok") ? 1
								: 0, !rs.getBoolean("nok") ? 1 : 0);
				data.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * INSERT NEW FAUTE
	 * 
	 * @param row
	 */
	public void saveFC(HashMap<Integer, Object> row) {
		if (getFauteByMC(row.get(1)) != null) {
			String[] cols = new String[] { "cbFaute", "designation", "OK",
					"NOK" };
			this.update("fautes", cols, row, "cbFaute", "'" + row.get(1) + "'");
		} else {
			String[] cols = new String[] { "cbFaute", "designation", "OK",
					"NOK" };
			this.save("fautes", cols, row);
		}

	}

	/**
	 * DELETE FAUTE BY ID
	 * 
	 * @param value
	 */
	public void deleteFC(Object value) {
		this.delete("fautes", "cbFaute", "'" + value + "'");
	}
}
