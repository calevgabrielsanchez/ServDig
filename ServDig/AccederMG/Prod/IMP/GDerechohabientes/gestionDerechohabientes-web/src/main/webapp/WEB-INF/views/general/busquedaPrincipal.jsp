<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/welcome/busquedaPrincipal.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
	


<div class="form-comment" align="center">	
<br>
<br>
<br>
<fieldset style="width:400px">
	<h2 id="bNSS"><spring:message code="label.busquedaPrincipal"/></h2> 
	 <!-- <h2 id="bFolio"><spring:message code="label.buscarSolicitud"/></h2>-->	
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
	<form:form modelAttribute="busqueda" action="${contextpath}/welcome/uno/valida" method="post" id="busquedaPrincipalForm" >		
		<br>	
		<h4  id="sBNSS" ><spring:message code="label.buscarPor"/></h4>	
		<!-- <h4  id="sBFolio"><spring:message code="label.busquedaTramite"/></h4> -->
		<br>			
		<center>		
		<table id="nssTable">			
			<tr>
				<td><strong><spring:message code="label.nss"/></strong>: </td>
				<td><form:input class="entero_11" name="nss" path="nss" type="text" value="" maxlength="11" />	</td>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
			<!-- 
			<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario != 4}">
			<tr>
				<td colspan="2">
					<spring:message code="label.seguirTramite"/> 
					<a href="#" onclick="mostrarFolio()">aquí</a> 
				<td>
			</tr>
			</c:if>
			 -->
		</table>
		
		<table id="folioTable">
		</table>
		
		<!-- 
			<tr>
				<td><strong><spring:message code="label.folio"/></strong>: </td>
				<td><form:input  class="entero" name="folio" path="folio" type="text" value="" maxlength="18"/>	</td>
				
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
			<tr>
				
				<td colspan="2">
				<a href="#" onclick="ocultarFolio()">Buscar por Grupo Familiar</a></td>
			</tr>
			<tr>
				<td colspan="2"><br></td>
			</tr>
		</table>
		 -->
		
		<table>
			<tr>
				<td>
					<form:hidden path="usuario" value="${usuario}" />
					<form:hidden path="busca" id="tipoBusqueda" name="tipoBusqueda" />
									
				</td>				
			</tr>
			<tr>
	  			<td>
	  			<c:if test="${pageContext.request.method=='POST'}">
	  			
						<c:if test="${busqueda.error !=null}">
							<script type="text/javascript">
								tb = document.getElementById( 'tipoBusqueda' ).value;
								
								if(tb == 'folio'){
									
									mostrarFolio();
								}
							
							</script>
							
						<br>
							<div id="contError" class="ui-state-error ui-corner-all" align="center">
								<div class="ui-icon ui-icon-alert"></div>
								<br>
								<p class="ui-helper-reset ui-state-error-text"
									id="errorFileUploadDiv">
									<spring:message code="${busqueda.error}"/>
								</p>
							</div>
						</c:if>
					</c:if>
				</td>
	  		</tr>
		  	<tr>
		  		<td align="center">
		  			<br>
					<input type="button" id="aceptarVal" class="mboton"  value="Aceptar">
					&nbsp;
					<input id = "cancelar" type="button"  class="mboton"  value="Cancelar">
				</td>
	  		</tr>
	  
	  	</table>
	  	</center>
		</form:form>
</fieldset>
<br>
<br>
</div>