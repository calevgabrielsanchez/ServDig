<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


			<form:form modelAttribute="modeloReporte" 
						method="post" id="reportesCaratulaForm" action="reportes.do">
				
				
				<form:hidden path="mensaje" id="mensaje"/>
				<form:hidden path="delegacion"/>
				<form:hidden path="subdelegacion"/>
				<form:hidden path="nombreDelegacion"/>
				<form:hidden path="nombreSubdelegacion"/>
			</form:form>							    
<script>
	
	
function returnMsg(){
	
	var msg = document.getElementById("mensaje").value;

	alert(msg)
	window.close();
	
}

	returnMsg();

	
</script>