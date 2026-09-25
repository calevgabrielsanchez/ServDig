var idDgConfirmacion = "#dgGuardarDenunciaConfirmacion";
var idPaso = "#hdIdPasoDenuncia";
var oDgConfirmacion;
var forma = "#frmReenviar";
var idDgPatronCompl = "#dgAgregarPatron";
var patronesCmpl = [];
var i;


$(document).ready(function() {
	
	$("#cargaPatCmpl").hide();
	$("#tblPatronCmpl").hide();
	i = 0;
	
	oDgConfirmacion = $(idDgConfirmacion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){
		},
		buttons: {
			"Siguiente Paso": function() {
				
				//guardar();
				
				if($(idPaso).val() == "1"){		
					var actionO = $("#datosTrabajadorForm").attr("action");
								
					$("#datosTrabajadorForm").attr("action",getAppContextParaJS() + actionO );				
					$("#datosTrabajadorForm").submit(); 
				}
				if($(idPaso).val() == "2"){
					var actionO =$("#datosPatronForm").attr("action")
					if(patronesCmpl.length >=  1){		
						$.postJSON( getAppContextParaJS() +"/denunciaLinea/datosPatron/guardarDatosPatron.do", patronesCmpl, function(data) {
							  $("#datosPatronForm").attr("action",getAppContextParaJS() + "/denunciaLinea/datosPatron/datosTrabajo.do" ); 
							  $("#datosPatronForm").submit();
						  }).error(function(data){
								alert("error" + data);
						  });
					}
					//$("#datosPatronForm").attr("action",getAppContextParaJS() + actionO );									
					//$("#datosPatronForm").submit(); 					
				}
				if($(idPaso).val() == "3"){
					
					$("#frmReenviar").attr("action",getAppContextParaJS() +"/denunciaLinea/denunciaRatificacion.do");
				}
				
							
			}, 
			"Salir": function() { 
				
				$(this).dialog("close"); 
			} 
		}
	}).error(function(data){
		alert("error" + data);
	});
});


function abrirDialogoConfirmacion(){	
	oDgConfirmacion.dialog('open');
}

function guardar(){	
	
}

function agregaPatron(){
	
	limpiaErrores();
	
	var denunciadoAntes = $('#denunciadoAntes').val();
	var desNomrazonsocial = $('#desNomrazonsocial').val();	
	var domicilioTrabajo = $('#domicilioTrabajo').val();	
	var desNomreplegal = $('#desNomreplegal').val();	
	var selGiroActividad = $('#selGiroActividad').val();
	var selSector = $('#selSector').val();
	var desRfc = $('#desRfc').val();
	var cveRegpat = $('#cveRegpat').val();
	var numTrabajadores = $('#numTrabajadores').val();
	var domicilioId = $('#domicilioId').val();
	var numTelefono = $('#numTelefono').val();
	var desObservaciones = $('#desObservaciones').val();
	
	if(desNomrazonsocial == ''){
		$("form#datosPatronForm #razonSocial").html('<label class="error">Capture raz&oacute;n Social del patr&oacute;n</label>');
		return;
	}
	if(domicilioTrabajo == ''){
		$("form#datosPatronForm #lblDomTrabajo").html('<label class="error">Capture domicilio de trabajo</label>');
		return;
	}
	if(selGiroActividad=='-1'){
		$("form#datosPatronForm #lblGiro").html('<label class="error">Seleccione Giro del patr&oacute;n</label>');
		return;
	}
	if(numTrabajadores==''){
		$("form#datosPatronForm #lblNumTrabajadores").html('<label class="error">Indique n&uacute;mero de trabajadores</label>');
		return;
	}

	var sDenunciadoAntes= '"denunciadoAntes":'+'"'+denunciadoAntes+'"';
	var sDesNomrazonsocial= '"desNomrazonsocial":'+'"'+desNomrazonsocial+'"';
	var sDomicilioTrabajo= '"domicilioTrabajo":'+'"'+domicilioTrabajo+'"';
	var sDesNomreplegal= '"desNomreplegal":'+'"'+desNomreplegal+'"';
	var sSelGiroActividad= '"selGiroActividad":'+'"'+selGiroActividad+'"';
	var sSelSector= '"selSector":'+'"'+selSector+'"';
	var sDesRfc= '"desRfc":'+'"'+desRfc+'"';
	var sCveRegpat= '"cveRegpat":'+'"'+cveRegpat+'"';
	var sNumTrabajadores= '"numTrabajadores":'+'"'+numTrabajadores+'"';
	var sDomicilioId= '"domicilioId":'+'"'+domicilioId+'"';
	var sNumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
	var sDesObservaciones= '"desObservaciones":'+'"'+desObservaciones+'"';
	
	var idOrdenPatron = ++i;
	var sIdOrdenPatron = '"idOrdenPatron":'+'"'+idOrdenPatron+'"';
	
	var strPatron = '{'+ sDenunciadoAntes +','+ sDesNomrazonsocial +','+ sDomicilioTrabajo +','+ sDesNomreplegal  
	                   +','+ sSelGiroActividad  +','+ sSelSector +','+ sDesRfc +','+ sCveRegpat +','+ sNumTrabajadores 
	                   +','+  sDomicilioId  +','+ sNumTelefono  +','+ sDesObservaciones +','+ sIdOrdenPatron +'}';
	
	var jPatron = jQuery.parseJSON(strPatron);
	
	patronesCmpl.push(jPatron);	
	
	listaPatronesComplemento();
}

function listaPatronesComplemento(){	
	var tabla = $("#tblPatronCmpl");
	tabla.html('');
	jQuery.each(patronesCmpl, function(i, patron) { 
		tabla.append('<tr><td>'+ patron.desNomrazonsocial + '</td><td>Editar &nbsp;<img src="../../../resources/images/details_open.png" width="16" height="16">&nbsp;</td><td> Eliminar &nbsp;<img src="<%=request.getContextPath()%>/resources/images/delete-icon.png" width="16" height="16"> </td>');
	});	
	$("#tblPatronCmpl").show();
}

function validaPatrones(){
	if(patronesCmpl.length >=  1){		
		$.postJSON( getAppContextParaJS() +"/denunciaLinea/datosPatron/agregaPatrones.do", patronesCmpl, function(data) {
			    abrirDialogoConfirmacion();
		  }).error(function(data){
				alert("error" + data);
		  });
	}
}

function muestraAgregaPC(){
	$("#cargaPatCmpl").show();
}

function limpiaErrores(){
	$("form#datosPatronForm #razonSocial").html('');
	$("form#datosPatronForm #lblDomTrabajo").html('');
	$("form#datosPatronForm #lblGiro").html('');
	$("form#datosPatronForm #lblNumTrabajadores").html('');
}
