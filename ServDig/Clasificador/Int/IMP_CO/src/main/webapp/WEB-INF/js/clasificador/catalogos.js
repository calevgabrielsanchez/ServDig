



var arrayFIeldRequiredsCatalogo = [  
{ nombre : 'Division Economica' , id: '#divisionSelect', tipo : 'select'},
{nombre:'Grupo', id: '#grupoSelect', tipo:'select'}
];





$(function(){

	
	/*
	 * Manejo de los eventos asociados al select del combo de division.
	 */
	
$("select#divisionSelect").change(function(){
	  try{
		  grupoCtrl.cargarComboXDvision();
		  
		  /*reseteamos el display start */
		  resetDisplayStart(oTable);
		  
		  
	  }catch (e) {
		alert(e);
	}
  });
/*
 * Manejo de los eventos asociados al select del combo de division.
 */

$("select#grupoSelect").change(function(){
	  try{
		  
		  /*reseteamos el display start */
		  resetDisplayStart(oTable);
		  
		  
	  }catch (e) {
		alert(e);
	}
});


})


function buscar(){

	fraccionCtrl.buscarFracciones();
}

//http://remysharp.com/2007/01/20/auto-populating-select-boxes-using-jquery-ajax/



var ctrlMsgCatalogos = new  msgObject();
ctrlMsgCatalogos.sDivWrapperMsg = '#wrapperMsgResults';
//ctrlMsgCatalogos.sSpanDisplayResult = '#msgResultados';
//ctrlMsgCatalogos.sSpanTotalResult = '#msgTotal';
ctrlMsgCatalogos.sDivWrapperTooMsg= '#wrapperMsgResultsToo';

var ctrlValidaDatosCatalogos = new ctrlValidaDatos();


function hasCapturaDatosCatalogo (){
			return 	ctrlValidaDatosCatalogos.checkCapturaCompleta(arrayFIeldRequiredsCatalogo, false);
}



/**
 * Iniciamos el data table de la primera pantalla
 */
$(document).ready(function() {

	
	//Inicializamos el combo de divisiones	
	if (showVigente) {
		divisionCtrl.cargarComboDivisiones();
	} else  {
		divisionVigenteCtrl.cargarComboDivisionesVigentes();
	}
		
oTable = $('#example').dataTable({
	bJQueryUI : true,
	bFilter : false,
	bInfo:true,
	bSort: false,
	"bPaginate": true,
	"bAutoWidth" : false,
	"bServerSide" : true,
	"sPaginationType": "full_numbers",
	"aoColumns" : [ 
               
				{
	fnRender :function(oObj){
		var retVal = '<input type="radio" value="' + oObj.aData['desFraccion'] +'" id="radio" class="radioFraccion" name="radio" onclick="fraccionCtrl.getFraccion(this.value);"/> ';
		return retVal;
		
	}, 
	aTargets: [0]
	},{
			"sTitle" : "Fracci&oacute;n",
			"mDataProp" : "desFraccion",
			"sClass": "dtFraccionClassColumn"
		}, {
			"sTitle" : "Actividad",
			"mDataProp" : "nomActividad",
			"sClass": "dtCenterClassColumn"
			
		}, {
			"sTitle" : "Descripci&oacute;n",
			"mDataProp" : "desActividad",
			"sClass":"dtJustifyClassHLColumn"
		}, {
			"sTitle" : "Clase",
			"mDataProp" : "cveClase",
			"sClass": "dtCenterClassColumn",
			"bVisible" : showClase
		}
		
		
		],

		"bProcessing" : true,
		"sAjaxSource" : 'fraccion/buscarFraccionPorGrupo.do',
		"fnServerData" : function(sSource, aoData, fnCallback) {
			
			
			/*Ocultamos el mensaje*/
			ctrlMsgCatalogos.hidde();
			
				var selectGrupo = $("select#grupoSelect").val();				
				var selectDivision = $("select#divisionSelect").val();
				
			if( hasCapturaDatosCatalogo() ){
			
				aoData.push({
				"name" : "cveGrupo",
				"value" : selectGrupo
			}, {"name" : "cveDivision",
				"value" : selectDivision}, 
			   {"name" : "vigente",
				"value" : showVigente} );
				
			$.postJSON(sSource, aoData, function(data) {
				
				var sustantivos = new Array(); 
				sustantivos = getSentences(data);
				
				fnCallback(data);
				/*Mostramos el mensaje de resultados*/
			  ctrlMsgCatalogos.show( data.iTotalRecords  , data.iTotalDisplayRecords);
			  
			  
			  /*
				 * LUDS: Se resolvio el problema con el subrayado, el cual 
				 * fue un problema de arreglos de arreglos.
				 */
					var c = 0;
				  $('#resultados td.dtJustifyClassHLColumn').each(function(){
					  var sentence = sustantivos[c];
					 innerHighlightSingle(this, sentence);
					  c++;
				  });
			  
//			  for (var count=0; count<sustantivos.length;count++) {
//				  
//				  var sentence = sustantivos[count];
//				  
//				  $('#resultados td.dtJustifyClassHLColumn').highlightClasif(  sentence  );
//			  
//			  }
			  
			});
			}else{
				fnCallback(dataEmpty);
			}
		}
	});

});

function limpiarFormularioCatalogo(){
limpiarFormulario('#wrapperFilters');
//	fraccionCtrl.buscarFracciones();
 		oTable.fnDraw();
}