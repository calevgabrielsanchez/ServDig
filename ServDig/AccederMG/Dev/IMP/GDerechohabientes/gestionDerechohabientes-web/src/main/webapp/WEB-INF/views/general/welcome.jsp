<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
	
<script type="text/javascript">
	$(document).ready(
		function() {
			$("#tramita").click(function(){
				$.blockUI();
				$("#forminicial").attr("action","<%=request.getContextPath()%>/tramita");
				$("#forminicial").submit();
			});
			
			$("#autoriza").click(function(){
				$.blockUI();
				$("#forminicial").attr("action","<%=request.getContextPath()%>/autoriza");
				$("#forminicial").submit();
			});
		}		
	);
</script>

<div class="form-comment">	
<br>
<br>
<br>
<center>
<fieldset style="width:600px">
	<h2 id="bNSS" align="center">Gesti&oacute;n de derechohabientes</h2> 
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		
	
		<h4  id="sBNSS" align="center" >Bienvenido al sistema de gesti&oacute;n de derechohabientes. De clic en Ingresar para introducir su usuario y contrase&ntilde;a</h4>	
		<br/>			
		<form id="forminicial">
		<div style="float: right">
				<input id="tramita" type="button" class="mboton"   value="Ingresar">
		</div>
	  	</form>
</fieldset>
</center>
<br>
<br>
</div>