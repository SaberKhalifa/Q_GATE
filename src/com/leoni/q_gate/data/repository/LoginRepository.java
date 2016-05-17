package com.leoni.q_gate.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.leoni.q_gate.md5.MD5Q_GATE;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class LoginRepository extends Q_GateRepository {
	/**
	 * Login METHOD
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public int login(String username, String password) {
		StringBuilder sql;
		try {
			sql = new StringBuilder(
					"SELECT idUtilisateur,nomUtilisateur,password FROM utilisateurs where nomUtilisateur=")
					.append("'" + username + "'").append(" AND password=")
					.append("'" + new MD5Q_GATE().encrypt(password) + "'")
					.append(" AND actived");

			ResultSet rs;
			rs = getConnection().createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				return rs.getInt("idUtilisateur");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}
}
