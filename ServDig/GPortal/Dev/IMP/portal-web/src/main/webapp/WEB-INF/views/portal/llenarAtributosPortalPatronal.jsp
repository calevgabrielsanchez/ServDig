<script type="text/javascript">
	$(function() {

		AtributosPersonaCtrl.personaFirmada.idPersona = '${idPersona}';
		AtributosPersonaCtrl.personaFirmada.curp = '${usuario.fisica.curp}';
		AtributosPersonaCtrl.personaFirmada.rfc = '${usuario.fisica.rfc}';
		AtributosPersonaCtrl.personaFirmada.nombreCompleto = '${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}';

		FirmanteCtrl.curp = '${usuario.fisica.curp}';
		FirmanteCtrl.rfc = '${usuario.fisica.rfc}';
		var nombreRazonSocial = "${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}";
	    nombreRazonSocial = nombreRazonSocial.replace(/\'/g, "\\'");
		FirmanteCtrl.nombreRazonSocial = nombreRazonSocial;
		 
		AtributosPersonaCtrl.personaPortal.idPersona = '${idPersonaEmpresa}';
		AtributosPersonaCtrl.personaPortal.idFiscalPersona = '${idFiscalEmpresa}';
		AtributosPersonaCtrl.personaPortal.rfc = '${rfc}';
		AtributosPersonaCtrl.personaPortal.idTipoPersona = '${tipoPersona}';
		AtributosPersonaCtrl.personaPortal.registroPatronal = '${numeroRegistroPatronal}';
		AtributosPersonaCtrl.personaPortal.modalidadPatron = '${modalidadPatron}';
		AtributosPersonaCtrl.personaPortal.isRPC = '${isRPC}';
		AtributosPersonaCtrl.personaPortal.curp = '${curp}';
		AtributosPersonaCtrl.personaPortal.nombreRazonSocial = "${nombre}";
		AtributosPersonaCtrl.personaPortal.idTipoRegPatron = '${idTipoRegPatron}';
	});
</script>