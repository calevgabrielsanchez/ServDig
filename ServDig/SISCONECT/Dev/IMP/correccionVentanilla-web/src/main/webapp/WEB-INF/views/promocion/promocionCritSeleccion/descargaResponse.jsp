<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


	<form:form modelAttribute="promocionCargaModel" method="post" id="descargaPromocionCritForm" action="">
		<form:hidden path="msg" id="msg"/>			
	</form:form>	
							    
<script>
function returnMsg(){
	
	var msg = document.getElementById("msg").value;

	alert(msg)
	window.close();
	
}


returnMsg();
</script>