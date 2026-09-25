
<%@ include file="../general/taglibs.jsp"%>

<!--inicia Encabezado-->

<div class="header_top ">

	<div class="top_version cell">
		<span> Version</span>: <span>
			1.0
		</span>
	</div>
	<!--
			<div class="top_nav cell">
				<ul>
					<li><a href="http://www.imss.gob.mx" title="Portal IMSS">Visita 
					el portal oficial del Instituto Mexicano del Seguro Social
					</a></li>
				</ul>
				
			</div>
			-->
</div>

<!--inicio logo-->
<img height="83"
	src="<spring:url value="/static/resources/imagenes/banner.gif" htmlEscape="true" />"
	title="Portal IMSS" width="471" />
<!--Termino logo-->

<div class="top_search">
	<!--inicio busqueda-->
	<form id="cse-search-box"
		action="http://www.imss.gob.mx/buscador/resultado.html">
		<input name="cx" type="hidden"
			value="002360038649913767611:zxhajmgbjye" /> <input name="cof"
			type="hidden" value="FORID:11" /> <input name="ie" type="hidden"
			value="ISO-8859-1" />
		<div>
			<input id="s" name="q" size="15" type="text" /> <input
				id="searchsubmit" type="submit" value="Buscar" />
		</div>
	</form>
</div>


<!-- Termina header -->