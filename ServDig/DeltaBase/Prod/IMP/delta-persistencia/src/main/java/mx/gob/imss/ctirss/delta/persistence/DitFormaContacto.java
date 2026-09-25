package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_FORMA_CONTACTO database table.
 * 
 */
@Entity
@Table(name="DIT_FORMA_CONTACTO")
public class DitFormaContacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_FORMA_CONTACTO_GENERATOR", sequenceName = "SEQ_DITFORMACONTACTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_FORMA_CONTACTO_GENERATOR")
	@Column(name="CVE_ID_FORMA_CONTACTO", nullable=false, precision=22)
	private Long cveIdFormaContacto;

	@Column(name="DES_FORMA_CONTACTO", length=255)
	private String desFormaContacto;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTipoContacto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_CONTACTO", nullable=false)
	private DitTipoContacto ditTipoContacto;

	//bi-directional many-to-one association to DitPatSujObligContacto
	@OneToMany(mappedBy="ditFormaContacto")
	private List<DitPatSujObligContacto> ditPatSujObligContactos;

	//bi-directional many-to-one association to DitPersonafContacto
	@OneToMany(mappedBy="ditFormaContacto", cascade = CascadeType.REMOVE)
	private List<DitPersonafContacto> ditPersonafContactos;

	//bi-directional many-to-one association to DitPersonamContacto
	@OneToMany(mappedBy="ditFormaContacto", cascade = CascadeType.REMOVE)
	private List<DitPersonamContacto> ditPersonamContactos;
	
	
	
	//bi-directional many-to-one association to DitCentroTrabajoContacto
	@OneToMany(mappedBy="ditFormaContacto")
	private List<DitCentroTrabajoContacto> ditCentroTrabajoContactos;

	//bi-directional many-to-one association to DitRepresentanteLegalContac
	@OneToMany(mappedBy="ditFormaContacto")
	private List<DitRepresentanteLegalContac> ditRepresentanteLegalContacs;

	//bi-directional many-to-one association to DitSocioContacto
	@OneToMany(mappedBy="ditFormaContacto")
	private List<DitSocioContacto> ditSocioContactos;
	
	//bi-directional many-to-one association to DitPersonaFContactoFiscal
	@OneToMany(mappedBy="ditFormaContacto", cascade = CascadeType.REMOVE)
	private List<DitPersonaFContactoFiscal> ditPersonaFContactoFiscales;
	
	//bi-directional many-to-one association to DitPersonaMContactoFiscal
	@OneToMany(mappedBy="ditFormaContacto", cascade = CascadeType.REMOVE)
	private List<DitPersonaMContactoFiscal> ditPersonaMContactoFiscales;
	
    public DitFormaContacto() {
    }

	public Long getCveIdFormaContacto() {
		return this.cveIdFormaContacto;
	}

	public void setCveIdFormaContacto(Long cveIdFormaContacto) {
		this.cveIdFormaContacto = cveIdFormaContacto;
	}

	public String getDesFormaContacto() {
		return this.desFormaContacto;
	}

	public void setDesFormaContacto(String desFormaContacto) {
		this.desFormaContacto = desFormaContacto;
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

	public DitTipoContacto getDitTipoContacto() {
		return this.ditTipoContacto;
	}

	public void setDitTipoContacto(DitTipoContacto ditTipoContacto) {
		this.ditTipoContacto = ditTipoContacto;
	}
	
	public List<DitPatSujObligContacto> getDitPatSujObligContactos() {
		return this.ditPatSujObligContactos;
	}

	public void setDitPatSujObligContactos(List<DitPatSujObligContacto> ditPatSujObligContactos) {
		this.ditPatSujObligContactos = ditPatSujObligContactos;
	}
	
	public List<DitPersonafContacto> getDitPersonafContactos() {
		return this.ditPersonafContactos;
	}

	public void setDitPersonafContactos(List<DitPersonafContacto> ditPersonafContactos) {
		this.ditPersonafContactos = ditPersonafContactos;
	}
	
	public List<DitPersonamContacto> getDitPersonamContactos() {
		return this.ditPersonamContactos;
	}

	public void setDitPersonamContactos(List<DitPersonamContacto> ditPersonamContactos) {
		this.ditPersonamContactos = ditPersonamContactos;
	}
	
	public List<DitCentroTrabajoContacto> getDitCentroTrabajoContactos() {
		return ditCentroTrabajoContactos;
	}

	public void setDitCentroTrabajoContactos(
			List<DitCentroTrabajoContacto> ditCentroTrabajoContactos) {
		this.ditCentroTrabajoContactos = ditCentroTrabajoContactos;
	}

	public List<DitRepresentanteLegalContac> getDitRepresentanteLegalContacs() {
		return ditRepresentanteLegalContacs;
	}

	public void setDitRepresentanteLegalContacs(
			List<DitRepresentanteLegalContac> ditRepresentanteLegalContacs) {
		this.ditRepresentanteLegalContacs = ditRepresentanteLegalContacs;
	}

	

	@Override
	public String toString() {
	    return new StringBuilder()
	        .append("CveForma: ").append(cveIdFormaContacto).append(" cveTipoContacto: ")
	        .append(ditTipoContacto == null ? null : ditTipoContacto.getCveIdTipoContacto())
	        .toString();
	}

	public List<DitSocioContacto> getDitSocioContactos() {
		return ditSocioContactos;
	}

	public void setDitSocioContactos(List<DitSocioContacto> ditSocioContactos) {
		this.ditSocioContactos = ditSocioContactos;
	}

	public List<DitPersonaFContactoFiscal> getDitPersonaFContactoFiscales() {
		return ditPersonaFContactoFiscales;
	}

	public void setDitPersonaFContactoFiscales(
			List<DitPersonaFContactoFiscal> ditPersonaFContactoFiscales) {
		this.ditPersonaFContactoFiscales = ditPersonaFContactoFiscales;
	}
	
	public List<DitPersonaMContactoFiscal> getDitPersonaMContactoFiscales() {
		return ditPersonaMContactoFiscales;
	}

	public void setDitPersonaMContactoFiscales(
			List<DitPersonaMContactoFiscal> ditPersonaMContactoFiscales) {
		this.ditPersonaMContactoFiscales = ditPersonaMContactoFiscales;
	}	
}