<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	$(function(){
		$("#formularioDomicilio").domicilioRecortado({
			funcionCambioColonia: function(asentamiento) {
				alert('cambio colonia ' + asentamiento.clave);
			},
			funcionError: null,
			mostrarMensajeCaptura: false,
			mostrarFormulario: true,
			domicilioDefault : {
				"codigoPostal" : {
					"codigoPostal" : "07700"
				},
				"asentamiento" : {
					"localidad" : {
						"clave" : "0001",
						"nombre" : "GUSTAVO A. MADERO",
						"municipio" : {
							"entidadFederativa" : {
								"clave" : "09",
								"nombre" : "DISTRITO FEDERAL"
							},
							"clave" : "005",
							"nombre" : "GUSTAVO A. MADERO"
						}
					},
					"clave" : "114722",
					"nombre" : "NUEVA INDUSTRIAL VALLEJO"
				},
				"calle" : "QWEQWEQWE",
				"vialidadPrimaria" : {
					"nombre" : "NINGUNO",
					"clave" : "21269850",
					"tipoVialidad" : {
						"descripcion" : "CALLE",
						"clave" : "5"
					}
				},
				"numExteriorAlf" : "12"
			},
			setDomicilioDefaultOnInit : true,
			formularioDeshabilitado: true,
			excepcionesBloqueo: ["calle","numExt", "numInt","colonia"]
		});

	});
</script>

<a href = "/gestionDomicilios-web/domicilio/recortado/prueba" >ir a pagina inicial</a>

<div id="formularioDomicilio"></div>
<div id="dialog-error"></div>