<%@ include file="../../general/taglibs.jsp"%>


<table style="width: 920px; border: none;">
	<tr>
		
		<td bgcolor="#C0504D" style="border: none; width: 50px;">
			<img alt="Acta Constitutiva" src="<spring:url value="/static/resources/imagenes/alta/acta.jpg" htmlEscape="true" />" onclick="visualizarPaginaActa(1)">
		</td>
		<td bgcolor="#C0504D" valign="middle" style="color:white; font-family: monospace; font-size: medium; border: none;" onclick="visualizarPaginaActa(1)">
			<b>Acta Constitutiva</b>
		</td>
		<td bgcolor="#9BBB59" style="border:none; width: 50px;">
			<img alt="Registro Sindicato" src="<spring:url value="/static/resources/imagenes/alta/sindicato.jpg" htmlEscape="true" />" onclick="visualizarPaginaActa(2)">
		</td>
		<td bgcolor="#9BBB59" valign="middle"  style="color:white; font-family: monospace; font-size: medium; border: none;" onclick="visualizarPaginaActa(2)">
			<b>Registro Sindicato</b>
		</td>
		
		<td style="width: 20px; border: none;" align="left">
			<!-- <input type="radio" name="radioTipoActa" value="1" onclick="visualizarPaginaActa(this);"> -->
		</td>
		<td style="width: 100px; border: none;" align="left">
			<!-- Acta Constitutiva  -->
		</td>
		<td style="width: 20px; border: none;" align="left">
			<!-- <input type="radio" name="radioTipoActa" value="2" onclick="visualizarPaginaActa(this);">  -->
		</td>
		<td style="border: none;" align="left">
			<!-- Sindicato  -->
		</td>
	</tr>
</table>

<jsp:include page="datosEscritura.jsp"/>
<jsp:include page="datosSindicato.jsp"/>
<jsp:include page="datosRepresentante.jsp"/>
<jsp:include page="datosSocio.jsp"/>