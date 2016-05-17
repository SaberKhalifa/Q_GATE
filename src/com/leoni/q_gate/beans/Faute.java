package com.leoni.q_gate.beans;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class Faute {
	private String faute;
	private String designation;
	private int OK;
	private int NOK;

	public Faute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Faute(String faute, String designation, int oK, int nOK) {
		super();
		this.faute = faute;
		this.designation = designation;
		OK = oK;
		NOK = nOK;
	}

	public String getFaute() {
		return faute;
	}

	public void setFaute(String faute) {
		this.faute = faute;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getOK() {
		return OK;
	}

	public void setOK(int oK) {
		OK = oK;
	}

	public int getNOK() {
		return NOK;
	}

	public void setNOK(int nOK) {
		NOK = nOK;
	}

	@Override
	public String toString() {
		return faute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + NOK;
		result = prime * result + OK;
		result = prime * result
				+ ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((faute == null) ? 0 : faute.hashCode());
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
		Faute other = (Faute) obj;
		if (NOK != other.NOK)
			return false;
		if (OK != other.OK)
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (faute == null) {
			if (other.faute != null)
				return false;
		} else if (!faute.equals(other.faute))
			return false;
		return true;
	}

}
