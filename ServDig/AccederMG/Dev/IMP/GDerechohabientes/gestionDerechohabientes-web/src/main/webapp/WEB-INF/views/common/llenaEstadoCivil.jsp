<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoCivilEnum"%>

<script type = "text/JavaScript">

	ESTADO_CIVIL_ENUM = {
		'SOLTERO' : <%=EstadoCivilEnum.SOLTERO.getId()%>,
		'CASADO': <%=EstadoCivilEnum.CASADO.getId()%>,
		'DIVORCIADO': <%=EstadoCivilEnum.DIVORCIADO.getId()%>,
		'VIUDO': <%=EstadoCivilEnum.VIUDO.getId()%>,
		'CONCUBINATO' : <%=EstadoCivilEnum.CONCUBINATO.getId()%>
	};
	
</script>