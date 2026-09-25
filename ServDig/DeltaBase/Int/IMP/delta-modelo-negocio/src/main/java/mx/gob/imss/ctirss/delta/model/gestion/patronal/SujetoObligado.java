package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;


@XmlRootElement
public class SujetoObligado extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 747276845170630097L;
	private Long cveIdSujetoObligado;
	
	private EscrituraConstitutiva escrituraConstitutiva;
	private TipoPersonaFiscal tipoPersonaFiscal;
	private DomicilioFiscal domicilioFiscal;
	private RegistroSindicato registroSindicato;
	private List<RepresentanteLegal> representantesLegales;
	private RepresentanteLegal representanteLegalAux; 
	private List<Socio> socios;
	private Moral moral;
	private Fisica fisica;
	private Integer indPatronConfirmado;
	// Contiene los datos del ICA
	private ICADatosRespuesta datosICA;
	// Contiene los datos del MDM
	private MDMDatosEntrada datosMDM;
	private String tipoPoder;
	private String otroPoder;
	//contiene la descripcion de baja o huelga para un patron ya que no se cuenta con cat�logo
	private String descSituacionBaja;
	private String fechaBaja;
	
	//Atributos de apoyo para obtener detalle de Sujetos Obligados 
	private List<Long> idsModalidadesConsulta;
	private boolean obtenerRPsConBaja;
	private boolean filtroPorIdPersona;
	
	private List<PersonaAutorizada> personasAutorizadas;
	
	//Se agrega el n�mero de trabajadores en la transformaci�n de los datos generales del patr�n
	private Integer numeroTrabajadores;
	
	//Atributo para guardar valor idTipoRegPatron (Saber si es RPU)
	private Long idTipoRegPatron;
	
	private Integer indMigrDom;
	
	//Aributo Razon social SINDO
	private String razonSocialSINDO; 
	
	//se agrego en mm de movpat
	private String quienTramita;
	private String nombreQuienTramita;
	
	public Integer getIndMigrDom() {
		return indMigrDom;
	}

	public void setIndMigrDom(Integer indMigrDom) {
		this.indMigrDom = indMigrDom;
	}

	public Long getIdTipoRegPatron() {
		return idTipoRegPatron;
	}

	public void setIdTipoRegPatron(Long idTipoRegPatron) {
		this.idTipoRegPatron = idTipoRegPatron;
	}
	
	public SujetoObligado() {
		
	}
	
	public SujetoObligado(Long cveIdSujetoObligado,Long idPersonaFisica, Long idPersona, String rfc, String nombre,String primerA, String segundoA){
		this.cveIdSujetoObligado = cveIdSujetoObligado;
		
		fisica = new Fisica();
		fisica.setCveFisica(idPersonaFisica);
		fisica.setIdPersona(idPersona);
		fisica.setRfc(rfc);
		fisica.setNombre(nombre);
		fisica.setPrimerApellido(primerA);
		fisica.setSegundoApellido(segundoA);
		tipoPersonaFiscal=TipoPersonaFiscal.FISICA;
		
	}
	
	public SujetoObligado(Long cveIdSujetoObligado,Long idPersonaMoral, String rfc, String razonS) {
		this.cveIdSujetoObligado = cveIdSujetoObligado;
		moral = new Moral();
		moral.setIdPersona(idPersonaMoral);
		moral.setCveMoral(idPersonaMoral);
		moral.setRfc(rfc);
		moral.setRazonSocial(razonS);
		tipoPersonaFiscal=TipoPersonaFiscal.MORAL;
	}
	
	public MDMDatosEntrada getDatosMDM() {
		return datosMDM;
	}

	public void setDatosMDM(MDMDatosEntrada datosMDM) {
		this.datosMDM = datosMDM;
	}

	private Socio socioAux;
	private String nombreComercial;
	private PersonaAutorizada personaAutorizada;
	private CentroTrabajo cntroTrabajo;
	private List<FormaContacto> formasContacto;

	private Municipio patronRelacionado;
	private String numeroRegistroPatronal;
	private List<SujetoObligado> sujetosObligados;

	private Proceso proceso = new Proceso();
	private Clasificacion clasificacion = new Clasificacion();
	private List<Bien> bienes = new ArrayList<Bien>();
	private List<Personal> personal = new ArrayList<Personal>();
	private List<Producto> productos = new ArrayList<Producto>();
	private List<MaquinariaEquipo> equipos = new ArrayList<MaquinariaEquipo>();
	private List<EquipoTransporte> equiposTransporte = new ArrayList<EquipoTransporte>();
	private List<MateriaPrima> materiaPrimaMateriales = new ArrayList<MateriaPrima>();
	private Subdelegacion subdelegacion;
	private MunicipioIMSS municipioIMSS;
	private String stringClasificacion;
	
	
	
	public MunicipioIMSS getMunicipioIMSS() {
		return municipioIMSS;
	}

	public void setMunicipioIMSS(MunicipioIMSS municipioIMSS) {
		this.municipioIMSS = municipioIMSS;
	}

	private Integer cuentaConTransporte = new Integer(0);

	// DerechoHabientes
	protected Modalidad modalidad;
	protected Date fechaAlta;

	private String desAfectacion;
	
	private String desUsosBienes;
	
	private String digVerificador;
	/**
	 * @return the modalidad
	 */
	public Modalidad getModalidad() {
		return modalidad;
	}

	/**
	 * @param modalidad
	 *            the modalidad to set
	 */
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta
	 *            the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getCveIdSujetoObligado() {
		return cveIdSujetoObligado;
	}

	public void setCveIdSujetoObligado(Long registroPatronal) {
		this.cveIdSujetoObligado = registroPatronal;
	}

	public EscrituraConstitutiva getEscrituraConstitutiva() {
		return escrituraConstitutiva;
	}

	public void setEscrituraConstitutiva(
			EscrituraConstitutiva escrituraConstitutiva) {
		this.escrituraConstitutiva = escrituraConstitutiva;
	}

	public Clasificacion getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(Clasificacion clasificacion) {
		this.clasificacion = clasificacion;
	}

	public TipoPersonaFiscal getTipoPersonaFiscal() {
		return tipoPersonaFiscal;
	}

	public void setTipoPersonaFiscal(TipoPersonaFiscal tipoPersonaFiscal) {
		this.tipoPersonaFiscal = tipoPersonaFiscal;
	}

	public DomicilioFiscal getDomicilioFiscal() {
		return domicilioFiscal;
	}

	public void setDomicilioFiscal(DomicilioFiscal domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}

	public RegistroSindicato getRegistroSindicato() {
		return registroSindicato;
	}

	public void setRegistroSindicato(RegistroSindicato registroSindicato) {
		this.registroSindicato = registroSindicato;
	}

	public List<RepresentanteLegal> getRepresentantesLegales() {
		return representantesLegales;
	}

	public void setRepresentantesLegales(
			List<RepresentanteLegal> representantesLegales) {
		this.representantesLegales = representantesLegales;
	}

	public List<Socio> getSocios() {
		return socios;
	}

	public void setSocios(List<Socio> socios) {
		this.socios = socios;
	}

	public Moral getMoral() {
		return moral;
	}

	public void setMoral(Moral moral) {
		this.moral = moral;
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}

	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}

	public CentroTrabajo getCntroTrabajo() {
		return cntroTrabajo;
	}

	public void setCntroTrabajo(CentroTrabajo cntroTrabajo) {
		this.cntroTrabajo = cntroTrabajo;
	}

	public List<FormaContacto> getFormasContacto() {
		return formasContacto;
	}

	public void setFormasContacto(List<FormaContacto> formasContacto) {
		this.formasContacto = formasContacto;
	}

	public Municipio getPatronRelacionado() {
		return patronRelacionado;
	}

	public void setPatronRelacionado(Municipio patronRelacionado) {
		this.patronRelacionado = patronRelacionado;
	}

	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}

	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}

	public List<SujetoObligado> getSujetosObligados() {
		return sujetosObligados;
	}

	public void setSujetosObligados(List<SujetoObligado> sujetosObligados) {
		this.sujetosObligados = sujetosObligados;
	}

	/**
	 * @return the productos
	 */
	public List<Producto> getProductos() {
		if (productos != null) {
			int index = 0;

			for (Producto productoModel : productos) {
				if (productoModel.getIdVista() == null) {
					productoModel.setIdVista(Long.valueOf(index + 1));
				}
				index++;
			}
		}

		return productos;
	}

	/**
	 * @param productos
	 *            the productos to set
	 */
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	/**
	 * @return the materiaPrimaMateriales
	 */
	public List<MateriaPrima> getMateriaPrimaMateriales() {
		if (materiaPrimaMateriales != null) {
			int index = 0;

			for (MateriaPrima materiaPrimaModel : materiaPrimaMateriales) {
				if (materiaPrimaModel.getIdVista() == null) {
					materiaPrimaModel.setIdVista(Long.valueOf(index + 1));
				}
				index++;
			}
		}

		return materiaPrimaMateriales;
	}

	/**
	 * @param materiaPrimaMateriales
	 *            the materiaPrimaMateriales to set
	 */
	public void setMateriaPrimaMateriales(
			List<MateriaPrima> materiaPrimaMateriales) {
		this.materiaPrimaMateriales = materiaPrimaMateriales;
	}

	/**
	 * @return the equipos
	 */
	public List<MaquinariaEquipo> getEquipos() {
		if (equipos != null) {
			int index = 0;

			for (MaquinariaEquipo equipoModel : equipos) {
				if (equipoModel.getIdVista() == null) {
					equipoModel.setIdVista(Long.valueOf(index + 1));
				}
				index++;
			}
		}

		return equipos;
	}

	/**
	 * @param equipos
	 *            the equipos to set
	 */
	public void setEquipos(List<MaquinariaEquipo> equipos) {
		this.equipos = equipos;
	}

	/**
	 * @return the equiposTransporte
	 */
	public List<EquipoTransporte> getEquiposTransporte() {
		if (equiposTransporte != null) {
			int index = 0;

			for (EquipoTransporte equipoTransporteModel : equiposTransporte) {
				if (equipoTransporteModel.getIdVista() == null) {
					equipoTransporteModel.setIdVista(Long.valueOf(index + 1));
				}
				index++;
			}
		}

		return equiposTransporte;
	}

	/**
	 * @param equiposTransporte
	 *            the equiposTransporte to set
	 */
	public void setEquiposTransporte(List<EquipoTransporte> equiposTransporte) {
		this.equiposTransporte = equiposTransporte;
	}

	/**
	 * @return the proceso
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/**
	 * @param proceso
	 *            the proceso to set
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return the personal
	 */
	public List<Personal> getPersonal() {
		if (personal != null) {
			int index = 0;

			for (Personal personalModel : personal) {
				if (personalModel.getIdVista() == null) {
					personalModel.setIdVista(Long.valueOf(index + 1));
				}
				index++;
			}
		}

		return personal;
	}

	/**
	 * @param personal
	 *            the personal to set
	 */
	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}

	/**
	 * @return the bienes
	 */
	public List<Bien> getBienes() {
		if (bienes != null) {
			int index = 0;

			for (Bien bienModel : bienes) {
				if (bienModel.getIdVista() == null) {
					bienModel.setIdVista(Long.valueOf(index + 1));
				}
				index++;
			}
		}

		return bienes;
	}

	/**
	 * @param bienes
	 *            the bienes to set
	 */
	public void setBienes(List<Bien> bienes) {
		this.bienes = bienes;
	}

	/**
	 * @return the subdelegacion
	 */
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	/**
	 * @param subdelegacion
	 *            the subdelegacion to set
	 */
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public Integer getCuentaConTransporte() {
		return cuentaConTransporte;
	}

	public void setCuentaConTransporte(Integer cuentaConTransporte) {
		this.cuentaConTransporte = cuentaConTransporte;
	}

	public String getDesAfectacion() {
		return desAfectacion;
	}

	public void setDesAfectacion(String desAfectacion) {
		this.desAfectacion = desAfectacion;
	}

	public String getDesUsosBienes() {
		return desUsosBienes;
	}

	public void setDesUsosBienes(String desUsosBienes) {
		this.desUsosBienes = desUsosBienes;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Socio getSocioAux() {
		return socioAux;
	}

	public void setSocioAux(Socio socioAux) {
		this.socioAux = socioAux;
	}

	public RepresentanteLegal getRepresentanteLegalAux() {
		return representanteLegalAux;
	}

	public void setRepresentanteLegalAux(RepresentanteLegal representanteLegalAux) {
		this.representanteLegalAux = representanteLegalAux;
	}
	
	public String getDigVerificador() {
		return digVerificador;
	}

	public void setDigVerificador(String digVerificador) {
		this.digVerificador = digVerificador;
	}
	
	public ICADatosRespuesta getDatosICA() {
		return datosICA;
	}

	public void setDatosICA(ICADatosRespuesta datosICA) {
		this.datosICA = datosICA;
	}
	
	public String getStringClasificacion() {
		return stringClasificacion;
	}

	public void setStringClasificacion(String stringClasificacion) {
		this.stringClasificacion = stringClasificacion;
	}
	
	public Integer getIndPatronConfirmado() {
		return indPatronConfirmado;
	}

	public void setIndPatronConfirmado(Integer indPatronConfirmado) {
		this.indPatronConfirmado = indPatronConfirmado;
	}
	
	public String getDescSituacionBaja() {
		return descSituacionBaja;
	}

	public void setDescSituacionBaja(String descSituacionBaja) {
		this.descSituacionBaja = descSituacionBaja;
	}

	public List<Long> getIdsModalidadesConsulta() {
		return idsModalidadesConsulta;
	}

	public void setIdsModalidadesConsulta(List<Long> idsModalidadesConsulta) {
		this.idsModalidadesConsulta = idsModalidadesConsulta;
	}
	
	public boolean getObtenerRPsConBaja() {
		return obtenerRPsConBaja;
	}

	public void setObtenerRPsConBaja(boolean obtenerRPsConBaja) {
		this.obtenerRPsConBaja = obtenerRPsConBaja;
	}

	public boolean getFiltroPorIdPersona() {
		return filtroPorIdPersona;
	}

	public void setFiltroPorIdPersona(boolean filtroPorIdPersona) {
		this.filtroPorIdPersona = filtroPorIdPersona;
	}

	public List<PersonaAutorizada> getPersonasAutorizadas() {
		return personasAutorizadas;
	}
	
	public String getTipoPoder() {
		return tipoPoder;
	}

	public void setTipoPoder(String tipoPoder) {
		this.tipoPoder = tipoPoder;
	}

	public void setPersonasAutorizadas(List<PersonaAutorizada> personasAutorizadas) {
		this.personasAutorizadas = personasAutorizadas;
	}
	
	public String getOtroPoder() {
		return otroPoder;
	}

	public void setOtroPoder(String otroPoder) {
		this.otroPoder = otroPoder;
	}
	
	public Integer getNumeroTrabajadores() {
		return numeroTrabajadores;
	}

	public void setNumeroTrabajadores(Integer numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SujetoObligado [cveIdSujetoObligado=");
		builder.append(cveIdSujetoObligado);
		builder.append(", escrituraConstitutiva=");
		builder.append(escrituraConstitutiva);
		builder.append(", tipoPersonaFiscal=");
		builder.append(tipoPersonaFiscal);
		builder.append(", domicilioFiscal=");
		builder.append(domicilioFiscal);
		builder.append(", registroSindicato=");
		builder.append(registroSindicato);
		builder.append(", representantesLegales=");
		builder.append(representantesLegales);
		builder.append(", representanteLegalAux=");
		builder.append(representanteLegalAux);
		builder.append(", socios=");
		builder.append(socios);
		builder.append(", moral=");
		builder.append(moral);
		builder.append(", fisica=");
		builder.append(fisica);
		builder.append(", socioAux=");
		builder.append(socioAux);
		builder.append(", nombreComercial=");
		builder.append(nombreComercial);
		builder.append(", personaAutorizada=");
		builder.append(personaAutorizada);
		builder.append(", cntroTrabajo=");
		builder.append(cntroTrabajo);
		builder.append(", formasContacto=");
		builder.append(formasContacto);
		builder.append(", patronRelacionado=");
		builder.append(patronRelacionado);
		builder.append(", numeroRegistroPatronal=");
		builder.append(numeroRegistroPatronal);
		builder.append(", sujetosObligados=");
		builder.append(sujetosObligados);
		builder.append(", proceso=");
		builder.append(proceso);
		builder.append(", clasificacion=");
		builder.append(clasificacion);
		builder.append(", bienes=");
		builder.append(bienes);
		builder.append(", personal=");
		builder.append(personal);
		builder.append(", productos=");
		builder.append(productos);
		builder.append(", equipos=");
		builder.append(equipos);
		builder.append(", equiposTransporte=");
		builder.append(equiposTransporte);
		builder.append(", materiaPrimaMateriales=");
		builder.append(materiaPrimaMateriales);
		builder.append(", subdelegacion=");
		builder.append(subdelegacion);
		builder.append(", cuentaConTransporte=");
		builder.append(cuentaConTransporte);
		builder.append(", modalidad=");
		builder.append(modalidad);
		builder.append(", fechaAlta=");
		builder.append(fechaAlta);
		builder.append(", desAfectacion=");
		builder.append(desAfectacion);
		builder.append(", desUsosBienes=");
		builder.append(desUsosBienes);
		builder.append(", descSituacionBaja=");
		builder.append(descSituacionBaja);
		builder.append(", razonSocialSINDO=");
		builder.append(razonSocialSINDO);
		builder.append(", fechaBaja=");
		builder.append(fechaBaja);
		builder.append(", quienTramita=");
		builder.append(quienTramita);
		builder.append(", nombreQuienTramita=");
		builder.append(nombreQuienTramita);
		
		builder.append("]");
		return builder.toString();
	}

	public String getRazonSocialSINDO() {
		return razonSocialSINDO;
	}

	public void setRazonSocialSINDO(String razonSocialSINDO) {
		this.razonSocialSINDO = razonSocialSINDO;
	}

	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public String getQuienTramita() {
		return quienTramita;
	}

	public void setQuienTramita(String quienTramita) {
		this.quienTramita = quienTramita;
	}

	public String getNombreQuienTramita() {
		return nombreQuienTramita;
	}

	public void setNombreQuienTramita(String nombreQuienTramita) {
		this.nombreQuienTramita = nombreQuienTramita;
	}
	
}
