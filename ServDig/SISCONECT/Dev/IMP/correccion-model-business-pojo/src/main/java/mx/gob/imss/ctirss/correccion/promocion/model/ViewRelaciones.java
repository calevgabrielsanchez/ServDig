package mx.gob.imss.ctirss.correccion.promocion.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.ejb.criteria.expression.function.LengthFunction;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class ViewRelaciones extends AbstractModel {
	
	public BigDecimal CVE_PK;
	public BigDecimal CVE_FK_UBICACION;
	public String NOM_RAZONSOCIAL;
	public BigDecimal CVE_FK_DELEGACION;
	public BigDecimal CVE_SUBDELEG;
	public BigDecimal CVE_FK_PATRON;	
	public String NUM_REGISTROPATRONAL;
	public BigDecimal CVE_NROREGOBRA;
	public String FEC_FECHAINICIO_FC;
	public String FEC_FECHATERMINO_FC;
	public String FEC_INICIO_2;
	public String FEC_TERMINO_2;
	public BigDecimal CVE_FK_INCIDENCIA;
	public String INCIDENCIA;
	public BigDecimal RELTRABCVE_FK_OBRA;
	public BigDecimal NUM_PERIODO;
	public String DOM_CALLE;
	public String NUM_CODIGOPOSTAL;
	public String REF_COLONIA;
	public String NUM_NROEXT;
	public BigDecimal CVE_FK_MUNICIPIO;
	public String CVE_CODIGO;
		
	public ViewRelaciones(Object[] obj) {
		
		int i = 0;
		
		setCVE_PK(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		String fechaIn = (obj[i++]!=null ? (Timestamp)obj[i-1]+"" : null);
		String fechaFin = (obj[i++]!=null ? (Timestamp)obj[i-1]+"" : null);
		setFEC_FECHAINICIO_FC(fechaIn!=null ? fechaIn.substring(8, 10)+"-"+fechaIn.substring(5, 7)+"-"+fechaIn.substring(0, 4) : "");
		setFEC_FECHATERMINO_FC(fechaFin!=null ? fechaFin.substring(8, 10)+"-"+fechaFin.substring(5, 7)+"-"+fechaFin.substring(0, 4) : "");
		setCVE_NROREGOBRA(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setCVE_FK_PATRON(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setCVE_FK_UBICACION(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setNOM_RAZONSOCIAL(obj[i++]!=null ? (String)obj[i-1] : "");
		setNUM_REGISTROPATRONAL(obj[i++]!=null ? (String)obj[i-1] : "");
		String fechaIni = (obj[i++]!=null ? (Timestamp)obj[i-1]+"" : null);
		String fechaTer = (obj[i++]!=null ? (Timestamp)obj[i-1]+"" : null);
		setFEC_INICIO_2(fechaIni!=null ? fechaIni.substring(8, 10)+"-"+fechaIni.substring(5, 7)+"-"+fechaIni.substring(0, 4) : "");
		setFEC_TERMINO_2(fechaTer!=null ? fechaTer.substring(8, 10)+"-"+fechaTer.substring(5, 7)+"-"+fechaTer.substring(0, 4) : "");
		setCVE_FK_INCIDENCIA(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setINCIDENCIA(obj[i++]!=null ? (String)obj[i-1] : "");
		setRELTRABCVE_FK_OBRA(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setNUM_PERIODO(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setDOM_CALLE(obj[i++]!=null ? (String)obj[i-1] : "");
		setNUM_CODIGOPOSTAL(obj[i++]!=null ? (String)obj[i-1] : "");
		setREF_COLONIA(obj[i++]!=null ? (String)obj[i-1] : "");
		setNUM_NROEXT(obj[i++]!=null ? (String)obj[i-1] : "");
		setCVE_FK_MUNICIPIO(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setCVE_SUBDELEG(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		setCVE_CODIGO(obj[i++]!=null ? (String)obj[i-1] : "");				
		setCVE_FK_DELEGACION(obj[i++]!=null ? (BigDecimal)obj[i-1] : new BigDecimal(0));
		
	}
	
	public BigDecimal getCVE_FK_DELEGACION() {
		return CVE_FK_DELEGACION;
	}
	public void setCVE_FK_DELEGACION(BigDecimal cVE_FK_DELEGACION) {
		CVE_FK_DELEGACION = cVE_FK_DELEGACION;
	}
	public BigDecimal getCVE_SUBDELEG() {
		return CVE_SUBDELEG;
	}
	public void setCVE_SUBDELEG(BigDecimal cVE_SUBDELEG) {
		CVE_SUBDELEG = cVE_SUBDELEG;
	}
	public BigDecimal getCVE_FK_PATRON() {
		return CVE_FK_PATRON;
	}
	public void setCVE_FK_PATRON(BigDecimal cVE_FK_PATRON) {
		CVE_FK_PATRON = cVE_FK_PATRON;
	}
	public String getNUM_REGISTROPATRONAL() {
		return NUM_REGISTROPATRONAL;
	}
	public void setNUM_REGISTROPATRONAL(String nUM_REGISTROPATRONAL) {
		NUM_REGISTROPATRONAL = nUM_REGISTROPATRONAL;
	}

	public BigDecimal getCVE_NROREGOBRA() {
		return CVE_NROREGOBRA;
	}
	public void setCVE_NROREGOBRA(BigDecimal cVE_NROREGOBRA) {
		CVE_NROREGOBRA = cVE_NROREGOBRA;
	}
	
	public String getFEC_FECHAINICIO_FC() {
		return FEC_FECHAINICIO_FC;
	}
	public void setFEC_FECHAINICIO_FC(String fEC_FECHAINICIO_FC) {
		FEC_FECHAINICIO_FC = fEC_FECHAINICIO_FC;
	}
	public String getFEC_FECHATERMINO_FC() {
		return FEC_FECHATERMINO_FC;
	}
	public void setFEC_FECHATERMINO_FC(String fEC_FECHATERMINO_FC) {
		FEC_FECHATERMINO_FC = fEC_FECHATERMINO_FC;
	}
	
	public BigDecimal getCVE_FK_INCIDENCIA() {
		return CVE_FK_INCIDENCIA;
	}
	public void setCVE_FK_INCIDENCIA(BigDecimal cVE_FK_INCIDENCIA) {
		CVE_FK_INCIDENCIA = cVE_FK_INCIDENCIA;
	}

	public BigDecimal getCVE_PK() {
		return CVE_PK;
	}

	public void setCVE_PK(BigDecimal cVE_PK) {
		CVE_PK = cVE_PK;
	}

	public BigDecimal getCVE_FK_UBICACION() {
		return CVE_FK_UBICACION;
	}

	public void setCVE_FK_UBICACION(BigDecimal cVE_FK_UBICACION) {
		CVE_FK_UBICACION = cVE_FK_UBICACION;
	}

	public String getNOM_RAZONSOCIAL() {
		return NOM_RAZONSOCIAL;
	}

	public void setNOM_RAZONSOCIAL(String nOM_RAZONSOCIAL) {
		NOM_RAZONSOCIAL = nOM_RAZONSOCIAL;
	}

	public String getFEC_INICIO_2() {
		return FEC_INICIO_2;
	}

	public void setFEC_INICIO_2(String fEC_INICIO_2) {
		FEC_INICIO_2 = fEC_INICIO_2;
	}

	public String getFEC_TERMINO_2() {
		return FEC_TERMINO_2;
	}

	public void setFEC_TERMINO_2(String fEC_TERMINO_2) {
		FEC_TERMINO_2 = fEC_TERMINO_2;
	}

	public String getINCIDENCIA() {
		return INCIDENCIA;
	}

	public void setINCIDENCIA(String iNCIDENCIA) {
		INCIDENCIA = iNCIDENCIA;
	}

	public BigDecimal getRELTRABCVE_FK_OBRA() {
		return RELTRABCVE_FK_OBRA;
	}

	public void setRELTRABCVE_FK_OBRA(BigDecimal rELTRABCVE_FK_OBRA) {
		RELTRABCVE_FK_OBRA = rELTRABCVE_FK_OBRA;
	}

	public BigDecimal getNUM_PERIODO() {
		return NUM_PERIODO;
	}

	public void setNUM_PERIODO(BigDecimal nUM_PERIODO) {
		NUM_PERIODO = nUM_PERIODO;
	}

	public String getDOM_CALLE() {
		return DOM_CALLE;
	}

	public void setDOM_CALLE(String dOM_CALLE) {
		DOM_CALLE = dOM_CALLE;
	}

	public String getNUM_CODIGOPOSTAL() {
		return NUM_CODIGOPOSTAL;
	}

	public void setNUM_CODIGOPOSTAL(String nUM_CODIGOPOSTAL) {
		NUM_CODIGOPOSTAL = nUM_CODIGOPOSTAL;
	}

	public String getREF_COLONIA() {
		return REF_COLONIA;
	}

	public void setREF_COLONIA(String rEF_COLONIA) {
		REF_COLONIA = rEF_COLONIA;
	}

	public String getNUM_NROEXT() {
		return NUM_NROEXT;
	}

	public void setNUM_NROEXT(String nUM_NROEXT) {
		NUM_NROEXT = nUM_NROEXT;
	}

	public BigDecimal getCVE_FK_MUNICIPIO() {
		return CVE_FK_MUNICIPIO;
	}

	public void setCVE_FK_MUNICIPIO(BigDecimal cVE_FK_MUNICIPIO) {
		CVE_FK_MUNICIPIO = cVE_FK_MUNICIPIO;
	}

	public String getCVE_CODIGO() {
		return CVE_CODIGO;
	}

	public void setCVE_CODIGO(String cVE_CODIGO) {
		CVE_CODIGO = cVE_CODIGO;
	}

}