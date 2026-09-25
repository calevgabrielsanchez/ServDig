<%@ include file="../../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum" %>

<c:set var="sinAccionesLabel" scope="page"><spring:message code="sin.acciones.disponibles" /></c:set>
<c:set var="idTipoTramiteActDatosGrales" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />	

<script>
	$(function() {
		if ($('a#datosPersonales').length > 0) {
			$.getScript('/gestionIndividuo-consulta-web-ventanilla/static/resources/js/wizard/common/actualizacion-datos/actualizacionDatosPersonaWizard.js',function(){
				$(document).on('click', '#datosPersonales', function() {
					//Al ejecutar el método datosPersonales(), se debe asignar onSolicitudExitosaCallback a null 
					//si es que no se requiere de callback, ya que ProcesandoSolicitudCtrl es genérico y llega a ocurrir
					//que se queda con onSolicitudExitosaCallback inicializado.
					ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
					datosPersonales();
				});
			});
		}
		
		if ($('a#actualizarDomicilio').length > 0) {
			$.getScript('/gestionDomicilios-web-ventanilla/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js', function(){
				$(document).on('click', '#actualizarDomicilio', function() {
					ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
					ProcesandoSolicitudCtrl.setBeforeCloseCallback(null);
					ProcesandoSolicitudCtrl.setAbrirDialogoExito(true);					
					actualizarDomicilio();
				});
			});
		}
		
		if ($('a#actualizarMediosContacto').length > 0) {
			$.getScript('/gestionIndividuo-consulta-web-ventanilla/static/resources/js/wizard/fisica/modificacion/medios/particulares/modificacionMediosParticularesWizard.js', function(){
				$(document).on('click', '#actualizarMediosContacto', function() {
					ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
					actualizarMediosContacto();
				});
			});
		}
		
		if ($('ul#menuAccionesIdentidad li a.menuIdentidad').length <= 0) {
			var _sinAcciones = '<li role="presentation">'
				+ '<a role="menuitem" tabindex="-1">'
				+ '<i class="icon-warning-sign" '
				+ 'style="margin-right: 6px;"></i>'
				+ '${sinAccionesLabel}</a></li>';
				
			$('ul#menuAccionesIdentidad').append(_sinAcciones);
		}

	});

	function datosPersonales() {
		datosPersonalesCommon(true, true);
	}

	function datosPersonalesSoloRenapo() {
		datosPersonalesCommon(true, false);
	}

	function datosPersonalesSoloSat() {
		datosPersonalesCommon(false, true);
	}

	function datosPersonalesCommon(_consultaRenapo, _consultaSat) {
		init();
		_wizardActualizarDatos = WizardActualizacionDatosCtrl;

		var _rfcPersona = _datoPersona.rfc;
		if (_rfcPersona == null || _rfcPersona == '') {
			_rfcPersona = 'SIN_RFC';
		}
		var _curpPersona = _datoPersona.curp;
		if (_curpPersona == null || _curpPersona == '') {
			_curpPersona = 'SIN_CURP';
		}

		var params = {
			idPersonaSesion : _datoPersona.idPersona,
			idTipoPersona : _datoPersona.tipoPersona == 1 ? 1 : 2,
			idPersona : _datoPersona.idPersona,
			curp : _curpPersona,
			rfc : _rfcPersona,
			consultaRenapo : _consultaRenapo,
			consultaSat : _consultaSat
		};
		
		var idTipoTramiteActDatosGrales = ${idTipoTramiteActDatosGrales};
		representantesLegalesCtrl.init(idTipoTramiteActDatosGrales);
		representantesLegalesCtrl.setWizardPrincipal(WizardActualizacionDatosCtrl);
		
		if(params.idTipoPersona == 1){
			_wizardActualizarDatos.init(_datoPersona.contenedor, params);
			_wizardActualizarDatos.abrir();
		}else{	
			_wizardActualizarDatos.init(_datoPersona.contenedor, params);
			_wizardActualizarDatos.abrir();
			$.unblockUI();
		}	

	}

	function actualizarDomicilio() {
		init();
		_wizardActualizarDomiclio = WizardDomicilioGeneralCtrl;
		_wizardActualizarDomiclio.init({
			idPersona : _datoPersona.idPersona
		}).abrir();
	}

	function actualizarMediosContacto() {
		init();
		_wizarActualizarMediosContacto = WizardModificacionMediosParticularesCtrl;
		_wizarActualizarMediosContacto.init(_datoPersona.contenedor, 1,
				_datoPersona.idPersona);
		_wizarActualizarMediosContacto.abrir();
	}

	function precargarInformacionIdentidad() {
		var identidadPersona = _identidadCtrl.identidad('option',
				'personaUbicada');
		var _personaUbicada = {
			idPersona : identidadPersona.idPersona,
			tipoPersona : identidadPersona.tipoPersona.idTipoPersona,
			curp : identidadPersona.curp != null ? identidadPersona.curp
					: 'SIN_CURP',
			rfc : identidadPersona.rfc != null ? identidadPersona.rfc
					: 'SIN_RFC',
			contenedor : 'wizardDatosActualizacion'
		};
		return _personaUbicada;
	}

	function init() {
		_datoPersona = precargarInformacionIdentidad();
	}
</script>


<c:choose>
	<c:when test="${idTipoPersona eq 1}">
		<!-- ACCIONES PARA UNA PERSONA FÍSICA -->
		<c:if test="${opc:habilitada('ind_actualizar_datos')}">
			<li role="presentation">
				<a role="menuitem" tabindex="-1" id="datosPersonales" class="menuIdentidad">
					<spring:message code="accion.identidad.fisica.datos" />
				</a>
			</li>
		</c:if>
		<c:if test="${opc:habilitada('ind_actualizar_domicilio')}">
			<li role="presentation">
				<a role="menuitem" tabindex="-1" id="actualizarDomicilio" class="menuIdentidad">
					<spring:message code="accion.identidad.fisica.domicilio" />
				</a>
			</li>
		</c:if>
		<c:if test="${opc:habilitada('ind_actualizar_medios')}">
			<li role="presentation">
				<a role="menuitem" tabindex="-1" id="actualizarMediosContacto" class="menuIdentidad">
					<spring:message code="accion.identidad.fisica.medio" />
				</a>
			</li>
		</c:if>
	</c:when>
	<c:otherwise>
		<!-- ACCIONES PARA UNA PERSONA MORAL -->
		<c:if test="${opc:habilitada('ind_actualizar_datos_sujeto')}">
			<li role="presentation">
				<a role="menuitem" tabindex="-1" id="datosPersonales" class="menuIdentidad">
					<spring:message code="accion.identidad.moral.datos" />
				</a>
			</li>
		</c:if>
	</c:otherwise>
</c:choose>