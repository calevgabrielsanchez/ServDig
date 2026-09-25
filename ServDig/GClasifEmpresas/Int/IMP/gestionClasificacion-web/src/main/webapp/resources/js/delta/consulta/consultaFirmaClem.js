var regPatronal,
tipoPersona,
delegacion,
subdelegacion,
idSolicitud,
dtSolicitudesConcluidas;

var muestraAvisos = '0';

/*Se ejecuta hasta que la pagina se carga completamente*/
$(window).load(function(){
	limpiarMensaje();
	initDatable();
	$.ajaxSetup({async:true});	
	

});

/*Se ejecuta al momento en el que el DOM esta listo.*/
$(document).ready(function() {
	/*configuracion del submit de la forma de filtros*/
	$('#boton').click(function(){
		buscar();
		
		$('.navbar').remove();
		$('.main-footer').remove();
	});

 	// Comprobar los checkbox seleccionados
//	$('#verSeleccionados').click(function(){
//	    var seleccionados = new Array();
//	    $('input[type=checkbox].checkClem:checked').each(function() {
//	      seleccionados.push($(this).val());
//	    });
//	    alert("Elementos seleccionados => " + seleccionados);
//	});
//	

	document.getElementById("cenefa").innerHTML = "Inicio&raquo; Firmar Clem";

	$.ajaxSetup({async:false});

});


function seleccionarTodosChecks(e){
 
    $('input[type=checkbox].checkClem').each(function() {
    	
      $(this).prop('checked', e.checked);
    });
   
};

function seleccionarCheck(e){
	
   if(!e.checked) {
	   document.getElementById("checkSeleccionar").checked = false
   }
   
};

//Cuando se preciona el boton buscar, mapea la tabla o envia mensajes, vuelve a ejecutar InitDataTable()
function buscar(){
	limpiarMensaje();	
	if (dtSolicitudesConcluidas) {
		dtSolicitudesConcluidas.fnDraw();
	} else {
		var oTable = $('#tableSolicitudesConcluidas').dataTable();  
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayStart = 0;
		oTable.fnDraw();
	}	
}

