package com.leoni.q_gate.beans;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class Groupe {
	private int numType;
	private String type;
	private String designation;
	private String service;

	public Groupe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Groupe(int numType, String type, String designation, String service) {
		super();
		this.numType = numType;
		this.type = type;
		this.designation = designation;
		this.service = service;
	}

	public int getNumType() {
		return numType;
	}

	public void setNumType(int numType) {
		this.numType = numType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + numType;
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Groupe other = (Groupe) obj;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (numType != other.numType)
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
