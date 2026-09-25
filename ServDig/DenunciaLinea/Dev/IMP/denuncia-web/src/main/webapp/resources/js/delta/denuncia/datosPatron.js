var idDgPatronCompl = "#dgAgregarPatron";
var patronesCmpl = [];
var i;

$(document).ready(function() {	
	$("#cargaPatCmpl").hide();
	$("#tblPatronCmpl").hide();
	i = 0;
});

function agregaPatron(){
		
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