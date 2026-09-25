<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<div class="navbar navbar-inverse navbar-fixed-top">
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
					<li class="active"><a href="${contextpath}/portal/ingresar">Inicio</a>
					</li>
						
					<li class="dropdown pull-right"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#">Ha iniciado sesi&oacute;n
							como <c:out value='${usuario.usuario}' /> <b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><span id="resumenpersona"></span>
						</ul></li>
				</ul>

			</div>
		</div>
	</div>
</div>


<div class="menu_holder" align="right">


	<div id="dgCerrarSesion" title="Cerrar Sesion">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span> &iquest;Esta Ud. seguro
			de cerrar su sesi&oacute;n?
		</p>
		</br> <span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>

</div>


<!--Inicio Menu -->
<div id="fgMenu" class="menu_holder">

	<ul class="menu" id="fgMenuContent">

	</ul>
</div>



