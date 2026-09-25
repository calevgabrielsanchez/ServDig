package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SPC_PARAMETRO")
@NamedQuery(name="SpcParametro.findAll", query="SELECT s FROM SpcParametro s")
public class SpcParametro implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO")
	private String idParametro;

	@Column(name="DES_PARAMETRO")
	private String desParametro;

	@Column(name="DES_VALOR_PARAMETRO")
	private String desValorParametro;

	@Column(name="IND_VISIBLE")
	private String indVisible;

	/**
	 * @return the idParametro
	 */
	public String getIdParametro() {
		return idParametro;
	}

	/**
	 * @param idParametro the idParametro to set
	 */
	public void setIdParametro(String idParametro) {
		this.idParametro = idParametro;
	}

	/**
	 * @return the desParametro
	 */
	public String getDesParametro() {
		return desParametro;
	}

	/**
	 * @param desParametro the desParametro to set
	 */
	public void setDesParametro(String desParametro) {
		this.desParametro = desParametro;
	}

	/**
	 * @return the desValorParametro
	 */
	public String getDesValorParametro() {
		return desValorParametro;
	}

	/**
	 * @param desValorParametro the desValorParametro to set
	 */
	public void setDesValorParametro(String desValorParametro) {
		this.desValorParametro = desValorParametro;
	}

	/**
	 * @return the indVisible
	 */
	public String getIndVisible() {
		return indVisible;
	}

	/**
	 * @param indVisible the indVisible to set
	 */
	public void setIndVisible(String indVisible) {
		this.indVisible = indVisible;
	}

}
