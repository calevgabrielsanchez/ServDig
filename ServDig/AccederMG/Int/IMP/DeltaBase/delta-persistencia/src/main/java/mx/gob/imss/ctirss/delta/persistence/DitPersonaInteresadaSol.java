package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_PERSONA_INTERESADA_SOL database table.
 * 
 */

@NamedQueries({ 
	
	
})
@Entity
@Table(name="DIT_PERSONA_INTERESADA_SOL")
public class DitPersonaInteresadaSol implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_DITPERSONAINTERESADASOL", sequenceName="SEQ_DITPERSONAINTERESADASOL" )
	@GeneratedValue(generator="SEQ_DITPERSONAINTERESADASOL")
	@Column(name="CVE_ID_PER_TRAM_INTERESADA_SOL")
	private long cveIdPerTramInteresadaSol;
	

	 //bi-directional many-to-one association to DitSolicitud
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SOLICITUD" , insertable=true ,updatable=false)
	private DitSolicitud ditSolicitud;
    
  //bi-directional many-to-one association to DitPersona
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA" , insertable=true ,updatable=false)
    private DitPersona ditPersona;
    
  //bi-directional many-to-one association to DicTipoPerInteresadaSol
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TIPO_INTERESADA_SOL" , insertable=true ,updatable=false)
    private DicTipoPerInteresadaSol dicTipoPersonaInteresadaSol;

    /**
	 * 
	 */
	public DitPersonaInteresadaSol() {
		super();
	}
    
	/**
	 * @return the cveIdPerTramInteresadaSol
	 */
	public long getCveIdPerTramInteresadaSol() {
		return cveIdPerTramInteresadaSol;
	}


	/**
	 * @param cveIdPerTramInteresadaSol the cveIdPerTramInteresadaSol to set
	 */
	public void setCveIdPerTramInteresadaSol(long cveIdPerTramInteresadaSol) {
		this.cveIdPerTramInteresadaSol = cveIdPerTramInteresadaSol;
	}


	/**
	 * @return the ditSolicitud
	 */
	public DitSolicitud getDitSolicitud() {
		return ditSolicitud;
	}

	/**
	 * @param ditSolicitud the ditSolicitud to set
	 */
	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	/**
	 * @return the ditPersona
	 */
	public DitPersona getDitPersona() {
		return ditPersona;
	}

	/**
	 * @param ditPersona the ditPersona to set
	 */
	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	/**
	 * @return the dicTipoPersonaInteresadaSol
	 */
	public DicTipoPerInteresadaSol getDicTipoPersonaInteresadaSol() {
		return dicTipoPersonaInteresadaSol;
	}

	/**
	 * @param dicTipoPersonaInteresadaSol the dicTipoPersonaInteresadaSol to set
	 */
	public void setDicTipoPersonaInteresadaSol(
			DicTipoPerInteresadaSol dicTipoPersonaInteresadaSol) {
		this.dicTipoPersonaInteresadaSol = dicTipoPersonaInteresadaSol;
	}

		    
}
