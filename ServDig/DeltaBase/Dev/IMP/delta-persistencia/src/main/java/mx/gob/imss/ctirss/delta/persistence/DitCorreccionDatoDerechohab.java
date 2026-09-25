package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_CORRECCION_DATO_DERECHOHAB database table.
 * 
 */
@Entity
@Table(name="DIT_CORRECCION_DATO_DERECHOHAB")
public class DitCorreccionDatoDerechohab implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -452837638963926933L;

	@Id
	@SequenceGenerator(name = "SEQ_DITCORRECCIONDATODERECHOHA", sequenceName = "SEQ_DITCORRECCIONDATODERECHOHA")
    @GeneratedValue(generator = "SEQ_DITCORRECCIONDATODERECHOHA")
	@Column(name="CVE_ID_CORRECCION_DER")
	private Long cveIdCorreccionDer;
	
	
	

	//bi-directional many-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch = FetchType.LAZY)
  	@JoinColumn(name="CVE_ID_TRAMITE")
  	private DitTramite ditTramite;	
  	
  	public Long getCveIdCorreccionDer() {
		return cveIdCorreccionDer;
	}

	public void setCveIdCorreccionDer(Long cveIdCorreccionDer) {
		this.cveIdCorreccionDer = cveIdCorreccionDer;
	}

	@Column(name="CVE_CURP", length=18)
	private String cveCurp;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NOM_NOMBRE", length=255)
	private String nomNombre;

	@Column(name="NOM_PRIMER_APELLIDO", length=255)
	private String nomPrimerApellido;

	@Column(name="NOM_SEGUNDO_APELLIDO", length=255)
	private String nomSegundoApellido;

	//bi-directional many-to-one association to DicEstadoCivil
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ESTADO_CIVIL")
	private DicEstadoCivil dicEstadoCivil;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CALIDAD_PARENTESCO")
	private DicCalidadParentesco dicCalidadParentesco;

	//bi-directional many-to-one association to DicSexo
    @ManyToOne
	@JoinColumn(name="CVE_ID_SEXO")
	private DicSexo dicSexo;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DOMICILIO_ID")
    private DgDomicilioGeografico dgDomicilioGeografico;
    
    @ManyToOne
    @JoinColumn(name = "CVE_ENT")
    private DgCatEstado dgCatEstado;
   
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedico;

    
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}

	
	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
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

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPrimerApellido() {
		return this.nomPrimerApellido;
	}

	public void setNomPrimerApellido(String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}

	public String getNomSegundoApellido() {
		return this.nomSegundoApellido;
	}

	public void setNomSegundoApellido(String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}

	public DicEstadoCivil getDicEstadoCivil() {
		return this.dicEstadoCivil;
	}

	public void setDicEstadoCivil(DicEstadoCivil dicEstadoCivil) {
		this.dicEstadoCivil = dicEstadoCivil;
	}
	
	public DicSexo getDicSexo() {
		return this.dicSexo;
	}

	public DicCalidadParentesco getDicCalidadParentesco() {
		return dicCalidadParentesco;
	}

	public void setDicCalidadParentesco(DicCalidadParentesco dicCalidadParentesco) {
		this.dicCalidadParentesco = dicCalidadParentesco;
	}

	public void setDicSexo(DicSexo dicSexo) {
		this.dicSexo = dicSexo;
	}

	public DgCatEstado getDgCatEstado() {
		return dgCatEstado;
	}

	public void setDgCatEstado(DgCatEstado dgCatEstado) {
		this.dgCatEstado = dgCatEstado;
	}

	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedico() {
		return this.ditUmfConsTurnoMedico;
	}

	public void setDitUmfConsTurnoMedico(DitUmfConsTurnoMedico ditUmfConsTurnoMedico) {
		this.ditUmfConsTurnoMedico = ditUmfConsTurnoMedico;
	}
	

}