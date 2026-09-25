<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Arquetipo</title>
</head>

<body>
	<table id="listaClientes" class="listado" style="margin: 10px; width:981px">
    	<thead>
       		<tr>
	       		<th>Clave Dia Inhabil</th>
				<th>Descripcion Dia Inhabil</th>
				<th>Fecha Dia Inhabil</th>
      		</tr>
   		</thead>
   		<tbody>
			<c:forEach items="${requestScope.lisDiasInhabilDTOs}" var="diaInhabil" varStatus="status">
				<tr>
					<td>
						${diaInhabil.cveDiaInhabil}
					</td>
					<td>
						${diaInhabil.desDiaInhabil}
					</td>
					<td>
						${diaInhabil.fecFechainhabil}
					</td>
        		</tr>
     		</c:forEach>
		</tbody>
	</table>
</body>
</html>