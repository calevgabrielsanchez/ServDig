<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type = "text/JavaScript">
	TIPO_TRAMITE_ENUM = {};
	<% for(TipoTramiteEnum tipoTramiteEnum : TipoTramiteEnum.values() ){ %>
		TIPO_TRAMITE_ENUM['<%= tipoTramiteEnum %>'] = <%= tipoTramiteEnum.getCodigo() %>;
	<%}%>
</script>

