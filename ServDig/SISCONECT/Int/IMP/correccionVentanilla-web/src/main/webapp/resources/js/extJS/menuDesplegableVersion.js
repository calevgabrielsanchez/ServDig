


var listaArregloMenu;
var identificadores;
var correccionActiva;


$(document).ready(function() {
	//console.log("Correccion actuva "+correccionActiva);
	
	
	recuperaListadoCorrecciones();
	//listenersIconos();
	if(correccionActiva!=undefined){
		$("#radioCorreccion"+correccionActiva).prop("checked", true)
		$("#radioCorreccion"+correccionActiva).trigger("onclick" );
	}
	
	
	if($("#cveSolcorrAct").val()!='null'){
		$("#radioCorreccion"+$("#cveSolcorrAct").val()).prop("checked", true)
		$("#radioCorreccion"+$("#cveSolcorrAct").val()).trigger("onclick" );
	}
	$("#regPatronalLog").text($("#regPatronalMenu").val());
	
	
	
	
	
});


function buscaElemento(arreglo,element){
	var flag=true;	
	for(var s=0;s<arreglo.length;s++){		
		//console.log(arreglo[s]+" "+element );
		if(arreglo[s]==element){
			
			flag=false;
		}
	}
	return flag;
}

function generaMenu(cveSolCorr,numeroFolio){
	$("#accordion").empty();
	$("#accordion").accordion( "destroy" );
	listaArregloMenu=generaNodo(recuperaMenu(cveSolCorr,numeroFolio));
	identificadores = new Array() 
		//console.log("Elemento "+listaArregloMenu.length);
	for(var s=0;s<listaArregloMenu.length;s++){
		
		if(listaArregloMenu[s].leaf){			
			//if(identificadores.indexOf(listaArregloMenu[s].IdFkMenu)<0){
			if(buscaElemento(identificadores,listaArregloMenu[s].IdFkMenu)){
				identificadores.push(listaArregloMenu[s].IdFkMenu)
			}			
		}
	}
	
	var arregloFinal=new Array() 
	//console.log("segunda busqueda ");
	for(var s=0;s<listaArregloMenu.length;s++){
		if(!buscaElemento(identificadores,listaArregloMenu[s].idMenu)){
		//if(identificadores.indexOf(''+listaArregloMenu[s].idMenu)>0){
			arregloFinal.push(listaArregloMenu[s]);
		}
	}
	
	
	
	for(var t=0;t<arregloFinal.length;t++){
		generaPanelMenu(arregloFinal[t],'accordion');		
	}	
	
	$("#accordion").accordion();
}

function generaPanelMenu(nodo,div){
	
	$("#"+div).append('<h3><a href="#">'+nodo.nombreProceso+'</a></h3>');
	$("#"+div).append('<div id="contenedorMenu'+nodo.idMenu+'"></div>');
	
	$("#contenedorMenu"+nodo.idMenu).append('<fieldset id="fieldSet'+nodo.idMenu+'" style="height: 200px;"></fieldset>');
	$("#fieldSet"+nodo.idMenu).append('<table id="submenu'+nodo.idMenu+'"  class="table table-striped table-bordered"><table>');
	
	
	
	
	
	$('#submenu'+nodo.idMenu).dataTable( {
	"aaData": nodo.children,
	bRetrieve:true,
	"aoColumns": [
		{ 
		   "sTitle": "Servicio",
		   "mDataProp" : "nombreProceso", 
		   "sClass": "dtCenterClassColumn",
		   "fnRender":function(o,val){
				   	if(o.aData['linkBloqueado']){
				   		return o.aData['nombreProceso'];
				   	}else{
				   		return '<a href="'+o.aData['href']+'">'+o.aData['nombreProceso']+'</a>';
				   	}				
				},
				"sWidth":"350px"
		},
		{ "sTitle": "Detalle","mDataProp" : "detalle", "sWidth":"450px"}
	],
	"oLanguage": {
	      "sInfo": ""
	    },
	bFilter :false,
	bSort :false,
	bPaginate :false
} );
	
	
	
	
//	$("#"+div).append(
//	'<fieldset style="height: 200px;">'
//	+'<table id="menu'+nodo.nombreProceso+'" class="table table-striped table-bordered" cellpadding="0"'
//	+'						cellspacing="0" border="0">'
//	+'						</table>	'
//	+'</fieldset>'
//	);
	
}

function recuperaMenu(cveSolcorr,numeroFolio){
	
	var urlRecuperaMenu;
	var listaArregloMenu;
	
	var sVarSeg = '{"cveSolicitudCorr":"'+cveSolcorr+'","nuFolio":"'+numeroFolio+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	
	if(window.location.href.indexOf("login")<0){
		urlRecuperaMenu=$("#contextoWeb").val()+"/login/recuperaMenu.do"
	}else{
		urlRecuperaMenu="../login/recuperaMenu.do"
	}
	
		
		 $.postJSON_Sync(urlRecuperaMenu, clase, function(data) {				
			 listaArregloMenu=data;
			 //generaNodo(listaArregloMenu);
			// console.log("lista de menu "+listaArregloMenu.length);
			 
		 });	
		 
		 return listaArregloMenu;
}






