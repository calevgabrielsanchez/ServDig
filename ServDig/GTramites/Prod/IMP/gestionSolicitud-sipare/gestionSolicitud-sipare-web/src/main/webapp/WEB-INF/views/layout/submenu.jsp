<%@ include file="../general/taglibs.jsp"%>
<!--Inicio Breadcrumb -->
<div class="breadcrumb">
	<div class="row">
		<div class="col-xs-11" style="padding-right: 0px;">
			<div class="row">
				<div class="col-xs-2 etiqueta">
					<span> Usuario : </span>
				</div>
				<div class="col-xs-4">
					<span class="dato"> <c:out value='${usuario.usuario}' />
					</span>
				</div>
				<div class="col-xs-2 etiqueta">
					<span> Fecha : </span>
				</div>
				<div class="col-xs-4">
					<span class="dato"> <c:out value='${fechaSistema}' />
					</span>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-2 etiqueta">
					<span>Delegaci&oacute;n : </span>
				</div>
				<div class="col-xs-4">
					<span class="dato"> <c:out
							value='${usuario.usuarioFuncionario.delegacion.clave} - ${usuario.usuarioFuncionario.delegacion.descripcion}' />
					</span>
				</div>
				<div class="col-xs-2 etiqueta">
					<span>Subdelegaci&oacute;n : </span>
				</div>
				<div class="col-xs-4">
					<span class="dato"> <c:out
							value='${usuario.usuarioFuncionario.subdelegacion.clave} - ${usuario.usuarioFuncionario.subdelegacion.descripcion}' />
					</span>
				</div>
			</div>
		</div>
		<div class="col-xs-1" style="padding-left: 0px; text-align: center;">
			<a href="<%=request.getContextPath()%>/home"
				style="padding-right: 10px;">
				<img src="<spring:url value="/static/resources/imagenes/go-home.png" htmlEscape="true" />"
				title="Inicio" />
			</a>
			<a href="#" onclick="fnAbrirDialogoCerrarSesion();">
				<img src="<spring:url value="/static/resources/imagenes/system-log-out.png" htmlEscape="true" />"
				title="Salir" />
			</a> 
			
		</div>
	</div>
</div>

<!--Termino Breadcrumb -->
<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n" style="">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"> </span>
		&iquest;Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
	</p>
</div>