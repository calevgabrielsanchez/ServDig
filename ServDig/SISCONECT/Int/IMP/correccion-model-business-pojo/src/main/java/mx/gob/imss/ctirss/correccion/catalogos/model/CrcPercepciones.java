package mx.gob.imss.ctirss.correccion.catalogos.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcPercepciones;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CRC_PERCEPCIONES")
@OnSearchLlavePrimaria		(atributos={"cvePercepcion"})
public class CrcPercepciones extends AbstractCrcPercepciones{

	@Transient
	private Double sumRemuneracion;	
	
	@Transient 
	private Double importeAuxiliarNomina;
	
	@Transient
	private String cveSubdelegcionOficial;
	
	@Transient
	private boolean validaPercepcion;
	
	public String imprimeObjeto(){
		return new StringBuffer().append("CrcPercepciones{")
								 .append("cvePercepcion:").append(this.getCvePercepcion()).append(";\n")
								 .append("txRemuneracion:").append(this.getTxRemuneracion()).append(";\n")
								 .append("cveSolicitudCorr").append(this.getCveSolicitudCorr()).append(";\n")
								 .append("folioCorreccion").append(this.getFolioCorreccion()).append(";\n")
								 .append("}")
								 .toString();
	}

		
	
	public Double getSumRemuneracion() {
		return sumRemuneracion;
	}
	
	
	public void setSumRemuneracion(Double sumRemuneracion) {
		this.sumRemuneracion = sumRemuneracion;
	}



	/**
	 * Retorna el valor cveSubdelegcionOficial
	 * @return  cveSubdelegcionOficial
	 */
	@Transient
	public String getCveSubdelegcionOficial() {
		return cveSubdelegcionOficial;
	}



	/**
	 * Asigna el valor del cveSubdelegcionOficial al atributo cveSubdelegcionOficial
	 * @param cveSubdelegcionOficial 
	 */
	public void setCveSubdelegcionOficial(String cveSubdelegcionOficial) {
		this.cveSubdelegcionOficial = cveSubdelegcionOficial;
	}



	public boolean isValidaPercepcion() {
		return validaPercepcion;
	}



	public void setValidaPercepcion(boolean validaPercepcion) {
		this.validaPercepcion = validaPercepcion;
	}



	public Double getImporteAuxiliarNomina() {
		return importeAuxiliarNomina;
	}



	public void setImporteAuxiliarNomina(Double importeAuxiliarNomina) {
		this.importeAuxiliarNomina = importeAuxiliarNomina;
	}

	

}
