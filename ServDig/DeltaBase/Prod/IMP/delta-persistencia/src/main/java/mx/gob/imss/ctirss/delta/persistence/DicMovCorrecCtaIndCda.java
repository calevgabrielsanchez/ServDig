package mx.gob.imss.ctirss.delta.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIC_MOV_CORREC_CTA_IND_CDA")
public class DicMovCorrecCtaIndCda 
{
	@Id
	//@SequenceGenerator(name = "SEQ_DICMOVCORRECCTAINDCDA", sequenceName = "SEQ_DICMOVCORRECCTAINDCDA")
  //  @GeneratedValue(generator = "SEQ_DICMOVCORRECCTAINDCDA")//tipo de llave primaria 
	@Column(name="CVE_ID_MOV_CORRECCION", length=1)
	private char cveIdMovCorreccion;
	
	@Column (name="CVE_ID_MOV_CORRECCION", length=50, insertable = false, updatable = false)
	private String desMovCorreccion;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	 
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public char getCveIdMovCorreccion() {
		return cveIdMovCorreccion;
	}

	public void setCveIdMovCorreccion(char cveIdMovCorreccion) {
		this.cveIdMovCorreccion = cveIdMovCorreccion;
	}

	public String getDesMovCorreccion() {
		return desMovCorreccion;
	}

	public void setDesMovCorreccion(String desMovCorreccion) {
		this.desMovCorreccion = desMovCorreccion;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	@Override
	public String toString() {
		return "DicMovCorrecCtaIndCda [cveIdMovCorreccion=" + cveIdMovCorreccion + ", desMovCorreccion="
				+ desMovCorreccion + ", fecRegistroAlta=" + fecRegistroAlta + ", fecRegistroBaja=" + fecRegistroBaja
				+ ", fecRegistroActualizado=" + fecRegistroActualizado + "]";
	}

	
}
