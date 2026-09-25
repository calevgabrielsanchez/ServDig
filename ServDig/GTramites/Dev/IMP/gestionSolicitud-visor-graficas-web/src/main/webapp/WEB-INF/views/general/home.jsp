<%@ include file="../general/taglibs.jsp"%>

<script>
	$(function(){
		$('a.graficaLink').each(function(){
			$(this).attr('title', $(this).text().replace(/\t/g, ''));
		});
	});
</script>

<div data-url="demo-page" data-role="page" id="demo-page">
	<div data-role="header" data-theme="b">
		<h1 style="font-weight: 700;">VISOR DE GR&Aacute;FICAS</h1>
		<a href="#left-panel" data-icon="bars" data-iconpos="notext" data-shadow="false" data-iconshadow="false"
			class="ui-nodisc-icon">Men&uacute;</a>
	</div>
	<!-- /header -->
	<div role="main" class="ui-content" id="contenido">
		<div style="text-align: center; width: 80%; margin: 0px auto 14px; display: none;" class="alert alert-danger"
			id="errorGeneral"></div>
		<div id="graficasWrapper"></div>
	</div>
	<!-- /content -->
	<div data-role="panel" id="left-panel" data-theme="b">
		<ul data-role="listview" data-theme="b" class="ui-nodisc-icon" data-filter="true" data-filter-theme="a"
			data-filter-placeholder="Buscar ..." id="lstGraficas">

			<c:forEach var="opcion" items="${opcionesMenu}">
				<c:choose>
					<c:when test="${!opcion.collapsible }">
						<li>
							<a href="#" class="graficaLink" grafica-url="${opcion.url}">${opcion.titulo}</a>
						</li>
					</c:when>
					<c:otherwise>
						<li data-role="collapsible" data-iconpos="right" data-inset="false">
							<h2>${opcion.titulo}</h2>
							<ul data-role="listview" data-theme="b">
								<c:forEach var="opcionDep" items="${opcion.dependientes}">
									<li>
										<a href="#" class="graficaLink" grafica-url="${opcionDep.url }">${opcionDep.titulo }</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	</div>
	<!-- /panel -->
</div>