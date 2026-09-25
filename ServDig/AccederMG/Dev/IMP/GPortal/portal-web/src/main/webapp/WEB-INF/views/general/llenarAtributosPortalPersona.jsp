<script type="text/javascript">

$(function(){
 
	 AtributosPersonaCtrl.personaFirmada.idPersona = '${idPersona}';
	 AtributosPersonaCtrl.personaFirmada.idFiscalPersona = '${idPersonaFisica}';
	 AtributosPersonaCtrl.personaFirmada.curp = '${curpPersona}';
	 AtributosPersonaCtrl.personaFirmada.rfc = '${rfcFisica}';
	 AtributosPersonaCtrl.personaFirmada.cveDomicilioParticular = '${domicilio.clave}';
	 AtributosPersonaCtrl.personaFirmada.idTipoPersona = '${tipoPersona}';
	 AtributosPersonaCtrl.personaFirmada.nssCifrado = '${objFisica.nssCifrado}';
	 
	 FirmanteCtrl.curp = '${usuario.fisica.curp}';
	 FirmanteCtrl.rfc = '${usuario.fisica.rfc}';

     var nombreRazonSocial = "${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}";
     nombreRazonSocial = nombreRazonSocial.replace(/\'/g, "\\'");
	 FirmanteCtrl.nombreRazonSocial = nombreRazonSocial;
	 
	 AtributosPersonaCtrl.personaPortal.idPersona = '${idPersona}';
	 AtributosPersonaCtrl.personaPortal.idFiscalPersona = '${idPersonaFisica}';
	 AtributosPersonaCtrl.personaPortal.curp = '${curpPersona}';
	 AtributosPersonaCtrl.personaPortal.rfc = '${rfcFisica}'; 
	 AtributosPersonaCtrl.personaPortal.cveDomicilioParticular = '${domicilio.clave}';
	 AtributosPersonaCtrl.personaPortal.idTipoPersona = '${tipoPersona}';
	 AtributosPersonaCtrl.personaPortal.nombreRazonSocial = "${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}";
 });
</script>