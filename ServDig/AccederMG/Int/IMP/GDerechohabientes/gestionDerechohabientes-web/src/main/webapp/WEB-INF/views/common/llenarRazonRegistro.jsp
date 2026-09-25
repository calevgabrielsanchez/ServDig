<%@ page import="mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum"%>

<script type = "text/JavaScript">
	
	RAZON_REGISTRO_ENUM = {
		'NORMAL' : <%=RazonRegistroEnum.NORMAL.getId()%>,
		'RECIEN_NACIDO' : <%=RazonRegistroEnum.RECIEN_NACIDO.getId()%>,
		'HASTA_16' :  <%=RazonRegistroEnum.HASTA_16.getId()%>,
		'LAUDO' : <%=RazonRegistroEnum.POR_LAUDO.getId()%>,
		'AMPARO' : <%=RazonRegistroEnum.POR_AMPARO.getId()%>,
		'HASTA_25' : <%=RazonRegistroEnum.HASTA_25.getId()%>,
		'ACUERDO' : <%=RazonRegistroEnum.POR_ACUERDO.getId()%>,
		'MAYOR_25' : <%=RazonRegistroEnum.MAYOR_A_25.getId()%>
	}; 
</script>