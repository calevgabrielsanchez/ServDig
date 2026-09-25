package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERSONAF_DOM database table.
 * 
 */
@NamedQueries({ 
	@NamedQuery(name="getPersonafDom",
			query="select p from DitPersonafDom p " +
					"where p.ditPersona.cveIdPersona=:idPersona and p.dicTipoDomicilio.cveIdTipoDomicilio=:tipoDomicilio order by p.cveIdPersonafDom")  	
							
})
@Entity
@Table(name="DIT_PERSONAF_DOM")
public class DitPersonafDom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_PERSONAFDOM_CVEIDPERSONA_GENERATOR", sequenceName = "SEQ_DITPERSONAFDOM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONAFDOM_CVEIDPERSONA_GENERATOR")
	@Column(name="CVE_ID_PERSONAF_DOM", nullable=false, precision=22)
	private Long cveIdPersonafDom;

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

    //bi-directional many-to-one association to DitPersona
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA")
    private DitPersona ditPersona;

    //bi-directional many-to-one association to DitPersonaView
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA", insertable = false, updatable = false)
    private DitPersonaView ditPersonaView;

	//bi-directional many-to-one association to DicTipoDomicilio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_DOMICILIO", nullable=false)
	private DicTipoDomicilio dicTipoDomicilio;

    public DitPersonafDom() {
    }

    public DitPersonafDom(Long cveIdPersonafDom, DgDomicilioGeografico dgDomicilioGeografico,
			DicTipoDomicilio dicTipoDomicilio) {
		super();
		this.cveIdPersonafDom = cveIdPersonafDom;
		this.dgDomicilioGeografico = dgDomicilioGeografico;
		this.dicTipoDomicilio = dicTipoDomicilio;
	}
    
	public DitPersonafDom(DgDomicilioGeografico dgDomicilioGeografico,
			DicTipoDomicilio dicTipoDomicilio) {
		super();
		this.dgDomicilioGeografico = dgDomicilioGeografico;
		this.dicTipoDomicilio = dicTipoDomicilio;
	}

	public Long getCveIdPersonafDom() {
		return this.cveIdPersonafDom;
	}

	public void setCveIdPersonafDom(Long cveIdPersonafDom) {
		this.cveIdPersonafDom = cveIdPersonafDom;
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
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
    public DitPersonaView getDitPersonaView() {
        return ditPersonaView;
    }

    public void setDitPersonaView(DitPersonaView ditPersonaView) {
        this.ditPersonaView = ditPersonaView;
    }
    
	public DicTipoDomicilio getDicTipoDomicilio() {
		return this.dicTipoDomicilio;
	}

	public void setDicTipoDomicilio(DicTipoDomicilio dicTipoDomicilio) {
		this.dicTipoDomicilio = dicTipoDomicilio;
	}	
}