<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript">

$(function(){
 
	 AtributosPersonaCtrl.personaFirmada.idPersona = '${idPersona}';
	 AtributosPersonaCtrl.personaFirmada.curp = '${usuario.fisica.curp}';
	 AtributosPersonaCtrl.personaFirmada.rfc = '${usuario.fisica.rfc}';
	 
	 AtributosPersonaCtrl.personaFirmada.nssEncriptado = '${usuario.fisica.nssEncriptado}';
	 AtributosPersonaCtrl.personaFirmada.rfcEncriptado = '${usuario.fisica.rfcEncriptado}';
	 
	 FirmanteCtrl.curp = '${usuario.fisica.curp}';
	 FirmanteCtrl.rfc = '${usuario.fisica.rfc}';
	 
	 var nombreRazonSocial = "${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}";
     nombreRazonSocial = nombreRazonSocial.replace(/\'/g, "\\'");
	 FirmanteCtrl.nombreRazonSocial = nombreRazonSocial;
	 
	 AtributosPersonaCtrl.personaPortal.idPersona = '${idPersonaRepresentada}';
	 AtributosPersonaCtrl.personaPortal.idFiscalPersona = '${idPersonaFM}';
	 AtributosPersonaCtrl.personaPortal.curp = '${curpPersona}';
	 AtributosPersonaCtrl.personaPortal.rfc = '${rfc}';
	 AtributosPersonaCtrl.personaPortal.idTipoPersona = '${tipoPersona}';
	 AtributosPersonaCtrl.personaPortal.nombreRazonSocial = "${nombre}";
	 
});
</script>