//Lo que hace al pasar al modulo firmar CLEM, crea la tabla
function initDatable(){

	/* Configuracion del data table de solicitudes concluidas*/
	if( $('#tableSolicitudesConcluidas').length) {
		
		var checkSeleccionar = "<label id='seleccionarTodos' disabled><input type='checkbox' name='checkSeleccionar' id='checkSeleccionar' disabled onchange='seleccionarTodosChecks(this)'/> Seleccionar todos</label>";
		
		dtSolicitudesConcluidas = $('#tableSolicitudesConcluidas').dataTable({
			bJQueryUI : true,
			bFilter : false,
			bInfo:true,
			bSort: false,
			"bPaginate": true,
			"aLengthMenu": [[5, 10], [5, 10]],
			"iDisplayLength" : 5,
			"bAutoWidth" : false,
			"iDeferLoading": 0,
			"bServerSide" : true,
			"aoColumns" : [ 
				{"sTitle" : checkSeleccionar,            	"mDataProp" : "idSolicitud",  	   "sClass":"dtJustifyClassColumnTiny"},
				{"sTitle" : "Registro Patronal",            "mDataProp" : "registroPatronal",  "sClass":"dtJustifyClassColumnTiny"},
				{"sTitle" : "Nombre o raz&oacute;n social", "mDataProp" : "nombreRS",          "sClass":"dtJustifyClassColumnTiny"},
				{"sTitle" : "Fecha de presentaci&oacute;n", "mDataProp" : "fecPresentacion",   "sClass":"dtCenterClassColumnTiny"},
				{"sTitle" : "Estado",                       "mDataProp" : "status",            "sClass":"dtCenterClassColumnTiny"},
				{"sTitle" : "Delegaci&oacute;n",            "mDataProp" : "delegacion",        "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Subdelegaci&oacute;n",         "mDataProp" : "subdelegacion",     "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Tr&aacute;mite",               "mDataProp" : "tipoTramite",       "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "EstadoFirma",                  "mDataProp" : "estadoFirma",       "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Tipo Clem",                    "mDataProp" : "tipoClem",          "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Ver CLEM",                     "mDataProp" : "verCLEM",           "sClass":"dtCenterClassColumnTiny" }				
			],"aoColumnDefs": [
					{								
						"fnRender": function ( oObj ) {
							retVal = oObj.aData['tipoClem'];
							return retVal;
						},
						"aTargets": [ 9 ]						
					},
					{
						"fnRender": function(oObj) {
							var idBoton = 'chk' + oObj.aData['idSolicitud'];
							var retVal = "<input type='checkbox' class='checkClem' id='"+idBoton
								+"'  onclick='seleccionarCheck(this)' name='"+idBoton+"' value='"+oObj.aData['idSolicitud']+"'/>";
							return retVal;
						},
		                "aTargets": [ 0 ]
						
					},
					{				                	   				                	 				                	   				                	   
		                "fnRender": function ( oObj ) {
		                	console.log("DATA OBTENIDA: ",oObj);
		                	var sSource = context_path + "/analisis/get/mostrarDocumento?id=" + oObj.aData['idClem'] + "&tipo=1";
		             	   
		             	   if(muestraAvisos == '1'){
			             	   var retVal = '<a href="' + sSource+ '"  target="_blank" onclick="imprimeAvisos()">VER CLEM</a>';
						   }else{
			             	   var retVal = '<a href="' + sSource+ '"  target="_blank">VER CLEM</a>';
						   }
			               
						   return retVal;
		                },
		                "aTargets": [ 10 ]
		            }								
				],

				"bProcessing" : true,
				"sAjaxSource" : context_path + '/modulo/firma/paginar/clem',
				"fnServerData" : function(sSource, aoData, fnCallback) {

					fnHideErrores("#formFiltros");
					aoData.push({
						"name" : "sSearch",
						"value" : ''
					});

					var wrapper = new Object();
					wrapper.aoData = aoData;
					var oForm = $('#formFiltros').serializeObject(true);
					wrapper.oForm = oForm;
					$.blockUI();
					$.postJSON(sSource, wrapper, function(data) {
						$.unblockUI() 
						document.getElementById("checkSeleccionar").disabled = false;
						document.getElementById("seleccionarTodos").disabled = false;
						fnCallback(data);
					}).error(function(data) {
						$.unblockUI()
						fnProcesarErrores(data, "#formFiltros");
						fnCallback(dataEmpty);
					});

				}
		});		
		$('#divBtn').show();
	}else{
		$('#divBtn').hide();
	}
}

function limpiarMensaje() {
	$("#mensaje").text('');
	$("#mensaje").hide();
}

/////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
// A ESTE METODO SE TENDRIA QUE LLAMAR PARA GUARDAR LOS DATOS DE LA FIRMA
///////////////////////////////////////////////////////////////////////////
// PASAR COMO PARAMETRO cveAnalisis, RFC, FOLIO, ACUSE, FIRMA, CADORI por cada respuesta
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////

function procesarRespuestaFirmaDigital(firmasObj){	
	var size = Object.keys(firmasObj).length;
	console.log(":::::::: Procesando respuesta de la firma");
	for(var i = 0; i < size; i++){
		console.log(":::::::: ::::::::Firma a procesar "+i+":::: ::::::::: :::::::::");	
		var firmaObj = firmasObj[i]; 
		var	cadoriArray = firmaObj.cadori.split('|');
		var cveA = cadoriArray[1].substring(14, cadoriArray[1].length);
		var rf = cadoriArray[9].substring(4, cadoriArray[9].length);
		console.log(firmaObj.acuse);
		console.log(firmaObj.cadori);
		console.log(firmaObj.firma);
		console.log(firmaObj.folio);
		console.log(cveA);
		console.log(rf);
		
		var firmaResponse = {				 
				    folio					: firmaObj.folio,
				    acuse					: firmaObj.acuse,
				    firma					: firmaObj.firma,
				    cadori					: firmaObj.cadori,
					cveAnalisis             : cveA,
					rfc             		: rf,					
		};

		var sSource = context_path + '/modulo/firma/procesarDatosFirma';
		prepararRequest(sSource, firmaResponse, false);
		console.log(":::::::: :::::::::::: ::::::::: :::::::::");	
	}		 
		//Ejemplo
		/*
		var firmaResponse = {
			cveAnalisis             : "18124",
			rfc             		: "SAMJ8103111JX5",
			folio					: "43b926db-593b-4b77-af4a-584fb76ef9b0",
			acuse					: "http://firmadigitalqa.imss.gob.mx/firmaElectronicaWeb/chfecynAcuseApp/view?id=43b926db-593b-4b77-af4a-584fb76ef9b0",
			firma					: "K9+lj/tDhT1HW5LiSCSWGvAiN5TxzQHODdgycxij+q31k/MTwYx5hcD4UuyvaOHE+LE7MpHVW0puwrJX0PFMCByRVwphS7WMTfG6rAh/uePteyARyT2Y0mlnGvPeLATNPqoegTeCmofLwDnFXgAQfyl2XiOz0AeTXCJC78g/BbLhtoLy8PmlODX5WgiC34yq3kiz+SsjSn4dRMYTy3m9EK0ReUoSf12nadQJo5feA5YIme94WyR7AaFRRUXHjEXTglQhlsbRvX6WHdkD8pss4ZiK2KxPLKKXzzyO5T1gzKrRIGR3WO/CsL2/K3MBGuSgsXh0T8OMqmkmvmX8UYndlg==",
			cadori					: "|Identificador_1234|NoFolioCE-17-27-17/02/2020/1111-S|Patron_MARTHA LOPEZ PEREZ|Registro patronal_C8726099103|Prima patron_1.13065|Prima Propuesta_ 4.65325|Delegacion_Regional Michoacán|Subdelegacion_ Lázaro Cárdenas|Titular_CARLOS ALBERTO RAMÍREZ RAMÍREZ|"
		};	*/

	console.log("Termine de procesar datos firma");
}


//function procesarRespuestaFirmaDigital(firmas){
//	
// var	arrayRespuesta = firmas.split(',');
// var    size = arrayRespuesta.length / 4;
// var    count = 0;	
// 
// console.log(":::::::: Procesando respuesta de la firma");
// for(var i = 0; i < size; i++){
//	 console.log(arrayRespuesta[count]);
// }
// console.log(":::::::: :::::::::::: ::::::::: :::::::::");
// 
// for(var i = 0; i < size; i++){
//
//	 var fol = "";
//	 var ac = "";
//	 var fir = "";
//	 var cad = "";
//	 var cveA = "";
//	 var rf = "";
//	 
//	 if(i == 0){
//		 fol = arrayRespuesta[count].substring(11,arrayRespuesta[count++].length-1);
//	 }else{
//		 fol = arrayRespuesta[count].substring(10,arrayRespuesta[count++].length-1);
//	 }
//	 
//	 ac = arrayRespuesta[count].substring(9,arrayRespuesta[count++].length-1);
//	 fir = arrayRespuesta[count].substring(9,arrayRespuesta[count++].length-1);
//	 	 
//	 if(i == size-1){
//		 cad = arrayRespuesta[count].substring(10,arrayRespuesta[count].length-2);
//		 rf = arrayRespuesta[count].substring(arrayRespuesta[count].indexOf("RFC_") + 4,arrayRespuesta[count].length-3);
//	 }else{
//		 cad = arrayRespuesta[count].substring(10,arrayRespuesta[count].length-3);
//		 rf = arrayRespuesta[count].substring(arrayRespuesta[count].indexOf("RFC_") + 4,arrayRespuesta[count].length-2);
//	 }
//	 
//	 cveA = arrayRespuesta[count].slice(25,30);
//	 
//	 var firmaResponse = {
//			 
//			    folio					: fol,
//			    acuse					: ac,
//			    firma					: fir,
//			    cadori					: cad,
//				cveAnalisis             : cveA,
//				rfc             		: rf,
//				
//			};
//	 count++;
//	 var sSource = context_path + '/modulo/firma/procesarDatosFirma';
//		prepararRequest(sSource, firmaResponse, false);
//		console.log("Termine de procesar datos firma");
// }
//	/*
//	var firmaResponse = {
//		cveAnalisis             : "18124",
//		rfc             		: "SAMJ8103111JX5",
//		folio					: "43b926db-593b-4b77-af4a-584fb76ef9b0",
//		acuse					: "http://firmadigitalqa.imss.gob.mx/firmaElectronicaWeb/chfecynAcuseApp/view?id=43b926db-593b-4b77-af4a-584fb76ef9b0",
//		firma					: "K9+lj/tDhT1HW5LiSCSWGvAiN5TxzQHODdgycxij+q31k/MTwYx5hcD4UuyvaOHE+LE7MpHVW0puwrJX0PFMCByRVwphS7WMTfG6rAh/uePteyARyT2Y0mlnGvPeLATNPqoegTeCmofLwDnFXgAQfyl2XiOz0AeTXCJC78g/BbLhtoLy8PmlODX5WgiC34yq3kiz+SsjSn4dRMYTy3m9EK0ReUoSf12nadQJo5feA5YIme94WyR7AaFRRUXHjEXTglQhlsbRvX6WHdkD8pss4ZiK2KxPLKKXzzyO5T1gzKrRIGR3WO/CsL2/K3MBGuSgsXh0T8OMqmkmvmX8UYndlg==",
//		cadori					: "|Identificador_1234|NoFolioCE-17-27-17/02/2020/1111-S|Patron_MARTHA LOPEZ PEREZ|Registro patronal_C8726099103|Prima patron_1.13065|Prima Propuesta_ 4.65325|Delegacion_Regional Michoacán|Subdelegacion_ Lázaro Cárdenas|Titular_CARLOS ALBERTO RAMÍREZ RAMÍREZ|"
//	};	*/
//
//	
//
//}

function prepararRequest(sSource, data, async) {
//	var request = $.ajax({
//		url : sSource,
//		async : async,
//		type : "POST",
//		data : data ? JSON.stringify(data) : null,
//		dataType : "json",
//		contentType : "application/json; charset=utf-8"
//	});
//	request.done(console.log("Se ejucuto correctamente"));
//	request.fail(console.log("Error"));
	
	$.ajax({
		url : sSource,
		async : async,
		type : 'POST',
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			console.log("Se ejucuto correctamente");
		},
		error : function(data) {
			console.log("Error en la respuesta: ");
		}
	});	
		
}

