<%@ include file="../general/taglibs.jsp"%>

<script src="${staticResourcesPath}/js/widget/widget.js"></script>
<script src="${staticResourcesPath}/js/portlet/portlet.js"></script>

<script>
	$(function(){
		widget.init();
		portlet.init();
	});
</script>

<div id="homecontenido" class="container">
	<div class="row">
		<div class="contenedor-widget col-xs-4">
			<div class="widget"
				widget-url="/gestionVigenciaGpoFamiliar-web-externo/widget/dummy/${idDummy}"></div>
		</div>
		<div class="contenedor-portlet col-xs-8">

			<div class="portlets">
				<div class="portlet"
					portlet-url="/gestionVigenciaGpoFamiliar-web-externo/portlet/dummy/${idDummy}"></div>
			</div>
		</div>
	</div>
</div>

<div id="wizardContainer"></div>