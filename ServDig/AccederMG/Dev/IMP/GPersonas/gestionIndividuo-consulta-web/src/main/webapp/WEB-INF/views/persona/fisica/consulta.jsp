<%@ include file="../../layout/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/consulta.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>
	
	
<div class="container">

    
	
      <div class="">
   
        
        <div class="form-comment" style="padding-right: 20px;">
					<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
					<form:form modelAttribute="fisica" id="forma" method="post" action="${contextpath}/persona/fisica/ubicar/consultar">	
						
						<c:if test="${ warning == true }">
		<div class="row" >

			<div class="alert alert-danger">
				<h6> Nota: ${mensajeException}</h6>
				Por favor verifique que los datos proporcionados sean correctos.
			</div>
		</div>
	</c:if>
						
						
						
						<c:set var="tipoBusquedaSat" value="BUSQUEDA_CON_SAT"/>
						
						    <div class="alert">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <strong>Nota:</strong> Capture todos los datos requeridos. 
    </div>
						
						<fieldset>
							
							<legend>
								<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
							</legend>
							
							
							<div class="contenedor">
							
							
							<!-- CURP -->
							<div class="row">
									<div class="cell">
										
										<form:label path="curp" cssClass="wide"> <span class="requerido">*</span> &nbsp; CURP  </form:label>
										<form:input path="curp" id="busquedaCurp" cssStyle="width: 300px" maxlength="18" />
										<form:errors path="curp" cssClass="error"></form:errors></br>
									</div>
							</div>
							
							<!-- RFC -->
									<div class="row">
										<div class="cell">
										<form:label path="rfc" cssClass="wide">	
										<c:choose>	
											<c:when test="${keyTipoBusqueda eq tipoBusquedaSat }">
												 <span class="requerido">*</span>&nbsp;
											</c:when>
											<c:otherwise>
												<span class="requerido">&nbsp;</span>
											</c:otherwise>
										</c:choose>
										RFC
										</form:label>
											
											<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" />
											<form:errors path="rfc" cssClass="error"></form:errors></br>
										</div>
									</div>
							
							<!-- Nombre -->
							<div class="row">
								<div class="cell">
									
									<form:label path="nombre" cssClass="wide"> <span class="requerido">*</span>&nbsp; Nombre(s)</form:label>
									<form:input path="nombre" id="busquedaNombres" cssStyle="width: 300px" maxlength="30" />
									<form:errors path="nombre" cssClass="error"></form:errors></br>
								</div>
							</div>
							<!-- Primer Apellido -->
							<div class="row">
								<div class="cell">
									<form:label path="primerApellido" cssClass="wide"><span class="requerido">*</span>&nbsp;Primer Apellido</form:label>
									<form:input path="primerApellido" id="busquedaPrimerApellido" cssStyle="width: 300px" maxlength="30" />
									<form:errors path="primerApellido" cssClass="error"></form:errors>	</br>
								</div>
							</div>
							<!-- Segundo Apellido -->
							<div class="row">
								<div class="cell">
									<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
									<form:input path="segundoApellido" id="busquedaSegundoApellido"	cssStyle="width: 300px" maxlength="30" />
									<form:errors path="segundoApellido" cssClass="error"></form:errors></br>
								</div>
							</div>
							<!-- Sexo -->
							<div class="row">
								<div class="cell">
									<form:label path="sexo.idSexo" cssClass="wide"><span class="requerido">*</span>&nbsp;Sexo</form:label>
									<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="forma" 
									entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${fisica.sexo.idSexo}" 
									mostrarSoloActivos="true"/>
									<form:errors path="sexo.idSexo" cssClass="error"></form:errors>
								</div>
							
							</div>
							<!-- Fecha de nacimiento -->
							<div class="row">
								<div class="cell">
									<form:label path="fechaNacimiento" cssClass="wide"><span class="requerido">*</span>&nbsp;Fecha de Nacimiento </form:label>
									<form:input path="fechaNacimiento" id="busquedaFechaNacimiento"	cssStyle="width: 150px" maxlength="10" />
									<form:errors path="fechaNacimiento" cssClass="error"></form:errors></br>
								</div>
							</div>
							<!-- Lugar de Nacimiento -->
							<div class="row">
							<div class="cell">
								<form:label path="lugarNacimiento.clave" cssClass="wide"><span class="requerido">*</span>&nbsp;Lugar de Nacimiento </form:label>
								<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="forma" 
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
								 idHtmlValor="${fisica.lugarNacimiento.clave}" mostrarSoloActivos="true"/>
								<form:errors path="lugarNacimiento.clave" cssClass="error"></form:errors></br>
							</div>
							</div>
							
								</div>						
						</fieldset>
	<br/>
						<div style="float: right;">
						
							<input type="button" class="mboton" id="cancelar" value="Cancelar"/>
							<input type="submit" class="mboton" id="buscar" value="Buscar"/>
							<input type="button" class="mboton" id="limpiar" value="Limpiar"/>
						
<!-- 							<button type="button" class="btn btn-secondary" id="cancelar"> Cancelar </button> -->
<!-- 							<button type="submit" class="btn btn-secondary" id="buscar"> Buscar </button> -->
<!-- 							<button type="button" class="btn btn-secondary" id="limpiar"> Limpiar </button> -->
						</div>	
						<br />
						
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
					</form:form>
						
					<form name="aoDataForm" id="aoDataForm">
						<input type="hidden" id="iDisplayStart" name="iDisplayStart" value="0"/>
						<input type="hidden" id="iDisplayLength" name="iDisplayLength" value="10"/>
					</form>
					
				</div>
        
        
      </div>

    

    </div>