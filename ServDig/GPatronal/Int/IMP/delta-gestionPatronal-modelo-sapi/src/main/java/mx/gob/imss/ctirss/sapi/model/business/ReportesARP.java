package mx.gob.imss.ctirss.sapi.model.business;
/*
 *
 * 
 */ 
import java.util.Date;

public class ReportesARP implements java.io.Serializable {

	private String LOGO_IMSS_HEADER_PARAM;
	private String LOGO_INFO_HEADER_PARAM;
	private String domicilio="";	
	private String maqEqpNU1="";
	private String maqEqpNU2="";
	private String maqEqpNU3="";
	private String maqEqpNU4="";
	private String maqEqpNU5="";
	private String maqEqpNom1="";
	private String maqEqpNom2="";
	private String maqEqpNom3="";
	private String maqEqpNom4="";
	private String maqEqpNom5="";	
	private String maqEqpUso1="";
	private String maqEqpUso2="";
	private String maqEqpUso3="";
	private String maqEqpUso4="";
	private String maqEqpUso5="";
	private String maqEqpTpo1="";
	private String maqEqpTpo2="";
	private String maqEqpTpo3="";
	private String maqEqpTpo4="";
	private String maqEqpTpo5="";
	private String maqEqpCapPot1="";
	private String maqEqpCapPot2="";
	private String maqEqpCapPot3="";
	private String maqEqpCapPot4="";
	private String maqEqpCapPot5="";
	private String eqpTrnNU1="";
	private String eqpTrnNU2="";
	private String eqpTrnNU3="";
	private String eqpTrnNU4="";
	private String eqpTrnNU5="";
	private String eqpTrnNom1="";
	private String eqpTrnNom2="";
	private String eqpTrnNom3="";
	private String eqpTrnNom4="";
	private String eqpTrnNom5="";
	private String eqpTrnUso1="";
	private String eqpTrnUso2="";
	private String eqpTrnUso3="";
	private String eqpTrnUso4="";
	private String eqpTrnUso5="";
	private String eqpTrnTpo1="";
	private String eqpTrnTpo2="";
	private String eqpTrnTpo3="";
	private String eqpTrnTpo4="";
	private String eqpTrnTpo5="";
	private String eqpTrnCapPot1="";
	private String eqpTrnCapPot2="";
	private String eqpTrnCapPot3="";
	private String eqpTrnCapPot4="";
	private String eqpTrnCapPot5="";	
	/**
	 * Fin Cambio por nueva estructura en el archivo PDF
	 */
	
	private String productosOServicios1="";
	private String productosOServicios2="";
	private String productosOServicios3="";
	private String productosOServicios4="";
	private String productosOServicios5="";	
	/**
	 * Inicio aumento a 10 unidades de Productos o Servicios 
	 */
	private String productosOServicios6="";
	private String productosOServicios7="";
	private String productosOServicios8="";
	private String productosOServicios9="";
	private String productosOServicios10="";
	/**
	 * Fin aumento a 10 unidades de Productos o Servicios
	 */
		
	private String materiasPrimas1="";
	private String materiasPrimas2="";
	private String materiasPrimas3="";
	private String materiasPrimas4="";
	private String materiasPrimas5="";
	/**
	 * Inicio aumento a 10 unidades de Materias Primas 
	 */
	private String materiasPrimas6="";
	private String materiasPrimas7="";
	private String materiasPrimas8="";
	private String materiasPrimas9="";
	private String materiasPrimas10="";
	/**
	 * Fin aumento a 10 unidades de Materias Primas
	 */
	
	/**
	 * Inicio Agregar la parte de personal 
	 */
	private String perNoTrab1="";
	private String perNoTrab2="";
	private String perNoTrab3="";
	private String perNoTrab4="";
	private String perNoTrab5="";
	private String perNoTrab6="";
	private String perNoTrab7="";
	private String perNoTrab8="";
	private String perNoTrab9="";
	private String perNoTrab10="";
	private String perNoTrab11="";
	private String perNoTrab12="";
	
	private String perOficOcup1="";
	private String perOficOcup2="";
	private String perOficOcup3="";
	private String perOficOcup4="";
	private String perOficOcup5="";
	private String perOficOcup6="";
	private String perOficOcup7="";
	private String perOficOcup8="";
	private String perOficOcup9="";
	private String perOficOcup10="";
	private String perOficOcup11="";
	private String perOficOcup12="";
	/**
	 * Fin Agregar la parte de personal
	 */
	private String apPaterno="";
	private String apMaterno="";
	private String nombre="";
	/**
	 * Inicio: Inclusion de nueva(s) variable(s) para ajustar al formato actual
	 */
	private String nomComercial="";
	private Integer noCentTrab=0;
	
	private String prestaServPersNo="";
	private String prestaServPersSi="";
	
	private String solRegPatronalClase="";
	
	private String procesoInicial1="";
	private String procesoIntermedio1="";
	private String procesoFinal1="";
	/**
	 * Fin: Inclusion de nueva(s) variable(s) para ajustar al formato actual
	 */
	private String curp="";
	private String rfc="";
	private String calle="";
	private String numExt="";
	private String numInt="";
	private String entrecalle1="";
	private String entrecalle2="";
	private String colonia="";
	private String localidad="";
	private String municipio="";
	private String entidadaFederativa="";
	private String cp="";
	private String cp1="";
	private String telefono1="";
	private String extencion1="";
	private String telefono2="";
	private String correo="";
	private String extencion2="";
	
	
	private String actosDominioRL="";
	private String actosAdministracionRL="";
	
	
	private String nombreCompletoRl;
	private String apPaternoRL="";
	private String apMaternoRL="";
	private String tipoSociedad="";
	private String nombreRL="";
	private String curpRL="";
	private String rfcRL="";
	private String calleRL="";
	private String telefono1RL="";
	private String telefono2RL="";
	private String extencionRL="";
	private String correoRL="";
	
	private String numEscritura="";
	private String numNotarira="";
	private String lugarExpedicion="";
	private  Date  fechaExpedicion;
	private String folioMercantil="";
	
	
	private String numeroReferencia="";
	private  Date  fechaDocumentoRegistro;
	private String autoridadLaboral;
	private String lugaRegistro;
	
	
	private String calleCT="";
	private String numExtCT="";
	private String numIntCT="";
	private String entrecalle1CT="";
	private String entrecalle2CT="";
	private String callePostCT="";
	private String coloniaCT="";
	private String localidadCT="";
	private String municipioCT="";
	private String entidadaFederativaCT="";
	private String telefono1CT="";
	private String telefono2CT="";
	private String correoCT="";
	private String cpCT="";
	private String cpCT1="";
	private String extencion1CT="";
	private String extencion2CT="";
	
	
	private String apPaternoPA1="";
	private String apMaternoPA1="";
	private String nombrePA1="";
	private String curpPA1="";
	private String rfcPA1="";
	private String telefono1PA1="";
	private String telefono2PA1="";
	private String extencionPA1="";
	private String correoPA1="";
	
	private String apPaternoPA2="";
	private String apMaternoPA2="";
	private String nombrePA2="";
	private String curpPA2="";
	private String rfcPA2="";
	private String telefono1PA2="";
	private String telefono2PA2="";
	private String extencionPA2="";
	private String correoPA2="";

	private String apPaternoPA3="";
	private String apMaternoPA3="";
	private String nombrePA3="";
	private String curpPA3="";
	private String rfcPA3="";
	private String telefono1PA3="";
	private String telefono2PA3="";
	private String extencionPA3="";
	private String correoPA3="";
	
	/**
	 * Inicio: Agregando datos de los socios
	 */
	private String apPaterno1="";
	private String apMaterno1="";
	private String nombre1="";
	private String rfc1="";
	private String curp1="";
	private String calle1="";
	private String numExt1="";
	private String numInt1="";
	private String colonia1="";
	private String localidad1="";
	private String munDelegacion1="";
	private String entFederativa1="";
	private String codPost1="";
	private String telfijo1="";
	private String email1="";
	private String apPaterno2="";
	private String apMaterno2="";
	private String nombre2="";
	private String rfc2="";
	private String curp2="";
	private String calle2="";
	private String numExt2="";
	private String numInt2="";
	private String colonia2="";
	private String localidad2="";
	private String munDelegacion2="";
	private String entFederativa2="";
	private String codPost2="";
	private String telfijo2="";
	private String email2="";
	private String apPaterno3="";
	private String apMaterno3="";
	private String nombre3="";
	private String rfc3="";
	private String curp3="";
	private String calle3="";
	private String numExt3="";
	private String numInt3="";
	private String colonia3="";
	private String localidad3="";
	private String munDelegacion3="";
	private String entFederativa3="";
	private String codPost3="";
	private String telfijo3="";
	private String email3="";
	private String apPaterno4="";
	private String apMaterno4="";
	private String nombre4="";
	private String rfc4="";
	private String curp4="";
	private String calle4="";
	private String numExt4="";
	private String numInt4="";
	private String colonia4="";
	private String localidad4="";
	private String munDelegacion4="";
	private String entFederativa4="";
	private String codPost4="";
	private String telfijo4="";
	private String email4="";
	/**
	 * Fin: Agregando datos de los socios
	 */
	
	private String giro="";
	
	private String divisionClave="";
	private String divisionDes="";
	
	private String grupoClave="";
	private String grupoDes=""; 
	
	private String fraccionClave="";
	private String fraccionDes="";
	private String clase="";
	private String prima="";
	
	
	
	private String conTransportePropio="";
	private String SinTransportePropio="";
	private String noDistribuye="";
	private String serviciosInstalacion="";
	
	
	private Date fecPresentacion;
	private Date fecEfecto;
	
	private Date fechaComodin1;
	private Date fechaComodin2;
	private String Comodin1="";
	private String Comodin2="";
	private String Comodin3="";
	
	private String nrp="";
	private String dv="";
	private String delegacion="" ;
	private String subDelegacion="";
	
	private String horaCita="";
	private String minCita="";
	
	private String mesTipExpedicion="";
	
	private String folioNRP=""; 
	private String matricula=""; 
	private String usuario="";
	private String vigencia="";
	private String tiempoAlta=""; 
	private String fechaRegistroCita=""; 
	
