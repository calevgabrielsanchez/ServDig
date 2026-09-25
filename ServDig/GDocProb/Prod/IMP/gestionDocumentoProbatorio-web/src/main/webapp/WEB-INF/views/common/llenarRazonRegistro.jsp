<%@ page import="mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum"%>

<script type = "text/JavaScript">
	RAZON_REGISTRO_ENUM = {};
	<% for(RazonRegistroEnum razon : RazonRegistroEnum.values() ){ %>
		RAZON_REGISTRO_ENUM['<%= razon %>'] = <%= razon.getId() %>;
	<%}%>
</script>
