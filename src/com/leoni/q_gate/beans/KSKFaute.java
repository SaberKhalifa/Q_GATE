package com.leoni.q_gate.beans;

/**
 * 
 * @author SABER KHALIFA
 *
 */
public class KSKFaute {
	private int idFaute;
	private String ksk;
	private String groupe;
	private String designationGroupe;
	private String faute;
	private int ok;
	private int nok;

	public KSKFaute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KSKFaute(int idFaute, String ksk, String groupe,
			String designationGroupe, String faute, int ok, int nok) {
		super();
		this.idFaute = idFaute;
		this.ksk = ksk;
		this.groupe = groupe;
		this.designationGroupe = designationGroupe;
		this.faute = faute;
		this.ok = ok;
		this.nok = nok;
	}

	public int getIdFaute() {
		return idFaute;
	}

	public void setIdFaute(int idFaute) {
		this.idFaute = idFaute;
	}

	public String getKsk() {
		return ksk;
	}

	public void setKsk(String ksk) {
		this.ksk = ksk;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public String getDesignationGroupe() {
		return designationGroupe;
	}

	public void setDesignationGroupe(String designationGroupe) {
		this.designationGroupe = designationGroupe;
	}

	public String getFaute() {
		return faute;
	}

	public void setFaute(String faute) {
		this.faute = faute;
	}

	public int getOk() {
		return ok;
	}

	public void setOk(int ok) {
		this.ok = ok;
	}

	public int getNok() {
		return nok;
	}

	public void setNok(int nok) {
		this.nok = nok;
	}

	@Override
	public String toString() {
		return "KSKFaute [idFaute=" + idFaute + ", ksk=" + ksk + ", groupe="
				+ groupe + ", designationGroupe=" + designationGroupe
				+ ", faute=" + faute + ", ok=" + ok + ", nok=" + nok + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((designationGroupe == null) ? 0 : designationGroupe
						.hashCode());
		result = prime * result + ((faute == null) ? 0 : faute.hashCode());
		result = prime * result + ((groupe == null) ? 0 : groupe.hashCode());
		result = prime * result + idFaute;
		result = prime * result + ((ksk == null) ? 0 : ksk.hashCode());
		result = prime * result + nok;
		result = prime * result + ok;
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
		KSKFaute other = (KSKFaute) obj;
		if (designationGroupe == null) {
			if (other.designationGroupe != null)
				return false;
		} else if (!designationGroupe.equals(other.designationGroupe))
			return false;
		if (faute == null) {
			if (other.faute != null)
				return false;
		} else if (!faute.equals(other.faute))
			return false;
		if (groupe == null) {
			if (other.groupe != null)
				return false;
		} else if (!groupe.equals(other.groupe))
			return false;
		if (idFaute != other.idFaute)
			return false;
		if (ksk == null) {
			if (other.ksk != null)
				return false;
		} else if (!ksk.equals(other.ksk))
			return false;
		if (nok != other.nok)
			return false;
		if (ok != other.ok)
			return false;
		return true;
	}

}
