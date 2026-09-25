package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcGastos;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CRC_GASTOS")
@OnSearchLlavePrimaria		(atributos={"cveGasto"})
public class CrcGastos extends AbstractCrcGastos{

	@Transient
	private Integer cveStatusCorreccion;
	
	@Transient
	private String cveSubdelegcionOficial;
	
	public String imprimeObjeto(){
		return new StringBuffer().append("CrcGastos{")
								 .append("cveGasto:").append(this.getCveGasto()).append(";\n")
								 .append("txGasto:").append(this.getTxGasto()).append(";\n")
								 .append("cveSolicitudCorr").append(this.getCveSolicitudCorr()).append(";\n")
								 .append("folioCorreccion").append(this.getFolioCorreccion()).append(";\n")
								 .append("}")
								 .toString();
	}

	public Integer getCveStatusCorreccion() {
		return cveStatusCorreccion;
	}

	public void setCveStatusCorreccion(Integer cveStatusCorreccion) {
		this.cveStatusCorreccion = cveStatusCorreccion;
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

	
}
