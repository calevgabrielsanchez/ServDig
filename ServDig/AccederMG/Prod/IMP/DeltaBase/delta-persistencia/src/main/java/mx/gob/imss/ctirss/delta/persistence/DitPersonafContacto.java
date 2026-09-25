package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERSONAF_CONTACTO database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONAF_CONTACTO")
public class DitPersonafContacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_PERSONAF_CONTACTO_GENERATOR", sequenceName = "SEQ_DITPERSONAFCONTACTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONAF_CONTACTO_GENERATOR")
	@Column(name="CVE_ID_PERSONAF_CONTACTO", nullable=false, precision=22)
	private Long cveIdPersonafContacto;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    //bi-directional many-to-one association to DitPersona
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA")
    private DitPersona ditPersona;

    //bi-directional many-to-one association to DitPersonaView
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA", insertable=false, updatable=false)
    private DitPersonaView ditPersonaView;

	//bi-directional many-to-one association to DitFormaContacto
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name="CVE_ID_FORMA_CONTACTO")
	private DitFormaContacto ditFormaContacto;

    public DitPersonafContacto() {
    }

	public Long getCveIdPersonafContacto() {
		return this.cveIdPersonafContacto;
	}

	public void setCveIdPersonafContacto(Long cveIdPersonafContacto) {
		this.cveIdPersonafContacto = cveIdPersonafContacto;
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

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	public DitFormaContacto getDitFormaContacto() {
		return this.ditFormaContacto;
	}

	public void setDitFormaContacto(DitFormaContacto ditFormaContacto) {
		this.ditFormaContacto = ditFormaContacto;
	}

    public DitPersonaView getDitPersonaView() {
        return ditPersonaView;
    }

    public void setDitPersonaView(DitPersonaView ditPersonaView) {
        this.ditPersonaView = ditPersonaView;
    }
	
}