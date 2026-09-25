/*
 * JS de la funcionalidad generica de los portlets
 */

var portlet = {
	waitingDiv : '',
	
	init : function() {
		$('.portlet').bind('two-phase-load', function() {
			var oPortlet = $(this);
			var url = $(oPortlet).attr('portlet-url');
			
			if (url != null && url != '' && typeof url != 'undefined') {
				// Se limpia el contenido del portlet
				$(oPortlet).children('.contenedor').empty();
	
				$.get(url, null, function(data) {
					$(oPortlet).html(data);
				}).done(function() {
					if (portlet.waitingDiv === '') {
						portlet.waitingDiv = $('div#waitingDivCommon').html();
					}
					
					if ($('.contenido', oPortlet).attr('load-on-startup') == 'true') {
						var resizeBtn = $('div.controles .widget-resize', oPortlet);
						resizeBtn.trigger('click');
					}
				});
			} else {
				if ($('.contenido', oPortlet).attr('load-on-startup') == 'true') {
					var resizeBtn = $('div.controles .widget-resize', oPortlet);
					resizeBtn.trigger('click');
				}
			}
		}).bind('load-portlet', function() {
			var contenedor = $('.contenedor', this);
			var contenidoWrapper = $('.contenido', contenedor);
			var urlInner = contenedor.attr('portlet-url');
			
			if (urlInner != null && urlInner != '' && urlInner != undefined) {
				contenidoWrapper.html(portlet.waitingDiv);
				
				setTimeout(function() {
					var urlInner = contenedor.attr('portlet-url');
					contenidoWrapper.load(urlInner);
				}, 300);
			}
		}).trigger('two-phase-load');
	},
	
	createSubscriber : function() {
		var idPersonaGeneral = $('#idPersonaWidgetCtrl').val();
		return {
			channel : [ '/portlets/modificacion/', idPersonaGeneral ].join(''),
			action : function(message) {
				portlet.refresh(message);
			}
		};
	},
	
	refresh : function(message) {		
		var idPortletEjecucion = message.data.idPortlet;

		$('.portlet').each(function() {
			var oPortlet = $(this);
			var idNamePortlet = $('.contenedor', oPortlet).attr('portlet-name');
			if (idNamePortlet == idPortletEjecucion) {
				$(this).trigger('load-portlet');
			}
		});
	}
};

$(document).ready(function() {
	$('.contenedor-portlet .portlets').sortable({
		handle : ".handle",
		placeholder : "sortable-placeholder",
		stop: function(event, ui) {
			
			$('.portlet').each(function(index, element){
//				alert(index + ' - ' + $('div.contenedor', element).attr('portlet-name'));
			});
			
		}
	});
	
	$('.portlet a:has(.icono-refrescar)').live("click", function(event) {
		var _portlet = $(this).parents().filter('.portlet');
		_portlet.trigger('load-portlet');
	});
	
	$('.portlet .titulo span').live('click',function(event) {
		var resizeBtn = $('div.controles .widget-resize',$(this).parent());
		resizeBtn.trigger('click');
	});

});
