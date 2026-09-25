var objDatable;

function salida(){	
	var concubina = $('#integrante').val();
	
	if(concubina == "1"){
		$("#bajaIntegrante").dialog({
			  width: 480,				  
		      buttons : {
		        "Aceptar" : function() {	
		        	$("#frmCita").attr("action","/${mvn.web.app.root}/derechohabiente/baja/concubinato/home");		        	
		        },
		        "Cancelar" : function() {
		        	$(this).dialog("close");
		        }	
		      }
		  });
	}	
}

function imprimeComprobante(idSolicitud){
	var url = context_path +"/derechohabientes/registro/imprimeSolicitud";
	location.href =url + "/" + idSolicitud;
}