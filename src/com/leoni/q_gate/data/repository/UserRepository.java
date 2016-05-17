package com.leoni.q_gate.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.leoni.q_gate.beans.TypeUser;
import com.leoni.q_gate.beans.User;
import com.leoni.q_gate.md5.MD5Q_GATE;

/***
 * 
 * @author SABER KHALIFA
 *
 */
public class UserRepository extends Q_GateRepository {
	/**
	 * GET USER BY ID
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(int id) {
		StringBuilder sql = new StringBuilder(
				"SELECT  u.idUtilisateur,u.matricule,u.idTypeUtilisateur,t.type,u.nomUtilisateur,u.password,u.actived FROM q_gate.utilisateurs as u,q_gate.typesutilisateur  as t where u.idTypeUtilisateur=t.idTypeUtilisateur  and idUtilisateur=")
				.append(id);
		ResultSet rs;
		try {
			rs = getConnection().createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				User user = new User(rs.getInt("idUtilisateur"),
						rs.getInt("matricule"), rs.getInt("idTypeUtilisateur"),
						rs.getString("type"), rs.getString("nomUtilisateur"),
						new MD5Q_GATE().decrypt(rs.getString("password")),
						rs.getBoolean("actived"));
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * GET TYPE OF USER BY ID
	 * 
	 * @param id
	 * @return
	 */
	public TypeUser getTypeById(int id) {
		StringBuilder sql = new StringBuilder(
				"SELECT idTypeUtilisateur, type FROM typesutilisateur WHERE idTypeUtilisateur=")
				.append(id);
		ResultSet rs;
		try {
			rs = getConnection().createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				TypeUser type = new TypeUser(rs.getInt("idTypeUtilisateur"),
						rs.getString("type"));
				return type;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ObservableList<User> findAll() {
		ObservableList<User> users = FXCollections.observableArrayList();
		StringBuilder sql = new StringBuilder(
				"SELECT u.idUtilisateur, u.matricule, u.idTypeUtilisateur,t.type,u.nomUtilisateur, u.password, u.actived FROM utilisateurs as u,typesutilisateur t  WHERE  u.idTypeUtilisateur= t.idTypeUtilisateur");
		ResultSet rs;
		try {
			rs = getConnection().createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				User user = new User(rs.getInt("idUtilisateur"),
						rs.getInt("matricule"), rs.getInt("idTypeUtilisateur"),
						rs.getString("type"), rs.getString("nomUtilisateur"),
						rs.getString("password"), rs.getBoolean("actived"));
				users.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 
	 * @return
	 */
	public ObservableList<TypeUser> findAllType() {
		ObservableList<TypeUser> users = FXCollections.observableArrayList();
		StringBuilder sql = new StringBuilder("SELECT * FROM typesutilisateur");
		ResultSet rs;
		try {
			rs = getConnection().createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				TypeUser type = new TypeUser(rs.getInt("idTypeUtilisateur"),
						rs.getString("type"));
				users.add(type);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * INSERT NEW USER
	 * 
	 * @param row
	 */
	public void saveOrUpdate(HashMap<Integer, Object> row, int id) {
		String[] cols = new String[] { "matricule", "idTypeUtilisateur",
				"nomUtilisateur", "password", "actived" };
		if (id == 0) {
			this.save("utilisateurs", cols, row);
		} else {
			this.update("utilisateurs", cols, row, "idUtilisateur", id);
		}

	}

	/**
	 * DELETE USER
	 * 
	 * @param idUtilisateur
	 */
	public void delete(Object idUtilisateur) {
		this.delete("utilisateurs", "idUtilisateur", idUtilisateur);
	}
}
