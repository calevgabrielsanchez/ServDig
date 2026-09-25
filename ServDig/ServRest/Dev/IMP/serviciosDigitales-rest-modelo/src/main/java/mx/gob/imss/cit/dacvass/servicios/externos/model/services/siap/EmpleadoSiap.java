package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "qry", propOrder = {
	    "matricula",
	    "nombre",
	    "apellidoPaterno",
	    "apellidoMaterno",
	    "claveDepto",
	    "desDepto",
	    "clavePuesto",
	    "desPuesto",
	    "claveArea",
	    "desArea",
	    "cuantiaBasica",
	    "status",
		"tc",
		"desTc",
		"rfc",
		"curp",
		"nss",
		"tipo_empleado",
		"delegacion",
		"desDelegacion",
		"localidad",
		"desLocalidad",
		"quincenaMes",
		"fechaJubPen",
		"tipoJubilacion",
		"porcentajePension",
		"fechaIngreso",
		"antAnios",
		"antQnas",
		"antDias",
		"fechaFaja",
		"claveBaja",
		"desBaja",
		"fechaModificacion",
		"fmodorden"
})
@XmlRootElement(name = "qry")
public class EmpleadoSiap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1026321667009882138L;
	
	@XmlElement(name = "MATRICULA", required = true, nillable = true)
	public int matricula;
	@XmlElement(name = "NOMBRE", required = true, nillable = true)
	public String nombre;
	@XmlElement(name = "APELLIDO_PATERNO", required = true, nillable = true)
	public String apellidoPaterno;
	@XmlElement(name = "APELLIDO_MATERNO", required = true, nillable = true)
	public String apellidoMaterno;
	@XmlElement(name = "CLAVE_DEPTO", required = true, nillable = true)
	public String claveDepto;
	@XmlElement(name = "DES_DEPTO", required = true, nillable = true)
	public String desDepto;
	@XmlElement(name = "CLAVE_PUESTO", required = true, nillable = true)
	public int clavePuesto;
	@XmlElement(name = "DES_PUESTO", required = true, nillable = true)
	public String desPuesto;
	@XmlElement(name = "CLAVE_AREA", required = true, nillable = true)
	public int claveArea;
	@XmlElement(name = "DES_AREA", required = true, nillable = true)
	public String desArea;
	@XmlElement(name = "CUANTIA_BASICA", required = true, nillable = true)
	public int cuantiaBasica;
	@XmlElement(name = "STATUS", required = true, nillable = true)
	public String status;
	@XmlElement(name = "TC", required = true, nillable = true)
	public int tc;
	@XmlElement(name = "DES_TC", required = true, nillable = true)
	public String desTc;
	@XmlElement(name = "RFC", required = true, nillable = true)
	public String rfc;
	@XmlElement(name = "CURP", required = true, nillable = true)
	public String curp;
	@XmlElement(name = "NSS", required = true, nillable = true)
	public double nss;
	@XmlElement(name = "TIPO_EMPLEADO", required = true, nillable = true)
	public String tipo_empleado;
	@XmlElement(name = "DELEGACION", required = true, nillable = true)
	public int delegacion;
	@XmlElement(name = "DES_DELEGACION", required = true, nillable = true)
	public String desDelegacion;
	@XmlElement(name = "LOCALIDAD", required = true, nillable = true)
	public int localidad;
	@XmlElement(name = "DES_LOCALIDAD", required = true, nillable = true)
	public String desLocalidad;
	@XmlElement(name = "QUINCENA_MES", required = true, nillable = true)
	public int quincenaMes;
	@XmlElement(name = "FECHA_JUB_PEN", required = true, nillable = true)
	public String fechaJubPen;
	@XmlElement(name = "TIPO_JUBILACION", required = true, nillable = true)
	public int tipoJubilacion;
	@XmlElement(name = "PORCENTAJE_PENSION", required = true, nillable = true)
	public int porcentajePension;
	@XmlElement(name = "FECHA_INGRESO", required = true, nillable = true)
	public String fechaIngreso;
	@XmlElement(name = "ANT_ANIOS", required = true, nillable = true)
	public int antAnios;
	@XmlElement(name = "ANT_QNAS", required = true, nillable = true)
	public int antQnas;
	@XmlElement(name = "ANT_DIAS", required = true, nillable = true)
	public int antDias;
	@XmlElement(name = "FECHA_BAJA", required = true, nillable = true)
	public String fechaFaja;
	@XmlElement(name = "CLAVE_BAJA", required = true, nillable = true)
	public int claveBaja;
	@XmlElement(name = "DES_BAJA", required = true, nillable = true)
	public String desBaja;
	@XmlElement(name = "FECHA_MODIFICACION", required = true, nillable = true)
	public String fechaModificacion;
	@XmlElement(name = "FMODORDEN", required = true, nillable = true)
	public int fmodorden;
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getClaveDepto() {
		return claveDepto;
	}
	public void setClaveDepto(String claveDepto) {
		this.claveDepto = claveDepto;
	}
	public String getDesDepto() {
		return desDepto;
	}
	public void setDesDepto(String desDepto) {
		this.desDepto = desDepto;
	}
	public int getClavePuesto() {
		return clavePuesto;
	}
	public void setClavePuesto(int clavePuesto) {
		this.clavePuesto = clavePuesto;
	}
	public String getDesPuesto() {
		return desPuesto;
	}
	public void setDesPuesto(String desPuesto) {
		this.desPuesto = desPuesto;
	}
	public int getClaveArea() {
		return claveArea;
	}
	public void setClaveArea(int claveArea) {
		this.claveArea = claveArea;
	}
	public String getDesArea() {
		return desArea;
	}
	public void setDesArea(String desArea) {
		this.desArea = desArea;
	}
	public int getCuantiaBasica() {
		return cuantiaBasica;
	}
	public void setCuantiaBasica(int cuantiaBasica) {
		this.cuantiaBasica = cuantiaBasica;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTc() {
		return tc;
	}
	public void setTc(int tc) {
		this.tc = tc;
	}
	public String getDesTc() {
		return desTc;
	}
	public void setDesTc(String desTc) {
		this.desTc = desTc;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public double getNss() {
		return nss;
	}
	public void setNss(double nss) {
		this.nss = nss;
	}
	public String getTipo_empleado() {
		return tipo_empleado;
	}
	public void setTipo_empleado(String tipo_empleado) {
		this.tipo_empleado = tipo_empleado;
	}
	public int getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(int delegacion) {
		this.delegacion = delegacion;
	}
	public String getDesDelegacion() {
		return desDelegacion;
	}
	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}
	public int getLocalidad() {
		return localidad;
	}
	public void setLocalidad(int localidad) {
		this.localidad = localidad;
	}
	public String getDesLocalidad() {
		return desLocalidad;
	}
	public void setDesLocalidad(String desLocalidad) {
		this.desLocalidad = desLocalidad;
	}
	public int getQuincenaMes() {
		return quincenaMes;
	}
	public void setQuincenaMes(int quincenaMes) {
		this.quincenaMes = quincenaMes;
	}
	public String getFechaJubPen() {
		return fechaJubPen;
	}
	public void setFechaJubPen(String fechaJubPen) {
		this.fechaJubPen = fechaJubPen;
	}
	public int getTipoJubilacion() {
		return tipoJubilacion;
	}
	public void setTipoJubilacion(int tipoJubilacion) {
		this.tipoJubilacion = tipoJubilacion;
	}
	public int getPorcentajePension() {
		return porcentajePension;
	}
	public void setPorcentajePension(int porcentajePension) {
		this.porcentajePension = porcentajePension;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public int getAntAnios() {
		return antAnios;
	}
	public void setAntAnios(int antAnios) {
		this.antAnios = antAnios;
	}
	public int getAntQnas() {
		return antQnas;
	}
	public void setAntQnas(int antQnas) {
		this.antQnas = antQnas;
	}
	public int getAntDias() {
		return antDias;
	}
	public void setAntDias(int antDias) {
		this.antDias = antDias;
	}
	public String getFechaFaja() {
		return fechaFaja;
	}
	public void setFechaFaja(String fechaFaja) {
		this.fechaFaja = fechaFaja;
	}
	public int getClaveBaja() {
		return claveBaja;
	}
	public void setClaveBaja(int claveBaja) {
		this.claveBaja = claveBaja;
	}
	public String getDesBaja() {
		return desBaja;
	}
	public void setDesBaja(String desBaja) {
		this.desBaja = desBaja;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public int getFmodorden() {
		return fmodorden;
	}
	public void setFmodorden(int fmodorden) {
		this.fmodorden = fmodorden;
	}
	
	public EmpleadoSiap(int matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String claveDepto,
			String desDepto, int clavePuesto, String desPuesto, int claveArea, String desArea, int cuantiaBasica,
			String status, int tc, String desTc, String rfc, String curp, double nss, String tipo_empleado,
			int delegacion, String desDelegacion, int localidad, String desLocalidad, int quincenaMes,
			String fechaJubPen, int tipoJubilacion, int porcentajePension, String fechaIngreso, int antAnios,
			int antQnas, int antDias, String fechaFaja, int claveBaja, String desBaja, String fechaModificacion,
			int fmodorden) {
		super();
		this.matricula = matricula;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.claveDepto = claveDepto;
		this.desDepto = desDepto;
		this.clavePuesto = clavePuesto;
		this.desPuesto = desPuesto;
		this.claveArea = claveArea;
		this.desArea = desArea;
		this.cuantiaBasica = cuantiaBasica;
		this.status = status;
		this.tc = tc;
		this.desTc = desTc;
		this.rfc = rfc;
		this.curp = curp;
		this.nss = nss;
		this.tipo_empleado = tipo_empleado;
		this.delegacion = delegacion;
		this.desDelegacion = desDelegacion;
		this.localidad = localidad;
		this.desLocalidad = desLocalidad;
		this.quincenaMes = quincenaMes;
		this.fechaJubPen = fechaJubPen;
		this.tipoJubilacion = tipoJubilacion;
		this.porcentajePension = porcentajePension;
		this.fechaIngreso = fechaIngreso;
		this.antAnios = antAnios;
		this.antQnas = antQnas;
		this.antDias = antDias;
		this.fechaFaja = fechaFaja;
		this.claveBaja = claveBaja;
		this.desBaja = desBaja;
		this.fechaModificacion = fechaModificacion;
		this.fmodorden = fmodorden;
	}

	public EmpleadoSiap() {};
	
}
