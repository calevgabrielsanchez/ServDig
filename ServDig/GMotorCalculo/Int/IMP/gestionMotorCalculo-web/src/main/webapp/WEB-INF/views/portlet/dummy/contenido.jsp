<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:choose>
	<c:when test="${not empty DATOS_DUMMY }">
		<div id="datosDummyWrapper" style="width: 100%; margin: 0 auto;">
			${DATOS_DUMMY }
			<br><br>
			<div id="ejemploCombosDependientes">
				<combo:creaCombo idHtml="idDelegacion"
					idHtmlContenedor="ejemploCombosDependientes"
					entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion"
					idHtmlValor="1"
					mostrarSoloActivos="true" />
				<combo:creaCombo
					entidad="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion"
					idHtml="idSubdelegacion"
					entidadPadre="dicDelegacion.cveIdDelegacion"
					idHtmlPadre="idDelegacion"
					idHtmlContenedor="ejemploCombosDependientes"
					mostrarSoloActivos="true" />
			</div>
			<br>
			<div id="ejemploDataTable">
				<table id="tblDummy" style="width: 100%;"
					class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
					</thead>
					<tbody>

					</tbody>
				</table>
				<br>
				<br>
				<br>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<!-- Contenedor del EMPTY STATE -->
		<div class="row-fluid empty-state">
			<div class="row-fluid">
				<!-- Imagen -->
				<div class="span12 imagen">
					<i class="icon-exclamation-sign icon-5x"></i>
				</div>
			</div>

			<div class="row-fluid">
				<!-- Titulo -->
				<div class="span12 titulo">
					<spring:message code="label.portlet.sin.resultados.dummy" />
				</div>
			</div>

			<br>

			<!-- Opciones -->
			<div class="row-fluid opciones">
				<!-- Opcion -->
				<div class="span6">
					<div class=" opcion">
						<div class="">
							<i class="icon-plus-sign icon-2x"></i>
						</div>
						<div class="">
							<p class="nombre" id="tramiteDummy1">Tr&aacute;mite
								Dummy 1</p>
							<p class="descripcion">Lorem ipsum dolor sit amet,
								consectetur adipiscing elit. In non lorem tristique, pretium
								nisl quis, tristique lacus. Duis sit amet libero et leo lacinia
								auctor. Nullam massa eros, malesuada vitae nisi ut, sollicitudin
								ultricies leo. Duis a vulputate tortor. Mauris vitae eros ut
								massa molestie luctus. Aenean posuere pulvinar tortor ut
								volutpat. Integer a leo vitae mauris cursus ultrices quis in
								nisi. Aliquam vitae libero mattis, rutrum quam ac, convallis
								diam. Nulla quis aliquet dolor. In posuere venenatis lacinia.</p>
						</div>
					</div>
				</div>

				<!-- Opcion -->
				<div class="span6 ">
					<div class=" opcion">
						<div class="">
							<i class="icon-exchange icon-2x"></i>
						</div>
						<div class="">
							<p class="nombre" id="tramiteDummy2">Tr&aacute;mite Dummy 2</p>
							<p class="descripcion">
								Lorem ipsum dolor sit amet,
								consectetur adipiscing elit. In non lorem tristique, pretium
								nisl quis, tristique lacus. Duis sit amet libero et leo lacinia
								auctor. Nullam massa eros, malesuada vitae nisi ut, sollicitudin
								ultricies leo. Duis a vulputate tortor. Mauris vitae eros ut
								massa molestie luctus. Aenean posuere pulvinar tortor ut
								volutpat. Integer a leo vitae mauris cursus ultrices quis in
								nisi. Aliquam vitae libero mattis, rutrum quam ac, convallis
								diam. Nulla quis aliquet dolor. In posuere venenatis lacinia.
							</p>
						</div>
					</div>
				</div>
			</div>

			<!-- Notas  -->
			<div class="row-fluid notas">
				<div class="span12">
					<span class="nota"> <spring:message
							code="label.portlet.emptystate.nota" /> <i
						class="icon-share-alt icon-rotate-90 icon-2x"></i>

					</span>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<script id="initPortlet">
	var columnas = [ {
		mDataProp : "idTipoTramite",
		sTitle : "ID Tipo Tr&aacute;mite"
	}, {
		mDataProp : "descripcion",
		sTitle : "Nombre Tr&aacute;mite"
	}];

	var gridDummy = $('#tblDummy').dataTable({
		"sPaginationType" : "bootstrap-full",
		"oLanguage": {
			"sZeroRecords": "<center><strong style=\"font-size: small;\">Sin informaci�n que mostrar</strong></center>"
		},
		"bServerSide" : true,
		"bLengthChange" : false,
		"bFilter" : false,
		"bProcessing" : false,
		"bSort" : false,
		"aoColumns" : columnas,
		"sAjaxSource" : "/gestionMotorCalculo-web/portlet/consultarDummy",
		"fnServerData" : consultar,
		"iDeferLoading" : 0
	});
	
	function consultar(sSource, aoData, fnCallback) {

		$.blockUI();
				
		var wrapper = new Object();
		/* wrapper.oForm = new Object();
		wrapper.oForm = $('#formBusquedaSolictiudes').serializeObject(true); */
		
		wrapper.aoData = aoData;
			
		$.postJSON(sSource, wrapper, function(data) {
			fnCallback(data);
		}).done(function(){
			$.unblockUI();
		}).error(function(response){
			$.unblockUI();
		});
	}
	
	gridDummy.fnPageChange('first');
</script>
