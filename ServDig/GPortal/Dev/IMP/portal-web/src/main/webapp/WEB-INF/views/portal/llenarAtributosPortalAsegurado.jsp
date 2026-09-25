<script type="text/javascript">

$(function(){
 
	AtributosPersonaCtrl.personaFirmada.idPersona = '${idPersona}';
	AtributosPersonaCtrl.personaFirmada.curp = '${usuario.fisica.curp}';
	AtributosPersonaCtrl.personaFirmada.rfc = '${usuario.fisica.rfc}';
	AtributosPersonaCtrl.personaFirmada.nombreCompleto = '${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}';
	AtributosPersonaCtrl.personaFirmada.nssCifrado = '${usuario.fisica.nssCifrado}';

	 FirmanteCtrl.curp = '${usuario.fisica.curp}';
	 FirmanteCtrl.rfc = '${usuario.fisica.rfc}';
	 var nombreRazonSocial = "${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}";
     nombreRazonSocial = nombreRazonSocial.replace(/\'/g, "\\'");
	 FirmanteCtrl.nombreRazonSocial = nombreRazonSocial;
	 
	 AtributosPersonaCtrl.personaPortal.idPersona = '${idPersona}';
	 AtributosPersonaCtrl.personaPortal.idFiscalPersona = '${idPersonaFM}';
	 AtributosPersonaCtrl.personaPortal.curp = '${curpPersona}';
	 AtributosPersonaCtrl.personaPortal.rfc = '${usuario.fisica.rfc}';
	 AtributosPersonaCtrl.personaPortal.idTipoPersona = '${tipoPersona}';
	 AtributosPersonaCtrl.personaPortal.nombreRazonSocial = '${nombre}';
	 AtributosPersonaCtrl.personaPortal.nss = '${nss}';
});
</script>