	private String firmaDigital="";
	private String cadenaOriginal="";
	private String numNotariaDigital="";
	//campos para rpnp//
	private String serieCertificado;
	private String vigenciaFiel;
	
	/*********** Campos para Origen de Solcitud *****/
	
	private Integer origenSolicitud;
	
	public Integer getOrigenSolicitud() {
		return origenSolicitud;
	}
	public void setOrigenSolicitud(Integer origenSolicitud) {
		this.origenSolicitud = origenSolicitud;
	}
	
	
	/*********** Campos para Modalidad 46 **************/
	private String nivelEducativo = "";
	private String confirmoConvenio="";
	
	
	/*********** Campos para Modalidad 46 **************/
	
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getProductosOServicios1() {
		return productosOServicios1;
	}
	public void setProductosOServicios1(String productosOServicios1) {
		this.productosOServicios1 = productosOServicios1;
	}
	public String getProductosOServicios2() {
		return productosOServicios2;
	}
	public void setProductosOServicios2(String productosOServicios2) {
		this.productosOServicios2 = productosOServicios2;
	}
	public String getProductosOServicios3() {
		return productosOServicios3;
	}
	public void setProductosOServicios3(String productosOServicios3) {
		this.productosOServicios3 = productosOServicios3;
	}
	public String getProductosOServicios4() {
		return productosOServicios4;
	}
	public void setProductosOServicios4(String productosOServicios4) {
		this.productosOServicios4 = productosOServicios4;
	}
	public String getProductosOServicios5() {
		return productosOServicios5;
	}
	public void setProductosOServicios5(String productosOServicios5) {
		this.productosOServicios5 = productosOServicios5;
	}
	public String getMateriasPrimas1() {
		return materiasPrimas1;
	}
	public void setMateriasPrimas1(String materiasPrimas1) {
		this.materiasPrimas1 = materiasPrimas1;
	}
	public String getMateriasPrimas2() {
		return materiasPrimas2;
	}
	public void setMateriasPrimas2(String materiasPrimas2) {
		this.materiasPrimas2 = materiasPrimas2;
	}
	public String getMateriasPrimas3() {
		return materiasPrimas3;
	}
	public void setMateriasPrimas3(String materiasPrimas3) {
		this.materiasPrimas3 = materiasPrimas3;
	}
	public String getMateriasPrimas4() {
		return materiasPrimas4;
	}
	public void setMateriasPrimas4(String materiasPrimas4) {
		this.materiasPrimas4 = materiasPrimas4;
	}
	public String getMateriasPrimas5() {
		return materiasPrimas5;
	}
	public void setMateriasPrimas5(String materiasPrimas5) {
		this.materiasPrimas5 = materiasPrimas5;
	}
	public String getApPaterno() {
		return apPaterno;
	}
	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}
	public String getApMaterno() {
		return apMaterno;
	}
	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumExt() {
		return numExt;
	}
	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}
	public String getNumInt() {
		return numInt;
	}
	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}
	public String getEntrecalle1() {
		return entrecalle1;
	}
	public void setEntrecalle1(String entrecalle1) {
		this.entrecalle1 = entrecalle1;
	}
	public String getEntrecalle2() {
		return entrecalle2;
	}
	public void setEntrecalle2(String entrecalle2) {
		this.entrecalle2 = entrecalle2;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getEntidadaFederativa() {
		return entidadaFederativa;
	}
	public void setEntidadaFederativa(String entidadaFederativa) {
		this.entidadaFederativa = entidadaFederativa;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getActosDominioRL() {
		return actosDominioRL;
	}
	public void setActosDominioRL(String actosDominioRL) {
		this.actosDominioRL = actosDominioRL;
	}
	public String getActosAdministracionRL() {
		return actosAdministracionRL;
	}
	public void setActosAdministracionRL(String actosAdministracionRL) {
		this.actosAdministracionRL = actosAdministracionRL;
	}
	public String getApPaternoRL() {
		return apPaternoRL;
	}
	public void setApPaternoRL(String apPaternoRL) {
		this.apPaternoRL = apPaternoRL;
	}
	public String getApMaternoRL() {
		return apMaternoRL;
	}
	public void setApMaternoRL(String apMaternoRL) {
		this.apMaternoRL = apMaternoRL;
	}
	public String getNombreRL() {
		return nombreRL;
	}
	public void setNombreRL(String nombreRL) {
		this.nombreRL = nombreRL;
	}
	public String getCurpRL() {
		return curpRL;
	}
	public void setCurpRL(String curpRL) {
		this.curpRL = curpRL;
	}
	public String getRfcRL() {
		return rfcRL;
	}
	public void setRfcRL(String rfcRL) {
		this.rfcRL = rfcRL;
	}
	public String getCalleRL() {
		return calleRL;
	}
	public void setCalleRL(String calleRL) {
		this.calleRL = calleRL;
	}
	public String getTelefono1RL() {
		return telefono1RL;
	}
	public void setTelefono1RL(String telefono1RL) {
		this.telefono1RL = telefono1RL;
	}
	public String getTelefono2RL() {
		return telefono2RL;
	}
	public void setTelefono2RL(String telefono2RL) {
		this.telefono2RL = telefono2RL;
	}
	public String getExtencionRL() {
		return extencionRL;
	}
	public void setExtencionRL(String extencionRL) {
		this.extencionRL = extencionRL;
	}
	public String getNumEscritura() {
		return numEscritura;
	}
	public void setNumEscritura(String numEscritura) {
		this.numEscritura = numEscritura;
	}
	public String getNumNotarira() {
		return numNotarira;
	}
	public void setNumNotarira(String numNotarira) {
		this.numNotarira = numNotarira;
	}
	public String getLugarExpedicion() {
		return lugarExpedicion;
	}
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}
	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}
	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	public String getFolioMercantil() {
		return folioMercantil;
	}
	public void setFolioMercantil(String folioMercantil) {
		this.folioMercantil = folioMercantil;
	}
	public String getNumeroReferencia() {
		return numeroReferencia;
	}
	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}
	public Date getFechaDocumentoRegistro() {
		return fechaDocumentoRegistro;
	}
	public void setFechaDocumentoRegistro(Date fechaDocumentoRegistro) {
		this.fechaDocumentoRegistro = fechaDocumentoRegistro;
	}
	
	public String getCalleCT() {
		return calleCT;
	}
	public void setCalleCT(String calleCT) {
		this.calleCT = calleCT;
	}
	public String getNumExtCT() {
		return numExtCT;
	}
	public void setNumExtCT(String numExtCT) {
		this.numExtCT = numExtCT;
	}
	public String getNumIntCT() {
		return numIntCT;
	}
	public void setNumIntCT(String numIntCT) {
		this.numIntCT = numIntCT;
	}
	public String getEntrecalle1CT() {
		return entrecalle1CT;
	}
	public void setEntrecalle1CT(String entrecalle1CT) {
		this.entrecalle1CT = entrecalle1CT;
	}
	public String getEntrecalle2CT() {
		return entrecalle2CT;
	}
	public void setEntrecalle2CT(String entrecalle2CT) {
		this.entrecalle2CT = entrecalle2CT;
	}
	
	public String getCallePostCT() {
		return callePostCT;
	}
	public void setCallePostCT(String callePostCT) {
		this.callePostCT = callePostCT;
	}
	public String getColoniaCT() {
		return coloniaCT;
	}
	public void setColoniaCT(String coloniaCT) {
		this.coloniaCT = coloniaCT;
	}
	public String getLocalidadCT() {
		return localidadCT;
	}
	public void setLocalidadCT(String localidadCT) {
		this.localidadCT = localidadCT;
	}
	public String getMunicipioCT() {
		return municipioCT;
	}
	public void setMunicipioCT(String municipioCT) {
		this.municipioCT = municipioCT;
	}
	public String getEntidadaFederativaCT() {
		return entidadaFederativaCT;
	}
	public void setEntidadaFederativaCT(String entidadaFederativaCT) {
		this.entidadaFederativaCT = entidadaFederativaCT;
	}
	public String getTelefono1CT() {
		return telefono1CT;
	}
	public void setTelefono1CT(String telefono1CT) {
		this.telefono1CT = telefono1CT;
	}
	public String getTelefono2CT() {
		return telefono2CT;
	}
	public void setTelefono2CT(String telefono2CT) {
		this.telefono2CT = telefono2CT;
	}
	public String getCorreoCT() {
		return correoCT;
	}
	public void setCorreoCT(String correoCT) {
		this.correoCT = correoCT;
	}
	public String getCpCT() {
		return cpCT;
	}
	public void setCpCT(String cpCT) {
		this.cpCT = cpCT;
	}
	public String getApPaternoPA1() {
		return apPaternoPA1;
	}
	public void setApPaternoPA1(String apPaternoPA1) {
		this.apPaternoPA1 = apPaternoPA1;
	}
	public String getApMaternoPA1() {
		return apMaternoPA1;
	}
	public void setApMaternoPA1(String apMaternoPA1) {
		this.apMaternoPA1 = apMaternoPA1;
	}
	public String getNombrePA1() {
		return nombrePA1;
	}
	public void setNombrePA1(String nombrePA1) {
		this.nombrePA1 = nombrePA1;
	}
	public String getCurpPA1() {
		return curpPA1;
	}
	public void setCurpPA1(String curpPA1) {
		this.curpPA1 = curpPA1;
	}
	public String getRfcPA1() {
		return rfcPA1;
	}
	public void setRfcPA1(String rfcPA1) {
		this.rfcPA1 = rfcPA1;
	}
	public String getTelefono1PA1() {
		return telefono1PA1;
	}
	public void setTelefono1PA1(String telefono1PA1) {
		this.telefono1PA1 = telefono1PA1;
	}
	public String getTelefono2PA1() {
		return telefono2PA1;
	}
	public void setTelefono2PA1(String telefono2PA1) {
		this.telefono2PA1 = telefono2PA1;
	}
	public String getExtencionPA1() {
		return extencionPA1;
	}
	public void setExtencionPA1(String extencionPA1) {
		this.extencionPA1 = extencionPA1;
	}
	public String getCorreoPA1() {
		return correoPA1;
	}
	public void setCorreoPA1(String correoPA1) {
		this.correoPA1 = correoPA1;
	}
	public String getApPaternoPA2() {
		return apPaternoPA2;
	}
	public void setApPaternoPA2(String apPaternoPA2) {
		this.apPaternoPA2 = apPaternoPA2;
	}
	public String getApMaternoPA2() {
		return apMaternoPA2;
	}
	public void setApMaternoPA2(String apMaternoPA2) {
		this.apMaternoPA2 = apMaternoPA2;
	}
	public String getNombrePA2() {
		return nombrePA2;
	}
	public void setNombrePA2(String nombrePA2) {
		this.nombrePA2 = nombrePA2;
	}
	public String getCurpPA2() {
		return curpPA2;
	}
	public void setCurpPA2(String curpPA2) {
		this.curpPA2 = curpPA2;
	}
	public String getRfcPA2() {
		return rfcPA2;
	}
	public void setRfcPA2(String rfcPA2) {
		this.rfcPA2 = rfcPA2;
	}
	public String getTelefono1PA2() {
		return telefono1PA2;
	}
	public void setTelefono1PA2(String telefono1PA2) {
		this.telefono1PA2 = telefono1PA2;
	}
	public String getTelefono2PA2() {
		return telefono2PA2;
	}
	public void setTelefono2PA2(String telefono2PA2) {
		this.telefono2PA2 = telefono2PA2;
	}
	public String getExtencionPA2() {
		return extencionPA2;
	}
	public void setExtencionPA2(String extencionPA2) {
		this.extencionPA2 = extencionPA2;
	}
	public String getCorreoPA2() {
		return correoPA2;
	}
	public void setCorreoPA2(String correoPA2) {
		this.correoPA2 = correoPA2;
	}
	public String getApPaternoPA3() {
		return apPaternoPA3;
	}
	public void setApPaternoPA3(String apPaternoPA3) {
		this.apPaternoPA3 = apPaternoPA3;
	}
	public String getApMaternoPA3() {
		return apMaternoPA3;
	}
	public void setApMaternoPA3(String apMaternoPA3) {
		this.apMaternoPA3 = apMaternoPA3;
	}
	public String getNombrePA3() {
		return nombrePA3;
	}
	public void setNombrePA3(String nombrePA3) {
		this.nombrePA3 = nombrePA3;
	}
	public String getCurpPA3() {
		return curpPA3;
	}
	public void setCurpPA3(String curpPA3) {
		this.curpPA3 = curpPA3;
	}
	public String getRfcPA3() {
		return rfcPA3;
	}
	public void setRfcPA3(String rfcPA3) {
		this.rfcPA3 = rfcPA3;
	}
	public String getTelefono1PA3() {
		return telefono1PA3;
	}
	public void setTelefono1PA3(String telefono1PA3) {
		this.telefono1PA3 = telefono1PA3;
	}
	public String getTelefono2PA3() {
		return telefono2PA3;
	}
	public void setTelefono2PA3(String telefono2PA3) {
		this.telefono2PA3 = telefono2PA3;
	}
	public String getExtencionPA3() {
		return extencionPA3;
	}
	public void setExtencionPA3(String extencionPA3) {
		this.extencionPA3 = extencionPA3;
	}
	public String getCorreoPA3() {
		return correoPA3;
	}
	public void setCorreoPA3(String correoPA3) {
		this.correoPA3 = correoPA3;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public String getDivisionClave() {
		return divisionClave;
	}
	public void setDivisionClave(String divisionClave) {
		this.divisionClave = divisionClave;
	}
	public String getDivisionDes() {
		return divisionDes;
	}
	public void setDivisionDes(String divisionDes) {
		this.divisionDes = divisionDes;
	}
	public String getGrupoClave() {
		return grupoClave;
	}
	public void setGrupoClave(String grupoClave) {
		this.grupoClave = grupoClave;
	}
	public String getGrupoDes() {
		return grupoDes;
	}
	public void setGrupoDes(String grupoDes) {
		this.grupoDes = grupoDes;
	}
	public String getFraccionClave() {
		return fraccionClave;
	}
	public void setFraccionClave(String fraccionClave) {
		this.fraccionClave = fraccionClave;
	}
	public String getFraccionDes() {
		return fraccionDes;
	}
	public void setFraccionDes(String fraccionDes) {
		this.fraccionDes = fraccionDes;
	}
	public String getConTransportePropio() {
		return conTransportePropio;
	}
	public void setConTransportePropio(String conTransportePropio) {
		this.conTransportePropio = conTransportePropio;
	}
	public String getSinTransportePropio() {
		return SinTransportePropio;
	}
	public void setSinTransportePropio(String sinTransportePropio) {
		SinTransportePropio = sinTransportePropio;
	}
	public String getNoDistribuye() {
		return noDistribuye;
	}
	public void setNoDistribuye(String noDistribuye) {
		this.noDistribuye = noDistribuye;
	}
	public String getServiciosInstalacion() {
		return serviciosInstalacion;
	}
	public void setServiciosInstalacion(String serviciosInstalacion) {
		this.serviciosInstalacion = serviciosInstalacion;
	}
	public Date getFecPresentacion() {
		return fecPresentacion;
	}
	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}
	public Date getFecEfecto() {
		return fecEfecto;
	}
	public void setFecEfecto(Date fecEfecto) {
		this.fecEfecto = fecEfecto;
	}
	public Date getFechaComodin1() {
		return fechaComodin1;
	}
	public void setFechaComodin1(Date fechaComodin1) {
		this.fechaComodin1 = fechaComodin1;
	}
	public Date getFechaComodin2() {
		return fechaComodin2;
	}
	public void setFechaComodin2(Date fechaComodin2) {
		this.fechaComodin2 = fechaComodin2;
	}
	public String getComodin1() {
		return Comodin1;
	}
	public void setComodin1(String comodin1) {
		Comodin1 = comodin1;
	}
	public String getComodin2() {
		return Comodin2;
	}
	public void setComodin2(String comodin2) {
		Comodin2 = comodin2;
	}
	public String getComodin3() {
		return Comodin3;
	}
	public void setComodin3(String comodin3) {
		Comodin3 = comodin3;
	}
	public String getTipoSociedad() {
		return tipoSociedad;
	}
	public void setTipoSociedad(String tipoSociedad) {
		this.tipoSociedad = tipoSociedad;
	}
	public String getNrp() {
		return nrp;
	}
	public void setNrp(String nrp) {
		this.nrp = nrp;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getSubDelegacion() {
		return subDelegacion;
	}
	public void setSubDelegacion(String subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	public String getAutoridadLaboral() {
		return autoridadLaboral;
	}
	public void setAutoridadLaboral(String autoridadLaboral) {
		this.autoridadLaboral = autoridadLaboral;
	}
	public String getCorreoRL() {
		return correoRL;
	}
	public void setCorreoRL(String correoRL) {
		this.correoRL = correoRL;
	}
	public String getLugaRegistro() {
		return lugaRegistro;
	}
	public void setLugaRegistro(String lugaRegistro) {
		this.lugaRegistro = lugaRegistro;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getFolioNRP() {
		return folioNRP;
	}
	public void setFolioNRP(String folioNRP) {
		this.folioNRP = folioNRP;
	}
	public String getExtencion1() {
		return extencion1;
	}
	public void setExtencion1(String extencion1) {
		this.extencion1 = extencion1;
	}
	public String getExtencion2() {
		return extencion2;
	}
	public void setExtencion2(String extencion2) {
		this.extencion2 = extencion2;
	}
	public String getExtencion1CT() {
		return extencion1CT;
	}
	public void setExtencion1CT(String extencion1CT) {
		this.extencion1CT = extencion1CT;
	}
	public String getExtencion2CT() {
		return extencion2CT;
	}
	public void setExtencion2CT(String extencion2CT) {
		this.extencion2CT = extencion2CT;
	}
	public String getHoraCita() {
		return horaCita;
	}
	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}
	public String getMinCita() {
		return minCita;
	}
	public void setMinCita(String minCita) {
		this.minCita = minCita;
	}
	public String getCp1() {
		return cp1;
	}
	public void setCp1(String cp1) {
		this.cp1 = cp1;
	}
	public String getNombreCompletoRl() {
		return nombreCompletoRl;
	}
	public void setNombreCompletoRl(String nombreCompletoRl) {
		this.nombreCompletoRl = nombreCompletoRl;
	}
	public String getCpCT1() {
		return cpCT1;
	}
	public void setCpCT1(String cpCT1) {
		this.cpCT1 = cpCT1;
	}
	public String getPrima() {
		return prima;
	}
	public void setPrima(String prima) {
		this.prima = prima;
	}
	public String getMesTipExpedicion() {
		return mesTipExpedicion;
	}
	public void setMesTipExpedicion(String mesTipExpedicion) {
		this.mesTipExpedicion = mesTipExpedicion;
	}
	public String getTiempoAlta() {
		return tiempoAlta;
	}
	public void setTiempoAlta(String tiempoAlta) {
		this.tiempoAlta = tiempoAlta;
	}
	public String getFechaRegistroCita() {
		return fechaRegistroCita;
	}
	public void setFechaRegistroCita(String fechaRegistroCita) {
		this.fechaRegistroCita = fechaRegistroCita;
	}	
	
	/**
	 * Set's y Get's de las nuevas variables que se agregaron para el formato a imprimir
	 */
	
	/**
	 * @return the productosOServicios6
	 */
	public String getProductosOServicios6() {
		return productosOServicios6;
	}
	
	/**
	 * @param productosOServicios6 the productosOServicios6 to set
	 */
	public void setProductosOServicios6(String productosOServicios6) {
		this.productosOServicios6 = productosOServicios6;
	}
	
	/**
	 * @return the productosOServicios7
	 */
	public String getProductosOServicios7() {
		return productosOServicios7;
	}
	
	/**
	 * @param productosOServicios7 the productosOServicios7 to set
	 */
	public void setProductosOServicios7(String productosOServicios7) {
		this.productosOServicios7 = productosOServicios7;
	}
	
	/**
	 * @return the productosOServicios8
	 */
	public String getProductosOServicios8() {
		return productosOServicios8;
	}
	
	/**
	 * @param productosOServicios8 the productosOServicios8 to set
	 */
	public void setProductosOServicios8(String productosOServicios8) {
		this.productosOServicios8 = productosOServicios8;
	}
	
	/**
	 * @return the productosOServicios9
	 */
	public String getProductosOServicios9() {
		return productosOServicios9;
	}
	
	/**
	 * @param productosOServicios9 the productosOServicios9 to set
	 */
	public void setProductosOServicios9(String productosOServicios9) {
		this.productosOServicios9 = productosOServicios9;
	}
	
	/**
	 * @return the productosOServicios10
	 */
	public String getProductosOServicios10() {
		return productosOServicios10;
	}
	
	/**
	 * @param productosOServicios10 the productosOServicios10 to set
	 */
	public void setProductosOServicios10(String productosOServicios10) {
		this.productosOServicios10 = productosOServicios10;
	}
	
	/**
	 * @return the materiasPrimas6
	 */
	public String getMateriasPrimas6() {
		return materiasPrimas6;
	}
	
	/**
	 * @param materiasPrimas6 the materiasPrimas6 to set
	 */
	public void setMateriasPrimas6(String materiasPrimas6) {
		this.materiasPrimas6 = materiasPrimas6;
	}
	
	/**
	 * @return the materiasPrimas7
	 */
	public String getMateriasPrimas7() {
		return materiasPrimas7;
	}
	
	/**
	 * @param materiasPrimas7 the materiasPrimas7 to set
	 */
	public void setMateriasPrimas7(String materiasPrimas7) {
		this.materiasPrimas7 = materiasPrimas7;
	}
	
	/**
	 * @return the materiasPrimas8
	 */
	public String getMateriasPrimas8() {
		return materiasPrimas8;
	}
	
	/**
	 * @param materiasPrimas8 the materiasPrimas8 to set
	 */
	public void setMateriasPrimas8(String materiasPrimas8) {
		this.materiasPrimas8 = materiasPrimas8;
	}
	
	/**
	 * @return the materiasPrimas9
	 */
	public String getMateriasPrimas9() {
		return materiasPrimas9;
	}
	
	/**
	 * @param materiasPrimas9 the materiasPrimas9 to set
	 */
	public void setMateriasPrimas9(String materiasPrimas9) {
		this.materiasPrimas9 = materiasPrimas9;
	}
	
	/**
	 * @return the materiasPrimas10
	 */
	public String getMateriasPrimas10() {
		return materiasPrimas10;
	}
	
	/**
	 * @param materiasPrimas10 the materiasPrimas10 to set
	 */
	public void setMateriasPrimas10(String materiasPrimas10) {
		this.materiasPrimas10 = materiasPrimas10;
	}
	
	/**
	 * @return the maqEqpNU1
	 */
	public String getMaqEqpNU1() {
		return maqEqpNU1;
	}
	
	/**
	 * @param maqEqpNU1 the maqEqpNU1 to set
	 */
	public void setMaqEqpNU1(String maqEqpNU1) {
		this.maqEqpNU1 = maqEqpNU1;
	}
	
	/**
	 * @return the maqEqpNU2
	 */
	public String getMaqEqpNU2() {
		return maqEqpNU2;
	}
	
	/**
	 * @param maqEqpNU2 the maqEqpNU2 to set
	 */
	public void setMaqEqpNU2(String maqEqpNU2) {
		this.maqEqpNU2 = maqEqpNU2;
	}
	
	/**
	 * @return the maqEqpNU3
	 */
	public String getMaqEqpNU3() {
		return maqEqpNU3;
	}
	
	/**
	 * @param maqEqpNU3 the maqEqpNU3 to set
	 */
	public void setMaqEqpNU3(String maqEqpNU3) {
		this.maqEqpNU3 = maqEqpNU3;
	}
	
	/**
	 * @return the maqEqpNU4
	 */
	public String getMaqEqpNU4() {
		return maqEqpNU4;
	}
	
	/**
	 * @param maqEqpNU4 the maqEqpNU4 to set
	 */
	public void setMaqEqpNU4(String maqEqpNU4) {
		this.maqEqpNU4 = maqEqpNU4;
	}
	
	/**
	 * @return the maqEqpNU5
	 */
	public String getMaqEqpNU5() {
		return maqEqpNU5;
	}
	
	/**
	 * @param maqEqpNU5 the maqEqpNU5 to set
	 */
	public void setMaqEqpNU5(String maqEqpNU5) {
		this.maqEqpNU5 = maqEqpNU5;
	}
	
	/**
	 * @return the maqEqpNom1
	 */
	public String getMaqEqpNom1() {
		return maqEqpNom1;
	}
	
	/**
	 * @param maqEqpNom1 the maqEqpNom1 to set
	 */
	public void setMaqEqpNom1(String maqEqpNom1) {
		this.maqEqpNom1 = maqEqpNom1;
	}
	
	/**
	 * @return the maqEqpNom2
	 */
	public String getMaqEqpNom2() {
		return maqEqpNom2;
	}
	
	/**
	 * @param maqEqpNom2 the maqEqpNom2 to set
	 */
	public void setMaqEqpNom2(String maqEqpNom2) {
		this.maqEqpNom2 = maqEqpNom2;
	}
	
	/**
	 * @return the maqEqpNom3
	 */
	public String getMaqEqpNom3() {
		return maqEqpNom3;
	}
	
	/**
	 * @param maqEqpNom3 the maqEqpNom3 to set
	 */
	public void setMaqEqpNom3(String maqEqpNom3) {
		this.maqEqpNom3 = maqEqpNom3;
	}
	
	/**
	 * @return the maqEqpNom4
	 */
	public String getMaqEqpNom4() {
		return maqEqpNom4;
	}
	
	/**
	 * @param maqEqpNom4 the maqEqpNom4 to set
	 */
	public void setMaqEqpNom4(String maqEqpNom4) {
		this.maqEqpNom4 = maqEqpNom4;
	}
	
	/**
	 * @return the maqEqpNom5
	 */
	public String getMaqEqpNom5() {
		return maqEqpNom5;
	}
	
	/**
	 * @param maqEqpNom5 the maqEqpNom5 to set
	 */
	public void setMaqEqpNom5(String maqEqpNom5) {
		this.maqEqpNom5 = maqEqpNom5;
	}
	
	/**
	 * @return the maqEqpUso1
	 */
	public String getMaqEqpUso1() {
		return maqEqpUso1;
	}
	
	/**
	 * @param maqEqpUso1 the maqEqpUso1 to set
	 */
	public void setMaqEqpUso1(String maqEqpUso1) {
		this.maqEqpUso1 = maqEqpUso1;
	}
	
	/**
	 * @return the maqEqpUso2
	 */
	public String getMaqEqpUso2() {
		return maqEqpUso2;
	}
	
	/**
	 * @param maqEqpUso2 the maqEqpUso2 to set
	 */
	public void setMaqEqpUso2(String maqEqpUso2) {
		this.maqEqpUso2 = maqEqpUso2;
	}
	
	/**
	 * @return the maqEqpUso3
	 */
	public String getMaqEqpUso3() {
		return maqEqpUso3;
	}
	
	/**
	 * @param maqEqpUso3 the maqEqpUso3 to set
	 */
	public void setMaqEqpUso3(String maqEqpUso3) {
		this.maqEqpUso3 = maqEqpUso3;
	}
	
	/**
	 * @return the maqEqpUso4
	 */
	public String getMaqEqpUso4() {
		return maqEqpUso4;
	}
	
	/**
	 * @param maqEqpUso4 the maqEqpUso4 to set
	 */
	public void setMaqEqpUso4(String maqEqpUso4) {
		this.maqEqpUso4 = maqEqpUso4;
	}
	
	/**
	 * @return the maqEqpUso5
	 */
	public String getMaqEqpUso5() {
		return maqEqpUso5;
	}
	
	/**
	 * @param maqEqpUso5 the maqEqpUso5 to set
	 */
	public void setMaqEqpUso5(String maqEqpUso5) {
		this.maqEqpUso5 = maqEqpUso5;
	}
	
	/**
	 * @return the maqEqpTpo1
	 */
	public String getMaqEqpTpo1() {
		return maqEqpTpo1;
	}
	
	/**
	 * @param maqEqpTpo1 the maqEqpTpo1 to set
	 */
	public void setMaqEqpTpo1(String maqEqpTpo1) {
		this.maqEqpTpo1 = maqEqpTpo1;
	}
	
	/**
	 * @return the maqEqpTpo2
	 */
	public String getMaqEqpTpo2() {
		return maqEqpTpo2;
	}
	
	/**
	 * @param maqEqpTpo2 the maqEqpTpo2 to set
	 */
	public void setMaqEqpTpo2(String maqEqpTpo2) {
		this.maqEqpTpo2 = maqEqpTpo2;
	}
	
	/**
	 * @return the maqEqpTpo3
	 */
	public String getMaqEqpTpo3() {
		return maqEqpTpo3;
	}
	
	/**
	 * @param maqEqpTpo3 the maqEqpTpo3 to set
	 */
	public void setMaqEqpTpo3(String maqEqpTpo3) {
		this.maqEqpTpo3 = maqEqpTpo3;
	}
	
	/**
	 * @return the maqEqpTpo4
	 */
	public String getMaqEqpTpo4() {
		return maqEqpTpo4;
	}
	
	/**
	 * @param maqEqpTpo4 the maqEqpTpo4 to set
	 */
	public void setMaqEqpTpo4(String maqEqpTpo4) {
		this.maqEqpTpo4 = maqEqpTpo4;
	}
	
	/**
	 * @return the maqEqpTpo5
	 */
	public String getMaqEqpTpo5() {
		return maqEqpTpo5;
	}
	
	/**
	 * @param maqEqpTpo5 the maqEqpTpo5 to set
	 */
	public void setMaqEqpTpo5(String maqEqpTpo5) {
		this.maqEqpTpo5 = maqEqpTpo5;
	}
	
	/**
	 * @return the maqEqpCapPot1
	 */
	public String getMaqEqpCapPot1() {
		return maqEqpCapPot1;
	}
	
	/**
	 * @param maqEqpCapPot1 the maqEqpCapPot1 to set
	 */
	public void setMaqEqpCapPot1(String maqEqpCapPot1) {
		this.maqEqpCapPot1 = maqEqpCapPot1;
	}
	
	/**
	 * @return the maqEqpCapPot2
	 */
	public String getMaqEqpCapPot2() {
		return maqEqpCapPot2;
	}
	
	/**
	 * @param maqEqpCapPot2 the maqEqpCapPot2 to set
	 */
	public void setMaqEqpCapPot2(String maqEqpCapPot2) {
		this.maqEqpCapPot2 = maqEqpCapPot2;
	}
	
	/**
	 * @return the maqEqpCapPot3
	 */
	public String getMaqEqpCapPot3() {
		return maqEqpCapPot3;
	}
	
	/**
	 * @param maqEqpCapPot3 the maqEqpCapPot3 to set
	 */
	public void setMaqEqpCapPot3(String maqEqpCapPot3) {
		this.maqEqpCapPot3 = maqEqpCapPot3;
	}
	
	/**
	 * @return the maqEqpCapPot4
	 */
	public String getMaqEqpCapPot4() {
		return maqEqpCapPot4;
	}
	
	/**
	 * @param maqEqpCapPot4 the maqEqpCapPot4 to set
	 */
	public void setMaqEqpCapPot4(String maqEqpCapPot4) {
		this.maqEqpCapPot4 = maqEqpCapPot4;
	}
	
	/**
	 * @return the maqEqpCapPot5
	 */
	public String getMaqEqpCapPot5() {
		return maqEqpCapPot5;
	}
	
	/**
	 * @param maqEqpCapPot5 the maqEqpCapPot5 to set
	 */
	public void setMaqEqpCapPot5(String maqEqpCapPot5) {
		this.maqEqpCapPot5 = maqEqpCapPot5;
	}
	
	/**
	 * @return the eqpTrnNU1
	 */
	public String getEqpTrnNU1() {
		return eqpTrnNU1;
	}
	
	/**
	 * @param eqpTrnNU1 the eqpTrnNU1 to set
	 */
	public void setEqpTrnNU1(String eqpTrnNU1) {
		this.eqpTrnNU1 = eqpTrnNU1;
	}
	
	/**
	 * @return the eqpTrnNU2
	 */
	public String getEqpTrnNU2() {
		return eqpTrnNU2;
	}
	
	/**
	 * @param eqpTrnNU2 the eqpTrnNU2 to set
	 */
	public void setEqpTrnNU2(String eqpTrnNU2) {
		this.eqpTrnNU2 = eqpTrnNU2;
	}
	
	/**
	 * @return the eqpTrnNU3
	 */
	public String getEqpTrnNU3() {
		return eqpTrnNU3;
	}
	
	/**
	 * @param eqpTrnNU3 the eqpTrnNU3 to set
	 */
	public void setEqpTrnNU3(String eqpTrnNU3) {
		this.eqpTrnNU3 = eqpTrnNU3;
	}
	
	/**
	 * @return the eqpTrnNU4
	 */
	public String getEqpTrnNU4() {
		return eqpTrnNU4;
	}
	
	/**
	 * @param eqpTrnNU4 the eqpTrnNU4 to set
	 */
	public void setEqpTrnNU4(String eqpTrnNU4) {
		this.eqpTrnNU4 = eqpTrnNU4;
	}
	
	/**
	 * @return the eqpTrnNU5
	 */
	public String getEqpTrnNU5() {
		return eqpTrnNU5;
	}
	
	/**
	 * @param eqpTrnNU5 the eqpTrnNU5 to set
	 */
	public void setEqpTrnNU5(String eqpTrnNU5) {
		this.eqpTrnNU5 = eqpTrnNU5;
	}
	
	/**
	 * @return the eqpTrnNom1
	 */
	public String getEqpTrnNom1() {
		return eqpTrnNom1;
	}
	
	/**
	 * @param eqpTrnNom1 the eqpTrnNom1 to set
	 */
	public void setEqpTrnNom1(String eqpTrnNom1) {
		this.eqpTrnNom1 = eqpTrnNom1;
	}
	
	/**
	 * @return the eqpTrnNom2
	 */
	public String getEqpTrnNom2() {
		return eqpTrnNom2;
	}
	
	/**
	 * @param eqpTrnNom2 the eqpTrnNom2 to set
	 */
	public void setEqpTrnNom2(String eqpTrnNom2) {
		this.eqpTrnNom2 = eqpTrnNom2;
	}
	
	/**
	 * @return the eqpTrnNom3
	 */
	public String getEqpTrnNom3() {
		return eqpTrnNom3;
	}
	
	/**
	 * @param eqpTrnNom3 the eqpTrnNom3 to set
	 */
	public void setEqpTrnNom3(String eqpTrnNom3) {
		this.eqpTrnNom3 = eqpTrnNom3;
	}
	
	/**
	 * @return the eqpTrnNom4
	 */
	public String getEqpTrnNom4() {
		return eqpTrnNom4;
	}
	
	/**
	 * @param eqpTrnNom4 the eqpTrnNom4 to set
	 */
	public void setEqpTrnNom4(String eqpTrnNom4) {
		this.eqpTrnNom4 = eqpTrnNom4;
	}
	
	/**
	 * @return the eqpTrnNom5
	 */
	public String getEqpTrnNom5() {
		return eqpTrnNom5;
	}
	
	/**
	 * @param eqpTrnNom5 the eqpTrnNom5 to set
	 */
	public void setEqpTrnNom5(String eqpTrnNom5) {
		this.eqpTrnNom5 = eqpTrnNom5;
	}
	
	/**
	 * @return the eqpTrnUso1
	 */
	public String getEqpTrnUso1() {
		return eqpTrnUso1;
	}
	
	/**
	 * @param eqpTrnUso1 the eqpTrnUso1 to set
	 */
	public void setEqpTrnUso1(String eqpTrnUso1) {
		this.eqpTrnUso1 = eqpTrnUso1;
	}
	
	/**
	 * @return the eqpTrnUso2
	 */
	public String getEqpTrnUso2() {
		return eqpTrnUso2;
	}
	
	/**
	 * @param eqpTrnUso2 the eqpTrnUso2 to set
	 */
	public void setEqpTrnUso2(String eqpTrnUso2) {
		this.eqpTrnUso2 = eqpTrnUso2;
	}
	
	/**
	 * @return the eqpTrnUso3
	 */
	public String getEqpTrnUso3() {
		return eqpTrnUso3;
	}
	
	/**
	 * @param eqpTrnUso3 the eqpTrnUso3 to set
	 */
	public void setEqpTrnUso3(String eqpTrnUso3) {
		this.eqpTrnUso3 = eqpTrnUso3;
	}
	
	/**
	 * @return the eqpTrnUso4
	 */
	public String getEqpTrnUso4() {
		return eqpTrnUso4;
	}
	
	/**
	 * @param eqpTrnUso4 the eqpTrnUso4 to set
	 */
	public void setEqpTrnUso4(String eqpTrnUso4) {
		this.eqpTrnUso4 = eqpTrnUso4;
	}
	
	/**
	 * @return the eqpTrnUso5
	 */
	public String getEqpTrnUso5() {
		return eqpTrnUso5;
	}
	
	/**
	 * @param eqpTrnUso5 the eqpTrnUso5 to set
	 */
	public void setEqpTrnUso5(String eqpTrnUso5) {
		this.eqpTrnUso5 = eqpTrnUso5;
	}
	
	/**
	 * @return the eqpTrnTpo1
	 */
	public String getEqpTrnTpo1() {
		return eqpTrnTpo1;
	}
	
	/**
	 * @param eqpTrnTpo1 the eqpTrnTpo1 to set
	 */
	public void setEqpTrnTpo1(String eqpTrnTpo1) {
		this.eqpTrnTpo1 = eqpTrnTpo1;
	}
	
	/**
	 * @return the eqpTrnTpo2
	 */
	public String getEqpTrnTpo2() {
		return eqpTrnTpo2;
	}
	
	/**
	 * @param eqpTrnTpo2 the eqpTrnTpo2 to set
	 */
	public void setEqpTrnTpo2(String eqpTrnTpo2) {
		this.eqpTrnTpo2 = eqpTrnTpo2;
	}
	
	/**
	 * @return the eqpTrnTpo3
	 */
	public String getEqpTrnTpo3() {
		return eqpTrnTpo3;
	}
	
	/**
	 * @param eqpTrnTpo3 the eqpTrnTpo3 to set
	 */
	public void setEqpTrnTpo3(String eqpTrnTpo3) {
		this.eqpTrnTpo3 = eqpTrnTpo3;
	}
	
	/**
	 * @return the eqpTrnTpo4
	 */
	public String getEqpTrnTpo4() {
		return eqpTrnTpo4;
	}
	
	/**
	 * @param eqpTrnTpo4 the eqpTrnTpo4 to set
	 */
	public void setEqpTrnTpo4(String eqpTrnTpo4) {
		this.eqpTrnTpo4 = eqpTrnTpo4;
	}
	
	/**
	 * @return the eqpTrnTpo5
	 */
	public String getEqpTrnTpo5() {
		return eqpTrnTpo5;
	}
	
	/**
	 * @param eqpTrnTpo5 the eqpTrnTpo5 to set
	 */
	public void setEqpTrnTpo5(String eqpTrnTpo5) {
		this.eqpTrnTpo5 = eqpTrnTpo5;
	}
	
	/**
	 * @return the eqpTrnCapPot1
	 */
	public String getEqpTrnCapPot1() {
		return eqpTrnCapPot1;
	}
	
	/**
	 * @param eqpTrnCapPot1 the eqpTrnCapPot1 to set
	 */
	public void setEqpTrnCapPot1(String eqpTrnCapPot1) {
		this.eqpTrnCapPot1 = eqpTrnCapPot1;
	}
	
	/**
	 * @return the eqpTrnCapPot2
	 */
	public String getEqpTrnCapPot2() {
		return eqpTrnCapPot2;
	}
	
	/**
	 * @param eqpTrnCapPot2 the eqpTrnCapPot2 to set
	 */
	public void setEqpTrnCapPot2(String eqpTrnCapPot2) {
		this.eqpTrnCapPot2 = eqpTrnCapPot2;
	}
	
	/**
	 * @return the eqpTrnCapPot3
	 */
	public String getEqpTrnCapPot3() {
		return eqpTrnCapPot3;
	}
	
	/**
	 * @param eqpTrnCapPot3 the eqpTrnCapPot3 to set
	 */
	public void setEqpTrnCapPot3(String eqpTrnCapPot3) {
		this.eqpTrnCapPot3 = eqpTrnCapPot3;
	}
	
	/**
	 * @return the eqpTrnCapPot4
	 */
	public String getEqpTrnCapPot4() {
		return eqpTrnCapPot4;
	}
	
	/**
	 * @param eqpTrnCapPot4 the eqpTrnCapPot4 to set
	 */
	public void setEqpTrnCapPot4(String eqpTrnCapPot4) {
		this.eqpTrnCapPot4 = eqpTrnCapPot4;
	}
	
	/**
	 * @return the eqpTrnCapPot5
	 */
	public String getEqpTrnCapPot5() {
		return eqpTrnCapPot5;
	}
	
	/**
	 * @param eqpTrnCapPot5 the eqpTrnCapPot5 to set
	 */
	public void setEqpTrnCapPot5(String eqpTrnCapPot5) {
		this.eqpTrnCapPot5 = eqpTrnCapPot5;
	}
	
	/**
	 * @return the perNoTrab1
	 */
	public String getPerNoTrab1() {
		return perNoTrab1;
	}
	
	/**
	 * @param perNoTrab1 the perNoTrab1 to set
	 */
	public void setPerNoTrab1(String perNoTrab1) {
		this.perNoTrab1 = perNoTrab1;
	}
	
	/**
	 * @return the perNoTrab2
	 */
	public String getPerNoTrab2() {
		return perNoTrab2;
	}
	
	/**
	 * @param perNoTrab2 the perNoTrab2 to set
	 */
	public void setPerNoTrab2(String perNoTrab2) {
		this.perNoTrab2 = perNoTrab2;
	}
	
	/**
	 * @return the perNoTrab3
	 */
	public String getPerNoTrab3() {
		return perNoTrab3;
	}
	
	/**
	 * @param perNoTrab3 the perNoTrab3 to set
	 */
	public void setPerNoTrab3(String perNoTrab3) {
		this.perNoTrab3 = perNoTrab3;
	}
	
	/**
	 * @return the perNoTrab4
	 */
	public String getPerNoTrab4() {
		return perNoTrab4;
	}
	
	/**
	 * @param perNoTrab4 the perNoTrab4 to set
	 */
	public void setPerNoTrab4(String perNoTrab4) {
		this.perNoTrab4 = perNoTrab4;
	}
	
	/**
	 * @return the perNoTrab5
	 */
	public String getPerNoTrab5() {
		return perNoTrab5;
	}
	
	/**
	 * @param perNoTrab5 the perNoTrab5 to set
	 */
	public void setPerNoTrab5(String perNoTrab5) {
		this.perNoTrab5 = perNoTrab5;
	}
	
	/**
	 * @return the perNoTrab6
	 */
	public String getPerNoTrab6() {
		return perNoTrab6;
	}
	
	/**
	 * @param perNoTrab6 the perNoTrab6 to set
	 */
	public void setPerNoTrab6(String perNoTrab6) {
		this.perNoTrab6 = perNoTrab6;
	}
	
	/**
	 * @return the perNoTrab7
	 */
	public String getPerNoTrab7() {
		return perNoTrab7;
	}
	
	/**
	 * @param perNoTrab7 the perNoTrab7 to set
	 */
	public void setPerNoTrab7(String perNoTrab7) {
		this.perNoTrab7 = perNoTrab7;
	}
	
	/**
	 * @return the perNoTrab8
	 */
	public String getPerNoTrab8() {
		return perNoTrab8;
	}
	
	/**
	 * @param perNoTrab8 the perNoTrab8 to set
	 */
	public void setPerNoTrab8(String perNoTrab8) {
		this.perNoTrab8 = perNoTrab8;
	}
	
	/**
	 * @return the perNoTrab9
	 */
	public String getPerNoTrab9() {
		return perNoTrab9;
	}
	
	/**
	 * @param perNoTrab9 the perNoTrab9 to set
	 */
	public void setPerNoTrab9(String perNoTrab9) {
		this.perNoTrab9 = perNoTrab9;
	}
	
	/**
	 * @return the perNoTrab10
	 */
	public String getPerNoTrab10() {
		return perNoTrab10;
	}
	
	/**
	 * @param perNoTrab10 the perNoTrab10 to set
	 */
	public void setPerNoTrab10(String perNoTrab10) {
		this.perNoTrab10 = perNoTrab10;
	}
	
	/**
	 * @return the perNoTrab11
	 */
	public String getPerNoTrab11() {
		return perNoTrab11;
	}
	
	/**
	 * @param perNoTrab11 the perNoTrab11 to set
	 */
	public void setPerNoTrab11(String perNoTrab11) {
		this.perNoTrab11 = perNoTrab11;
	}
	
	/**
	 * @return the perNoTrab12
	 */
	public String getPerNoTrab12() {
		return perNoTrab12;
	}
	
	/**
	 * @param perNoTrab12 the perNoTrab12 to set
	 */
	public void setPerNoTrab12(String perNoTrab12) {
		this.perNoTrab12 = perNoTrab12;
	}
	
	/**
	 * @return the nomComercial
	 */
	public String getNomComercial() {
		return nomComercial;
	}
	
	/**
	 * @param nomComercial the nomComercial to set
	 */
	public void setNomComercial(String nomComercial) {
		this.nomComercial = nomComercial;
	}
	
	/**
	 * @return the noCentTrab
	 */
	public Integer getNoCentTrab() {
		return noCentTrab;
	}
	
	/**
	 * @param noCentTrab the noCentTrab to set
	 */
	public void setNoCentTrab(Integer noCentTrab) {
		this.noCentTrab = noCentTrab;
	}
	
	/**
	 * @return the prestaServPersNo
	 */
	public String getPrestaServPersNo() {
		return prestaServPersNo;
	}
	
	/**
	 * @param prestaServPersNo the prestaServPersNo to set
	 */
	public void setPrestaServPersNo(String prestaServPersNo) {
		this.prestaServPersNo = prestaServPersNo;
	}
	
	/**
	 * @return the prestaServPersSi
	 */
	public String getPrestaServPersSi() {
		return prestaServPersSi;
	}
	
	/**
	 * @param prestaServPersSi the prestaServPersSi to set
	 */
	public void setPrestaServPersSi(String prestaServPersSi) {
		this.prestaServPersSi = prestaServPersSi;
	}
	
	/**
	 * @return the solRegPatronalClase
	 */
	public String getSolRegPatronalClase() {
		return solRegPatronalClase;
	}
	
	/**
	 * @param solRegPatronalClase the solRegPatronalClase to set
	 */
	public void setSolRegPatronalClase(String solRegPatronalClase) {
		this.solRegPatronalClase = solRegPatronalClase;
	}
	
	/**
	 * @return the procesoInicial1
	 */
	public String getProcesoInicial1() {
		return procesoInicial1;
	}
	
	/**
	 * @param procesoInicial1 the procesoInicial1 to set
	 */
	public void setProcesoInicial1(String procesoInicial1) {
		this.procesoInicial1 = procesoInicial1;
	}
	
	/**
	 * @return the procesoIntermedio1
	 */
	public String getProcesoIntermedio1() {
		return procesoIntermedio1;
	}
	
	/**
	 * @param procesoIntermedio1 the procesoIntermedio1 to set
	 */
	public void setProcesoIntermedio1(String procesoIntermedio1) {
		this.procesoIntermedio1 = procesoIntermedio1;
	}
	
	/**
	 * @return the procesoFinal1
	 */
	public String getProcesoFinal1() {
		return procesoFinal1;
	}
	
	/**
	 * @param procesoFinal1 the procesoFinal1 to set
	 */
	public void setProcesoFinal1(String procesoFinal1) {
		this.procesoFinal1 = procesoFinal1;
	}
	
	/**
	 * @return the perOficOcup1
	 */
	public String getPerOficOcup1() {
		return perOficOcup1;
	}
	
	/**
	 * @param perOficOcup1 the perOficOcup1 to set
	 */
	public void setPerOficOcup1(String perOficOcup1) {
		this.perOficOcup1 = perOficOcup1;
	}
	
	/**
	 * @return the perOficOcup2
	 */
	public String getPerOficOcup2() {
		return perOficOcup2;
	}
	
	/**
	 * @param perOficOcup2 the perOficOcup2 to set
	 */
	public void setPerOficOcup2(String perOficOcup2) {
		this.perOficOcup2 = perOficOcup2;
	}
	
	/**
	 * @return the perOficOcup3
	 */
	public String getPerOficOcup3() {
		return perOficOcup3;
	}
	
	/**
	 * @param perOficOcup3 the perOficOcup3 to set
	 */
	public void setPerOficOcup3(String perOficOcup3) {
		this.perOficOcup3 = perOficOcup3;
	}
	
	/**
	 * @return the perOficOcup4
	 */
	public String getPerOficOcup4() {
		return perOficOcup4;
	}
	
	/**
	 * @param perOficOcup4 the perOficOcup4 to set
	 */
	public void setPerOficOcup4(String perOficOcup4) {
		this.perOficOcup4 = perOficOcup4;
	}
	
	/**
	 * @return the perOficOcup5
	 */
	public String getPerOficOcup5() {
		return perOficOcup5;
	}
	
	/**
	 * @param perOficOcup5 the perOficOcup5 to set
	 */
	public void setPerOficOcup5(String perOficOcup5) {
		this.perOficOcup5 = perOficOcup5;
	}
	
	/**
	 * @return the perOficOcup6
	 */
	public String getPerOficOcup6() {
		return perOficOcup6;
	}
	
	/**
	 * @param perOficOcup6 the perOficOcup6 to set
	 */
	public void setPerOficOcup6(String perOficOcup6) {
		this.perOficOcup6 = perOficOcup6;
	}
	
	/**
	 * @return the perOficOcup7
	 */
	public String getPerOficOcup7() {
		return perOficOcup7;
	}
	
	/**
	 * @param perOficOcup7 the perOficOcup7 to set
	 */
	public void setPerOficOcup7(String perOficOcup7) {
		this.perOficOcup7 = perOficOcup7;
	}
	
	/**
	 * @return the perOficOcup8
	 */
	public String getPerOficOcup8() {
		return perOficOcup8;
	}
	
	/**
	 * @param perOficOcup8 the perOficOcup8 to set
	 */
	public void setPerOficOcup8(String perOficOcup8) {
		this.perOficOcup8 = perOficOcup8;
	}
	
	/**
	 * @return the perOficOcup9
	 */
	public String getPerOficOcup9() {
		return perOficOcup9;
	}
	
	/**
	 * @param perOficOcup9 the perOficOcup9 to set
	 */
	public void setPerOficOcup9(String perOficOcup9) {
		this.perOficOcup9 = perOficOcup9;
	}
	
	/**
	 * @return the perOficOcup10
	 */
	public String getPerOficOcup10() {
		return perOficOcup10;
	}
	
	/**
	 * @param perOficOcup10 the perOficOcup10 to set
	 */
	public void setPerOficOcup10(String perOficOcup10) {
		this.perOficOcup10 = perOficOcup10;
	}
	
	/**
	 * @return the perOficOcup11
	 */
	public String getPerOficOcup11() {
		return perOficOcup11;
	}
	
	/**
	 * @param perOficOcup11 the perOficOcup11 to set
	 */
	public void setPerOficOcup11(String perOficOcup11) {
		this.perOficOcup11 = perOficOcup11;
	}
	
	/**
	 * @return the perOficOcup12
	 */
	public String getPerOficOcup12() {
		return perOficOcup12;
	}
	
	/**
	 * @param perOficOcup12 the perOficOcup12 to set
	 */
	public void setPerOficOcup12(String perOficOcup12) {
		this.perOficOcup12 = perOficOcup12;
	}
	
	/**
	 * @return the apPaterno1
	 */
	public String getApPaterno1() {
		return apPaterno1;
	}
	
	/**
	 * @param apPaterno1 the apPaterno1 to set
	 */
	public void setApPaterno1(String apPaterno1) {
		this.apPaterno1 = apPaterno1;
	}
	
	/**
	 * @return the apMaterno1
	 */
	public String getApMaterno1() {
		return apMaterno1;
	}
	
	/**
	 * @param apMaterno1 the apMaterno1 to set
	 */
	public void setApMaterno1(String apMaterno1) {
		this.apMaterno1 = apMaterno1;
	}
	
	/**
	 * @return the nombre1
	 */
	public String getNombre1() {
		return nombre1;
	}
	
	/**
	 * @param nombre1 the nombre1 to set
	 */
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	
	/**
	 * @return the rfc1
	 */
	public String getRfc1() {
		return rfc1;
	}
	
	/**
	 * @param rfc1 the rfc1 to set
	 */
	public void setRfc1(String rfc1) {
		this.rfc1 = rfc1;
	}
	
	/**
	 * @return the curp1
	 */
	public String getCurp1() {
		return curp1;
	}
	
	/**
	 * @param curp1 the curp1 to set
	 */
	public void setCurp1(String curp1) {
		this.curp1 = curp1;
	}
	
	/**
	 * @return the calle1
	 */
	public String getCalle1() {
		return calle1;
	}
	
	/**
	 * @param calle1 the calle1 to set
	 */
	public void setCalle1(String calle1) {
		this.calle1 = calle1;
	}
	
	/**
	 * @return the numExt1
	 */
	public String getNumExt1() {
		return numExt1;
	}
	
	/**
	 * @param numExt1 the numExt1 to set
	 */
	public void setNumExt1(String numExt1) {
		this.numExt1 = numExt1;
	}
	
	/**
	 * @return the numInt1
	 */
	public String getNumInt1() {
		return numInt1;
	}
	
	/**
	 * @param numInt1 the numInt1 to set
	 */
	public void setNumInt1(String numInt1) {
		this.numInt1 = numInt1;
	}
	
	/**
	 * @return the colonia1
	 */
	public String getColonia1() {
		return colonia1;
	}
	
	/**
	 * @param colonia1 the colonia1 to set
	 */
	public void setColonia1(String colonia1) {
		this.colonia1 = colonia1;
	}
	
	/**
	 * @return the localidad1
	 */
	public String getLocalidad1() {
		return localidad1;
	}
	
	/**
	 * @param localidad1 the localidad1 to set
	 */
	public void setLocalidad1(String localidad1) {
		this.localidad1 = localidad1;
	}
	
	/**
	 * @return the munDelegacion1
	 */
	public String getMunDelegacion1() {
		return munDelegacion1;
	}
	
	/**
	 * @param munDelegacion1 the munDelegacion1 to set
	 */
	public void setMunDelegacion1(String munDelegacion1) {
		this.munDelegacion1 = munDelegacion1;
	}
	
	/**
	 * @return the entFederativa1
	 */
	public String getEntFederativa1() {
		return entFederativa1;
	}
	
	/**
	 * @param entFederativa1 the entFederativa1 to set
	 */
	public void setEntFederativa1(String entFederativa1) {
		this.entFederativa1 = entFederativa1;
	}
	
	/**
	 * @return the codPost1
	 */
	public String getCodPost1() {
		return codPost1;
	}
	
	/**
	 * @param codPost1 the codPost1 to set
	 */
	public void setCodPost1(String codPost1) {
		this.codPost1 = codPost1;
	}
	
	/**
	 * @return the telfijo1
	 */
	public String getTelfijo1() {
		return telfijo1;
	}
	
	/**
	 * @param telfijo1 the telfijo1 to set
	 */
	public void setTelfijo1(String telfijo1) {
		this.telfijo1 = telfijo1;
	}
	
	/**
	 * @return the email1
	 */
	public String getEmail1() {
		return email1;
	}
	
	/**
	 * @param email1 the email1 to set
	 */
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	/**
	 * @return the apPaterno2
	 */
	public String getApPaterno2() {
		return apPaterno2;
	}
	
	/**
	 * @param apPaterno2 the apPaterno2 to set
	 */
	public void setApPaterno2(String apPaterno2) {
		this.apPaterno2 = apPaterno2;
	}
	
	/**
	 * @return the apMaterno2
	 */
	public String getApMaterno2() {
		return apMaterno2;
	}
	
	/**
	 * @param apMaterno2 the apMaterno2 to set
	 */
	public void setApMaterno2(String apMaterno2) {
		this.apMaterno2 = apMaterno2;
	}
	
	/**
	 * @return the nombre2
	 */
	public String getNombre2() {
		return nombre2;
	}
	
	/**
	 * @param nombre2 the nombre2 to set
	 */
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	
	/**
	 * @return the rfc2
	 */
	public String getRfc2() {
		return rfc2;
	}
	
	/**
	 * @param rfc2 the rfc2 to set
	 */
	public void setRfc2(String rfc2) {
		this.rfc2 = rfc2;
	}
	
	/**
	 * @return the curp2
	 */
	public String getCurp2() {
		return curp2;
	}
	
	/**
	 * @param curp2 the curp2 to set
	 */
	public void setCurp2(String curp2) {
		this.curp2 = curp2;
	}
	
	/**
	 * @return the calle2
	 */
	public String getCalle2() {
		return calle2;
	}
	
	/**
	 * @param calle2 the calle2 to set
	 */
	public void setCalle2(String calle2) {
		this.calle2 = calle2;
	}
	
	/**
	 * @return the numExt2
	 */
	public String getNumExt2() {
		return numExt2;
	}
	
	/**
	 * @param numExt2 the numExt2 to set
	 */
	public void setNumExt2(String numExt2) {
		this.numExt2 = numExt2;
	}
	
	/**
	 * @return the numInt2
	 */
	public String getNumInt2() {
		return numInt2;
	}
	
	/**
	 * @param numInt2 the numInt2 to set
	 */
	public void setNumInt2(String numInt2) {
		this.numInt2 = numInt2;
	}
	
	/**
	 * @return the colonia2
	 */
	public String getColonia2() {
		return colonia2;
	}
	
	/**
	 * @param colonia2 the colonia2 to set
	 */
	public void setColonia2(String colonia2) {
		this.colonia2 = colonia2;
	}
	
	/**
	 * @return the localidad2
	 */
	public String getLocalidad2() {
		return localidad2;
	}
	
	/**
	 * @param localidad2 the localidad2 to set
	 */
	public void setLocalidad2(String localidad2) {
		this.localidad2 = localidad2;
	}
	
	/**
	 * @return the munDelegacion2
	 */
	public String getMunDelegacion2() {
		return munDelegacion2;
	}
	
	/**
	 * @param munDelegacion2 the munDelegacion2 to set
	 */
	public void setMunDelegacion2(String munDelegacion2) {
		this.munDelegacion2 = munDelegacion2;
	}
	
	/**
	 * @return the entFederativa2
	 */
	public String getEntFederativa2() {
		return entFederativa2;
	}
	
	/**
	 * @param entFederativa2 the entFederativa2 to set
	 */
	public void setEntFederativa2(String entFederativa2) {
		this.entFederativa2 = entFederativa2;
	}
	
	/**
	 * @return the codPost2
	 */
	public String getCodPost2() {
		return codPost2;
	}
	
	/**
	 * @param codPost2 the codPost2 to set
	 */
	public void setCodPost2(String codPost2) {
		this.codPost2 = codPost2;
	}
	
	/**
	 * @return the telfijo2
	 */
	public String getTelfijo2() {
		return telfijo2;
	}
	
	/**
	 * @param telfijo2 the telfijo2 to set
	 */
	public void setTelfijo2(String telfijo2) {
		this.telfijo2 = telfijo2;
	}
	
	/**
	 * @return the email2
	 */
	public String getEmail2() {
		return email2;
	}
	
	/**
	 * @param email2 the email2 to set
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	/**
	 * @return the apPaterno3
	 */
	public String getApPaterno3() {
		return apPaterno3;
	}
	
	/**
	 * @param apPaterno3 the apPaterno3 to set
	 */
	public void setApPaterno3(String apPaterno3) {
		this.apPaterno3 = apPaterno3;
	}
	
	/**
	 * @return the apMaterno3
	 */
	public String getApMaterno3() {
		return apMaterno3;
	}
	
	/**
	 * @param apMaterno3 the apMaterno3 to set
	 */
	public void setApMaterno3(String apMaterno3) {
		this.apMaterno3 = apMaterno3;
	}
	
	/**
	 * @return the nombre3
	 */
	public String getNombre3() {
		return nombre3;
	}
	
	/**
	 * @param nombre3 the nombre3 to set
	 */
	public void setNombre3(String nombre3) {
		this.nombre3 = nombre3;
	}
	
	/**
	 * @return the rfc3
	 */
	public String getRfc3() {
		return rfc3;
	}
	
	/**
	 * @param rfc3 the rfc3 to set
	 */
	public void setRfc3(String rfc3) {
		this.rfc3 = rfc3;
	}
	
	/**
	 * @return the curp3
	 */
	public String getCurp3() {
		return curp3;
	}
	
	/**
	 * @param curp3 the curp3 to set
	 */
	public void setCurp3(String curp3) {
		this.curp3 = curp3;
	}
	
	/**
	 * @return the calle3
	 */
	public String getCalle3() {
		return calle3;
	}
	
	/**
	 * @param calle3 the calle3 to set
	 */
	public void setCalle3(String calle3) {
		this.calle3 = calle3;
	}
	
	/**
	 * @return the numExt3
	 */
	public String getNumExt3() {
		return numExt3;
	}
	
	/**
	 * @param numExt3 the numExt3 to set
	 */
	public void setNumExt3(String numExt3) {
		this.numExt3 = numExt3;
	}
	
	/**
	 * @return the numInt3
	 */
	public String getNumInt3() {
		return numInt3;
	}
	
	/**
	 * @param numInt3 the numInt3 to set
	 */
	public void setNumInt3(String numInt3) {
		this.numInt3 = numInt3;
	}
	
	/**
	 * @return the colonia3
	 */
	public String getColonia3() {
		return colonia3;
	}
	
	/**
	 * @param colonia3 the colonia3 to set
	 */
	public void setColonia3(String colonia3) {
		this.colonia3 = colonia3;
	}
	
	/**
	 * @return the localidad3
	 */
	public String getLocalidad3() {
		return localidad3;
	}
	
	/**
	 * @param localidad3 the localidad3 to set
	 */
	public void setLocalidad3(String localidad3) {
		this.localidad3 = localidad3;
	}
	
	/**
	 * @return the munDelegacion3
	 */
	public String getMunDelegacion3() {
		return munDelegacion3;
	}
	
	/**
	 * @param munDelegacion3 the munDelegacion3 to set
	 */
	public void setMunDelegacion3(String munDelegacion3) {
		this.munDelegacion3 = munDelegacion3;
	}
	
	/**
	 * @return the entFederativa3
	 */
	public String getEntFederativa3() {
		return entFederativa3;
	}
	
	/**
	 * @param entFederativa3 the entFederativa3 to set
	 */
	public void setEntFederativa3(String entFederativa3) {
		this.entFederativa3 = entFederativa3;
	}
	
	/**
	 * @return the codPost3
	 */
	public String getCodPost3() {
		return codPost3;
	}
	
	/**
	 * @param codPost3 the codPost3 to set
	 */
	public void setCodPost3(String codPost3) {
		this.codPost3 = codPost3;
	}
	
	/**
	 * @return the telfijo3
	 */
	public String getTelfijo3() {
		return telfijo3;
	}
	
	/**
	 * @param telfijo3 the telfijo3 to set
	 */
	public void setTelfijo3(String telfijo3) {
		this.telfijo3 = telfijo3;
	}
	
	/**
	 * @return the email3
	 */
	public String getEmail3() {
		return email3;
	}
	
	/**
	 * @param email3 the email3 to set
	 */
	public void setEmail3(String email3) {
		this.email3 = email3;
	}
	
	/**
	 * @return the apPaterno4
	 */
	public String getApPaterno4() {
		return apPaterno4;
	}
	
	/**
	 * @param apPaterno4 the apPaterno4 to set
	 */
	public void setApPaterno4(String apPaterno4) {
		this.apPaterno4 = apPaterno4;
	}
	
	/**
	 * @return the apMaterno4
	 */
	public String getApMaterno4() {
		return apMaterno4;
	}
	
	/**
	 * @param apMaterno4 the apMaterno4 to set
	 */
	public void setApMaterno4(String apMaterno4) {
		this.apMaterno4 = apMaterno4;
	}
	
	/**
	 * @return the nombre4
	 */
	public String getNombre4() {
		return nombre4;
	}
	
	/**
	 * @param nombre4 the nombre4 to set
	 */
	public void setNombre4(String nombre4) {
		this.nombre4 = nombre4;
	}
	
	/**
	 * @return the rfc4
	 */
	public String getRfc4() {
		return rfc4;
	}
	
	/**
	 * @param rfc4 the rfc4 to set
	 */
	public void setRfc4(String rfc4) {
		this.rfc4 = rfc4;
	}
	
	/**
	 * @return the curp4
	 */
	public String getCurp4() {
		return curp4;
	}
	
	/**
	 * @param curp4 the curp4 to set
	 */
	public void setCurp4(String curp4) {
		this.curp4 = curp4;
	}
	
	/**
	 * @return the calle4
	 */
	public String getCalle4() {
		return calle4;
	}
	
	/**
	 * @param calle4 the calle4 to set
	 */
	public void setCalle4(String calle4) {
		this.calle4 = calle4;
	}
	
	/**
	 * @return the numExt4
	 */
	public String getNumExt4() {
		return numExt4;
	}
	
	/**
	 * @param numExt4 the numExt4 to set
	 */
	public void setNumExt4(String numExt4) {
		this.numExt4 = numExt4;
	}
	
	/**
	 * @return the numInt4
	 */
	public String getNumInt4() {
		return numInt4;
	}
	
	/**
	 * @param numInt4 the numInt4 to set
	 */
	public void setNumInt4(String numInt4) {
		this.numInt4 = numInt4;
	}
	
	/**
	 * @return the colonia4
	 */
	public String getColonia4() {
		return colonia4;
	}
	
	/**
	 * @param colonia4 the colonia4 to set
	 */
	public void setColonia4(String colonia4) {
		this.colonia4 = colonia4;
	}
	
	/**
	 * @return the localidad4
	 */
	public String getLocalidad4() {
		return localidad4;
	}
	
	/**
	 * @param localidad4 the localidad4 to set
	 */
	public void setLocalidad4(String localidad4) {
		this.localidad4 = localidad4;
	}
	
	/**
	 * @return the munDelegacion4
	 */
	public String getMunDelegacion4() {
		return munDelegacion4;
	}
	
	/**
	 * @param munDelegacion4 the munDelegacion4 to set
	 */
	public void setMunDelegacion4(String munDelegacion4) {
		this.munDelegacion4 = munDelegacion4;
	}
	
	/**
	 * @return the entFederativa4
	 */
	public String getEntFederativa4() {
		return entFederativa4;
	}
	
	/**
	 * @param entFederativa4 the entFederativa4 to set
	 */
	public void setEntFederativa4(String entFederativa4) {
		this.entFederativa4 = entFederativa4;
	}
	
	/**
	 * @return the codPost4
	 */
	public String getCodPost4() {
		return codPost4;
	}
	
	/**
	 * @param codPost4 the codPost4 to set
	 */
	public void setCodPost4(String codPost4) {
		this.codPost4 = codPost4;
	}
	
	/**
	 * @return the telfijo4
	 */
	public String getTelfijo4() {
		return telfijo4;
	}
	
	/**
	 * @param telfijo4 the telfijo4 to set
	 */
	public void setTelfijo4(String telfijo4) {
		this.telfijo4 = telfijo4;
	}
	
	/**
	 * @return the email4
	 */
	public String getEmail4() {
		return email4;
	}
	
	/**
	 * @param email4 the email4 to set
	 */
	public void setEmail4(String email4) {
		this.email4 = email4;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public String getFirmaDigital() {
		return firmaDigital;
	}
	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
	}
	
	public void setNivelEducativo(String nivelEducativo){
		this.nivelEducativo = nivelEducativo;
	}
	public void setConfirmoConvenio(String confirmo) {
		this.confirmoConvenio = confirmo;
	}
	public String getNivelEducativo() {
		return this.nivelEducativo;
	}
	public String getConfirmoConvenio() {
		if (null == confirmoConvenio) {
			return "";
		}
		if ("true".equals(this.confirmoConvenio)){
			return "X";
		} else {
			return "";
		}
	}
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	
	public String getNumNotariaDigital() {
		return numNotariaDigital;
	}
	public void setNumNotariaDigital(String numNotariaDigital) {
		this.numNotariaDigital = numNotariaDigital;
	}
	/**
	 * @return the serieCertificado
	 */
	public String getSerieCertificado() {
		return serieCertificado;
	}
	/**
	 * @param serieCertificado the serieCertificado to set
	 */
	public void setSerieCertificado(String serieCertificado) {
		this.serieCertificado = serieCertificado;
	}
	/**
	 * @return the vigenciaFiel
	 */
	public String getVigenciaFiel() {
		return vigenciaFiel;
	}
	/**
	 * @param vigenciaFiel the vigenciaFiel to set
	 */
	public void setVigenciaFiel(String vigenciaFiel) {
		this.vigenciaFiel = vigenciaFiel;
	}

	public String getLOGO_IMSS_HEADER_PARAM() {
		return LOGO_IMSS_HEADER_PARAM;
	}
	public void setLOGO_IMSS_HEADER_PARAM(String lOGO_IMSS_HEADER_PARAM) {
		LOGO_IMSS_HEADER_PARAM = lOGO_IMSS_HEADER_PARAM;
	}
	public String getLOGO_INFO_HEADER_PARAM() {
		return LOGO_INFO_HEADER_PARAM;
	}
	public void setLOGO_INFO_HEADER_PARAM(String lOGO_INFO_HEADER_PARAM) {
		LOGO_INFO_HEADER_PARAM = lOGO_INFO_HEADER_PARAM;
	}

	
}
