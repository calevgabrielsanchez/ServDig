<%@ include file="../general/taglibs.jsp"%>
<!--Inicio Breadcrumb -->
<div class="breadcrumb">
	<div class="row">
		<div class="col-xs-8">
			<ul style="float: left !important;">
				<li><span class="etiqueta" style="padding: 5px !important;">
						Fecha : </span> <span class="dato"> <c:out value='${fechaSistema}' />
				</span></li>

				<li><span class="etiqueta" style="padding: 5px !important;">
						Usuario : </span> <span class="dato"> <c:out
							value='${usuario.usuario}' />
				</span></li>

			</ul>
		</div>
		<div class="col-xs-4">
			<ul>
				<li><a href="#" onclick="fnAbrirDialogoCerrarSesion();"><img
						src="<spring:url value="/static/resources/imagenes/system-log-out.png" htmlEscape="true" />"
						title="Salir" /></a></li>

				<li><a href="<%=request.getContextPath()%>/home/ventanilla"><img
						src="<spring:url value="/static/resources/imagenes/go-home.png" htmlEscape="true" />"
						title="Inicio" /></a></li>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<ul style="float: left !important;">
				<li><span class="etiqueta" style="padding: 5px !important;">
						Subdelegaci&oacute;n : </span> <span class="dato"> <c:out
							value='${usuario.usuarioFuncionario.subdelegacion.clave} - ${usuario.usuarioFuncionario.subdelegacion.descripcion}' />
				</span></li>
				<li><span class="etiqueta" style="padding: 5px !important;">
						Delegaci&oacute;n : </span> <span class="dato"> <c:out
							value='${usuario.usuarioFuncionario.delegacion.clave} - ${usuario.usuarioFuncionario.delegacion.descripcion}' />
				</span></li>
			</ul>
		</div>
	</div>
</div>
<!--Termino Breadcrumb -->

<div id="dgCerrarSesion" title="Cerrar Sesi�n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"> </span> &iquest;Est&aacute; Ud.
		seguro de cerrar su sesi&oacute;n?
	</p>
</div>