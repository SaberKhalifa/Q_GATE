package com.leoni.q_gate.beans;
/**
 * 
 * @author SABER KHALIFA
 *
 */
public class User {
	private int numUtilisateur;
	private int matricule;
	private int idTypeUtilisateur;
	private String typeUtilisateur;
	private String username;
	private String password;
	private boolean actived;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int numUtilisateur, int matricule, int idTypeUtilisateur,
			String typeUtilisateur, String username, String password,
			boolean actived) {
		super();
		this.numUtilisateur = numUtilisateur;
		this.matricule = matricule;
		this.idTypeUtilisateur = idTypeUtilisateur;
		this.typeUtilisateur = typeUtilisateur;
		this.username = username;
		this.password = password;
		this.actived = actived;
	}

	public int getNumUtilisateur() {
		return numUtilisateur;
	}

	public void setNumUtilisateur(int numUtilisateur) {
		this.numUtilisateur = numUtilisateur;
	}

	public int getMatricule() {
		return matricule;
	}

	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}

	public int getIdTypeUtilisateur() {
		return idTypeUtilisateur;
	}

	public void setIdTypeUtilisateur(int idTypeUtilisateur) {
		this.idTypeUtilisateur = idTypeUtilisateur;
	}

	public String getTypeUtilisateur() {
		return typeUtilisateur;
	}

	public void setTypeUtilisateur(String typeUtilisateur) {
		this.typeUtilisateur = typeUtilisateur;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	@Override
	public String toString() {
		return "User [numUtilisateur=" + numUtilisateur + ", matricule="
				+ matricule + ", idTypeUtilisateur=" + idTypeUtilisateur
				+ ", typeUtilisateur=" + typeUtilisateur + ", username="
				+ username + ", password=" + password + ", actived=" + actived
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (actived ? 1231 : 1237);
		result = prime * result + idTypeUtilisateur;
		result = prime * result + matricule;
		result = prime * result + numUtilisateur;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((typeUtilisateur == null) ? 0 : typeUtilisateur.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (actived != other.actived)
			return false;
		if (idTypeUtilisateur != other.idTypeUtilisateur)
			return false;
		if (matricule != other.matricule)
			return false;
		if (numUtilisateur != other.numUtilisateur)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (typeUtilisateur == null) {
			if (other.typeUtilisateur != null)
				return false;
		} else if (!typeUtilisateur.equals(other.typeUtilisateur))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