function enviarAFirma() {
	var idForm = "#formFirmarClem";
	var rfc = document.getElementById("rfcV").value;
	var curp = document.getElementById("curp").value;
	
	if(ValidaRfc(rfc,curp)){
		document.getElementById("paramrfcV").value = rfc ;

		//Obtiene tramites seleccionados		
		var seleccionados = new Array();
	    $('input[type=checkbox]:checked').each(function() {
	      seleccionados.push($(this).val());
	    });
		document.getElementById("solicitudesFirma").value = seleccionados;
		
		$(idForm).attr('action', context_path + '/modulo/firma/autenticacion/clem');
		$(idForm).submit();	
	}else{		
		document.getElementById('strRfcError').innerHTML = "El RFC ingresado es incorrecto";
	}
}

function mostrarModal() {
	var modal = document.getElementById("tvesModal");
	var span = document.getElementsByClassName("close")[0];
	var body = document.getElementsByTagName("body")[0];

	modal.style.display = "block";
	body.style.position = "static";
	body.style.height = "100%";
	body.style.overflow = "hidden";

	span.onclick = function() {
		modal.style.display = "none";
		body.style.position = "inherit";
		body.style.height = "auto";
		body.style.overflow = "visible";
	}

	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
			body.style.position = "inherit";
			body.style.height = "auto";
			body.style.overflow = "visible";
		}
	}
} 

