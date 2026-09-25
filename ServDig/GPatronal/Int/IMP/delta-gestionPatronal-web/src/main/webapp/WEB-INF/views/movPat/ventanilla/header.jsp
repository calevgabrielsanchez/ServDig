<%--
  Created by IntelliJ IDEA.
  User: hsosa
  Date: 11/08/2023
  Time: 09:56 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../../general/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="usuario" value="<%=session.getAttribute(\"usuario\")%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/movPat/ventanilla/header.js" htmlEscape="true" />"></script>

<div class="well header info-usuario">
	
	<div class="row">
		
			<form class="form-horizontal">
				<div class="form-group" style="margin-bottom: 15px;">
					<label class="col-sm-2 control-label">Usuario:</label>
					<div class="col-sm-3">
						<p class="form-control-static">
							<c:out value='${usuario.usuario}' />
						</p>
						<input id="userCtrl" type="hidden" value="${usuario.usuario}" />
					</div>
					<label class="col-sm-2 control-label">Fecha:</label>
					<div class="col-sm-3">
						<p class="form-control-static">
							<c:out value='${fechaSistema}' />
						</p>
					</div>
					
					<div class="col-sm-2" style="padding-top: 3px;">
						<a href="<%=request.getContextPath()%>/movPat/acceso/ventanilla/home"
							style="padding-right: 10px;"> <i class="icon-home fa-lg"></i>
						</a> <a href="#" id="hrefCerrarSesion"> <i
							class="glyphicon glyphicon-log-out  fa-lg"></i>
						</a>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Delegaci&oacute;n:</label>
					<div class="col-sm-3">
						<p class="form-control-static">
							<c:out value='${usuario.usuarioFuncionario.subdelegacion.delegacion.clave} - ${usuario.usuarioFuncionario.subdelegacion.delegacion.descripcion}' />
						</p>
					</div>
					<label class="col-sm-2 control-label">Subdelegaci&oacute;n:</label>
					<div class="col-sm-3">
						<p class="form-control-static">
							<c:out value='${usuario.usuarioFuncionario.subdelegacion.clave} - ${usuario.usuarioFuncionario.subdelegacion.descripcion}' />
						</p>
					</div>
					<div class="col-sm-2">
						<label class="control-label" style="padding-right: 10px;"> <spring:message
							code="label.version" text="Version" />:
						</label>
						<spring:message code="version.ventanilla" text="1.0.0" />
					</div>
					<!--  div class="col-sm-3">
						<p class="form-control-static">
							<spring:message code="version" text="1.0.0" />
						</p>
					</div-->
				</div>
			</form>
		</div>
</div>

<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n"
	style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"> </span>
		&iquest;Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
	</p>
</div>



