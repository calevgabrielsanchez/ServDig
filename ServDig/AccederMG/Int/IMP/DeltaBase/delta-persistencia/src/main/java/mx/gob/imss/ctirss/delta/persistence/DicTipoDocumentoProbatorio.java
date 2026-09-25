package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIC_TIPO_DOCUMENTO_PROBATORIO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_DOCUMENTO_PROBATORIO")
public class DicTipoDocumentoProbatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_DOCUMENTO_PROBATOR", unique=true, nullable=false, precision=22)
	private long cveIdTipoDocumentoProbator;

	@Column(name="DES_TIPO_DOCUMENTO_PROBATORIO", nullable=false, length=255)
	private String desTipoDocumentoProbatorio;
    
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @OneToMany(mappedBy="dicTipoDocumentoProbatorio")
    private List<DitDocumentoPorTipo> ditDocumentoPorTipos;
    
    

    
    


    public List<DitDocumentoPorTipo> getDitDocumentoPorTipos() {
		return ditDocumentoPorTipos;
	}

	public void setDitDocumentoPorTipos(
			List<DitDocumentoPorTipo> ditDocumentoPorTipos) {
		this.ditDocumentoPorTipos = ditDocumentoPorTipos;
	}

	public DicTipoDocumentoProbatorio() {
    }

	public long getCveIdTipoDocumentoProbator() {
		return this.cveIdTipoDocumentoProbator;
	}

	public void setCveIdTipoDocumentoProbator(long cveIdTipoDocumentoProbator) {
		this.cveIdTipoDocumentoProbator = cveIdTipoDocumentoProbator;
	}

	public String getDesTipoDocumentoProbatorio() {
		return this.desTipoDocumentoProbatorio;
	}

	public void setDesTipoDocumentoProbatorio(String desTipoDocumentoProbatorio) {
		this.desTipoDocumentoProbatorio = desTipoDocumentoProbatorio;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	
	
}