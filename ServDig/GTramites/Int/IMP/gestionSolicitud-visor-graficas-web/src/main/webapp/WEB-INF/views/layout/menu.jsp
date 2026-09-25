<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<%-- <div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">

		<div class="container">

			<button data-target=".nav-collapse" data-toggle="collapse"
				class="btn btn-navbar" type="button">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<a href="#" class="brand">Portal IMSS</a>
			<div class="nav-collapse collapse">
				<ul class="nav pull-right">
					<li class="active">
						<a href="${contextpath}/portal">Inicio</a>
					</li>

					<li class="dropdown pull-right">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">Ha iniciado sesi&oacute;n
							como <c:out value='${usuario.usuario}' /> <b class="caret"></b>
						</a>
						
						<ul class="dropdown-menu">
							<li>
								<address class="resumen-text resumen-persona">
									<i class="icon-user" style="margin-right: 15px;"></i>
									<strong>
										${usuario.usuario}
									</strong> <br>
									<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>
										${usuario.perfilUsuario.descripcion}
									<c:if test="${not empty usuario.usuarioFuncionario.delegacion }">
										<br>
										<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>
										${usuario.usuarioFuncionario.delegacion.clave } - ${usuario.usuarioFuncionario.delegacion.descripcion }
									</c:if>
									<c:if test="${not empty usuario.usuarioFuncionario.subdelegacion }">
										<br>
										<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>
										${usuario.usuarioFuncionario.subdelegacion.clave } - ${usuario.usuarioFuncionario.subdelegacion.descripcion }
									</c:if>
									<div>
										<br>
										<a id="cerrarSesionLink" href="#"> <i class="icon-off"
											style="margin-right: 15px;"></i>Cerrar Sesi&oacute;n
										</a>
									</div>
								</address>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div> --%>


<div class="menu_holder" align="right">


	<div id="dgCerrarSesion" title="Cerrar Sesion">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span> &iquest;Esta Ud.
			seguro de cerrar su sesi&oacute;n?
		</p>
		<br> <span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>

</div>


<!--Inicio Menu 
<div id="fgMenu" class="menu_holder">

	<ul class="menu" id="fgMenuContent">

	</ul>
</div>
-->