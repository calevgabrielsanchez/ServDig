package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ReporteCifrasDTO extends AbstractModel {
private static final long serialVersionUID = 1L;
	
	private String rfc;
	private String registroPatronalSustituto;
	private String claseSustituto;
	private String fraccionSustituto;
	private String primaSustituto;
	private String delegacionSustituto;
	private String subdelegacionSustituto;
	private String registroPatronalSustituido;
	private String claseSustituido;
	private String fraccionSustituido;
	private String primaSustituido;
	private String delegacionSustituido;
	private String subdelegacionSustituido;
	private String registroPatronalSustituido2;
	private String claseSustituido2;
	private String fraccionSustituido2;
	private String primaSustituido2;
	private String delegacionSustituido2;
	private String subdelegacionSustituido2;
	private String registroPatronalSustituido3;
	private String claseSustituido3;
	private String fraccionSustituido3;
	private String primaSustituido3;
	private String delegacionSustituido3;
	private String subdelegacionSustituido3;
	private String registroPatronalSustituido4;
	private String claseSustituido4;
	private String fraccionSustituido4;
	private String primaSustituido4;
	private String delegacionSustituido4;
	private String subdelegacionSustituido4;
	private String registroPatronalSustituido5;
	private String claseSustituido5;
	private String fraccionSustituido5;
	private String primaSustituido5;
	private String delegacionSustituido5;
	private String subdelegacionSustituido5;
	private String registroPatronalSustituido61;
	private String claseSustituido6;
	private String fraccionSustituido6;
	private String primaSustituido6;
	private String delegacionSustituido6;
	private String subdelegacionSustituido6;
	private String registroPatronalSustituido7;
	private String claseSustituido7;
	private String fraccionSustituido7;
	private String primaSustituido7;
	private String delegacionSustituido7;
	private String subdelegacionSustituido7;
	private String registroPatronalSustituido8;
	private String claseSustituido8;
	private String fraccionSustituido8;
	private String primaSustituido8;
	private String delegacionSustituido8;
	private String subdelegacionSustituido8;
	private String registroPatronalSustituido9;
	private String claseSustituido9;
	private String fraccionSustituido9;
	private String primaSustituido9;
	private String delegacionSustituido9;
	private String subdelegacionSustituido9;
	private String registroPatronalSustituido10;
	private String claseSustituido10;
	private String fraccionSustituido10;
	private String primaSustituido10;
	private String delegacionSustituido10;
	private String subdelegacionSustituido10;
	private String fechaRegistro;
	private String fechaEfectos;
	
	public ReporteCifrasDTO(String rfc, String registroPatronalSustituto, String claseSustituto, String fraccionSustituto, String primaSustituto,
			String delegacionSustituto, String subdelegacionSustituto, String fechaRegistro, String fechaEfectos) {
		this.rfc = rfc;
		this.registroPatronalSustituto = registroPatronalSustituto;
		this.claseSustituto=claseSustituto;
		this.fraccionSustituto=fraccionSustituto;
		this.primaSustituto=primaSustituto;
		this.delegacionSustituto=delegacionSustituto;
		this.subdelegacionSustituto=subdelegacionSustituto;
		this.fechaRegistro=fechaRegistro;
		this.fechaEfectos=fechaEfectos;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "" + rfc + "|" + registroPatronalSustituto
				+ "|" + claseSustituto + "|" + fraccionSustituto
				+ "|" + primaSustituto + "|" + delegacionSustituto
				+ "|" + subdelegacionSustituto + "|"
				+ registroPatronalSustituido + "|" + claseSustituido + ",|"
				+ fraccionSustituido + "|" + primaSustituido + "|"
				+ delegacionSustituido + "|" + subdelegacionSustituido
				+ "|" + fechaRegistro + "|" + fechaEfectos + '\n' ;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRegistroPatronalSustituto() {
		return registroPatronalSustituto;
	}

	public void setRegistroPatronalSustituto(String registroPatronalSustituto) {
		this.registroPatronalSustituto = registroPatronalSustituto;
	}

	public String getClaseSustituto() {
		return claseSustituto;
	}

	public void setClaseSustituto(String claseSustituto) {
		this.claseSustituto = claseSustituto;
	}

	public String getFraccionSustituto() {
		return fraccionSustituto;
	}

	public void setFraccionSustituto(String fraccionSustituto) {
		this.fraccionSustituto = fraccionSustituto;
	}

	public String getPrimaSustituto() {
		return primaSustituto;
	}

	public void setPrimaSustituto(String primaSustituto) {
		this.primaSustituto = primaSustituto;
	}

	public String getDelegacionSustituto() {
		return delegacionSustituto;
	}

	public void setDelegacionSustituto(String delegacionSustituto) {
		this.delegacionSustituto = delegacionSustituto;
	}

	public String getSubdelegacionSustituto() {
		return subdelegacionSustituto;
	}

	public void setSubdelegacionSustituto(String subdelegacionSustituto) {
		this.subdelegacionSustituto = subdelegacionSustituto;
	}

	public String getRegistroPatronalSustituido() {
		return registroPatronalSustituido;
	}

	public void setRegistroPatronalSustituido(String registroPatronalSustituido1) {
		this.registroPatronalSustituido = registroPatronalSustituido1;
	}

	public String getClaseSustituido() {
		return claseSustituido;
	}

	public void setClaseSustituido(String claseSustituido) {
		this.claseSustituido = claseSustituido;
	}

	public String getFraccionSustituido() {
		return fraccionSustituido;
	}

	public void setFraccionSustituido(String fraccionSustituido) {
		this.fraccionSustituido = fraccionSustituido;
	}

	public String getPrimaSustituido() {
		return primaSustituido;
	}

	public void setPrimaSustituido(String primaSustituido) {
		this.primaSustituido = primaSustituido;
	}

	public String getDelegacionSustituido() {
		return delegacionSustituido;
	}

	public void setDelegacionSustituido(String delegacionSustituido) {
		this.delegacionSustituido = delegacionSustituido;
	}

	public String getSubdelegacionSustituido() {
		return subdelegacionSustituido;
	}

	public void setSubdelegacionSustituido(String subdelegacionSustituido) {
		this.subdelegacionSustituido = subdelegacionSustituido;
	}

	public String getRegistroPatronalSustituido2() {
		return registroPatronalSustituido2;
	}

	public void setRegistroPatronalSustituido2(String registroPatronalSustituido2) {
		this.registroPatronalSustituido2 = registroPatronalSustituido2;
	}

	public String getClaseSustituido2() {
		return claseSustituido2;
	}

	public void setClaseSustituido2(String claseSustituido2) {
		this.claseSustituido2 = claseSustituido2;
	}

	public String getFraccionSustituido2() {
		return fraccionSustituido2;
	}

	public void setFraccionSustituido2(String fraccionSustituido2) {
		this.fraccionSustituido2 = fraccionSustituido2;
	}

	public String getPrimaSustituido2() {
		return primaSustituido2;
	}

	public void setPrimaSustituido2(String primaSustituido2) {
		this.primaSustituido2 = primaSustituido2;
	}

	public String getDelegacionSustituido2() {
		return delegacionSustituido2;
	}

	public void setDelegacionSustituido2(String delegacionSustituido2) {
		this.delegacionSustituido2 = delegacionSustituido2;
	}

	public String getSubdelegacionSustituido2() {
		return subdelegacionSustituido2;
	}

	public void setSubdelegacionSustituido2(String subdelegacionSustituido2) {
		this.subdelegacionSustituido2 = subdelegacionSustituido2;
	}

	public String getRegistroPatronalSustituido3() {
		return registroPatronalSustituido3;
	}

	public void setRegistroPatronalSustituido3(String registroPatronalSustituido3) {
		this.registroPatronalSustituido3 = registroPatronalSustituido3;
	}

	public String getClaseSustituido3() {
		return claseSustituido3;
	}

	public void setClaseSustituido3(String claseSustituido3) {
		this.claseSustituido3 = claseSustituido3;
	}

	public String getFraccionSustituido3() {
		return fraccionSustituido3;
	}

	public void setFraccionSustituido3(String fraccionSustituido3) {
		this.fraccionSustituido3 = fraccionSustituido3;
	}

	public String getPrimaSustituido3() {
		return primaSustituido3;
	}

	public void setPrimaSustituido3(String primaSustituido3) {
		this.primaSustituido3 = primaSustituido3;
	}

	public String getDelegacionSustituido3() {
		return delegacionSustituido3;
	}

	public void setDelegacionSustituido3(String delegacionSustituido3) {
		this.delegacionSustituido3 = delegacionSustituido3;
	}

	public String getSubdelegacionSustituido3() {
		return subdelegacionSustituido3;
	}

	public void setSubdelegacionSustituido3(String subdelegacionSustituido3) {
		this.subdelegacionSustituido3 = subdelegacionSustituido3;
	}

	public String getRegistroPatronalSustituido4() {
		return registroPatronalSustituido4;
	}

	public void setRegistroPatronalSustituido4(String registroPatronalSustituido4) {
		this.registroPatronalSustituido4 = registroPatronalSustituido4;
	}

	public String getClaseSustituido4() {
		return claseSustituido4;
	}

	public void setClaseSustituido4(String claseSustituido4) {
		this.claseSustituido4 = claseSustituido4;
	}

	public String getFraccionSustituido4() {
		return fraccionSustituido4;
	}

	public void setFraccionSustituido4(String fraccionSustituido4) {
		this.fraccionSustituido4 = fraccionSustituido4;
	}

	public String getPrimaSustituido4() {
		return primaSustituido4;
	}

	public void setPrimaSustituido4(String primaSustituido4) {
		this.primaSustituido4 = primaSustituido4;
	}

	public String getDelegacionSustituido4() {
		return delegacionSustituido4;
	}

	public void setDelegacionSustituido4(String delegacionSustituido4) {
		this.delegacionSustituido4 = delegacionSustituido4;
	}

	public String getSubdelegacionSustituido4() {
		return subdelegacionSustituido4;
	}

	public void setSubdelegacionSustituido4(String subdelegacionSustituido4) {
		this.subdelegacionSustituido4 = subdelegacionSustituido4;
	}

	public String getRegistroPatronalSustituido5() {
		return registroPatronalSustituido5;
	}

	public void setRegistroPatronalSustituido5(String registroPatronalSustituido5) {
		this.registroPatronalSustituido5 = registroPatronalSustituido5;
	}

	public String getClaseSustituido5() {
		return claseSustituido5;
	}

	public void setClaseSustituido5(String claseSustituido5) {
		this.claseSustituido5 = claseSustituido5;
	}

	public String getFraccionSustituido5() {
		return fraccionSustituido5;
	}

	public void setFraccionSustituido5(String fraccionSustituido5) {
		this.fraccionSustituido5 = fraccionSustituido5;
	}

	public String getPrimaSustituido5() {
		return primaSustituido5;
	}

	public void setPrimaSustituido5(String primaSustituido5) {
		this.primaSustituido5 = primaSustituido5;
	}

	public String getDelegacionSustituido5() {
		return delegacionSustituido5;
	}

	public void setDelegacionSustituido5(String delegacionSustituido5) {
		this.delegacionSustituido5 = delegacionSustituido5;
	}

	public String getSubdelegacionSustituido5() {
		return subdelegacionSustituido5;
	}

	public void setSubdelegacionSustituido5(String subdelegacionSustituido5) {
		this.subdelegacionSustituido5 = subdelegacionSustituido5;
	}

	public String getRegistroPatronalSustituido61() {
		return registroPatronalSustituido61;
	}

	public void setRegistroPatronalSustituido61(String registroPatronalSustituido61) {
		this.registroPatronalSustituido61 = registroPatronalSustituido61;
	}

	public String getClaseSustituido6() {
		return claseSustituido6;
	}

	public void setClaseSustituido6(String claseSustituido6) {
		this.claseSustituido6 = claseSustituido6;
	}

	public String getFraccionSustituido6() {
		return fraccionSustituido6;
	}

	public void setFraccionSustituido6(String fraccionSustituido6) {
		this.fraccionSustituido6 = fraccionSustituido6;
	}

	public String getPrimaSustituido6() {
		return primaSustituido6;
	}

	public void setPrimaSustituido6(String primaSustituido6) {
		this.primaSustituido6 = primaSustituido6;
	}

	public String getDelegacionSustituido6() {
		return delegacionSustituido6;
	}

	public void setDelegacionSustituido6(String delegacionSustituido6) {
		this.delegacionSustituido6 = delegacionSustituido6;
	}

	public String getSubdelegacionSustituido6() {
		return subdelegacionSustituido6;
	}

	public void setSubdelegacionSustituido6(String subdelegacionSustituido6) {
		this.subdelegacionSustituido6 = subdelegacionSustituido6;
	}

	public String getRegistroPatronalSustituido7() {
		return registroPatronalSustituido7;
	}

	public void setRegistroPatronalSustituido7(String registroPatronalSustituido7) {
		this.registroPatronalSustituido7 = registroPatronalSustituido7;
	}

	public String getClaseSustituido7() {
		return claseSustituido7;
	}

	public void setClaseSustituido7(String claseSustituido7) {
		this.claseSustituido7 = claseSustituido7;
	}

	public String getFraccionSustituido7() {
		return fraccionSustituido7;
	}

	public void setFraccionSustituido7(String fraccionSustituido7) {
		this.fraccionSustituido7 = fraccionSustituido7;
	}

	public String getPrimaSustituido7() {
		return primaSustituido7;
	}

	public void setPrimaSustituido7(String primaSustituido7) {
		this.primaSustituido7 = primaSustituido7;
	}

	public String getDelegacionSustituido7() {
		return delegacionSustituido7;
	}

	public void setDelegacionSustituido7(String delegacionSustituido7) {
		this.delegacionSustituido7 = delegacionSustituido7;
	}

	public String getSubdelegacionSustituido7() {
		return subdelegacionSustituido7;
	}

	public void setSubdelegacionSustituido7(String subdelegacionSustituido7) {
		this.subdelegacionSustituido7 = subdelegacionSustituido7;
	}

	public String getRegistroPatronalSustituido8() {
		return registroPatronalSustituido8;
	}

	public void setRegistroPatronalSustituido8(String registroPatronalSustituido8) {
		this.registroPatronalSustituido8 = registroPatronalSustituido8;
	}

	public String getClaseSustituido8() {
		return claseSustituido8;
	}

	public void setClaseSustituido8(String claseSustituido8) {
		this.claseSustituido8 = claseSustituido8;
	}

	public String getFraccionSustituido8() {
		return fraccionSustituido8;
	}

	public void setFraccionSustituido8(String fraccionSustituido8) {
		this.fraccionSustituido8 = fraccionSustituido8;
	}

	public String getPrimaSustituido8() {
		return primaSustituido8;
	}

	public void setPrimaSustituido8(String primaSustituido8) {
		this.primaSustituido8 = primaSustituido8;
	}

	public String getDelegacionSustituido8() {
		return delegacionSustituido8;
	}

	public void setDelegacionSustituido8(String delegacionSustituido8) {
		this.delegacionSustituido8 = delegacionSustituido8;
	}

	public String getSubdelegacionSustituido8() {
		return subdelegacionSustituido8;
	}

	public void setSubdelegacionSustituido8(String subdelegacionSustituido8) {
		this.subdelegacionSustituido8 = subdelegacionSustituido8;
	}

	public String getRegistroPatronalSustituido9() {
		return registroPatronalSustituido9;
	}

	public void setRegistroPatronalSustituido9(String registroPatronalSustituido9) {
		this.registroPatronalSustituido9 = registroPatronalSustituido9;
	}

	public String getClaseSustituido9() {
		return claseSustituido9;
	}

	public void setClaseSustituido9(String claseSustituido9) {
		this.claseSustituido9 = claseSustituido9;
	}

	public String getFraccionSustituido9() {
		return fraccionSustituido9;
	}

	public void setFraccionSustituido9(String fraccionSustituido9) {
		this.fraccionSustituido9 = fraccionSustituido9;
	}

	public String getPrimaSustituido9() {
		return primaSustituido9;
	}

	public void setPrimaSustituido9(String primaSustituido9) {
		this.primaSustituido9 = primaSustituido9;
	}

	public String getDelegacionSustituido9() {
		return delegacionSustituido9;
	}

	public void setDelegacionSustituido9(String delegacionSustituido9) {
		this.delegacionSustituido9 = delegacionSustituido9;
	}

	public String getSubdelegacionSustituido9() {
		return subdelegacionSustituido9;
	}

	public void setSubdelegacionSustituido9(String subdelegacionSustituido9) {
		this.subdelegacionSustituido9 = subdelegacionSustituido9;
	}

	public String getRegistroPatronalSustituido10() {
		return registroPatronalSustituido10;
	}

	public void setRegistroPatronalSustituido10(String registroPatronalSustituido10) {
		this.registroPatronalSustituido10 = registroPatronalSustituido10;
	}

	public String getClaseSustituido10() {
		return claseSustituido10;
	}

	public void setClaseSustituido10(String claseSustituido10) {
		this.claseSustituido10 = claseSustituido10;
	}

	public String getFraccionSustituido10() {
		return fraccionSustituido10;
	}

	public void setFraccionSustituido10(String fraccionSustituido10) {
		this.fraccionSustituido10 = fraccionSustituido10;
	}

	public String getPrimaSustituido10() {
		return primaSustituido10;
	}

	public void setPrimaSustituido10(String primaSustituido10) {
		this.primaSustituido10 = primaSustituido10;
	}

	public String getDelegacionSustituido10() {
		return delegacionSustituido10;
	}

	public void setDelegacionSustituido10(String delegacionSustituido10) {
		this.delegacionSustituido10 = delegacionSustituido10;
	}

	public String getSubdelegacionSustituido10() {
		return subdelegacionSustituido10;
	}

	public void setSubdelegacionSustituido10(String subdelegacionSustituido10) {
		this.subdelegacionSustituido10 = subdelegacionSustituido10;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaEfectos() {
		return fechaEfectos;
	}

	public void setFechaEfectos(String fechaEfectos) {
		this.fechaEfectos = fechaEfectos;
	}
	
	
	
	

}