function generaNodo(lista){
	//console.log("Genera nodo "+lista.length);
	var menu = new Array();
	 for(var s=0;s<lista.length;s++){		 
		 nodo={
				nombreProceso:lista[s].nombreProceso,
				leaf:false,
				children:[],
				IdFkMenu:lista[s].cveFkMenuItem,
				idMenu:lista[s].cvePkMenu,
				detalle:lista[s].detalleModulo,
				linkBloqueado:lista[s].linkBloqueado
				
		};		 
		if(lista[s].href!=null && lista[s].href!='' && lista[s].href!='null'){
			nodo.href=$("#contextoWeb").val()+lista[s].href;
			nodo.leaf=true;
		} 		 
		menu.push(nodo);	
	 }
	 
	 
	 for(var t=0;t<menu.length;t++){
		 for(var n=0;n<menu.length;n++){
				if(menu[t].idMenu==menu[n].IdFkMenu){
					menu[t].children.push(menu[n]);
				}
		 }
	 }
	
	 
	return menu;

	}
	


function recuperaListadoCorrecciones(){
	var urlRecuperaLista=$("#contextoWeb").val()+"/login/recuperaListaCorreciones.do"
	var listaCorreciones;	
	
	 $.postJSON_Sync(urlRecuperaLista, null, function(data) {				
		 listaCorreciones=data;
			$('#listaSolCorreciones').dataTable( {
				"aaData": listaCorreciones,
				iDisplayLength :4,
				"aoColumns": [
				    {
				    	"sTitle":"",
				    	"fnRender":function(o,val){
							//return '<input type="button" value="X" id="botonElim'+o.aData['idRow']+'" onclick="borrarRegistro('+o.aData['idRow']+')" >';
				    		if(o.aData['idFormaPresenta']==1){
				    			return  '<input type="radio" id="radioCorreccion'+o.aData['cveSolicitudCorr']+'" name="solicitudesCorreccion" onclick="recuperaMenuDinamico('+o.aData['cveSolicitudCorr']+',\''+o.aData['nuFolio']+'\')"></input>'
				    		}else{
				    			return '';
				    		}
							
				    		}
				    },          
					{ 
				    	"sTitle": "Folio",
				    	"mDataProp" : "nuFolio", 
				    	"sWidth": "30%" 	
				    },{ 
				    	"sTitle": "Fecha de elaboraci&Oacute;n",
				    	"mDataProp" : "fecFechaElacoracionCorreccion" ,
				    	"sWidth": "30%"
				    },{ 
				    	"sTitle": "Fecha l&iacute;mite",
				    	"mDataProp" : "fecFechaLimite",
				    	"sWidth": "30%"
				    }
				]
			} );	
		 
		 
			
	 });	
	
}

function recuperaMenuDinamico(cveSolCorr,numeroFolio){
	correccionActiva=cveSolCorr;
	generaMenu(cveSolCorr,numeroFolio);
	
}


function listenersIconos(){
	
	
	var options = {};

	$('.widget-resize').live('click',function(event) {
		var icon = $('i', this);
		var css_present = $(icon).attr('class');
		var iconRefresh = $('.widget-refresh', $(this).parents().filter('.cuerpo'));
		var iconresizefull = "icono-abrir";
		var iconresizesmall = "icono-cerrar";

		if (css_present == iconresizesmall) {
			$(icon).removeClass(css_present).addClass(
					iconresizefull);
		} else {
			$(icon).removeClass(css_present).addClass(
					iconresizesmall);
		}
		
		$('.contenido', $(this).parent().parent().parent())
			.toggle('drop', options, 500, function (){
				iconRefresh.toggle();
				
				if (css_present == iconresizefull) {
					var loaded = $(this).attr('already-loaded');
					
					if (loaded == 'false') {
						$(this).attr('already-loaded','true');
						if ($(this).parents().filter('.widget').length > 0) {
							$(this).parents().filter('.widget').trigger('load-widget');
						} else {
							$(this).parents().filter('.portlet').trigger('load-portlet');
						}
					}
				} 
		});
	});
	
}




function toggleCorrecciones(){	
	$( "#listaSolCorreciones_wrapper" ).toggle( 'drop',{}, 500 );
	//iconoCorreccion;
	
	
	var iconresizefull = "icono-abrir";
	var iconresizesmall = "icono-cerrar";
	
	var css_present = $("#iconoCorreccion").attr('class');
	if (css_present == iconresizesmall) {
		$("#iconoCorreccion").removeClass(css_present).addClass(
				iconresizefull);
	} else {
		$("#iconoCorreccion").removeClass(css_present).addClass(
				iconresizesmall);
	}
	
}


function toggleMenu(){	
	$( "#accordion" ).toggle( 'drop',{}, 500 );
	
	var iconresizefull = "icono-abrir";
	var iconresizesmall = "icono-cerrar";
	
	var css_present = $("#iconoMenu").attr('class');
	if (css_present == iconresizesmall) {
		$("#iconoMenu").removeClass(css_present).addClass(
				iconresizefull);
	} else {
		$("#iconoMenu").removeClass(css_present).addClass(
				iconresizesmall);
	}
}