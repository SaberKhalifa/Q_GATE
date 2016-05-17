package com.leoni.q_gate.data.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class StatistiquesRepository extends Q_GateRepository {
	public List<HashMap<String, String>> getXFautes() {
		String sql = "SELECT COUNT(f.cbFaute) as nb , CONCAT(f.cbFaute, ' : ', f.designation )as faute  FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g WHERE  f.cbFaute=t.cbFaute AND g.idGroupe=t.idGroupe and f.nok=1  GROUP BY f.cbFaute ORDER BY  nb DESC LIMIT 8";
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet rs = this.getConnexion().createStatement().executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				row.put("faute", rs.getString("faute"));
				row.put("nb", String.valueOf(rs.getInt("nb")));
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public List<HashMap<String, String>> getXFautesByGroupe(ArrayList<String> list) {
		String sql = "";
		if (list.size() > 0) {
			sql += "SELECT COUNT(f.cbFaute) as nb ,CONCAT(f.cbFaute, ' : ', f.designation )as faute FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g WHERE  f.cbFaute=t.cbFaute AND g.idGroupe=t.idGroupe and f.nok=1 AND g.groupe IN ( ";
			for (int i = 0; i < list.size(); i++) {
				if (i != list.size() - 1) {
					sql += "'" + list.get(i) + "' ,";
				} else {
					sql += "'" + list.get(i) + "')";
				}
			}
		} else {
			sql += "SELECT COUNT(f.cbFaute) as nb ,CONCAT(f.cbFaute, ' : ', f.designation )as faute FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g WHERE  f.cbFaute=t.cbFaute AND g.idGroupe=t.idGroupe and f.nok=1 ";

		}
		sql += " GROUP BY f.cbFaute ORDER BY  nb DESC LIMIT 8";
		System.out.println(sql);
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet rs = this.getConnexion().createStatement().executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				row.put("faute", rs.getString("faute"));
				row.put("nb", String.valueOf(rs.getInt("nb")));
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public List<HashMap<String, String>> getXFautesByDate(Date debut, Date fin) {
		String sql = "SELECT COUNT(f.cbFaute) as nb ,f.cbFaute as faute  FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g WHERE  f.cbFaute=t.cbFaute AND g.idGroupe=t.idGroupe and f.nok=1  AND t.dateFaute>='"
				+ debut + "' AND t.dateFaute<='" + fin + "' GROUP BY f.cbFaute ORDER BY nb DESC LIMIT 8";
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet rs = this.getConnexion().createStatement().executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				row.put("faute", rs.getString("faute"));
				row.put("nb", String.valueOf(rs.getInt("nb")));
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public List<HashMap<String, String>> getXGroues() {
		String sql = "SELECT COUNT(g.groupe) as nb ,CONCAT(g.groupe, ' : ', g.designation ) as groupe  FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g  WHERE t.cbFaute=f.cbFaute AND t.idGroupe=g.idGroupe GROUP BY g.groupe  ORDER BY nb DESC LIMIT 8";
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet rs = this.getConnexion().createStatement().executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				row.put("groupe", rs.getString("groupe"));
				row.put("nb", String.valueOf(rs.getInt("nb")));
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public List<HashMap<String, String>> getXGrouesByGroupe(ArrayList<String> list) {
		String sql = "";
		if (list.size() > 0) {
			sql += "SELECT COUNT(g.groupe) as nb ,CONCAT(g.groupe, ' : ', g.designation ) as groupe   FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g  WHERE t.cbFaute=f.cbFaute AND t.idGroupe=g.idGroupe AND g.groupe IN ( ";
			for (int i = 0; i < list.size(); i++) {
				if (i != list.size() - 1) {
					sql += "'" + list.get(i) + "' ,";
				} else {
					sql += "'" + list.get(i) + "')";
				}
			}
		} else {
			sql += "SELECT COUNT(g.groupe) as nb ,CONCAT(g.groupe, ' : ', g.designation ) as groupe   FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g  WHERE t.cbFaute=f.cbFaute AND t.idGroupe=g.idGroupe ";
		}
		sql += " GROUP BY g.groupe  ORDER BY nb DESC LIMIT 8";
		System.out.println(sql);
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet rs = this.getConnexion().createStatement().executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				row.put("groupe", rs.getString("groupe"));
				row.put("nb", String.valueOf(rs.getInt("nb")));
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public List<HashMap<String, String>> getXGrouesByDate(Date debut, Date fin) {
		String sql = "SELECT COUNT(g.groupe) as nb ,g.groupe as groupe  FROM q_gate.fautes as f ,q_gate.test as t,q_gate.groupes as g  WHERE t.cbFaute=f.cbFaute AND t.idGroupe=g.idGroupe "
				+ " AND t.dateFaute>='" + debut + "' AND t.dateFaute<='" + fin
				+ "' GROUP BY g.groupe  ORDER BY nb DESC LIMIT 8";
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet rs = this.getConnexion().createStatement().executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				row.put("groupe", rs.getString("groupe"));
				row.put("nb", String.valueOf(rs.getInt("nb")));
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
