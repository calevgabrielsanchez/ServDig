package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERSONAM_DOM database table.
 * 
 */
@NamedQueries({ 
	@NamedQuery(name="getPersonamDom",
			query="select p from DitPersonamDom p " +
					"where p.ditPersonaMoral.cveIdPersonaMoral=:idPersona and p.dicTipoDomicilio.cveIdTipoDomicilio=:tipoDomicilio")  	
							
})
@Entity
@Table(name="DIT_PERSONAM_DOM")
public class DitPersonamDom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_PERSONAMDOM_CVEIDPERSONA_GENERATOR", sequenceName = "SEQ_DITPERSONAMDOM", allocationSize = 1)	
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONAMDOM_CVEIDPERSONA_GENERATOR")
	@Column(name="CVE_ID_PERSONAM_DOM", nullable=false, precision=22)
	private long cveIdPersonamDom;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

	//bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	//bi-directional many-to-one association to DicTipoDomicilio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_DOMICILIO", nullable=false)
	private DicTipoDomicilio dicTipoDomicilio;
	
    public DitPersonamDom() {
    }

	public DitPersonamDom(DgDomicilioGeografico dgDomicilioGeografico,
			DicTipoDomicilio dicTipoDomicilio) {
		super();
		this.dgDomicilioGeografico = dgDomicilioGeografico;
		this.dicTipoDomicilio = dicTipoDomicilio;
	}

	public long getCveIdPersonamDom() {
		return this.cveIdPersonamDom;
	}

	public void setCveIdPersonamDom(long cveIdPersonamDom) {
		this.cveIdPersonamDom = cveIdPersonamDom;
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

	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}
	
	public DicTipoDomicilio getDicTipoDomicilio() {
		return this.dicTipoDomicilio;
	}

	public void setDicTipoDomicilio(DicTipoDomicilio dicTipoDomicilio) {
		this.dicTipoDomicilio = dicTipoDomicilio;
	}	
	
}