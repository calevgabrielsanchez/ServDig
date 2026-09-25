
var sIdNameFormPaginarSocios="#sociosFormPaginar";
var dtSocios;
var arrayDatos = new Array();

/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {	
	
	/* Configuracion del data table de Socios*/
	dtSocios = $('#tbSocios').dataTable({
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"iDisplayLength": 5,
		"bAutoWidth": false,
		"aoColumns" : [ 
			    {			            	   
			       	"mDataProp" : "idSocio",
			       	"bVisible": false
			    }
			    ,{			            	   
			       	"mDataProp" : "idPersona",
			       	"bVisible": false
			    }
			    ,{			            	   
			       	"mDataProp" : "idPersonaMoralPatron",
			       	"bVisible": false
			    }
			    ,{ 
					"sTitle" : "RFC",
					"mDataProp" : "rfc"					
				}
			    ,{ 
					"sTitle" : "CURP",
					"mDataProp" : "curp"					
				}
				,{ 
					"sTitle" : "Nombre o Raz&oacute;n Social",
					"mDataProp" : "nombreRazonSocial"				
				}				
				//,{ 
					//"sTitle" : "",					
					//"fnRender": function ( oObj ) {						
						//index=oObj.aData.idSocio;
						//arrayDatos[index]=oObj.aData;						
						//return construyeLiga(index);
					//}					
				//}
			],

			"bProcessing" : true,
			"sAjaxSource" : '/delta-gestionPatronal-web/portlet/empresas/socios/paginar',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				aoData.push({
					"name" : "sSearch",
					"value" : ''
				});
					
				var wrapper = new Object();
				wrapper.aoData = aoData;
				var oForm = $(sIdNameFormPaginarSocios).serializeObject(true);
				wrapper.oForm = oForm;
				wrapper.oForm.idPersonaMoralPatron = $("#idPersonaHidden").val();;
				
				$.postJSON(sSource, wrapper, function(data) {								
					fnCallback(data);
				});
					
			}
	
	});
	
	$("#tbSocios tbody").hover(function(){
		$(this).css('cursor', 'pointer');
	});


	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbSocios tbody").click(function(event) {
		$(dtSocios.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}else{
			$(event.target.parentNode).addClass('row_selected');
		}		
	});
	
});


function construyeLiga( object ){
	return "<a href='#' onclick='showSocio("+object+")'>Mostrar Detalle</a>";
}

function showSocio(indice){	

}
