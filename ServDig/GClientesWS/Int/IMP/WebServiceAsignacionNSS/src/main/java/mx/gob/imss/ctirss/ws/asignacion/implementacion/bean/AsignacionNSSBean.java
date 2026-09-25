//:AsignacionNSSBean.java
package mx.gob.imss.ctirss.ws.asignacion.implementacion.bean;

import java.io.Serializable;
import java.util.List;

/** Bean para guardar los datos de un trabajador que es ocupado en 
 *todo el flujo para la preafiliación y afiliación de trabajadores.
 *@author Alberto Beltran Murillo
 *@author Modificado por Acosta Trejo Julieta y Dulce Douglas Gallegos
 *@version 1.0
 */
public class AsignacionNSSBean implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
   private String CURP = "";
   private String ApellidoPaterno = "";
   private String ApellidoMaterno = "";
   private String Nombre;
   private int Sexo;
   private String SexoDes = "";
   private int LugarNacimiento;
   private String LugarNacimientoDes = "";
   private int Anio;
   private int Mes;
   private String MesDes = "";
   private int Dia;
   private int Anio2;
   private int Mes2;
   private int Dia2;
   private String tipoOperacion ="";
   private String strDelegacion ="";
   private String strSubDelegacion="";
   private int idTransaccion;
   /*Datos nuevos para la asignacion de NSS Internet*/
   private Boolean sinCurp ;
   private int lugarRegistro;
   private String lugarDesRegistro = "";
   private int lugarRegistrMun;
   private String lugarDesRegistroMun = "";
   private String direcciones;
   private List matriz=null;
   
   private int anioRegistro;
   private int numLibro;
   private int numfoja;
   private int numTomo;
   private int numActa;
   private String crip;
   private String curp_acta;
   private int mesRegistro;
   private int diaRegistro;
   private String mesDesRegistro = "";
   private  boolean chkSinCurp=false;
   private String IUMF;
   private String tipoDomicilio;
   private String captcha;
   
   
    private String tipoDomicio;//*
    private String domCalle;//*
	private String refNoExt;//*
	private String refNoInt;//*
	private String domEntreCalle1;//*
	private String domEntreCalle2;//*
	private String cveColonia;//*
	private String cveLocalidad;//*
	private String cveMunicipDeleg;//*
	private String entidadFederativa;//*
	private String refCodigoPostal;//*
	private String cveLocalidadDes;//*
	private String cveMunicipDelegDes;//*
	private String entidadFederativaDes;//*	
	private String hdnCveLocalidad;
	private String hdnCveColonia;
	private String numTelFijo1;//*
	private String numTelFijo2;//*
	private String numExtTel1;//*
	private String numExtTel2;//*
	private String domCorreoElec;//*
    private String direccionTrabajo;
    private String direccionCasa;
    private String direccionOtra;
   
   
   
public String getDireccionCasa() {
		return direccionCasa;
	}
	public void setDireccionCasa(String direccionCasa) {
		this.direccionCasa = direccionCasa;
	}
	public String getDireccionOtra() {
		return direccionOtra;
	}
	public void setDireccionOtra(String direccionOtra) {
		this.direccionOtra = direccionOtra;
	}
	public String getDireccionTrabajo() {
		return direccionTrabajo;
	}
	public void setDireccionTrabajo(String direccionTrabajo) {
		this.direccionTrabajo = direccionTrabajo;
	}
public String getIUMF() {
	return IUMF;
}
public void setIUMF(String iumf) {
	IUMF = iumf;
}
public boolean isChkSinCurp() {
	return chkSinCurp;
}
public void setChkSinCurp(boolean chkSinCurp) {
	this.chkSinCurp = chkSinCurp;
}
/**
 * @return Returns the idTransaccion.
 */
public int getIdTransaccion() {
    return idTransaccion;
}
/**
 * @param idTransaccion The idTransaccion to set.
 */
public void setIdTransaccion(int idTransaccion) {
    this.idTransaccion = idTransaccion;
}
/**
 * @return Returns the strDelegacion.
 */
public String getStrDelegacion() {
	return strDelegacion;
}
/**
 * @param strDelegacion The strDelegacion to set.
 */
