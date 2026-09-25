package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_DOCUMENTO_POR_TIPO database table.
 * 
 */
@Entity
@Table(name="DIT_DOCUMENTO_POR_TIPO")
public class DitDocumentoPorTipo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DOCTO_PROB_POR_TIPO")
	private long cveIdDoctoProbPorTipo;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	
	 //bi-directional many-to-one association to DicModulo
    @ManyToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO" , insertable=false ,updatable=false)
	private DicDocumento dicDocumento;	
	
    //bi-directional many-to-one association to DicModulo
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_DOCUMENTO_PROBATOR" , insertable=false ,updatable=false)
	private DicTipoDocumentoProbatorio dicTipoDocumentoProbatorio;
    
    @OneToMany(mappedBy="ditDocumentoPorTipo")
    private List<DitDoctoReqTramite> ditDoctoReqTramites;
    
    @OneToMany(mappedBy="ditDocumentoPorTipo")
    private List<DitDocumentoProbatorio> ditDocumentoProbatorios;
    
    /**
    @OneToMany(mappedBy="ditDocumentoPorTipo")
    private List<DitDoctoReqTramOrigenSol> ditDoctoReqTramOrigenSols;
    

    
    public List<DitDoctoReqTramOrigenSol> getDitDoctoReqTramOrigenSols() {
		return ditDoctoReqTramOrigenSols;
	}



	public void setDitDoctoReqTramOrigenSols(List<DitDoctoReqTramOrigenSol> ditDoctoReqTramOrigenSols) {
		this.ditDoctoReqTramOrigenSols = ditDoctoReqTramOrigenSols;
	}
	**/


	public List<DitDocumentoProbatorio> getDitDocumentoProbatorios() {
		return ditDocumentoProbatorios;
	}



	public void setDitDocumentoProbatorios(
			List<DitDocumentoProbatorio> ditDocumentoProbatorios) {
		this.ditDocumentoProbatorios = ditDocumentoProbatorios;
	}



	public List<DitDoctoReqTramite> getDitDoctoReqTramites() {
		return ditDoctoReqTramites;
	}



	public void setDitDoctoReqTramites(List<DitDoctoReqTramite> ditDoctoReqTramites) {
		this.ditDoctoReqTramites = ditDoctoReqTramites;
	}



	/**
	 * 
	 */
	public DitDocumentoPorTipo() {
		super();
	}



	public long getCveIdDoctoProbPorTipo() {
		return cveIdDoctoProbPorTipo;
	}



	public void setCveIdDoctoProbPorTipo(long cveIdDoctoProbPorTipo) {
		this.cveIdDoctoProbPorTipo = cveIdDoctoProbPorTipo;
	}



	/**
	 * @return the dicDocumento
	 */
	public DicDocumento getDicDocumento() {
		return dicDocumento;
	}

	/**
	 * @param dicDocumento the dicDocumento to set
	 */
	public void setDicDocumento(DicDocumento dicDocumento) {
		this.dicDocumento = dicDocumento;
	}

	/**
	 * @return the dicTipoDocumentoProbatorio
	 */
	public DicTipoDocumentoProbatorio getDicTipoDocumentoProbatorio() {
		return dicTipoDocumentoProbatorio;
	}

	/**
	 * @param dicTipoDocumentoProbatorio the dicTipoDocumentoProbatorio to set
	 */
	public void setDicTipoDocumentoProbatorio(
			DicTipoDocumentoProbatorio dicTipoDocumentoProbatorio) {
		this.dicTipoDocumentoProbatorio = dicTipoDocumentoProbatorio;
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
	
	

}
