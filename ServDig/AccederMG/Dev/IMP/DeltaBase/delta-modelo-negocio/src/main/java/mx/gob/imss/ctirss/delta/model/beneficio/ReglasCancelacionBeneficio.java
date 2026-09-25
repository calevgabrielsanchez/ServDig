package mx.gob.imss.ctirss.delta.model.beneficio;

public enum ReglasCancelacionBeneficio {

	RN_CANCELA_BENEFICIO_TODO_SAT_RFC("RN1"),				//Cancelar beneficios como persona y patron por RFC
	RN_CANCELA_BENEFICIO_TODO_INFONAVIT_NRP("RN2"),			//Cancelar beneficios como persona y patron por NRP
	RN_CANCELA_BENEFICIO_PERSONA_INFONAVIT_NSS("RN3"),		//Cancelar beneficios como persona por NSS
	RN_CANCELA_BENEFICIO_TODO_IMSS_NRP("RN4"),				//Cancelar beneficios como persona y patron por NRP
	RN_CANCELA_BENEFICIO_PERSONA_IMSS_NSS("RN5");			//Cancelar beneficios como persona por NSS

	private String clave;

	private ReglasCancelacionBeneficio(String clave) {
		this.clave = clave;
	}

	public String getClave() {
		return clave;
	}
	
}
