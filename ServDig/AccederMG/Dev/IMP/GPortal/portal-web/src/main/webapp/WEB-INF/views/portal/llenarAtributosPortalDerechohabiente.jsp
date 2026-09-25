<script type="text/javascript">

$(function(){
 
	AtributosPersonaCtrl.personaFirmada.idPersona = '${asignacion.idPersona}';
	AtributosPersonaCtrl.personaFirmada.curp = '${usuario.fisica.curp}';
	AtributosPersonaCtrl.personaFirmada.rfc = '${usuario.fisica.rfc}';
	AtributosPersonaCtrl.personaFirmada.nombreCompleto = '${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}';

	 FirmanteCtrl.curp = '${usuario.fisica.curp}';
	 FirmanteCtrl.rfc = '${usuario.fisica.rfc}';
	 var nombreRazonSocial = "${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}";
     nombreRazonSocial = nombreRazonSocial.replace(/\'/g, "\\'");
	 FirmanteCtrl.nombreRazonSocial = nombreRazonSocial;
	 
	 AtributosPersonaCtrl.personaPortal.idPersona = '${persona.idPersona}';
	 AtributosPersonaCtrl.personaPortal.idFiscalPersona = '${idPersonaFM}';
	 AtributosPersonaCtrl.personaPortal.curp = '${persona.curp}';
	 AtributosPersonaCtrl.personaPortal.rfc = '${persona.rfc}';
	 AtributosPersonaCtrl.personaPortal.idTipoPersona = '${tipoPersona}';
	 AtributosPersonaCtrl.personaPortal.nombreRazonSocial = '${nombre}';
	 AtributosPersonaCtrl.personaPortal.nss = '${asignacion.nss}';
});
</script>