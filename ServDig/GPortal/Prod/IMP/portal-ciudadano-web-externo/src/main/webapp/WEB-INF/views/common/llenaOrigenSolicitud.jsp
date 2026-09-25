<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<script type = "text/JavaScript">
	ORIGEN_SOLICITUD_ENUM = {};
	<% for(OrigenSolicitudEnum origen : OrigenSolicitudEnum.values() ){ %>
		ORIGEN_SOLICITUD_ENUM['<%= origen %>'] = <%= origen.getId() %>;
	<%}%>
</script>
