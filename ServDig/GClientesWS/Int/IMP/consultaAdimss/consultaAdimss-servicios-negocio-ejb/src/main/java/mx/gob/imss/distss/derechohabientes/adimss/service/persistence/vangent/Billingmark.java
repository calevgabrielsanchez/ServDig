package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BILLINGMARK database table.
 * 
 */
@Entity
@NamedQuery(name="Billingmark.findAll", query="SELECT b FROM Billingmark b")
public class Billingmark implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BillingmarkPK id;

	@Column(name="RANULAR_DERECHO")
	private String ranularDerecho;

	@Column(name="RANULAR_IZQUIERDO")
	private String ranularIzquierdo;

	@Column(name="RINDICE_DERECHO")
	private String rindiceDerecho;

	@Column(name="RINDICE_IZQUIERDO")
	private String rindiceIzquierdo;

	@Column(name="RMEDIO_DERECHO")
	private String rmedioDerecho;

	@Column(name="RMEDIO_IZQUIERDO")
	private String rmedioIzquierdo;

	@Column(name="RMENIQUE_DERECHO")
	private String rmeniqueDerecho;

	@Column(name="RMENIQUE_IZQUIERDO")
	private String rmeniqueIzquierdo;

	@Column(name="RPULGAR_DERECHO")
	private String rpulgarDerecho;

	@Column(name="RPULGAR_IZQUIERDO")
	private String rpulgarIzquierdo;

	public Billingmark() {
	}

	public BillingmarkPK getId() {
		return this.id;
	}

	public void setId(BillingmarkPK id) {
		this.id = id;
	}

	public String getRanularDerecho() {
		return this.ranularDerecho;
	}

	public void setRanularDerecho(String ranularDerecho) {
		this.ranularDerecho = ranularDerecho;
	}

	public String getRanularIzquierdo() {
		return this.ranularIzquierdo;
	}

	public void setRanularIzquierdo(String ranularIzquierdo) {
		this.ranularIzquierdo = ranularIzquierdo;
	}

	public String getRindiceDerecho() {
		return this.rindiceDerecho;
	}

	public void setRindiceDerecho(String rindiceDerecho) {
		this.rindiceDerecho = rindiceDerecho;
	}

	public String getRindiceIzquierdo() {
		return this.rindiceIzquierdo;
	}

	public void setRindiceIzquierdo(String rindiceIzquierdo) {
		this.rindiceIzquierdo = rindiceIzquierdo;
	}

	public String getRmedioDerecho() {
		return this.rmedioDerecho;
	}

	public void setRmedioDerecho(String rmedioDerecho) {
		this.rmedioDerecho = rmedioDerecho;
	}

	public String getRmedioIzquierdo() {
		return this.rmedioIzquierdo;
	}

	public void setRmedioIzquierdo(String rmedioIzquierdo) {
		this.rmedioIzquierdo = rmedioIzquierdo;
	}

	public String getRmeniqueDerecho() {
		return this.rmeniqueDerecho;
	}

	public void setRmeniqueDerecho(String rmeniqueDerecho) {
		this.rmeniqueDerecho = rmeniqueDerecho;
	}

	public String getRmeniqueIzquierdo() {
		return this.rmeniqueIzquierdo;
	}

	public void setRmeniqueIzquierdo(String rmeniqueIzquierdo) {
		this.rmeniqueIzquierdo = rmeniqueIzquierdo;
	}

	public String getRpulgarDerecho() {
		return this.rpulgarDerecho;
	}

	public void setRpulgarDerecho(String rpulgarDerecho) {
		this.rpulgarDerecho = rpulgarDerecho;
	}

	public String getRpulgarIzquierdo() {
		return this.rpulgarIzquierdo;
	}

	public void setRpulgarIzquierdo(String rpulgarIzquierdo) {
		this.rpulgarIzquierdo = rpulgarIzquierdo;
	}

}