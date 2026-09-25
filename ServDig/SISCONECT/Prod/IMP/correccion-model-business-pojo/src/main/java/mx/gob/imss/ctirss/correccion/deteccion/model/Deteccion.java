package mx.gob.imss.ctirss.correccion.deteccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.deteccion.base.model.AbstractDeteccion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CRT_DETECCION_PRUEBAS")
@OnSearchLlavePrimaria		(atributos={"cveDeteccion"})
public class Deteccion extends AbstractDeteccion{
	
	public String imprimeObjeto(){
		return new StringBuffer().append("Deteccion{")
								 .append("cveDeteccion:").append(this.getCveDeteccion()).append(";\n")
								 .append("razonSocial:").append(this.getRazonSocial()).append(";\n")
								 .append("numRegPat:").append(this.getNumRegPat()).append(";\n")
								 .append("canSuperficie:").append(this.getCanSuperficie()).append(";\n")
								 .append("importe:").append(this.getImporte()).append(";\n")
								 .append("cveContrato:").append(this.getCveContrato()).append(";\n")
								 .append("cveLicitacion:").append(this.getCveLicitacion()).append(";\n")
								 .append("tipoClase:").append(this.getTipoClase()).append(";\n")
								 .append("cruzado:").append(this.getCruzado()).append(";\n")
								 .append("tipoOrigen:").append(this.getTipoOrigen()).append(";\n")
								 .append("fecInicio:").append(this.getFecInicio()).append(";\n")
								 .append("fecTermino:").append(this.getFecTermino()).append(";\n")
								 .append("fecExpedicion:").append(this.getFecExpedicion()).append(";\n")
								 .append("impManoObra:").append(this.getImpManoObra()).append(";\n")
								 .append("impMontoContratado:").append(this.getImpMontoContratado()).append(";\n")
								 .append("desDepContratante:").append(this.getDesDepContratante()).append(";\n")
								 .append("avanceObra:").append(this.getAvanceObra()).append(";\n")
								 .append("desSector:").append(this.getDesSector()).append(";\n")
								 .append("desSubSector:").append(this.getDesSubSector()).append(";\n")
								 .append("fecRecorrido:").append(this.getFecRecorrido()).append(";\n")
								 .append("fecDeteccion:").append(this.getFecDeteccion()).append(";\n")
								 .append("domCalle:").append(this.getDomCalle()).append(";\n")
								 .append("codigoPostal:").append(this.getCodigoPostal()).append(";\n")
								 .append("refColonia:").append(this.getRefColonia()).append(";\n")
								 .append("refEmail:").append(this.getRefEmail()).append(";\n")
								 .append("numExt:").append(this.getNumExt()).append(";\n")
								 .append("numInt:").append(this.getNumInt()).append(";\n")
								 .append("numTelefono:").append(this.getNumTelefono()).append(";\n")
								 .append("idMunicipio:").append(this.getIdMunicipio()).append(";\n")
								 .append("entidad:").append(this.getEntidad()).append(";\n")
								 .append("sDelegOrig:").append(this.getsDelegOrig()).append(";\n")
								 .append("delegOrig:").append(this.getDelegOrig()).append(";\n")
								 .append("cvePersona:").append(this.getCvePersona()).append(";\n")
								 .append("folioDeteccion:").append(this.getFolioDeteccion()).append(";\n")
								 .append("tpObra:").append(this.getTpObra()).append(";\n")
								 .append("faseObra:").append(this.getFaseObra()).append(";\n")
								 .append("curp:").append(this.getCurp()).append(";\n")
								 .append("rfc:").append(this.getRfc()).append(";\n")
								 .append("trabajadores:").append(this.getTrabajadores()).append(";\n")
								 .append("zona:").append(this.getZona()).append(";\n")
								 .append("}")
								 .toString();
	}

}
