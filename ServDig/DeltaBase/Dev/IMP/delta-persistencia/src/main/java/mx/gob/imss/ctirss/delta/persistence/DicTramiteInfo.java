package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DIC_TRAMITE_INFO database table.
 * 
 */
@Entity
@Table(name="DIC_TRAMITE_INFO")
public class DicTramiteInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_TRAMITE_INFO_CVEIDTRAMITEINFO_GENERATOR", sequenceName="SEQ_DICTRAMITEINFO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_TRAMITE_INFO_CVEIDTRAMITEINFO_GENERATOR")
	@Column(name="CVE_ID_TRAMITE_INFO")
	private Long cveIdTramiteInfo;

	@Column(name="DES_INSTRUCCIONES")
	private String desInstrucciones;

	@Column(name="DES_TRAMITE")
	private String desTramite;

	@Column(name="DES_URL_WIZARD")
	private String desUrlWizard;

	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Timestamp fecRegistroActualizado;

	@Column(name="FEC_REGISTRO_ALTA")
	private Timestamp fecRegistroAlta;

	@Column(name="FEC_REGISTRO_BAJA")
	private Timestamp fecRegistroBaja;

	//bi-directional many-to-one association to DicOrigenSolicitud
    @ManyToOne
	@JoinColumn(name="CVE_ID_ORIGEN")
	private DicOrigenSolicitud dicOrigen;

	//bi-directional many-to-one association to DicTipoTramite
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_TRAMITE")
	private DicTipoTramite dicTipoTramite;

    public DicTramiteInfo() {
    }

	public Long getCveIdTramiteInfo() {
		return this.cveIdTramiteInfo;
	}

	public void setCveIdTramiteInfo(Long cveIdTramiteInfo) {
		this.cveIdTramiteInfo = cveIdTramiteInfo;
	}

	public String getDesInstrucciones() {
		return this.desInstrucciones;
	}

	public void setDesInstrucciones(String desInstrucciones) {
		this.desInstrucciones = desInstrucciones;
	}

	public String getDesTramite() {
		return this.desTramite;
	}

	public void setDesTramite(String desTramite) {
		this.desTramite = desTramite;
	}

	public String getDesUrlWizard() {
		return this.desUrlWizard;
	}

	public void setDesUrlWizard(String desUrlWizard) {
		this.desUrlWizard = desUrlWizard;
	}

	public Timestamp getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Timestamp fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Timestamp getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Timestamp fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Timestamp getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Timestamp fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DicOrigenSolicitud getDicOrigen() {
		return this.dicOrigen;
	}

	public void setDicOrigen(DicOrigenSolicitud dicOrigen) {
		this.dicOrigen = dicOrigen;
	}
	
	public DicTipoTramite getDicTipoTramite() {
		return this.dicTipoTramite;
	}

	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
	}
	
}