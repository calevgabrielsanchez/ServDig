<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum"%>

<script type = "text/JavaScript">

	ESTADO_DERECHOHABIENTE_ENUM = {
		'VIGENTE' : <%=EstadoDerechohabienteEnum.VIGENTE.getId()%>,
		'CONSERVACION_DERECHOS': <%=EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()%>,
		'BAJA': <%=EstadoDerechohabienteEnum.BAJA.getId()%>,
		'PENSION_TRAMITE': <%=EstadoDerechohabienteEnum.PENSION_TRAMITE.getId()%>,
		'CON_DERECHO' : <%=EstadoDerechohabienteEnum.CON_DERECHO.getId()%>,
		'FALLECIDO': <%=EstadoDerechohabienteEnum.FALLECIDO.getId()%>
	};
	
</script>