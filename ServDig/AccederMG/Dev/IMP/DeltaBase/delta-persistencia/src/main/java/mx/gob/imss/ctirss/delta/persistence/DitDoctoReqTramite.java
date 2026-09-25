package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_DOCTO_REQ_TRAMITE database table.
 * 
 */

@NamedQueries({ 
	/** query que recupera los tipos de documentos para un tramite con la maraca si es opcional o requerido
	@NamedQuery(name = "findListTipoDocomentoTramite", 
			query = "select dtipo.cveIdDoctoProbPorTipo , " + 
	                "case " +  
	                	"when sum (dtra.indDoctoOpcional) = count (dtipo.cveIdDoctoProbPorTipo) "+
	                     "then 1 "+
		                 "else 0 " +
		                 "end as IND_DOCTO_OPCIONAL "+
		                 "from DitDoctoReqTramite dtra , DitDocumentoPorTipo dtipo "+
		                 "where dtra.dicTipoTramite.cveIdTipoTramite  = :cveTramite "+
		                 "and dtra.ditDocumentoPorTipo.cveIdDoctoProbPorTipo = dtipo.cveIdDoctoProbPorTipo "+
		                 "group by dtipo.cveIdDoctoProbPorTipo ")
	*/

	})

@Entity
@Table(name="DIT_DOCTO_REQ_TRAMITE")
public class DitDoctoReqTramite implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DOCTO_REQ_TRAMITE")
	private long cveIdDoctoReqTramite;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTROS_BAJA")
	private Date fecRegistrosBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	
	@Column(name="IND_DOCTO_REQ_CAPTURA")
	private Integer indDoctoReqCaptura;
	
	@Column(name="IND_DOCTO_OPCIONAL")
	private Integer indDoctoOpcional; 

	@Column(name="IND_DOCTO_REQ_DIGITALIZACION")
	private Integer indDoctoReqDigitalizacion; 
	
	
	//bi-directional many-to-one association to DicModulo
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_TRAMITE" , insertable=false ,updatable=false)
	private DicTipoTramite dicTipoTramite;
	
	
  //bi-directional many-to-one association to DicModulo
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCTO_PROB_POR_TIPO" , insertable=false ,updatable=false)
	private DitDocumentoPorTipo ditDocumentoPorTipo;


    
	/**
	 * @return the cveIdDoctoReqTramite
	 */
	public long getCveIdDoctoReqTramite() {
		return cveIdDoctoReqTramite;
	}


	/**
	 * @param cveIdDoctoReqTramite the cveIdDoctoReqTramite to set
	 */
	public void setCveIdDoctoReqTramite(long cveIdDoctoReqTramite) {
		this.cveIdDoctoReqTramite = cveIdDoctoReqTramite;
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
	 * @return the fecRegistrosBaja
	 */
	public Date getFecRegistrosBaja() {
		return fecRegistrosBaja;
	}


	/**
	 * @param fecRegistrosBaja the fecRegistrosBaja to set
	 */
	public void setFecRegistrosBaja(Date fecRegistrosBaja) {
		this.fecRegistrosBaja = fecRegistrosBaja;
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
	 * @return the dicTipoTramite
	 */
	public DicTipoTramite getDicTipoTramite() {
		return dicTipoTramite;
	}


	/**
	 * @param dicTipoTramite the dicTipoTramite to set
	 */
	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
	}


	public DitDocumentoPorTipo getDitDocumentoPorTipo() {
		return ditDocumentoPorTipo;
	}


	public void setDitDocumentoPorTipo(DitDocumentoPorTipo ditDocumentoPorTipo) {
		this.ditDocumentoPorTipo = ditDocumentoPorTipo;
	}


	public Integer getIndDoctoReqCaptura() {
		return indDoctoReqCaptura;
	}


	public void setIndDoctoReqCaptura(Integer indDoctoReqCaptura) {
		this.indDoctoReqCaptura = indDoctoReqCaptura;
	}


	public Integer getIndDoctoOpcional() {
		return indDoctoOpcional;
	}


	public void setIndDoctoOpcional(Integer indDoctoOpcional) {
		this.indDoctoOpcional = indDoctoOpcional;
	}



/**
	 * @return the indDoctoReqDigitalizacion
	 */
	public Integer getIndDoctoReqDigitalizacion() {
		return indDoctoReqDigitalizacion;
	}

	/**
	 * @param indDoctoReqDigitalizacion the indDoctoReqDigitalizacion to set
	 */
	public void setIndDoctoReqDigitalizacion(Integer indDoctoReqDigitalizacion) {
		this.indDoctoReqDigitalizacion = indDoctoReqDigitalizacion;
	}



    
    
    
    
    
	
	
}
