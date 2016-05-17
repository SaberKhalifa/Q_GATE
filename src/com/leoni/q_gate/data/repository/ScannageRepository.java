package com.leoni.q_gate.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.leoni.q_gate.beans.KSKFaute;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class ScannageRepository extends Q_GateRepository {
	public ObservableList<KSKFaute> findAll() {
		ObservableList<KSKFaute> data = FXCollections.observableArrayList();
		String sql = "SELECT t.idFaute,t.cbCable as KSK,g.groupe as Groupe,g.designation as designation,f.cbFaute as Faute,f.OK as ok,f.NOK as nok FROM q_gate.test as t,q_gate.fautes as f,q_gate.groupes as g where t.cbFaute=f.cbFaute AND t.idGroupe=g.idGroupe ORDER BY t.idFaute DESC LIMIT 10";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				KSKFaute c = new KSKFaute(rs.getInt("idFaute"),
						rs.getString("KSK"), rs.getString("Groupe"),
						rs.getString("designation"), rs.getString("Faute"),
						rs.getBoolean("ok") ? 1 : 0, rs.getBoolean("nok") ? 1
								: 0);
				data.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public ObservableList<KSKFaute> findAllByGroupe(Object value) {
		ObservableList<KSKFaute> data = FXCollections.observableArrayList();
		String sql = "SELECT t.idFaute,t.cbCable as KSK,g.groupe as Groupe,g.designation as designation,f.cbFaute as Faute,f.OK as ok,f.NOK as nok FROM q_gate.test as t,q_gate.fautes as f,q_gate.groupes as g where t.cbFaute=f.cbFaute AND t.idGroupe=g.idGroupe"
				+ " AND t.idGroupe="
				+ value
				+ " ORDER BY t.idFaute DESC LIMIT 10";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				KSKFaute c = new KSKFaute(rs.getInt("idFaute"),
						rs.getString("KSK"), rs.getString("Groupe"),
						rs.getString("designation"), rs.getString("Faute"),
						rs.getBoolean("ok") ? 1 : 0, rs.getBoolean("nok") ? 1
								: 0);
				data.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public int getKSKOK() {
		String sql = "SELECT COUNT(f.OK) as OK FROM q_gate.fautes as f ,q_gate.test as t WHERE t.cbFaute=f.cbFaute AND f.OK=1";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("OK");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int getKSKOKByGroupe(Object value) {
		String sql = "SELECT COUNT(f.OK) as OK FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g  WHERE t.cbFaute=f.cbFaute  AND t.idGroupe=g.idGroupe AND f.OK=1 and g.idGroupe="+value;
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("OK");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int getKSKNOK() {
		String sql = "SELECT COUNT(f.NOK) as NOK FROM q_gate.fautes as f ,q_gate.test as t WHERE t.cbFaute=f.cbFaute AND f.NOK=1";
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("NOK");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int getKSKNOKByGroupe(Object value) {
		String sql = "SELECT COUNT(f.NOK) as NOK FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g  WHERE t.cbFaute=f.cbFaute  AND t.idGroupe=g.idGroupe AND f.NOK=1 and g.idGroupe="+value;
		try {
			ResultSet rs = this.getConnexion().createStatement()
					.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("NOK");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void save(HashMap<Integer, Object> row) {
		String[] cols = new String[] { "idGroupe", "cbFaute", "dateFaute",
				"cbCable", "cbCableNOK" };
		this.save("test", cols, row);
	}

	public void delete(Object value) {
		this.delete("test", "idFaute", value);
	}
}
