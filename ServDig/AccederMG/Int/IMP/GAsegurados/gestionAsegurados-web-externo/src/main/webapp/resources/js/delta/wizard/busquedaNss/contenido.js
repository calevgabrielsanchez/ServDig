var nsssEncontrados = null;
var tipoBusquedaNss = 1;

$(document).ready(
	function() {
		
		$("#fechaNacimiento").datepicker({
			showOn: 'both',
			dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(),
			regional:'es',
			yearRange: '-112:+0'
		});
		
		$("#buscar").click(
			function() {
				ocultarMensajeError();
				quitarEspaciosSeguidos("asignacion");
				validarFormulario();
			}	
		);
		
		$("#limpiar").click(
			function() {
				nsssEncontrados = null;
				
				mostrarListado(false);
				$("#fechaNacimiento").val("");
				$("#asignacion").clearForm();
				$("#divError").hide();
			}
		);
	}
);

function validarFormulario() {
	fnHideErrores("form#asignacion");
	var asignacion = $("#asignacion").toObject();
	var url="";
	
	if(tipoBusquedaNss == 1) {
		url='/gestionAsegurados-web-externo/wizard/busqueda/nss/validaciones';
	} else {
		url='/gestionAsegurados-web-externo/wizard/busqueda/nss/validacionesBasicos';
	}
	
	$.postJSON(url, asignacion, function(data2) {
		buscarNss(asignacion);//buscarPersona(oForm);
	}).error(function(data){
		fnProcesarErrores(data, "form#asignacion");
	});
}

function buscarNss(fisica) {
	mostrarListado(false);
	nsssEncontrados = null;
	var url = '/gestionAsegurados-web-externo/wizard/busqueda/nss/buscar';
	
	$.blockUI();
	$.postJSON(url, fisica, function(result) {
		if(result.error != undefined && result.error != null) {
			mostrarMensajeError(result.error);
			$.unblockUI();
		} else {
			procesarNssEncontrados(result.lista);
			$.unblockUI();
		}
	});
}

function procesarNssEncontrados(lista) {
	nsssEncontrados = lista;
	mostrarListado(true);
	pintarFilas(lista);
}

function pintarFilas(lista) {
	var tabla = $("#nssFoundIMSSTable");
	$(tabla).find('tbody').html('');
	if(lista != null && lista != undefined) {
		for(var i=0; i < lista.length ; i++ ) {
			var fila = "<tr>";

			fila += "<td>" + getTexto(lista[i].nss) + "</td>";
			fila += "<td>" + getTexto(lista[i].rfc)+ "</td>";
			fila += "<td>" + getTexto(lista[i].curp)+ "</td>";
			fila += "<td>" + getTexto(lista[i].nombre) + "</td>";
			fila += "<td>" + getTexto(lista[i].primerApellido) + "</td>";
			fila += "<td>" + getTexto(lista[i].segundoApellido) + "</td>";
			fila += "<td>" + getTexto(lista[i].sexo.descripcion) + "</td>";
			fila += "<td>" + getTexto(lista[i].fechaNacimientoFormateada) + "</td>";
			fila += "<td>" + getTexto(lista[i].lugarNacimiento.nombre) + "</td>";
			fila += "<td> <button type='button' class='btn btn-primary' onclick='seleccionarNss("+i+")'> Elegir </button></td>";
			
			fila += "</tr>";
			//Agregamos la fila al tbody
			$(tabla).find('tbody').append(fila);
		}
		
	}
}

function seleccionarNss(indice) {
	
	parent.BusquedaNssCtrl.setPersona(nsssEncontrados[indice]);
	parent.BusquedaNssCtrl.cerrar();
}

function mostrarListado(mostrar) {
	if(mostrar) {
		$("#listadoPersonas").show();
	} else {
		$("#listadoPersonas").hide();
	}
	
}

function mostrarMensajeError(mensaje) {
	$("#divError").show();
	$("#mensajeError").html(mensaje);
}

function ocultarMensajeError() {
	$("#divError").hide();
	$("#mensajeError").html("");
}

function setTipoBusqueda(tipoB) {
	fnHideErrores("form#asignacion");
	$("#asignacion").clearForm();
	tipoBusquedaNss = tipoB;
	ocultarMensajeError();
	if(tipoBusquedaNss==1) {
		$("#camposDatosBasicos").hide();
		$("#camposCurp").show();
		
		$("#busquedaCurp").addClass('active');
		$("#busquedaDatos").removeClass('active');
	} else {
		$("#camposCurp").hide();
		$("#camposDatosBasicos").show();
		
		$("#busquedaDatos").addClass('active');
		$("#busquedaCurp").removeClass('active');
	}
}
function quitarEspaciosSeguidos(formulario) {
	$('form#'+formulario+' input[type=text]').each(function(){
		$(this).val($.trim($(this).val()));
		$(this).val($(this).val().replace(/\s+/gi,' '));
		$(this).val($(this).val().replace(/-+/gi,'-'));
		$(this).val($(this).val().replace(/\'+/gi,'\''));
		$(this).val($(this).val().replace(/\.+/gi,'\.'));
	});
}

function getTexto(propiedad) {
	return propiedad == null || propiedad == undefined? '': propiedad;
}