public void setStrDelegacion(String strDelegacion) {
	this.strDelegacion = strDelegacion;
}
/**
 * @return Returns the strSubDelegacion.
 */
public String getStrSubDelegacion() {
	return strSubDelegacion;
}
/**
 * @param strSubDelegacion The strSubDelegacion to set.
 */
public void setStrSubDelegacion(String strSubDelegacion) {
	this.strSubDelegacion = strSubDelegacion;
}
/**
 * @return Returns the tipoOperacion.
 */
public String getTipoOperacion() {
	return tipoOperacion;
}
/**
 * @param tipoOperacion The tipoOperacion to set.
 */
public void setTipoOperacion(String tipoOperacion) {
	this.tipoOperacion = tipoOperacion;
}
   private String ApellidoPaternoPadre = "";
   private String ApellidoMaternoPadre = "";
   private String NombrePadre = "";
   private String ApellidoPaternoMadre = "";
   private String ApellidoMaternoMadre = "";
   private String NombreMadre = "";
   private int CodigoPostal;
   private int	UMF;
   private String UMFDes = "";
   private String seguridadSocial = "";
   private char PreafiliacionReingreso;
   private char VerificadorNSS;
   private int AnioIngreso;
   private int MesIngreso;
   private int DiaIngreso; 
   private double SalarioBase;
   private char JornadaSemana;
   private char TipoSalario;
   private char ClaveOcupacion = '0';
   private String DescripcionOcupacion = "";
   private char TipoTrabajador;
   private String SFolio = "";
   private int TipoTramite;
   private long idPatron;
   
  
   //para propositos de administracion
   private int terminoOk;
   private int folio;	
   private String fecha;

   //para la tabal SSCA_CANASE
   private short antecedentes;
   private String serie;

   // Objeto que guarda el contenido de un archivo
