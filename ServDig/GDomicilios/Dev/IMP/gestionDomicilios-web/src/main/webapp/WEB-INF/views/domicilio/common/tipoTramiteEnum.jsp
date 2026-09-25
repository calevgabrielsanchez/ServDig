<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type = "text/JavaScript">
	
	TipoTramiteEnum = {
		'ACTUALIZACION_DOMICILIO_PARTICULAR' : <%=TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo()%>,
		'ASIGNACION_DE_DOMICILIO_PARTICULAR_DH' : <%=TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo()%>,
		'CAMBIO_CLINICA' : <%=TipoTramiteEnum.CAMBIO_CLINICA.getCodigo()%>
	};
</script>