function ingresarRFC() {
	
	if ($('#tableSolicitudesConcluidas tr td').length > 1) {
	    var seleccionados = new Array();
	    $('input[type=checkbox].checkClem:checked').each(function() {
	      seleccionados.push($(this).val());
	    });
	    //alert("Elementos seleccionados => " + seleccionados);
		if(seleccionados.length > 0){
			mostrarModal();
		}else{
			alert("Seleccione un documento para firmar");		
		}
	}
	else {
		alert("No se encontraron documentos a firmar");
	}
}

function ValidaRfc(rfcStr,curp) {
	var strCorrecta;
	strCorrecta = rfcStr;
	
	if (rfcStr.length == 12){
	var valid = '^(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
	}else{
	var valid = '^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
	}
	var validRfc=new RegExp(valid);
	var matchArray=strCorrecta.match(validRfc);
	if (matchArray==null) {		
		alert('El RFC ingresado es incorrecto');
		return false;
	}
	if(rfcStr.substring(0,10) !== curp.substring(0,10)){
		alert('El RFC ingresado no pertenece al usuario IMSS a firmar');
		return false;
	}else{	
		return true;
	}
	
}

  function imprimeAvisos(){
    sleep(2000);
    var url = context_path + '/solicitud/downloadAvisos';
    console.log("::: En imprimeAvisos, url: " + url);
    window.open(url, '_blank', 'width=500,height=500,top=100,left=100,resizable=1,scrollbars=1');
  }
 
  function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
  }


