<%@ include file="../general/taglibs.jsp"%>
<!-- Main Wizard page template -->

<div class="body">
	<div class="page m-t-none">
		<div class="container-fluid">
			<div class="wizard row">
				<tiles:insertAttribute name="contenido" />
			</div>
			<div id="pie" class="row">
				<tiles:insertAttribute name="pie" />
			</div>
		</div>
	</div>
</div>
<jsp:include page="btn-accesibilidad.jsp"/>
