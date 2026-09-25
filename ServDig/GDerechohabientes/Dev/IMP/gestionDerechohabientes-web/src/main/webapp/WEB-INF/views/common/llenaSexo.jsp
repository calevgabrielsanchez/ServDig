<%@ page import="mx.gob.imss.ctirss.delta.model.enums.SexoEnum"%>

<script type = "text/JavaScript">

	SEXO_ENUM = {
		'HOMBRE' : <%=SexoEnum.HOMBRE.getId()%>,
		'MUJER': <%=SexoEnum.MUJER.getId()%>,
		'NO_BINARIO': <%=SexoEnum.NO_BINARIO.getId()%>
	};
	
</script>