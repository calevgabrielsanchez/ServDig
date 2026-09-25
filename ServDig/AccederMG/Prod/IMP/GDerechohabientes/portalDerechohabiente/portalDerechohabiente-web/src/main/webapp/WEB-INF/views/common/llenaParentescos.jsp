<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>

<script type = "text/JavaScript">
	
	PARENTESCO_ENUM = {
		'PADRES' : <%=ParentescoEnum.PADRES.getId()%>,
		'HIJOS' : <%=ParentescoEnum.HIJOS.getId()%>,
		'CONYUGE' : <%=ParentescoEnum.CONYUGE.getId()%>,
		'CONCUBINARIO' : <%=ParentescoEnum.CONCUBINARIO.getId()%>,
		'ASEGURADO' : <%=ParentescoEnum.ASEGURADO.getId()%>,
		'PENSIONADO' : <%=ParentescoEnum.PENSIONADO.getId()%>,
		'CONCUBINA' : <%=ParentescoEnum.CONCUBINA.getId()%>,
		'MADRES' : <%=ParentescoEnum.MADRE.getId()%>
	};
</script>