<%@ include file="../general/taglibs.jsp"%>

<script src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>

<script>
	var _tiempoEspera = 180;
    var _tiempoIntervaloEspera = 30;
	var conector = null;
	
    $(function() {
    	
    	setTimeout(function() {
    		conector = new CometCtrl();
    		conector.init(false);
    	}, 1100);
    	
	    $('button#testProcesando').click(function(e) {
		    e.preventDefault();
			// Se inicializa el componente de Procesando Solicitud
		    ProcesandoSolicitudCtrl.init('procesandoSolicitudComponent',
		            _tiempoEspera, _tiempoIntervaloEspera);
		    ProcesandoSolicitudCtrl.abrir($('input#noFolioSolicitud').val());
	    });
    });
</script>

<div id="homecontenido" class="container">
	<div class="jumbotron">
		<form:form modelAttribute="solicitud">
			<div class="form-group">
				<form:label path="noFolioSolicitud" for="folio">FOLIO</form:label>
				<form:input path="noFolioSolicitud" cssClass="form-control" id="noFolioSolicitud" />
			</div>
			<button type="button" id="testProcesando" class="btn btn-primary">TEST!</button>
		</form:form>
	</div>
</div>

<div id="procesandoSolicitudComponent"></div>