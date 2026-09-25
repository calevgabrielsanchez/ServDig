package mx.gob.imss.ctirss.correccion.model;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Persister;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrcPatron;
import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;

@Entity
@Table(name="CRC_PATRON")
public class CrcPatron extends AbstractCrcPatron{
	
	@Transient
	private String registroPatronal;
	
	@Transient
	private String domicilioCompleto;

	public String getRegistroPatronal() {
		return this.getRegPatron()+this.getCveModal();
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public String getDomicilioCompleto() {
		return this.getTxCalle()+" "+this.getNuExterior()+" "+this.getNuInterior()+" "+this.getTxColonia();
	}
	

	public String imprimeObjeto(){
		return new StringBuffer().append("CrcPatron{")
								 .append("regPatron:").append(this.getRegPatron()).append(";\n")
								 .append("cveModal:").append(this.getCveModal()).append(";\n")
								 .append("cveDelegOrig:").append(this.getCveDelegOrig()).append(";\n")
								 .append("cveModalPr:").append(this.getCveModalPr()).append(";\n")
								 .append("digVerificador:").append(this.getDigVerificador()).append(";\n")
								 .append("eMail:").append(this.getEMail()).append(";\n")
								 .append("entFed:").append(this.getEntFed()).append(";\n")
								 .append("fhInicioAct:").append(this.getFhInicioAct()).append(";\n")
								 .append("idMunicipio:").append(this.getIdMunicipio()).append(";\n")
								 .append("inTpPatron:").append(this.getInTpPatron()).append(";\n")
								 .append("nuCp:").append(this.getNuCp()).append(";\n")
								 .append("nuExterior:").append(this.getNuExterior()).append(";\n")
								 .append("nuInterior:").append(this.getNuInterior()).append(";\n")
								 .append("nuTrabajadores:").append(this.getNuTrabajadores()).append(";\n")
								 .append("regPatronPr:").append(this.getRegPatronPr()).append(";\n")
								 .append("rfc:").append(this.getRfc()).append(";\n")
								 .append("sdelegOrig:").append(this.getSdelegOrig()).append(";\n")
								 .append("telefono:").append(this.getTelefono()).append(";\n")
								 .append("txActividad:").append(this.getTxActividad()).append(";\n")
								 .append("txCalle:").append(this.getTxCalle()).append(";\n")
								 .append("txClase:").append(this.getTxClase()).append(";\n")
								 .append("txColonia:").append(this.getTxColonia()).append(";\n")
								 .append("txFraccion:").append(this.getTxFraccion()).append(";\n")
								 .append("txPrima:").append(this.getTxPrima()).append(";\n")
								 .append("txRazonSocial:").append(this.getTxRazonSocial()).append(";\n")
								 .append("txRepLegal:").append(this.getTxRepLegal()).append(";\n")
								 .append("txTipoPersona:").append(this.getTxTipoPersona()).append(";\n")
								 .append("cveActEconomiva:").append(this.getCveActEconomiva()).append(";\n")
								 .append("}")
								 .toString();
	}

	
	
}
