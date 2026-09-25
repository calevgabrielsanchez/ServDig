<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="wrapperData"
	style="background-color: white !important;width: 900px" align="center">
	<form:form modelAttribute="crtDetBaseCotOmitida" method="post" id="formData">
		<form:hidden path="cveAnexoSolCorrPat" id="cveAnexoSolCorrPat"/>
		<form:hidden path="cveEjercicio" id="cveEjercicio"/>
	</form:form>
	<table id="dtdetBaseCotOmitida"  style="width: 900px">
		<thead style="width: 900px">
		</thead>
		<tbody style="width: 900px">
		</tbody>
	</table>
	
</div>
<br>