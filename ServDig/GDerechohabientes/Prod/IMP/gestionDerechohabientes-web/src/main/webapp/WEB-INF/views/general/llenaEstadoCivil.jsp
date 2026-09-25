<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoCivilEnum"%>

<script type = "text/JavaScript">

	SOLTERO = <%=EstadoCivilEnum.SOLTERO.getId()%>;
	CASADO = <%=EstadoCivilEnum.CASADO.getId()%>;
	DIVORCIADO = <%=EstadoCivilEnum.DIVORCIADO.getId()%>;
	VIUDO = <%=EstadoCivilEnum.VIUDO.getId()%>;
	CONCUBINATO = <%=EstadoCivilEnum.CONCUBINATO.getId()%>;
	PERSONA_EN_UNION_CIVIL =<%=EstadoCivilEnum.PERSONA_EN_UNION_CIVIL.getId()%>;

</script>