//   protected FormFile theFile;
   protected String correoElectronico;

	/**
	 * Constructor.
	 */
	
	public AsignacionNSSBean() {
		
	}
	/**
	 * Establece El CURP.
	 * @param CURP Un String que forma la CURP.
	 */
	public void setCURP(String CURP) {
		this.CURP = CURP.toUpperCase(); 
	}
	/**
	 * Establece El Apellido Paterno.
	 * @param ApellidoPAterno  Un String con el Apellido Paterno.
	 */
	public void setApellidoPaterno(String ApellidoPaterno) {
		this.ApellidoPaterno = (ApellidoPaterno.toUpperCase()).trim(); 
	}
	
	/**
	 * Establece El Apellido Materno.
	 * @param ApellidoMaterno Un String con el Apellido MAterno.
	 */
	public void setApellidoMaterno(String ApellidoMaterno) {
		this.ApellidoMaterno = (ApellidoMaterno.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Nombre.
	 * @param Nombre Un String con el  Nombre.
	 */
	public void setNombre(String Nombre) {
		this.Nombre = (Nombre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Sexo mediante un Numero.
	 * @param Sexo Un int que contiene 1 Hombre, 2 Mujer.
	 */
	public void setSexo(int Sexo) {
		this.Sexo = Sexo; 
	}

	/**
	 * Establece El Sexo mediante una Cadena.
	 * @param SexoDes Un String con la descripcion del sexo.
	 */
	public void setSexoDes(String SexoDes) {
		this.SexoDes = (SexoDes.toUpperCase()).trim(); 
	}

	/**
	 * Establece el lugar de Nacimiento mediante un Numero.
	 * @param LugarNacimiento Un int con el numero del estado que corresponde
	 * al lugar de naciemiento.
	 */
	public void setLugarNacimiento(int LugarNacimiento) {
		this.LugarNacimiento = LugarNacimiento; 
	}
	
	/**
	 * Establece el lugar de Nacimiento mediante una Cadena.
	 * @param LugarNacimientoDes Un String con la descripción del estado que corresponde
	 * al lugar de Nacimiento.
	 */
	public void setLugarNacimientoDes(String LugarNacimientoDes) {
		this.LugarNacimientoDes = (LugarNacimientoDes.toUpperCase()).trim(); 
	}

	/**
	 * Establece el Año de Nacimiento.
	 * @param Anio Un int con el Año de Nacimiento.
	 */
	public void setAnio(int Anio) {
		this.Anio = Anio; 
	}

	/**
	 * Establece el Mes de Nacimeinto mediante un numero.
	 * @param Mes Un int con el Mes de Nacimiento.
	 */
	public void setMes(int Mes) {
		this.Mes = Mes; 
	}

	/**
	 * Establece el Mes de Nacimiento Mediante una Cadena.
	 * @param MesDes Un String con la descripción del Mes de Nacimiento.
	 */
	public void setMesDes(String MesDes) {
		this.MesDes = (MesDes.toUpperCase()).trim(); 
	}

	/**
	 * Establece el Día de Nacimiento.
	 * @param Dia Un int con el dia de Nacimiento.
	 */
	public void setDia(int Dia) {
		this.Dia = Dia; 
	}

	/**
	 * Establece el Año2 de Nacimiento.
	 * @param Anio Un int con el Año de Nacimiento.
	 */
	public void setAnio2(int Anio2) {
		this.Anio2 = Anio2; 
	}

	/**
	 * Establece el Mes de Nacimeinto mediante un numero.
	 * @param Mes Un int con el Mes de Nacimiento.
	 */
	public void setMes2(int Mes2) {
		this.Mes2 = Mes2; 
	}

	/**
	 * Establece el Día de Nacimiento.
	 * @param Dia Un int con el dia de Nacimiento.
	 */
	public void setDia2(int Dia2) {
		this.Dia2 = Dia2; 
	}

	/**
	 * Establece El Apellido Paterno del Padre.
	 * @param ApellidoPaternoPadre Un String con el Apellido Paterno del Padre.
	 */
	public void setApellidoPaternoPadre(String ApellidoPaternoPadre) {
		this.ApellidoPaternoPadre = (ApellidoPaternoPadre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Apellido Materno del Padre.
	 * @param ApellidoMaternoPadre Un String con el Apellido Materno del Padre.
	 */
	public void setApellidoMaternoPadre(String ApellidoMaternoPadre) {
		this.ApellidoMaternoPadre = (ApellidoMaternoPadre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Nombre del Padre.
	 * @param NombrePadre Un String con el Nombre del Padre.
	 */
	public void setNombrePadre(String NombrePadre) {
		this.NombrePadre = (NombrePadre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Apellido Paterno de la Madre.
	 * @param ApellidoPaternoMadre Un String con el Apellido Paterno de la Madre.
	 */
	public void setApellidoPaternoMadre(String ApellidoPaternoMadre) {
		this.ApellidoPaternoMadre = (ApellidoPaternoMadre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Apellido Materno de la Madre.
	 * @param ApellidoMaternoMadre Un String con el Apellido Materno de la Madre.
	 */
	public void setApellidoMaternoMadre(String ApellidoMaternoMadre) {
		this.ApellidoMaternoMadre = (ApellidoMaternoMadre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Nombre de la Madre.
	 * @param NombreMadre Un String con el Nombre de la Madre.
	 */
	public void setNombreMadre(String NombreMadre) {
		this.NombreMadre = (NombreMadre.toUpperCase()).trim(); 
	}

	/**
	 * Establece El Codigo Postal.
	 * @param Un int con el Codigo Postal.
	 */
	public void setCodigoPostal(int CodigoPostal) {
		this.CodigoPostal = CodigoPostal; 
	}

	/**
	 * Establece La UMF (Unidad Medica Familiar).
	 * @param Un int con el numero de la UMF correpsondiente.
	 */
	public void setUMF(int UMF) {
		this.UMF = UMF; 
	}
	
	/**
	 * Establece La UMF (Unidad Medica Familiar).
	 * @param Un String la descipción de la UMF correpsondiente.
	 */
	public void setUMFDes(String UMFDes) {
		this.UMFDes = UMFDes; 
	}
	
	/**
	 * Establece El Numero de Seguridad Social Mediante una cadena.
	 * @param seguridadSocial Un String con el numero de Seguridad Social.
	 */
	public void setSeguridadSocial(String seguridadSocial) {
		this.seguridadSocial = (seguridadSocial.toUpperCase()).trim(); 
	}

	/**
	 * Establece La Preafiliación Reingreso
	 * @param PreafiliacionReingreso Un char que indica la Preafiliación y Reingreso.
	 */
	public void setPreafiliacionReingreso(char PreafiliacionReingreso) {
		this.PreafiliacionReingreso = PreafiliacionReingreso; 
	}

	/**
	 * Establece El Verificador del NSS (Numero de Seguridad Social).
	 * @param VerificadorNSS Un char con el Verificador del NSS.
	 */
	public void setVerificadorNSS(char VerificadorNSS) {
		this.VerificadorNSS = VerificadorNSS; 
	}

	/**
	 * Establece El año de Ingreso.
	 * @param AnioIngreso Un int con el Año de Ingreso.
	 */
	public void setAnioIngreso(int AnioIngreso) {
		this.AnioIngreso = AnioIngreso; 
	}

	/**
	 * Establece el Mes de Ingreso.
	 * @param MesIngreso Un int con el Mes de Ingreso.
	 */
	public void setMesIngreso(int MesIngreso) {
		this.MesIngreso = MesIngreso; 
	}

	/**
	 * Establece el Día de Ingreso.
	 * @param DiaIngreso Un int con el Día de Ingreso.
	 */
	public void setDiaIngreso(int DiaIngreso) {
		this.DiaIngreso = DiaIngreso; 
	}

	/**
	 * Establece el Salario Base.
	 * @param SalarioBase Un double con el Salario Base.
	 */
	public void setSalarioBase(double SalarioBase) {
		this.SalarioBase = SalarioBase; 
	}

	/**
	 * Establece la Jornada de la Semana.
	 * @param JornadaSemana Un char que especifica la Jornada de la Semana.
	 */
	public void setJornadaSemana(char JornadaSemana) {
		this.JornadaSemana = JornadaSemana; 
	}

	/**
	 * Establece el Tipo de Salario.
	 * @param TipoSalario Un char con el Tipo de Salario.
	 */
	public void setTipoSalario(char TipoSalario) {
		this.TipoSalario = TipoSalario; 
	}

	/**
	 * Establece la Clave de Ocupación.
	 * @param ClaveOcupacion Un char con al Clave de Ocupación.
	 */
	public void setClaveOcupacion(char ClaveOcupacion) {
		this.ClaveOcupacion = ClaveOcupacion; 
	}

	/**
	 * Establece la Descripcion de Ocupación.
	 * @param DescripcionOcupacion Un String con la Descripción de la Ocupación
	 */
	public void setDescripcionOcupacion(String DescripcionOcupacion) {
		this.DescripcionOcupacion = (DescripcionOcupacion.toUpperCase()).trim(); 
	}

	/**
	 * Establece el Tipo de Trabajador.
	 * @param TipoTrabajador Un char con el Tipo de Trabajador.
	 */
	public void setTipoTrabajador(char TipoTrabajador) {
		this.TipoTrabajador = TipoTrabajador; 
	}

	/**
	 * Establece Si Termino Correcto.
	 * @param terminoOk Un int que indica como fue el Termino.
	 */
	public void setTerminoOk(int terminoOk) {
		this.terminoOk = terminoOk; 
	}

	/**
	 * Establece el Folio mediante un Numero.
	 * @param folio Un int con el Numero de Folio.
	 */
	public void setFolio(int folio) {
		this.folio = folio; 
	}

	/**
	 * Establece la Fecha.
	 * @param fecha Un String con la fecha.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha; 
	}
	
	/**
	 * Establece el Folio mediante una Cadena.
	 * @param SFolio Un String con el  numero de Folio en un Cadena.
	 */
	public void setSFolio(String SFolio) {
		this.SFolio = SFolio;
	}
	
	/**
	 * Establece el Tipo de Tramite.
	 * @param TipoTramite Un int con el Tipo de Tramite.
	 */
	public void setTipoTramite(int TipoTramite) {
		this.TipoTramite = TipoTramite;
	}
	
	/**
	 * Establece los Antecedentes.
	 * @param antecedentes Un short que marca los Antecedentes.
	 */
	public void setAntecedentes(short antecedentes) {
		this.antecedentes = antecedentes; 
	}
	
	/**
	 * Establece la Serie.
	 * @param serie Un String que tiene la Serie.
	 */
	public void setSerie(String serie) {
		this.serie = serie; 
	}
	
	/**
	 * Establece el Id del Patron.
	 * @param id Un long con el Id del Patron.
	 */
	public void setIdPatron(long id) {
		this.idPatron = id; 
	}

	/**
     * Asigna el archivo que contiene la lista
	 * de trabajadores por medio magnetico
     */
//    public void setTheFile(FormFile theFile) {
//        this.theFile = theFile;
//    }
	
// getters

	/**
	 * Devuleve el CURP.
	 * @return Un String con el CURP.
	 */
	public String getCURP() {
		return (this.CURP); 
	}

	/**
	 * Devuleve el Apellido Paterno.
	 * @return Un String con el Apellido Paterno.
	 */
	public String getApellidoPaterno() {
		return (this.ApellidoPaterno); 
	}

	/**
	 * Devuleve el Apellido Materno.
	 * @return Un String con el Apellido Materno.
	 */
	public String getApellidoMaterno() {
		return (this.ApellidoMaterno); 
	}

	/**
	 * Devuleve el Nombre.
	 * @return Un String con el Nombre.
	 */
	public String getNombre() {
		return (this.Nombre); 
	}

	/**
	 * Devuleve el Sexo.
	 * @return Un int con el Sexo.
	 */
	public int getSexo() {
		return (this.Sexo); 
	}

	/**
	 * Devuleve la Descripción del Sexo.
	 * @return Un String con la Descripción del Sexo.
	 */
	public String getSexoDes() {
		return (this.SexoDes); 
	}

	/**
	 * Devuleve el Lugar de Nacimiento.
	 * @return Un int con el  Lugar de Nacimeinto.
	 */
	public int getLugarNacimiento() {
		return (this.LugarNacimiento); 
	}

	/**
	 * Devuleve la Descripción del Lugar de Nacimiento.
	 * @return Un String con la descripción del Lugar de Nacimiento.
	 */
	public String getLugarNacimientoDes() {
		return (this.LugarNacimientoDes); 
	}

	/**
	 * Devuleve el Año.
	 * @return Un int con el Año.
	 */
	public int getAnio() {
		return (this.Anio); 
	}

	/**
	 * Devuleve el Mes.
	 * @return Un int con el Mes.
	 */
	public int getMes() {
		return (this.Mes); 
	}

	/**
	 * Devuleve la Descripción del Mes.
	 * @return Un String con la Descripción del Mes.
	 */
	public String getMesDes() {
		return (this.MesDes); 
	}

	/**
	 * Devuleve el Día.
	 * @return Un int con el Día.
	 */
	public int getDia() {
		return (this.Dia); 
	}

	/**
	 * Devuleve el Año2.
	 * @return Un int con el Año2.
	 */
	public int getAnio2() {
		return (this.Anio2); 
	}

	/**
	 * Devuleve el Mes2.
	 * @return Un int con el Mes2.
	 */
	public int getMes2() {
		return (this.Mes2); 
	}

	/**
	 * Devuleve el Día2.
	 * @return Un int con el Día2.
	 */
	public int getDia2() {
		return (this.Dia2); 
	}
	
	/**
	 * Devuleve el Apellido Paterno del Padre.
	 * @return Un String con el Apellido Paterno del Padre.
	 */
	public String getApellidoPaternoPadre() {
		return (this.ApellidoPaternoPadre); 
	}

	/**
	 * Devuleve El Apellido Materno del Padre.
	 * @return Un String con el Apellido Materno del Padre.
	 */
	public String getApellidoMaternoPadre() {
		return (this.ApellidoMaternoPadre); 
	}

	/**
	 * Devuleve el Nombre del Padre.
	 * @return Un String con el Nombre del Padre.
	 */
	public String getNombrePadre() {
		return (this.NombrePadre); 
	}

	/**
	 * Devuleve el Apellido Paterno de la Madre.
	 * @return Un String con el Apellido Paterno de la Madre.
	 */
	public String getApellidoPaternoMadre() {
		return (this.ApellidoPaternoMadre); 
	}

	/**
	 * Devuleve el Apellido Materno de la Madre.
	 * @return Un String con el Apellido Materno de la Madre.
	 */
	public String getApellidoMaternoMadre() {
		return (this.ApellidoMaternoMadre); 
	}

	/**
	 * Devuleve el Nombre de la Madre.
	 * @return Un String con el Nombre de la Madre.
	 */
	public String getNombreMadre() {
		return (this.NombreMadre); 
	}

	/**
	 * Devuleve el Codigo Postal.
	 * @return Un int con el Codigo Postal.
	 */
	public int getCodigoPostal() {
		return (this.CodigoPostal); 
	}

	/**
	 * Devuleve la UMF (Unidad Medica Familiar).
	 * @return Un int con la UMF.
	 */
	public int getUMF() {
		return (this.UMF); 
	}
	
	/**
	 * Devuelve La UMF (Unidad Medica Familiar).
	 * @param Un String la descipción de la UMF correpsondiente.
	 */
	public String getUMFDes() {
		return (this.UMFDes); 
	}

	/**
	 * Devuleve el Numero de Seguridad Social.
	 * @return Un String con el Numero de SEguridad Social.
	 */
	public String getSeguridadSocial() {
		return (this.seguridadSocial); 
	}

	/**
	 * Devuleve la Preafiliación Reingreso.
	 * @return Un char con la Preafiliación Reingreso.
	 */
	public char getPreafiliacionReingreso() {
		return (this.PreafiliacionReingreso); 
	}

	/**
	 * Devuleve el Verificador del NSS (Numero de Seguridad Social).
	 * @return Un char con el Verificador del NSS.
	 */
	public char getVerificadorNSS() {
		return (this.VerificadorNSS); 
	}

	/**
	 * Devuleve el Año de Ingreso.
	 * @return Un int con el Año de Ingreso.
	 */
	public int getAnioIngreso() {
		return (this.AnioIngreso); 
	}

	/**
	 * Devuleve el Mes de Ingreso.
	 * @return Un int con el Mes de Ingreso.
	 */
	public int getMesIngreso() {
		return (this.MesIngreso); 
	}

	/**
	 * Devuleve el Día de Ingreso.
	 * @return Un int con el Día de Ingreso.
	 */
	public int getDiaIngreso() {
		return (this.DiaIngreso); 
	}

	/**
	 * Devuleve el Salario Base.
	 * @return Un double con el Salario Base.
	 */
	public double getSalarioBase() {
		return (this.SalarioBase); 
	}

	/**
	 * Devuleve la Jornada a la Semana.
	 * @return Un char con la Jornada a la Semana.
	 */
	public char getJornadaSemana() {
		return (this.JornadaSemana); 
	}

	/**
	 * Devuleve el Tipo de Salario.
	 * @return Un char con el Tipo de SAlario.
	 */
	public char getTipoSalario() {
		return (this.TipoSalario); 
	}

	/**
	 * Devuleve la Clave de Ocupación.
	 * @return Un char con la Clave de Ocupación
	 */
	public char getClaveOcupacion() {
		return (this.ClaveOcupacion); 
	}

	/**
	 * Devuleve la Descripción de la Ocupación.
	 * @return Un String con la Descripción de la Ocupación.
	 */
	public String getDescripcionOcupacion() {
		return (this.DescripcionOcupacion); 
	}

	/**
	 * Devuleve el Tipo de Trabajador.
	 * @return Un char con el Tipo de Trabajador.
	 */
	public char getTipoTrabajador() {
		return (this.TipoTrabajador); 
	}

	/**
	 * Devuleve el TerminoOk.
	 * @return Un int con el TerminoOk.
	 */
	public int getTerminoOk() {
		return (this.terminoOk); 
	}

	/**
	 * Devuleve  un numero de Folio.
	 * @return Un int con el Folio.
	 */
	public int getFolio() {
		return (this.folio); 
	}

	/**
	 * Devuleve  la Fecha.
	 * @return Un String con la Fecha.
	 */
	public String getFecha() {
		return (this.fecha); 
	}

	/**
	 * Devuleve el Folio como Cadena.
	 * @return Un String con el Folio.
	 */
	public String getSFolio() {
		return (this.SFolio);
	}

	/**
	 * Devuleve el Tipo de Tramite.
	 * @return Un int con el Tipo de Tramite.
	 */
	public int getTipoTramite() {
		return (this.TipoTramite); 
	}
	
	/**
	 * Devuleve los Antecedentes.
	 * @return Un short con los Antecedentes.
	 */
	public short getAntecedentes() {
		return (this.antecedentes);
	}
	
	/**
	 * Devuleve la Serie.
	 * @return Un String con la Serie.
	 */
	public String getSerie() {
		return (this.serie); 
	}
	
	/**
	 * Devuleve el Id del Patron
	 * @return Un Long con el Id del Patron.
	 */
	public long getIdPatron() {
		return (this.idPatron); 
	}

    /**
     * Devuelve el archivo que contiene la lista
	 * de trabajadores por medio magnetico
     */
//     public FormFile getTheFile() {
//        return theFile;
//     }

    /**
     * Imprime el contenido del Bean
     */
	public String toString() {
		StringBuffer bean = new StringBuffer();
		//bean.append ("\nfile = " + theFile);
		return bean.toString();
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Boolean getSinCurp() {
		return sinCurp;
	}
	public void setSinCurp(Boolean sinCurp) {
		this.sinCurp = sinCurp;
	}
	public int getAnioRegistro() {
		return anioRegistro;
	}
	public void setAnioRegistro(int AnioRegistro) {
		this.anioRegistro = AnioRegistro;
	}
	
	public String getLugarDesRegistro() {
		return lugarDesRegistro;
	}
	public void setLugarDesRegistro(String lugarDesRegistro) {
		this.lugarDesRegistro = lugarDesRegistro;
	}
	public String getLugarDesRegistroMun() {
		return lugarDesRegistroMun;
	}
	public void setLugarDesRegistroMun(String lugarDesRegistroMun) {
		this.lugarDesRegistroMun = lugarDesRegistroMun;
	}
	public int getLugarRegistrMun() {
		return lugarRegistrMun;
	}
	public void setLugarRegistrMun(int lugarRegistrMun) {
		this.lugarRegistrMun = lugarRegistrMun;
	}
	public int getLugarRegistro() {
		return lugarRegistro;
	}
	public void setLugarRegistro(int lugarRegistro) {
		this.lugarRegistro = lugarRegistro;
	}
	public int getNumfoja() {
		return numfoja;
	}
	public void setNumfoja(int numfoja) {
		this.numfoja = numfoja;
	}
	public int getNumLibro() {
		return numLibro;
	}
	public void setNumLibro(int numLibro) {
		this.numLibro = numLibro;
	}
	public int getNumTomo() {
		return numTomo;
	}
	public void setNumTomo(int numTomo) {
		this.numTomo = numTomo;
	}
	public String getCrip() {
		return crip;
	}
	public void setCrip(String crip) {
		this.crip = crip;
	}
	public String getCurp_acta() {
		return curp_acta;
	}
	public void setCurp_acta(String curp_acta) {
		this.curp_acta = curp_acta;
	}
	public int getNumActa() {
		return numActa;
	}
	public void setNumActa(int numActa) {
		this.numActa = numActa;
	}
	public int getDiaRegistro() {
		return diaRegistro;
	}
	public void setDiaRegistro(int diaRegistro) {
		this.diaRegistro = diaRegistro;
	}
	public String getMesDesRegistro() {
		return mesDesRegistro;
	}
	public void setMesDesRegistro(String mesDesRegistro) {
		mesDesRegistro = mesDesRegistro;
	}
	public int getMesRegistro() {
		return mesRegistro;
	}
	public void setMesRegistro(int mesRegistro) {
		mesRegistro = mesRegistro;
	}
	
	
	
	public List getMatriz() {
		return matriz;
	}

	/**
	 * @param matriz the matriz to set
	 */
	public void setMatriz(List matriz) {
		this.matriz = matriz;
	}

//	public void setPermiso( int index, DomicilioBean bean )	{
//		matriz.set( index, bean );
//	}
//	
//	public DomicilioBean getPermiso( int index )	{
//		return (DomicilioBean)matriz.get( index );
//	}
	public String getTipoDomicilio() {
		return tipoDomicilio;
	}
	public void setTipoDomicilio(String tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}
	public String getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(String direcciones) {
		this.direcciones = direcciones;
	}
	public String getCveColonia() {
		return cveColonia;
	}
	public void setCveColonia(String cveColonia) {
		this.cveColonia = cveColonia;
	}
	public String getCveLocalidad() {
		return cveLocalidad;
	}
	public void setCveLocalidad(String cveLocalidad) {
		this.cveLocalidad = cveLocalidad;
	}
	public String getCveMunicipDeleg() {
		return cveMunicipDeleg;
	}
	public void setCveMunicipDeleg(String cveMunicipDeleg) {
		this.cveMunicipDeleg = cveMunicipDeleg;
	}
	public String getDomCalle() {
		return domCalle;
	}
	public void setDomCalle(String domCalle) {
		this.domCalle = domCalle;
	}
	public String getDomCorreoElec() {
		return domCorreoElec;
	}
	public void setDomCorreoElec(String domCorreoElec) {
		this.domCorreoElec = domCorreoElec;
	}
	public String getDomEntreCalle1() {
		return domEntreCalle1;
	}
	public void setDomEntreCalle1(String domEntreCalle1) {
		this.domEntreCalle1 = domEntreCalle1;
	}
	public String getDomEntreCalle2() {
		return domEntreCalle2;
	}
	public void setDomEntreCalle2(String domEntreCalle2) {
		this.domEntreCalle2 = domEntreCalle2;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getNumExtTel1() {
		return numExtTel1;
	}
	public void setNumExtTel1(String numExtTel1) {
		this.numExtTel1 = numExtTel1;
	}
	public String getNumExtTel2() {
		return numExtTel2;
	}
	public void setNumExtTel2(String numExtTel2) {
		this.numExtTel2 = numExtTel2;
	}
	public String getNumTelFijo1() {
		return numTelFijo1;
	}
	public void setNumTelFijo1(String numTelFijo1) {
		this.numTelFijo1 = numTelFijo1;
	}
	public String getNumTelFijo2() {
		return numTelFijo2;
	}
	public void setNumTelFijo2(String numTelFijo2) {
		this.numTelFijo2 = numTelFijo2;
	}
	public String getRefCodigoPostal() {
		return refCodigoPostal;
	}
	public void setRefCodigoPostal(String refCodigoPostal) {
		this.refCodigoPostal = refCodigoPostal;
	}
	public String getRefNoExt() {
		return refNoExt;
	}
	public void setRefNoExt(String refNoExt) {
		this.refNoExt = refNoExt;
	}
	public String getRefNoInt() {
		return refNoInt;
	}
	public void setRefNoInt(String refNoInt) {
		this.refNoInt = refNoInt;
	}
	public String getTipoDomicio() {
		return tipoDomicio;
	}
	public void setTipoDomicio(String tipoDomicio) {
		this.tipoDomicio = tipoDomicio;
	}
	public String getCveLocalidadDes() {
		return cveLocalidadDes;
	}
	public void setCveLocalidadDes(String cveLocalidadDes) {
		this.cveLocalidadDes = cveLocalidadDes;
	}
	public String getCveMunicipDelegDes() {
		return cveMunicipDelegDes;
	}
	public void setCveMunicipDelegDes(String cveMunicipDelegDes) {
		this.cveMunicipDelegDes = cveMunicipDelegDes;
	}
	public String getEntidadFederativaDes() {
		return entidadFederativaDes;
	}
	public void setEntidadFederativaDes(String entidadFederativaDes) {
		this.entidadFederativaDes = entidadFederativaDes;
	}
	public String getHdnCveColonia() {
		return hdnCveColonia;
	}
	public void setHdnCveColonia(String hdnCveColonia) {
		this.hdnCveColonia = hdnCveColonia;
	}
	public String getHdnCveLocalidad() {
		return hdnCveLocalidad;
	}
	public void setHdnCveLocalidad(String hdnCveLocalidad) {
		this.hdnCveLocalidad = hdnCveLocalidad;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}