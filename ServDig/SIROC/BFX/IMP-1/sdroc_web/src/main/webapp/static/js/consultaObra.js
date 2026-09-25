/**
 *Fecha UM    : 21 de Octubre del 2021
 *Version UM  : 3.2
 *Autor UM    : Erika Gutierrez
 *Descripcion : : Cambio para consultar acuses que no se pueden visualizar en SIROC
 *
 */
$(document).ready(function() {

	var tableConfig = {
		"lengthMenu" : "Mostrar _MENU_ registros por página",
		"zeroRecords" : "No se han encontrado registros",
		"info" : "Mostrando página _PAGE_ de _PAGES_",
		"infoEmpty" : "No hay registros disponibles",
		"infoFiltered" : "(filtrada a partir de _MAX_ registros totales)",
		"search" : "Buscar:",
		"paginate" : {
			"previous" : "Anterior",
			"next" : "Siguiente",
			"first" : "Primer página",
			"last" : "Última página"
		}
	};
	
	$('#tblSubcontratos').DataTable({
		"language" : tableConfig
	});
	
	if($('#tblSubcontratos').length){
		$('#tblSubcontratos').removeClass('dataTable');
		$('.table thead tr th').css('border-bottom','1px solid #000');
	}
	
	$('#tblRegistrosPatronales').DataTable({
		"language" : tableConfig
	});
	
	$('#btnReporteDatosObra').click(function() {
		
//		var accion = "/sdroc_web/resumenObra";
		var path = "/sdroc_web/getResumenObraPDF";
		var cveObra = $("#cveInformacionObra").val();
//		var data = {};
//		data = {
//				cveInformacionObra : cveObra				
//		};
//		$.ajax({
//			type : "POST",
//			contentType : "application/json",
//			async : false,
//			url : accion,
//			data : JSON.stringify(data),
//			timeout : 100000,
//			success:function(response) { 
//				console.log(">>>Reporte:  "+response);
				$("#pnlReporteDatosObra").removeClass("hidden");
				$('#pnlRepResumenObra').attr("src", path);	
				$("#pnlRepResumenObra").removeClass("hidden");
				$("#btnReporteDatosObra").addClass("hidden");

				
//			},
//			error:function(response){
//				resultado =  false;
//			}
//		});
	});

});

function mostrarAcusesContrato(select){
	console.log("cambio el select")
		var accion = null;
		var numRegObra = null;
		var $selectAtual = $(select);
		
		var tds = $selectAtual.parent().parent().children();
		var numeroObra = $(tds[1]).find('a').text();
		console.log("#OBRA:"+numeroObra);
		
		var $optionSelected = $selectAtual.find(":selected");
		accion = $optionSelected.attr("value");

		var idaction = obtenerAccion(accion);
		if (idaction > 0) {

			if (idaction == 1) {
				$("#visorAcusesReporte").addClass("hidden");
				var secuenciaNotaria = $optionSelected.attr("id");
				console.log("la secuencia de notaria es " + secuenciaNotaria);
				$selectAtual[0].selectedIndex = 0;
				location = accion;
			} else {
				numRegObra = $optionSelected.attr("id");
				var path = accion + numRegObra + "/" + idaction;
				console.log("accion : " + " " + path);
				$.ajax({
					type : "POST",
					contentType : "plain/text",
					url : "/sdroc_web/guardarNumeroObra/"+numeroObra,
					timeout : 100000,
					cache: false,
					success : function() {
						console.log("SUCCESS: guardarNumeroObra");
					},
					error : function() {
						console.log("ERROR: guardarNumeroObra");
					}
				});
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : path,
					// data : JSON.stringify(numRegObra),
					timeout : 100000,
					cache: false,
					success : function() {
						console.log("SUCCESS: ");
						location = path;
					},
					error : function() {
						console.log("ERROR: ");
					}
				});
			}

		}
		
		function obtenerAccion(accion) {

			if (accion.search('Selecci') != -1) {
				return 0;
			} else if (accion.search('acu') != -1) {
				return 1;
			} else if (accion.search('sub') != -1) {
				return 2;
			}
		}
	}

function mostrarAcusesNotaria(secuenciaNotaria) {
	//creamos el dialogo de acuses
	var $dialogAcuses = $("#modalVisorAcuses").dialog({
		autoOpen : false,
		title: 'Acuses',
		resizable: false,
		closeOnEscape: false,
		modal: true,
		height: secuenciaNotaria ? 530 : 230,
		width: 750,
		buttons: {
			"Cerrar" : function() {
				//limpiamos el div
				$(this).html("");
				$(this).dialog("close");
				$(this).dialog("destroy");
			}
		}
	});
	//si viene la secuencia mostraremos los datos
	if(secuenciaNotaria) {
		//creamos el frame dentro del div de visor de acuses
		$dialogAcuses.html('<iframe id="acusesRegistroObra" name="acusesRegistroObra" width="100%" height="100%" frameborder="0"/>');
		//Creamos formulario para envio de parametros al frame
		var formularioAcuse = $("<form/>", {
			id: 'formAcuseObra',
			name: 'formAcuseObra',
			action : '/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento',
			method : 'POST',
			target: 'acusesRegistroObra'
		});
		//dentro del formulario ponemos la secuencia de notaria
		formularioAcuse.append($("<input/>", {type : 'hidden',name : 'params', id:"params",value : "{\"tramite\":\""+secuenciaNotaria+"\"}"}));
		//agregamos el formulario al mismo div que contiene el frame y le hacemos submit
		$(formularioAcuse).appendTo("#modalVisorAcuses").submit();
		//quitamos el formulario del div
		$("#modalVisorAcuses #formAcuseObra").remove();
		//abrimos el dialogo
	} else {
		//creamos el frame dentro del div de visor de acuses
		$dialogAcuses.html('<div class="alert alert-info">No fue posible localizar el acuse</div>');
	}
	
	$dialogAcuses.dialog("open");
}
