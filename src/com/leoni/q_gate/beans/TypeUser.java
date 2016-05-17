package com.leoni.q_gate.beans;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class TypeUser {
	private int numType;
	private String type;

	public TypeUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TypeUser(int numType, String type) {
		super();
		this.numType = numType;
		this.type = type;
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

	@Override
	public String toString() {
		return type;
	}

}
