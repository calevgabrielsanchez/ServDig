package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ADC_CONTRATO_PROVEDOR database table.
 * 
 */
@Entity
@Table(name="ADC_CONTRATO_PROVEDOR")
@NamedQuery(name="AdcContratoProvedor.findAll", query="SELECT a FROM AdcContratoProvedor a")
public class AdcContratoProvedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_CTO_PROVEEDOR")
	private String numCtoProveedor;

	@Column(name="DES_PROVEDOR_EXPIDE")
	private String desProvedorExpide;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIN_CTO")
	private Date fecFinCto;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_CTO")
	private Date fecInicioCto;

	public AdcContratoProvedor() {
	}

	public String getNumCtoProveedor() {
		return this.numCtoProveedor;
	}

	public void setNumCtoProveedor(String numCtoProveedor) {
		this.numCtoProveedor = numCtoProveedor;
	}

	public String getDesProvedorExpide() {
		return this.desProvedorExpide;
	}

	public void setDesProvedorExpide(String desProvedorExpide) {
		this.desProvedorExpide = desProvedorExpide;
	}

	public Date getFecFinCto() {
		return this.fecFinCto;
	}

	public void setFecFinCto(Date fecFinCto) {
		this.fecFinCto = fecFinCto;
	}

	public Date getFecInicioCto() {
		return this.fecInicioCto;
	}

	public void setFecInicioCto(Date fecInicioCto) {
		this.fecInicioCto = fecInicioCto;
	}

}