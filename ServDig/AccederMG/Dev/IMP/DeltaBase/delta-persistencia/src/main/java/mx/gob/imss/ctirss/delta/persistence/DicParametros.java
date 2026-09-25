package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIC_PARAMETROS")
public class DicParametros implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7508767756968893950L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PARAMETRO")
	private long cveIdParametro;
	@Column(name="DES_LLAVE_PARAMETRO")
	private String desLlaveParametro;
	
	@Column(name="DES_VALOR_PARAMETRO")
	private String desValorParametro;
	
	
	public long getCveIdParametro() {
		return cveIdParametro;
	}

	public void setCveIdParametro(long cveIdParametro) {
		this.cveIdParametro = cveIdParametro;
	}

	public String getDesLlaveParametro() {
		return desLlaveParametro;
	}

	public void setDesLlaveParametro(String desLlaveParametro) {
		this.desLlaveParametro = desLlaveParametro;
	}

	public String getDesValorParametro() {
		return desValorParametro;
	}

	public void setDesValorParametro(String desValorParametro) {
		this.desValorParametro = desValorParametro;
	}
	
}
