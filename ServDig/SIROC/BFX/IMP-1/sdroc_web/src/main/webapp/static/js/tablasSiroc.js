/**
 * 
 */
var dataTableObras = null;
var tablasCtrl = {
	inicialConfig : {
		"lengthMenu" : "Mostrar _MENU_ registros por p&aacute;gina",
		"zeroRecords" : "No se han encontrado registros",
		"info" : "Mostrando p&aacute;gina _PAGE_ de _PAGES_",
		"infoEmpty" : "No hay registros disponibles",
		"infoFiltered" : "(filtrada a partir de _MAX_ registros totales)",
		"search" : "Buscar:",
		"pageLength": 5,
		"paginate" : {
			"previous" : "Anterior",
			"next" : "Siguiente",
			"first" : "Primer p&aacute;gina",
			"last" : "&Uacute;ltima p&aacute;gina"
		}
	},
	initDatatableObras : function() {
		if($('#tblEscritorioR').length) {
			dataTableObras = $('#tblEscritorioR').DataTable({
				"lengthChange": false,
				"pageLength": 10,
				"language" : tablasCtrl.inicialConfig,
		        "processing": true,
		        "serverSide": true,
		        "searching": false,
		        "ordering": false,
		        "ajax": {
		        	cache: false,
		        	url:"/sdroc_web/escritorio/consultaObras",
		        	type: "POST",
		        	contentType: "application/json",
		        	"data": function ( d ) {
		                  return JSON.stringify( d );
		            }
		        },
		        "columnDefs": [
					{
						"visible": false,"targets": 0,
						 "render": function ( data, type, row ) {
							 return row.numActualiza;
						 }
					},
					{
						"visible": false,"targets": 1,
		            	   "render": function ( data, type, row ) {
		            		   return ''+row.numEvaluacionD32;
		            	   }
		               },
		               {
		            	   "render": function ( data, type, row ,meta) {
		            		        return meta.row + meta.settings._iDisplayStart + 1;
		            	   },
		            	   "targets": 2
		               },
	               {
	            	   "render": function ( data, type, row ) {
	            		   return row.cveRegistroObra;
	            	   },
	            	   "targets": 3
	               },
	               {
	            	   "render": function ( data, type, row ) {
	            		   var num_incumplimientos = row.numIncumplimientos;
	            		   var rutaImagen = num_incumplimientos != 1 ? (num_incumplimientos >= 2 ? '/static/images/red.png' : '/static/images/green.png') : '/static/images/yellow.png';
	            		   var imagen = '<img src="/sdroc_web'+rutaImagen+'" width="25px;" height="25px;"></img>';
	               			return imagen;
	            	   },
	            	   "targets": 4
	               },
	               {
	            	   "render": function ( data, type, row ) {
	               			return ''+ row.estatusObraDTO.desEstatusObra;
	            	   },
	            	   "targets": 5
	               },
	               {
	            	   "render": function ( data, type, obra ) {
						   var select ='<select onchange="accionIncidencias(this)" style="min-Width:199.03px"><option id="'+obra.cveRegistroObra+'" selected="selected">Seleccione ...</option>';

						   if ((obra.cveTipoPatron == 4
							   && (obra.estatusObraDTO.cveEstatusObra == 1 || obra.estatusObraDTO.cveEstatusObra == 4 || obra.estatusObraDTO.cveEstatusObra == 6)
							   && !obra.aplicaIncRemplazo) &&
							   ((obra.cveRegistroObra.indexOf("C") > -1) ) ) {
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/remplazar/">Modificar tipo de usuario</option>';
								   select+='<option id="cveRegistroObraRemplazar" value = "'+obra.cveRegistroObra+'" class="hidden"></option>';
						   } else if ((obra.cveTipoPatron == 4 && obra.aplicaIncRemplazo) || ((obra.cveRegistroObra.indexOf("I") > -1))){
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/terminar/">Terminaci\u00F3n</option>';
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/cancelar/">Cancelaci\u00F3n</option>';
						   } else {
							   if(obra.estatusObraDTO.cveEstatusObra == 1 || obra.estatusObraDTO.cveEstatusObra == 2 || obra.estatusObraDTO.cveEstatusObra == 4) {
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/cancelar/">Cancelaci\u00F3n</option>';
							   }
							   if(obra.estatusObraDTO.cveEstatusObra == 1 || obra.estatusObraDTO.cveEstatusObra == 2) {
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/actualizar/">Actualizaci\u00F3n</option>';
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/suspender/">Suspensi\u00F3n</option>';
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/terminar/">Terminaci\u00F3n</option>';
							   }

							   if(obra.estatusObraDTO.cveEstatusObra == 4) {
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/reanudar/">Reanudaci\u00F3n</option>';
							   }

							   if(obra.estatusObraDTO.cveEstatusObra == 1 || obra.estatusObraDTO.cveEstatusObra == 2 || obra.estatusObraDTO.cveEstatusObra == 4) {
								   select+='<option id="'+obra.cveRegistroObra+'" value="/sdroc_web/reporteBimestral/">Reporte Bimestral</option>';
							   }
						   }

	            		   select +='</select>';
	               			return select;
	            	   },
	            	   "targets": 6
	               }
		        ]
		    });
		}
	},
	initDataTables: function() {
		console.log("Inicio las tablas")
		var tableConfig = tablasCtrl.inicialConfig;
		tablasCtrl.initDatatableObras();
		
		if($('#tabRegPatronal').length) {
			$('#tabRegPatronal').DataTable({
				"language" : tableConfig
			});
		}

		if($('#tblConsultaObraRFC').length){
			$('#tblConsultaObraRFC').DataTable({
				"language" : tableConfig
			});
		}

		if($('#tblEscritorio').length) {
			$('#tblEscritorio').DataTable({
				"language" : tableConfig
			});
		}
		
		if($('#tblConsultaAvisoUbicacionObra').length) {
			$('#tblConsultaAvisoUbicacionObra').DataTable({
				"language" : tableConfig
			});
	
			if ($('#tblConsultaAvisoUbicacionObra').length) {
				$('#tblConsultaAvisoUbicacionObra').removeClass('dataTable');
				$('.table thead tr th').css('border-bottom', '1px solid #000');
			}
		}
	}
}