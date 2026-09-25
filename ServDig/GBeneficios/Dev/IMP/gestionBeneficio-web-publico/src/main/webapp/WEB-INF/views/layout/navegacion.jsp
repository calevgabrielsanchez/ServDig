<%@ include file="../general/taglibs.jsp"%>

<div>
	<nav role="navigation" class="navbar navbar-inverse sub-navbar navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button data-target="#navBarImssCollapse" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
					<span class="sr-only">Interruptor de Navegación</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="${contextpath}/portal/ingresar" class="navbar-brand">
					IMSS
				</a>
			</div>
			<div id="navBarImssCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<p class="navbar-text">
							<spring:message code="label.version" />
							:
							<spring:message code="version" />
						</p>
					</li>
					<li>
						<p class="navbar-text">
							Fecha:
							<c:out value='${fechaSistema}' />
						</p>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/altaPublica/riss/iniciar">
							<i class="icon-home"></i>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</div>
 
<div class="separadorseccion text-center" style="margin-top: 55px;">
	<h2> GESTI&Oacute;N BENEFICIOS </h2>
</div>

<!--Termino -->
<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n" style="">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"> </span>
		&iquest;Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
	</p>
</div>