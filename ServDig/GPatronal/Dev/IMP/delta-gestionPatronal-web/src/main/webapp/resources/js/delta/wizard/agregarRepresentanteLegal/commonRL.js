var _dataTableRLs;

$(document).ready(function() {
	
	/* Configuracion del data table de Socios*/
	_dataTableRLs = $('#tbRLs').dataTable({
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"iDisplayLength": 10,
		"bAutoWidth": false,
		"oTableTools": {
			"sRowSelect": "single"
        },		
		"aoColumns" : [ 
		        {			            	   
			       	"mDataProp" : "personaFisica.idPersona",
			       	"bVisible": false
			    },
			    { 					
					"fnRender": function ( oObj ) {						
						var id = oObj.aData.personaFisica.idPersona;
						return construyeSelected(id);
					}					
				}
			    ,{ 
					"sTitle" : "RFC",
					"mDataProp" : "personaFisica.rfc"					
				}
			    ,{ 
					"sTitle" : "Nombre",
					"mDataProp" : "personaFisica.nombre"					
				}
			    ,{ 
					"sTitle" : "Primer Apellido",
					"mDataProp" : "personaFisica.primerApellido"					
				}
			    ,{ 
					"sTitle" : "Segundo Apellido",
					"mDataProp" : "personaFisica.segundoApellido"					
				}
				
			],

			"bProcessing" : true,
			"sAjaxSource" : '/${mvn.web.app.root}/wizard/alta/representeLegal/cargarPaginacionRL',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				aoData.push({
					"name" : "sSearch",
					"value" : ''
				});
				
				var wrapper = new Object();
				wrapper.aoData = aoData;
				var oForm = $('#rlFormPaginar').serializeObject(true);
				wrapper.oForm = oForm;
								
				$.postJSON(sSource, wrapper, function(data) {								
					fnCallback(data);
				});					
			}
	
	});
	
	$("#tbRLs tbody").hover(function(){
		$(this).css('cursor', 'pointer');
	});


	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbRLs tbody").click(function(event) {
		$(_dataTableRLs.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}else{
			$(event.target.parentNode).addClass('row_selected');
		}		
	});
	
});

function construyeSelected( idElemento ){
	return "<input type='radio' name='selRL' onclick='cargarValor("+idElemento+")' >";	
}


function cargarValor(idElemento){	
	$("#hndRepresentanteLegalSelected").val(idElemento);
	//console.debug("RL SELECTED : " + $("#hndRepresentanteLegalSelected").val());
}

function seleccionRL(){
	 var aReturn = new Array();
	 var aTrs = _dataTableRLs.fnGetNodes();
	 
	 for ( var i=0 ; i<aTrs.length ; i++ ) {
		 if ( $(aTrs[i]).hasClass('row_selected') ) {
			 aReturn.push( aTrs[i] );
		 }
	 }
	 alert(aReturn);
	 	
}

function agregarRL(oDataTable, datos){
	var newRow = oDataTable.fnAddData([{"personaFisica":{
		"idPersona"			: datos.idPersona,
		"rfc"				: datos.rfc,
		"nombre"			: datos.nombre,
		"primerApellido"	: datos.primerApellido,
		"segundoApellido"	: datos.segundoApellido	
		}}]);
	var oSettings = oDataTable.fnSettings(); 
	oDataTable.fnDraw();
}

function agregarRLDummy(){
	var newRow = _dataTableRLs.fnAddData([{"personaFisica":{
		"idPersona"			: 1,
		"rfc"				: 'TEST RF ',
		"nombre"			: 'TEST N ',
		"primerApellido"	: 'TEST AP',
		"segundoApellido"	: 'TEST AM'	
		}}]);
	var oSettings = _dataTableRLs.fnSettings(); 
	_dataTableRLs.fnDraw();
}