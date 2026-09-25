<%@ include file="../general/taglibs.jsp"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<c:set var="errorLogin" value="${errorLogin}"/>

<script>
	function tramite(valor){
		document.getElementById('grupoTramite').value = valor;		
		document.getElementById('formlogin').submit();
	}
	
	function firmaE(){
		var idForm = "#formlogin";
		document.getElementById('grupoTramite').value = 0;
		$(idForm).attr('action', '<%=request.getContextPath()%>/modulo/firma');
		$(idForm).submit();
	}
	
</script>

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="rol" value="${usuario.perfilUsuario.idPerfilUsuario}" />
<c:set var="codigoJefeDeptoD" value="<%=CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().toString()%>" />
<c:set var="codigoJefeDeptoSD" value="<%=CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().toString()%>" />
<c:set var="codigoJefeOfnaD" value="<%=CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo().toString()%>" />
<c:set var="codigoJefeOfnaSD" value="<%=CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().toString()%>" />
<c:set var="codigoVenllaClasifD" value="<%=CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().toString()%>" />
<c:set var="codigoVenllaClasifSD" value="<%=CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().toString()%>" />

<c:set var="codigoNormativoCe" value="<%=CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().toString()%>" />
<c:set var="codigoNormativoD" value="<%=CodigoRolClasificacion.NORMATIVO_DEL.getCodigo().toString()%>" />
<c:set var="codigoNormativoSD" value="<%=CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().toString()%>" />

<c:set var="codigoDelegado" value="<%=CodigoRolClasificacion.DELEGADO_DEL.getCodigo().toString()%>" />
<c:set var="codigoSubDelegadoSD" value="<%=CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo().toString()%>" />
<c:set var="codigoJefeOfnaCobrosSD" value="<%=CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo().toString()%>" />


<%-- 
<input type="hidden" id="rol" name="rol" value="${rol.idPerfilUsuario}"/>
 --%>
<c:set var="grupoTramite" value="<%=session.getAttribute(\"grupoTramite\")%>" />

<div id="homecontenido" class="contenedor">
	<div class="row" style="height: 400px;">

		<div class="cell" style="padding: 20px;">
			<div class="page_holder_no_height">
			<div class="post_entry_wide no-border">
				<!--Aquí pega tu código-->
				<div class="form-comment">
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />
					<%session.setAttribute("cenefa", "Inicio» An&aacute;lisis y consulta"); %>
					<form:form modelAttribute="usuario"	action="${contextpath}/analisis/seleccionar" method="get" id="formlogin">
						<fieldset>
							<legend><strong>Clasificaci&oacute;n de Empresas</strong></legend>
							<div style="text-align:center; padding-left:190px;">

								<c:if test="${errorLogin eq 'errorLogin'}">
									<p>&nbsp;</p>
									<p align="left" style=" padding-left:55px;">
										<span class="error">El usuario no cuenta con un perfil valido para operar en la aplicación</span>
									</p>
									<p>&nbsp;</p>
								</c:if>
								
								<c:if test="${errorLogin ne 'errorLogin'}">
									
									<c:if test="${rol == codigoJefeDeptoD || rol == codigoJefeDeptoSD || rol == codigoJefeOfnaD || rol == codigoJefeOfnaSD
										|| rol == codigoVenllaClasifD || rol == codigoVenllaClasifSD || rol == codigoNormativoCe || rol == codigoNormativoD
										|| rol == codigoNormativoSD }">
										<input type="button" value="<spring:message code="label.inscripcion.inicial" />" class="mboton" onclick="tramite(1)"/>
										<input type="button" value="<spring:message code="label.modificacion.patronal" />" class="mboton" onclick="tramite(2)"/> 										
										<input type="button" value="<spring:message code="label.dictamen.patronal" />" class="mboton" onclick="tramite(3)"/>
										<input type="button" value="<spring:message code="label.firma.clem-ver" />" class="mboton" onclick="firmaE()"/>	
									</c:if>
									<%-- 
										Valida los roles para mostrar el texto adecuado
									 --%>
									<c:if test="${rol == codigoDelegado || rol == codigoSubDelegadoSD || rol == codigoJefeOfnaCobrosSD}">
										<input type="button" value="<spring:message code="label.firma.clem" />" class="mboton" onclick="firmaE()"/>	
									</c:if>
								</c:if>
									
                            	<input type="hidden" id="grupoTramite" name="grupoTramite">
							</div>
						</fieldset>
					</form:form>
				</div>
			</div>
			</div>	
		</div>
	</div>
</div>