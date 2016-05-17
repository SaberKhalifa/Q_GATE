/**
 * 
 */
package com.leoni.q_gate.data.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import org.apache.tomcat.jdbc.pool.DataSource;
/**
 * @author Saber Ben Khalifa
 *
 */
public class Q_GateRepository {
	public static Connection con = null;
	FileInputStream inputStream;

	public Q_GateRepository() {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(new File("jdbc.properties"));
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException(
						"property file not found in the classpath");
			}
			DataSource ds = new DataSource();
			ds.setDriverClassName(prop.getProperty("driver"));
			ds.setUrl(prop.getProperty("url"));
			ds.setUsername(prop.getProperty("username"));
			ds.setPassword(prop.getProperty("password"));
			ds.setMaxActive(100);
			ds.setMaxWait(10000);
			ds.setMaxIdle(20);
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cette m�thode permet d'enregistrer un nouveau enregistrement (INSERT
	 * INTO ...
	 * 
	 * @param table
	 * @param cols
	 * @param row
	 */
	public void save(String table, String[] cols, HashMap<Integer, Object> row) {
		Connection con = getConnection();
		PreparedStatement stmt;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO " + table + " (");
			for (int i = 0; i < cols.length; i++) {
				if (i != (cols.length - 1))
					sql.append(cols[i] + ",");
				else
					sql.append(cols[i] + ") VALUES (");
			}
			for (int j = 0; j < cols.length; j++) {
				if (j != cols.length - 1)
					sql.append("?,");
				else
					sql.append("?)");
			}
			stmt = con.prepareStatement(sql.toString());
			for (int c = 1; c <= row.size(); c++) {
				stmt.setObject(c, row.get(c));
			}
			stmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Cette m�thode permet de supprimer un row (DELETE FROM ...
	 * 
	 * @param table
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delete(String table, String key, Object value) {
		Connection con = getConnection();
		PreparedStatement stmt;
		try {
			String sql = "DELETE FROM " + table + " WHERE " + key + "=" + value;
			stmt = con.prepareStatement(sql);
			stmt.executeUpdate();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Cette m�thode permet de modifer un row (UPDATE ...
	 * 
	 * @param table
	 * @param cols
	 * @param row
	 */
	public void update(String table, String[] cols,
			HashMap<Integer, Object> row, String key, Object value) {
		Connection con = getConnection();
		PreparedStatement stmt;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE " + table + " SET ");
			for (int i = 0; i < cols.length; i++) {
				if (i != (cols.length - 1)) {
					sql.append(cols[i] + "=?,");
				} else {
					sql.append(cols[i] + "=? WHERE " + key + "=" + value);
				}
			}
			System.out.println(sql.toString());
			stmt = con.prepareStatement(sql.toString());
			for (int c = 1; c <= row.size(); c++) {
				stmt.setObject(c, row.get(c));
			}
			stmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Cette m�thode permet de retourner une seule row
	 * 
	 * @param table
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultSet findOne(String table, String key, Object value) {
		Connection con = getConnection();
		ResultSet rs;
		try {
			String sql = "SELECT * FROM  " + table + " WHERE " + key + "="
					+ value;
			rs = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Cette m�thode permet de retourner une collection d'objet
	 * 
	 * @param table
	 * @return
	 */
	public ResultSet findAll(String table) {
		Connection con = getConnection();
		ResultSet rs;
		try {
			String sql = "SELECT * FROM  " + table;
			rs = con.createStatement().executeQuery(sql);
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Cette m�thode permet de retourner le nombre des row dans un table
	 * 
	 * @param table
	 * @return
	 */
	public int getCount(String table) {
		Connection con = getConnection();
		ResultSet rs;
		try {
			String sql = "SELECT COUNT(*) AS size FROM  " + table;
			rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		return 0;
	}

	/**
	 * Cette m�thode permet de retourner le max row inserer dans un table
	 * 
	 * @param table
	 * @return
	 */
	public int getMax(String table, String key) {
		Connection con = getConnection();
		ResultSet rs;
		try {
			String sql = "SELECT MAX(" + key + ") FROM  " + table;
			rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		return 0;
	}

	/**
	 * Cette m�thode permet de retourner le max row inserer dans un table
	 * 
	 * @param table
	 * @return
	 */
	public int getMin(String table, String key) {
		Connection con = getConnection();
		ResultSet rs;
		try {
			String sql = "SELECT MIN(" + key + ") FROM  " + table;
			rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		return 0;
	}

	/**
	 * Cette m�thode permet de retourner une connection JDBC
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			if (con == null || con.isClosed()) {
				new Q_GateRepository();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Cette m�thode permet de retourner une connection JDBC
	 * 
	 * @return
	 */
	public Connection getConnexion() {
		try {
			if (con == null || con.isClosed())
				new Q_GateRepository();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Cette m�thode peremt de retourner un ReslSet
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet getResultSet(String sql) {
		try {
			ResultSet rs = getConnection()
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
			return rs;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
