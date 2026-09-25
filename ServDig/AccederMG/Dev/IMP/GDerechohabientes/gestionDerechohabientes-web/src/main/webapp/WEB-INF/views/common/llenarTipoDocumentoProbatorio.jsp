<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum"%>

<script type = "text/JavaScript">
	TIPO_DOCUMENTO_PROBATORIO_ENUM = {};
	<% for(TipoDocumentoProbatorioEnum tipo : TipoDocumentoProbatorioEnum.values() ){ %>
		TIPO_DOCUMENTO_PROBATORIO_ENUM['<%= tipo %>'] = <%= tipo.getId() %>;
	<%}%>
</script>