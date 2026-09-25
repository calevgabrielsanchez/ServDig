package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="DIC_DOCUMENTO")
public class DicDocumento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_ID_DOCUMENTO")
	private long cveIdDocumento;
	
	@Column(name="DES_DOCUMENTO")
	private String desDocumento;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	@OneToMany(mappedBy="dicDocumento")
	private List<DitDocumentoPorTipo> ditDocumentoPorTipos;
	
	

	public List<DitDocumentoPorTipo> getDitDocumentoPorTipos() {
		return ditDocumentoPorTipos;
	}


	public void setDitDocumentoPorTipos(
			List<DitDocumentoPorTipo> ditDocumentoPorTipos) {
		this.ditDocumentoPorTipos = ditDocumentoPorTipos;
	}


	/**
	 * @return the cveIdDocumento
	 */
	public long getCveIdDocumento() {
		return cveIdDocumento;
	}


	/**
	 * @param cveIdDocumento the cveIdDocumento to set
	 */
	public void setCveIdDocumento(long cveIdDocumento) {
		this.cveIdDocumento = cveIdDocumento;
	}


	/**
	 * @return the desDocumento
	 */
	public String getDesDocumento() {
		return desDocumento;
	}


	/**
	 * @param desDocumento the desDocumento to set
	 */
	public void setDesDocumento(String desDocumento) {
		this.desDocumento = desDocumento;
	}


	/**
	 * @return the fecRegistroAlta
	 */
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}


	/**
	 * @param fecRegistroAlta the fecRegistroAlta to set
	 */
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}


	/**
	 * @return the fecRegistroBaja
	 */
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}


	/**
	 * @param fecRegistroBaja the fecRegistroBaja to set
	 */
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


	/**
	 * @return the fecRegistroActualizado
	 */
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}


	/**
	 * @param fecRegistroActualizado the fecRegistroActualizado to set
	 */
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}





	/**
	 * 
	 */
	public DicDocumento() {
		super();
	}
	
	
	
	
}
