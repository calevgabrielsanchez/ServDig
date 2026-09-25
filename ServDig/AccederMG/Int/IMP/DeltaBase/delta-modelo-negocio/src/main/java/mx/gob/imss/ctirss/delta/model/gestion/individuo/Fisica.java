package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.model.xmlAdapters.DateAdapter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Fisica extends Persona implements Serializable {

	static {
		LOG = LoggerFactory.getLogger(Fisica.class);
	}
	
    private static final Logger LOG;

    private static final long serialVersionUID = 7542724168099821641L;
    
    @XmlAttribute
    private Long cveFisica;
    @XmlAttribute
	private String nombre;
    @XmlAttribute
	private String primerApellido;
    @XmlAttribute
	private String segundoApellido;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
	private Date fechaNacimiento;
	private EntidadFederativa lugarNacimiento;
	@XmlAttribute
	private Date fechaDefuncion;
	private EstadoCivil estadoCivil;
	@XmlAttribute
	private String curp;
    private Pais pais;
    private Sexo sexo;
    @XmlAttribute
    private String curpRenapo;
    @XmlAttribute
    private String fechaNacimientoFormateada;
    @XmlAttribute
    private Date fechaRegistro;
    @XmlAttribute
    private Date fechaBaja;
    @XmlAttribute
    private Date fechaModificacion;
    @XmlAttribute
    private String altaEnImss;
    @XmlAttribute
    private Integer numeroLineaArchivo;
    @XmlAttribute
    private String busqAprox;
    @XmlAttribute
    private String estadosFormateados;
    @XmlAttribute
    private String subEstadosFormateados;
    @XmlAttribute
    private Integer mesRegistroNac;
    @XmlAttribute
    private Integer anioRegistroNac;
    
    @XmlAttribute//Se agrega para probar Asegurados en Ventanilla
    private String nombreCompleto;
    
    
	@XmlAttribute
    private String nss;
	private Long cveIdAsignacionNSS;
   
	private String estatusRenapo;
    private String cveEstatusRenapo;
    
    private List<SituacionSAT> situacionesSAT;
    private DatosPersonaSAT datosPersonaSAT;
	
    // Grupo de documentos probatorios
    private CURP documentoMigratorio;
    private CURP cartaNaturalizacion;
    private CURP numeroUnicoExtranjero;
    private CURP certificadoNacionalidadMexicana;
    private CURP oficioSolicitanteRefugiado;
    private CURP formaMigratoriaTurista;
    
    private UnidadMedicaFamiliar umf;
    private List<Regimen> regimenes;
    
    private String nssCifrado;
    
    private TipoPoder tipoPoder;
    
    private Long edad;
    
    private List<String> curpsHistoricas;
    
	public List<String> getCurpsHistoricas() {
		return curpsHistoricas;
	}

	public void setCurpsHistoricas(List<String> curpsHistoricas) {
		this.curpsHistoricas = curpsHistoricas;
	}

	/**
	 * Constructor por omision
	 */
	public Fisica() {
		super();
		sexo = new Sexo();
		lugarNacimiento = new EntidadFederativa();
	}
    
	public Fisica(Long idPersona, Long idPersonaFisica, String nombre, String primerApellido, String segundoApellido, String rfc, String curp) {
		super();
		sexo = new Sexo();
		lugarNacimiento = new EntidadFederativa();
		this.setTipoPersona(new TipoPersona());
		this.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		this.setIdPersona(idPersona);
		this.cveFisica=idPersonaFisica;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.setRfc(rfc);
		this.curp=curp;
	}
	
	public Fisica(Long idPersona) {
		super();
		this.setIdPersona(idPersona);
	}
	
	
	
	public Long getCveFisica() {
		return cveFisica;
	}

	public void setCveFisica(Long cveFisica) {
		this.cveFisica = cveFisica;
	}

	public String getNss(){
		return nss;
	}
	
	public void setNss(String nss){
		this.nss = nss;
		
		if (StringUtils.isNotBlank(nss)) {
			try {
				this.nssCifrado = Base64Cipher.cifrar(nss);
			} catch (InvalidKeyException e) {
				LOG.error("Error al cifrar el NSS", e);
			} catch (IllegalBlockSizeException e) {
				LOG.error("Error al cifrar el NSS", e);
			} catch (BadPaddingException e) {
				LOG.error("Error al cifrar el NSS", e);
			}
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		
		if (fechaNacimiento != null) {
			this.fechaNacimientoFormateada = DateUtils.dateToStringConFormato(fechaNacimiento, "dd/MM/yyyy");
		}
	}

	public EntidadFederativa getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(EntidadFederativa lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}

	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getCurpRenapo() {
        return curpRenapo;
    }

    public void setCurpRenapo(String curpRenapo) {
        this.curpRenapo = curpRenapo;
    }

    public String getFechaNacimientoFormateada() {
        return fechaNacimientoFormateada;
    }

    public void setFechaNacimientoFormateada(String fechaNacimientoFormateada) {
        this.fechaNacimientoFormateada = fechaNacimientoFormateada;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getAltaEnImss() {
        return altaEnImss;
    }

    public void setAltaEnImss(String altaEnImss) {
        this.altaEnImss = altaEnImss;
    }

    public Integer getNumeroLineaArchivo() {
        return numeroLineaArchivo;
    }

    public void setNumeroLineaArchivo(Integer numeroLineaArchivo) {
        this.numeroLineaArchivo = numeroLineaArchivo;
    }

    public String getBusqAprox() {
        return busqAprox;
    }

    public void setBusqAprox(String busqAprox) {
        this.busqAprox = busqAprox;
    }

    public String getEstadosFormateados() {
        return estadosFormateados;
    }

    public void setEstadosFormateados(String estadosFormateados) {
        this.estadosFormateados = estadosFormateados;
    }

    public String getSubEstadosFormateados() {
        return subEstadosFormateados;
    }

    public void setSubEstadosFormateados(String subEstadosFormateados) {
        this.subEstadosFormateados = subEstadosFormateados;
    }

    /**
     * Metodo que asigna pais a la persona fisica en caso de que no este
     * definido el pais actualmente y si lo este el lugar de nacimiento.
     */
    public void asignarPais() {
        if ((getPais() == null || getPais().getIdPais() == null) && getLugarNacimiento() != null && getLugarNacimiento().getClave() != null) {
            final Pais pais = new Pais();
            if (0 < Integer.parseInt(getLugarNacimiento().getClave()) && Integer.parseInt(getLugarNacimiento().getClave()) < ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("NE")) {
                pais.setIdPais(1); // MEXICANA
            } else if (getLugarNacimiento().getClave().equals(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("NE").toString()) 
            		|| getLugarNacimiento().getClave().equals(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("SE").toString())) {
                pais.setIdPais(2); // EXTRANJERA
            } else {
                pais.setIdPais(null);
                LOG.warn("Pais no definido!");
            }
            setPais(pais);
        }
    }

	/**
	 * @return the estatusRenapo
	 */
	public String getEstatusRenapo() {
		return estatusRenapo;
	}

	/**
	 * @param estatusRenapo the estatusRenapo to set
	 */
	public void setEstatusRenapo(String estatusRenapo) {
		this.estatusRenapo = estatusRenapo;
	}
	
	/**
	 * @return the cveEstatusRenapo
	 */
	public String getCveEstatusRenapo() {
		return cveEstatusRenapo;
	}

	/**
	 * @param cveEstatusRenapo the cveEstatusRenapo to set
	 */
	public void setCveEstatusRenapo(String cveEstatusRenapo) {
		this.cveEstatusRenapo = cveEstatusRenapo;
	}
	

	public List<SituacionSAT> getSituacionesSAT() {
		return situacionesSAT;
	}

	public void setSituacionesSAT(List<SituacionSAT> situacionesSAT) {
		this.situacionesSAT = situacionesSAT;
	}

	/**
	 * @return the datosPersonaSAT
	 */
	public DatosPersonaSAT getDatosPersonaSAT() {
		return datosPersonaSAT;
	}

	/**
	 * @param datosPersonaSAT the datosPersonaSAT to set
	 */
	public void setDatosPersonaSAT(DatosPersonaSAT datosPersonaSAT) {
		this.datosPersonaSAT = datosPersonaSAT;
	}
	
	/**
	 * @return the documentoMigratorio
	 */
	public CURP getDocumentoMigratorio() {
		return documentoMigratorio;
	}

	/**
	 * Cuando se agrega el Documento Migratorio, se busca si existe en la lista
	 * de documentos probatorios, si es así se elimina de la lista
	 * 
	 * @param documentoMigratorio the documentoMigratorio to set
	 */
	public void setDocumentoMigratorio(CURP documentoMigratorio) {
		this.documentoMigratorio = documentoMigratorio;
				
		if(documentoMigratorio != null){
			/*
			 * Se verifica si el Documento Migratorio ya existe en la lista de
			 * documentos probatorios, si existe se elimina
			 */
			ListIterator<DocumentoProbatorio> iterador = this.getDocumentosProbatorios().listIterator();
			
			DocumentoProbatorio documentoProbatorio = null;
			
			if (iterador != null){
				while(iterador.hasNext()){
			    	documentoProbatorio = (DocumentoProbatorio)iterador.next();
			    	
			    	if (documentoProbatorio instanceof CURP){
			    		CURP doctoAux = (CURP) documentoProbatorio;
						
			    		if (doctoAux.getNumTipoDocumento() != null
								&& doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor().intValue()){
							iterador.remove();
							break;
						} else if (doctoAux.getDocumentoPorTipo() != null
								&& doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId())) {
							iterador.remove();
							break;
						}
			    		
			    	}
				}
			}
			
			//AGREGA EL OBJETO CURP A LA LISTA DE DOCUMENTOS PROBATORIOS
			this.getDocumentosProbatorios().add(documentoMigratorio);	
		}
	}

	/**
	 * @return the cartaNaturalizacion
	 */
	public CURP getCartaNaturalizacion() {
		return cartaNaturalizacion;
	}

	/**
	 * Cuando se agrega la Carta de Naturalización, se busca si existe en la lista
	 * de documentos probatorios, si es así se elimina de la lista
	 * 
	 * @param cartaNaturalizacion the cartaNaturalizacion to set
	 */
	public void setCartaNaturalizacion(CURP cartaNaturalizacion) {
		this.cartaNaturalizacion = cartaNaturalizacion;
		
		if(cartaNaturalizacion != null){
			/*
			 * Se verifica si la Carta de Naturalización ya existe en la lista de
			 * documentos probatorios, si existe se elimina
			 */
			ListIterator<DocumentoProbatorio> iterador = this.getDocumentosProbatorios().listIterator();
			
			DocumentoProbatorio documentoProbatorio = null;
			
			if (iterador != null){
				while(iterador.hasNext()){
			    	documentoProbatorio = (DocumentoProbatorio)iterador.next();
			    	
			    	if (documentoProbatorio instanceof CURP){
			    		CURP doctoAux = (CURP) documentoProbatorio;
						
			    		if (doctoAux.getNumTipoDocumento() != null
								&& doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor().intValue()){
							iterador.remove();
							break;
						} else if (doctoAux.getDocumentoPorTipo() != null
								&& doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId())) {
							iterador.remove();
							break;
						}
			    		
			    	}
				}
			}
			
			//AGREGA EL OBJETO CURP A LA LISTA DE DOCUMENTOS PROBATORIOS
			this.getDocumentosProbatorios().add(cartaNaturalizacion);	
		}
	}

	/**
	 * @return the numeroUnicoExtranjero
	 */
	public CURP getNumeroUnicoExtranjero() {
		return numeroUnicoExtranjero;
	}

	/**
	 * Cuando se agrega el Número Unico de Extranjero, se busca si existe en la lista
	 * de documentos probatorios, si es así se elimina de la lista
	 * 
	 * @param numeroUnicoExtranjero the numeroUnicoExtranjero to set
	 */
	public void setNumeroUnicoExtranjero(CURP numeroUnicoExtranjero) {
		this.numeroUnicoExtranjero = numeroUnicoExtranjero;
		
		if(numeroUnicoExtranjero != null){
			/*
			 * Se verifica si el Número Unico de Extranjero ya existe en la lista de
			 * documentos probatorios, si existe se elimina
			 */
			ListIterator<DocumentoProbatorio> iterador = this.getDocumentosProbatorios().listIterator();
			
			DocumentoProbatorio documentoProbatorio = null;
			
			if (iterador != null){
				while(iterador.hasNext()){
			    	documentoProbatorio = (DocumentoProbatorio)iterador.next();
			    	
			    	if (documentoProbatorio instanceof CURP){
			    		CURP doctoAux = (CURP) documentoProbatorio;
						
			    		if (doctoAux.getNumTipoDocumento() != null
								&& doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor().intValue()){
							iterador.remove();
							break;
						} else if (doctoAux.getDocumentoPorTipo() != null
								&& doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId())) {
							iterador.remove();
							break;
						}
			    		
			    	}
				}
			}
			
			//AGREGA EL OBJETO CURP A LA LISTA DE DOCUMENTOS PROBATORIOS
			this.getDocumentosProbatorios().add(numeroUnicoExtranjero);	
		}
	}

	/**
	 * @return the certificadoNacionalidadMexicana
	 */
	public CURP getCertificadoNacionalidadMexicana() {
		return certificadoNacionalidadMexicana;
	}

	/**
	 * Cuando se agrega el Certificado de Nacionalidad Mexicana, se busca si existe en la lista
	 * de documentos probatorios, si es así se elimina de la lista
	 * 
	 * @param certificadoNacionalidadMexicana the certificadoNacionalidadMexicana to set
	 */
	public void setCertificadoNacionalidadMexicana(
			CURP certificadoNacionalidadMexicana) {
		this.certificadoNacionalidadMexicana = certificadoNacionalidadMexicana;
		
		if(certificadoNacionalidadMexicana != null){
			/*
			 * Se verifica si el Certificado de Nacionalidad Mexicana ya existe en la lista de
			 * documentos probatorios, si existe se elimina
			 */
			ListIterator<DocumentoProbatorio> iterador = this.getDocumentosProbatorios().listIterator();
			
			DocumentoProbatorio documentoProbatorio = null;
			
			if (iterador != null){
				while(iterador.hasNext()){
			    	documentoProbatorio = (DocumentoProbatorio)iterador.next();
			    	
			    	if (documentoProbatorio instanceof CURP){
			    		CURP doctoAux = (CURP) documentoProbatorio;
						
			    		if (doctoAux.getNumTipoDocumento() != null
								&& doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().intValue()){
							iterador.remove();
							break;
						} else if (doctoAux.getDocumentoPorTipo() != null
								&& doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId())) {
							iterador.remove();
							break;
						}
			    		
			    	}
				}
			}
			
			//AGREGA EL OBJETO CURP A LA LISTA DE DOCUMENTOS PROBATORIOS
			this.getDocumentosProbatorios().add(certificadoNacionalidadMexicana);	
		}
	}

	/**
	 * @return the oficioSolicitanteRefugiado
	 */
	public CURP getOficioSolicitanteRefugiado() {
		return oficioSolicitanteRefugiado;
	}

	/**
	 * Cuando se agrega el Oficio Solicitante de Refugiado, se busca si existe
	 * en la lista de documentos probatorios, si es así se elimina de la lista
	 * 
	 * @param oficioSolicitanteRefugiado the oficioSolicitanteRefugiado to set
	 */
	public void setOficioSolicitanteRefugiado(CURP oficioSolicitanteRefugiado) {
		this.oficioSolicitanteRefugiado = oficioSolicitanteRefugiado;
		
		if(oficioSolicitanteRefugiado != null){
			/*
			 * Se verifica si el Oficio Solicitante de Refugiado ya existe en la lista de
			 * documentos probatorios, si existe se elimina
			 */
			ListIterator<DocumentoProbatorio> iterador = this.getDocumentosProbatorios().listIterator();
			
			DocumentoProbatorio documentoProbatorio = null;
			
			if (iterador != null){
				while(iterador.hasNext()){
			    	documentoProbatorio = (DocumentoProbatorio)iterador.next();
			    	
			    	if (documentoProbatorio instanceof CURP){
			    		CURP doctoAux = (CURP) documentoProbatorio;
						
			    		if (doctoAux.getNumTipoDocumento() != null
								&& doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().intValue()){
							iterador.remove();
							break;
						} else if (doctoAux.getDocumentoPorTipo() != null
								&& doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId())) {
							iterador.remove();
							break;
						}
			    		
			    	}
				}
			}
			
			//AGREGA EL OBJETO CURP A LA LISTA DE DOCUMENTOS PROBATORIOS
			this.getDocumentosProbatorios().add(oficioSolicitanteRefugiado);	
		}
	}

	/**
	 * @return the formaMigratoriaTurista
	 */
	public CURP getFormaMigratoriaTurista() {
		return formaMigratoriaTurista;
	}

	/**
	 * Cuando se agrega la Forma Migratoria Turista, se busca si existe en la lista
	 * de documentos probatorios, si es así se elimina de la lista
	 * 
	 * @param formaMigratoriaTurista the formaMigratoriaTurista to set
	 */
	public void setFormaMigratoriaTurista(CURP formaMigratoriaTurista) {
		this.formaMigratoriaTurista = formaMigratoriaTurista;
		
		if(formaMigratoriaTurista != null){
			/*
			 * Se verifica si la Forma Migratoria Turista ya existe en la lista de
			 * documentos probatorios, si existe se elimina
			 */
			ListIterator<DocumentoProbatorio> iterador = this.getDocumentosProbatorios().listIterator();
			
			DocumentoProbatorio documentoProbatorio = null;
			
			if (iterador != null){
				while(iterador.hasNext()){
			    	documentoProbatorio = (DocumentoProbatorio)iterador.next();
			    	
			    	if (documentoProbatorio instanceof CURP){
			    		CURP doctoAux = (CURP) documentoProbatorio;
						
			    		if (doctoAux.getNumTipoDocumento() != null
								&& doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor().intValue()){
							iterador.remove();
							break;
						} else if (doctoAux.getDocumentoPorTipo() != null
								&& doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId())) {
							iterador.remove();
							break;
						}
			    		
			    	}
				}
			}
			
			//AGREGA EL OBJETO CURP A LA LISTA DE DOCUMENTOS PROBATORIOS
			this.getDocumentosProbatorios().add(formaMigratoriaTurista);	
		}
	}
	
	/**
	 * Settea el Documento Migratorio sin agregarlo a la lista de documentos
	 * probatorios
	 * 
	 * @param documentoMigratorio the documentoMigratorio to set
	 */
	public void setDocumentoMigratorioAux(CURP documentoMigratorio) {
		this.documentoMigratorio = documentoMigratorio;
	}


	/**
	 * Settea la Carta de Naturalización sin agregarla a la lista de documentos
	 * probatorios
	 * 
	 * @param cartaNaturalizacion the cartaNaturalizacion to set
	 */
	public void setCartaNaturalizacionAux(CURP cartaNaturalizacion) {
		this.cartaNaturalizacion = cartaNaturalizacion;
	}

	/**
	 * Settea el Número Unico de Extranjero sin agregarlo a la lista de documentos
	 * probatorios
	 * 
	 * @param numeroUnicoExtranjero the numeroUnicoExtranjero to set
	 */
	public void setNumeroUnicoExtranjeroAux(CURP numeroUnicoExtranjero) {
		this.numeroUnicoExtranjero = numeroUnicoExtranjero;
	}

	/**
	 * Settea el Certificado Nacionalidad Mexicana sin agregarlo a la lista de documentos
	 * probatorios
	 * 
	 * @param certificadoNacionalidadMexicana the certificadoNacionalidadMexicana to set
	 */
	public void setCertificadoNacionalidadMexicanaAux(
			CURP certificadoNacionalidadMexicana) {
		this.certificadoNacionalidadMexicana = certificadoNacionalidadMexicana;
	}

	/**
	 * Settea el Oficio Solicitante de Refugiado sin agregarlo a la lista de documentos
	 * probatorios
	 * 
	 * @param oficioSolicitanteRefugiado the oficioSolicitanteRefugiado to set
	 */
	public void setOficioSolicitanteRefugiadoAux(CURP oficioSolicitanteRefugiado) {
		this.oficioSolicitanteRefugiado = oficioSolicitanteRefugiado;
	}

	/**
	 * Settea la Forma Migratoria Turista sin agregarla a la lista de documentos
	 * probatorios
	 * 
	 * @param formaMigratoriaTurista the formaMigratoriaTurista to set
	 */
	public void setFormaMigratoriaTuristaAux(CURP formaMigratoriaTurista) {
		this.formaMigratoriaTurista = formaMigratoriaTurista;
	}

	public UnidadMedicaFamiliar getUmf() {
		return umf;
	}

	public void setUmf(UnidadMedicaFamiliar umf) {
		this.umf = umf;
	}

	public Integer getMesRegistroNac() {
		return mesRegistroNac;
	}

	public void setMesRegistroNac(Integer mesRegistroNac) {
		this.mesRegistroNac = mesRegistroNac;
	}

	public Integer getAnioRegistroNac() {
		return anioRegistroNac;
	}

	public void setAnioRegistroNac(Integer anioRegistroNac) {
		this.anioRegistroNac = anioRegistroNac;
	}

	public List<Regimen> getRegimenes() {
		return regimenes;
	}

	public void setRegimenes(List<Regimen> regimenes) {
		this.regimenes = regimenes;
	}
	
	public String getNssCifrado() {
		return nssCifrado;
	}

	public void setNssCifrado(String nssCifrado) {
		this.nssCifrado = nssCifrado;
	}
	
	public TipoPoder getTipoPoder() {
		return tipoPoder;
	}

	public void setTipoPoder(TipoPoder tipoPoder) {
		this.tipoPoder = tipoPoder;
	}

	public String getNombreCompleto() {
		StringBuffer nombreCompleto = new StringBuffer();
		
		if (StringUtils.isNotEmpty(this.nombre)) {
			nombreCompleto.append(this.nombre.trim());
		}
		
		if (StringUtils.isNotEmpty(this.primerApellido)) {
			nombreCompleto.append(" ");
			nombreCompleto.append(this.primerApellido.trim());
		}
		
		if (this.segundoApellido != null && StringUtils.isNotEmpty(this.segundoApellido) && !this.segundoApellido.trim().equals("NULL") 
				&& !this.segundoApellido.trim().equals("null")) {
			nombreCompleto.append(" ");
			nombreCompleto.append(this.segundoApellido.trim());
		}
		
		return nombreCompleto.toString();
		
	}
	
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public int hashCodeDatosBasicos() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((curp == null) ? 0 : curp.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result
				+ ((lugarNacimiento == null) ? 0 : lugarNacimiento.getClave().hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((primerApellido == null) ? 0 : primerApellido.hashCode());
		result = prime * result
				+ ((segundoApellido == null) ? 0 : segundoApellido.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.getIdSexo().hashCode());
		
		return result;
	}
	
	public void setEdad(Long edad) {
		this.edad = edad;
	}
	
	public Long getEdad() {
		
		if(this.edad == null && this.fechaNacimiento != null) {
			int edadPersona = 0;
			Calendar fechaHoy = new GregorianCalendar();
			Calendar fechaNacimientoC = new GregorianCalendar();
			fechaHoy.setTime(new Date());
			fechaNacimientoC.setTime(this.fechaNacimiento);
			
			int diaN = fechaNacimientoC.get(Calendar.DATE);
			int mesN = fechaNacimientoC.get(Calendar.MONTH);
			int anioN = fechaNacimientoC.get(Calendar.YEAR);
			
			int diaH =  fechaHoy.get(Calendar.DATE);
			int mesH = fechaHoy.get(Calendar.MONTH);
			int anioH = fechaHoy.get(Calendar.YEAR);
			
			edadPersona = anioH - anioN;
			
			if(mesH < mesN) {
				edadPersona--;
			} else if(mesH == mesN && diaH < diaN) {
				edadPersona --;
			}
			
			this.edad = new Long(edadPersona);
		}

		return this.edad;

	}
	
	 public Long getCveIdAsignacionNSS() {
			return cveIdAsignacionNSS;
		}

		public void setCveIdAsignacionNSS(Long cveIdAsignacionNSS) {
			this.cveIdAsignacionNSS = cveIdAsignacionNSS;
		}

}
