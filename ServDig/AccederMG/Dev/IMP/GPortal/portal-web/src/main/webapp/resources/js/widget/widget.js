/*
 * JS Widget 
 */

var widget = {
	waitingDiv : '',
	
	init : function() {
		$('.widget').bind('init-widget', function() {
			var _widget = $(this);
			var _url = _widget.attr('widget-url');
			
			if (_url != null && _url != '' && _url != undefined) {
				// Se limpia el contenido del widget
				$(_widget).children('.contenedor').empty();
				
				$.get(_url, null, function(data) {
					//carga de una sola vez
					_widget.html(data);
				}).done(function() {
					if (widget.waitingDiv === '') {
						widget.waitingDiv = $('div#waitingDivCommon').html();
					}
					
					if ($('.contenido', _widget).attr('load-on-startup') == 'true') {
						var resizeBtn = $('div.controles .widget-resize', _widget);
						resizeBtn.trigger('click');
					}
				});
			} else {
				if ($('.contenido', _widget).attr('load-on-startup') == 'true') {
					var resizeBtn = $('div.controles .widget-resize', _widget);
					resizeBtn.trigger('click');
				}
			}
		}).bind('load-widget', function() {
			var contenedor = $('.contenedor', this);
			var contenidoWrapper = $('.contenido', contenedor);
			var urlInner = contenedor.attr('widget-url');
			
			if (urlInner != null && urlInner != '' && urlInner != undefined) {
				contenidoWrapper.html(widget.waitingDiv);
				
				setTimeout(function() {
					contenidoWrapper.load(urlInner);
				}, 300);
			}
		}).trigger('init-widget');
	},
	
	createSubscriber : function() {
		var idPersonaGeneral = $('#idPersonaWidgetCtrl').val();
		return {
			channel : '/persona/modificacion/' + idPersonaGeneral,
			action : function(message) {
				widget.refresh(message);
			}
		};
	},
	
	refresh : function(message) {
		var idWidgetEjecucion = message.data.idWidget;

		$('.widget').each(function() {
			var oWidget = $(this);
			var idNameWidget = $('.contenedor', oWidget).attr('widget-name');
			if (idNameWidget == idWidgetEjecucion) {
				$(this).trigger('load-widget');
			}
		});
	}
};

$(document).ready(function() {
	//Configuracion de los efectos de presentacion
	$('.contenedor-widget').sortable({
		handle : ".handle",
		placeholder : "sortable-placeholder",
		stop: function(event, ui) {
						
			$('.widget').each(function(index, element){
//				alert(index + ' - ' + $('div.contenedor', element).attr('widget-name'));
			});
			
		}
	});

	var options = {};

	$('.widget-resize').live('click',function(event) {
		var icon = $('i', this);
		var css_present = $(icon).attr('class');
		var iconRefresh = $('.widget-refresh', $(this).parents().filter('.cuerpo'));
		var divDescripcion = $('div.descripcion', $(this).parents().filter('.cuerpo'));
		var divFiltros = $('div.filtros', $(this).parents().filter('.cuerpo'));
		var iconresizefull = "icono-abrir";
		var iconresizesmall = "icono-cerrar";

		if (css_present == iconresizesmall) {
			$(icon).removeClass(css_present).addClass(
					iconresizefull);
		} else {
			$(icon).removeClass(css_present).addClass(
					iconresizesmall);
		}
		
		iconRefresh.toggle();
		divDescripcion.toggle();
		divFiltros.toggle();
		
		$('.contenido', $(this).parent().parent().parent())
			.toggle('drop', options, 500, function (){
								
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
	
	$('.widget .titulo span').live('click',function(event) {
		var resizeBtn = $('div.controles .widget-resize',$(this).parent().parent());
		resizeBtn.trigger('click');
	});

	$('.widget a:has(.icono-refrescar)').live('click', function(event) {
		var _widget = $(this).parents().filter('.widget');
		_widget.trigger('load-widget');
	});
});
