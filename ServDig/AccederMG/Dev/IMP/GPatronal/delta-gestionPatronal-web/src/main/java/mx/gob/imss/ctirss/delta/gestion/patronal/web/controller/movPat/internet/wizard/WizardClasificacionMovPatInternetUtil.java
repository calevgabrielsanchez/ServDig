package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat.internet.wizard;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

public class WizardClasificacionMovPatInternetUtil {
	
	public static String getNombreCompletoRp(SujetoObligado sujeto) {
		String nombreCompleto = "";

		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			Fisica fisica = sujeto.getFisica(); 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
		}else{
			Moral moral = sujeto.getMoral();
			nombreCompleto = moral.getRazonSocial();
		}
		return nombreCompleto;
	}
		
	public static String obtenerNombreCompletoPersonaFisica(Fisica fisica) {

		StringBuffer nombreCompleto = new StringBuffer();
		if(fisica.getPrimerApellido()!=null && fisica.getPrimerApellido()!="null"){
			nombreCompleto.append(fisica.getPrimerApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getSegundoApellido()!=null && fisica.getSegundoApellido()!="null"){
			nombreCompleto.append(fisica.getSegundoApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getNombre()!=null && fisica.getNombre()!="null")
			nombreCompleto.append(fisica.getNombre());
		
		return nombreCompleto.toString();
	}
	
	public static String formatDateddMMyyyy(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(date);
		return fecha;
	}
	
	public static String formatDateddMMMMyyyy(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
		String fecha = sdf.format(date);
		return fecha;
	}

}
