/**
 * 
 */
var dtResultadosActual;


var bSearchByPalabraActual = true;



var arrayFIeldRequiredsActual = [  
{ nombre : 'Fracci\u00F3n' , id: '#txtNumActual', tipo : 'input'},
{nombre:'Palabra(a) clave', id: '#txtPalabraActual', tipo:'input'}
];



/*Creamos el objeto para mostrar o ocultar el mensaje de error*/


var ctrlMsg = new  msgObject();
ctrlMsg.sDivWrapperMsg = '#wrapperMsgResultsActual';
//ctrlMsg.sSpanDisplayResult = '#msgResultadosActual';
//ctrlMsg.sSpanTotalResult = '#msgTotalActual';
ctrlMsg.sDivWrapperTooMsg = '#wrapperMsgResultsActualToo';


/*Handles de los eventos de los input text de las busquedas*/
handelPalabraActual = function(e){

		bSearchByPalabraActual = true;
			
			/*Si esta checado debemos deshabilitar el campo de por numero*/
			$('#txtNumActual').val('');
			$('#txtNumActual').attr({'disabled': false});
			
			/*Y habilitar el de Palabra */
			$('#txtPalabraActual').val('');
			$('#txtPalabraActual').attr({'disabled': false});

			resetDisplayStart(dtResultadosActual);
			
}

handleNumActual = function(e){

			bSearchByPalabraActual = false;
		
	
			/*Si esta checado debemos deshabilitar el campo de por palabra*/
			$('#txtPalabraActual').val('');
			$('#txtPalabraActual').attr({'disabled': false});
			
			/*Y habilitar el de Numero */
			$('#txtNumActual').val('');
			$('#txtNumActual').attr({'disabled': false});
			
			resetDisplayStart(dtResultadosActual);


}


/*Funcion para validar que se haya capturado al menos un filtro*/
function hasFIltroCapturadoActual(){
	
	var oCampo;
	var ctrlValidaDatosActual= new ctrlValidaDatos();
	
	var sFilter;
    
	var num = $('#txtNumActual').val();
	var pal = $('#txtPalabraActual').val();

	if(num == '' &&  pal == '')
		return 	ctrlValidaDatosCatalogos.checkCapturaCompleta(arrayFIeldRequiredsActual, true);
			
	if(bSearchByPalabraActual){
		/*recuperamos el txto de la palabra clave.*/
		sFilter = $('#txtPalabraActual').val();
		oCampo = arrayFIeldRequiredsActual[1];
	}else{
		/*recuperamos el txt del numero*/
		sFilter = $('#txtNumActual').val();
		oCampo = arrayFIeldRequiredsActual[0];
	}

	
	return ctrlValidaDatosActual.checkFiltroCapturado(oCampo);
}


/**
 * Iniciamos la configuracion del data table de la tercera pantalla.
 */
$(document).ready(function() {
	
	
	
	var txtPalabraAnt = $('#txtPalabraActual').button().bind('keypress', handleEnter).bind('focus',handelPalabraActual ).bind('click', handelPalabraActual).addClass('cajaTexto');;
	txtPalabraAnt.attr({'disabled':false});
	var txtNumAnt = $('#txtNumActual').button().attr({'disabled':false}).bind('keypress', handleEnter).bind('focus',handleNumActual ).bind('click', handleNumActual).addClass('cajaTexto');;
	

	
	/*
	 * Configuracion del datatable de resultados anteriores
	 * 
	 * */
	dtResultadosActual = $('#dtResultadosActual').dataTable({
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
				"sAjaxSource" : 'fraccion/buscarFraccionPorCatalogoNuevo.do',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					
					var vSearch ='';
					
					var bSource = 'dpPalabraClave';
					
					if(bSearchByPalabraActual){
						//Buscamos por palabra
						bSource = 'dpPalabraClave';
						/*recuperamos el txto de la palabra clave.*/
						vSearch = $('#txtPalabraActual').val( ).toUpperCase();
					}else{
						//Buscamos por numero
						bSource = 'dpNumero';
						/*recuperamos el txt del numero*/
						vSearch = $('#txtNumActual').val( );
						
						
						
					}
					
					/*Ocultamos el mensaje*/
//					hiddeMsgResultsActual();
					ctrlMsg.hidde();
					/*Ocultamos el texto remarcado*/
					$('#resultadosActual td').removeHighlight();
					
					if(hasFIltroCapturadoActual()){
					
						aoData.push({
						"name" : "sSearch",
						"value" : vSearch

					}, {
						"name" : "time",
						"value" : new Date()
					}, {
						"name" : "dispatch",
						"value" : bSource
					});
			
					$.postJSON(sSource, aoData, function(data) {
						
						var sustantivos = new Array(); 
						sustantivos = getSentences(data);
						
						fnCallback(data);
						/*Mostramos el mensaje de resultados*/
//					 	showMsgResultsActual( data.iTotalDisplayRecords);
					ctrlMsg.show(data.iTotalRecords  , data.iTotalDisplayRecords);
					
					 /*resaltamos el texto que coincida con la busqueda*/
					
					for (var count=0; count<sustantivos.length;count++) {
						  
						  var sentence = sustantivos[count];
						  
						  $('#resultadosActual td.dtJustifyClassHLColumn').highlightClasif(  sentence  );
					  
					  }
					
					/*Marcamos las palabras concretas encontradas*/
					 for (var i = 0 ; i < data.palabrasConcretas.length ; i++){
						 $('#resultadosActual td').highlight(  data.palabrasConcretas[i] );
					 }
					

					});
					
					}else{
						fnCallback(dataEmpty);
					
					}
					
				
				}
			});

	
});

function limpiarFormularioActual(){
	limpiarFormulario('#wrapperFiltersActual');
	dtResultadosActual.fnDraw();
}






