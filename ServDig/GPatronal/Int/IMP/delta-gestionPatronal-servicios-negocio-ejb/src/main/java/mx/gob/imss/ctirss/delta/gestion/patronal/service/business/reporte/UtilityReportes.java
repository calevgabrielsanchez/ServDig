package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.reporte;

import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import net.sf.jasperreports.engine.JRDefaultScriptlet;

public class UtilityReportes  extends JRDefaultScriptlet {

	
	public String getListMediosContacto(List<MedioContacto> mediosContacto){
		
		StringBuffer sbMediosContacto = new StringBuffer();
		
		for(MedioContacto medioContacto : mediosContacto){
			sbMediosContacto.append(stringMedioContacto(medioContacto));
		}
		
		return sbMediosContacto.toString();
	} 
	
	private String stringMedioContacto(MedioContacto medioContacto) {
		StringBuffer medioContactoRetorno = new StringBuffer();
		switch (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().intValue()) {
		case 1:
			medioContactoRetorno.append("CORREO ELECTRÓNICO PERSONAL: ").append(medioContacto.getDesFormaContacto()).append("; ");
			break;
		case 2:
			medioContactoRetorno.append("TELÉFONO FIJO CON LADA (10 DÍGITOS): ").append(medioContacto.getDesFormaContacto()).append("; ");
			break;
		case 3:
			medioContactoRetorno.append("TELÉFONO MÓVIL: ").append(medioContacto.getDesFormaContacto()).append("; ");
			break;
		case 4:
			medioContactoRetorno.append("FACEBOOK: ").append(medioContacto.getDesFormaContacto()).append("; ");
			break;
		case 5:
			medioContactoRetorno.append("TWITTER: ").append(medioContacto.getDesFormaContacto()).append("; ");
			break;
		default:
			break;
		}
		return medioContactoRetorno.toString();
	}
	
}
