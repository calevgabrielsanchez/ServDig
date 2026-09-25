package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_DOMICILIO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_DOMICILIO")
public class DicTipoDomicilio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_DOMICILIO", nullable=false, precision=22)
	private Long cveIdTipoDomicilio;

	@Column(name="DES_TIPO_DOMICILIO", length=255)
	private String desTipoDomicilio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicTipoPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_PERSONA")
	private DicTipoPersona dicTipoPersona;

	//bi-directional many-to-one association to DitPatSujObligDomicilio
	@OneToMany(mappedBy="dicTipoDomicilio")
	private List<DitPatSujObligDomicilio> ditPatSujObligDomicilios;

	//bi-directional many-to-one association to DitPersonafDom
	@OneToMany(mappedBy="dicTipoDomicilio")
	private List<DitPersonafDom> ditPersonafDoms;

    public DicTipoDomicilio() {
    }

	public Long getCveIdTipoDomicilio() {
		return this.cveIdTipoDomicilio;
	}

	public void setCveIdTipoDomicilio(Long cveIdTipoDomicilio) {
		this.cveIdTipoDomicilio = cveIdTipoDomicilio;
	}

	public String getDesTipoDomicilio() {
		return this.desTipoDomicilio;
	}

	public void setDesTipoDomicilio(String desTipoDomicilio) {
		this.desTipoDomicilio = desTipoDomicilio;
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

	public DicTipoPersona getDicTipoPersona() {
		return this.dicTipoPersona;
	}

	public void setDicTipoPersona(DicTipoPersona dicTipoPersona) {
		this.dicTipoPersona = dicTipoPersona;
	}
	
	public List<DitPatSujObligDomicilio> getDitPatSujObligDomicilios() {
		return this.ditPatSujObligDomicilios;
	}

	public void setDitPatSujObligDomicilios(List<DitPatSujObligDomicilio> ditPatSujObligDomicilios) {
		this.ditPatSujObligDomicilios = ditPatSujObligDomicilios;
	}
	
	public List<DitPersonafDom> getDitPersonafDoms() {
		return this.ditPersonafDoms;
	}

	public void setDitPersonafDoms(List<DitPersonafDom> ditPersonafDoms) {
		this.ditPersonafDoms = ditPersonafDoms;
	}
	
}