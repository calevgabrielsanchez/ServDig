(function($) {
	$.widget("delta.sujeto", {
		options : {
			idTipoSujeto: null,
			nrp : null,
            nss : null,
            idAsignacionNss : null,
            idPersona : null,
            idTipoPersona : null
		},
		_create : function() {
			this.element.html(this._buildEmptyState());
			this._desactivarMenu();
		},
		_setOption : function(key, value) {
			this._super(key, value);
		},
		_setOptions : function(options) {
			this._super(options);
		},
		_destroy : function() {
			console.log("DESTRUYENDO");
		},
		_buildEmptyState : function(){
			var html = '<div id="sujetoWrapper">';
			html += '<div class="empty-state well">';
			html += '<div class="imagen">';
			html += '<i class="glyphicon glyphicon-exclamation-sign"></i>';
			html += '</div>';
			html += '<div class="titulo">No ha seleccionado un registro patronal o sujeto.</div>';
			html += '</div>';
			html += '</div>';

			return html;
		},
		_activarMenu : function(){			
			$('ul#accionesSujetoWrapper').show();
		},
		_desactivarMenu: function(){		
			$('ul#accionesSujetoWrapper').hide();
		},
		mostrar : function() {
			var _this = this;
			
			if (this.options.idTipoSujeto === null) {
                $.error('Para mostrar el detalle de sujeto es necesario especificar su tipo');
            } else if (this.options.idTipoSujeto == '1' 
            	&& (this.options.nss === null
        			|| this.options.idAsignacionNss === null
        			|| this.options.idPersona === null)){
            	$.error('Datos insuficientes para mostrar el detalle del asegurado');
            } else if (this.options.idTipoSujeto == '2' && this.options.nrp === null){
            	$.error('Datos insuficientes para mostrar el detalle del patron');
            } 
			var _container = $('div#sujetoWrapper', this.element);
       	 	
			delete this.options.create;
       	 	delete this.options.disabled;	
			
        	 $.ajax({
                 url: '/portal-ventanilla-web/detalle/sujeto',
                 type: 'post',
                 dataType: 'html',
                 contentType: "application/json; charset=utf-8",
                 data: JSON.stringify(this.options),
                 beforeSend: function() {
                	 _container.html('<div class="loading well" style="text-align: center;"><img class="loading"></div>');
                 },
                 success: function(contenido) {
                	 _container.html(contenido);
                	 
                	 $.ajax({
							url: '/portal-ventanilla-web/menu/sujeto',
							type: 'post',
							dataType: 'html',
							contentType: "application/json; charset=utf-8",
							data: JSON.stringify({
								idTipoSujeto : _this.options.idTipoSujeto
							}),
							success: function(contenido) {
								$('#menuAccionesSujeto').html(contenido);
								_this._activarMenu();
							},
							error: function(error) {
								$('#menuAccionesSujeto').html('Error al obtener las acciones');
							}
						});
                 },
                 error: function(error) {
                	 _container.html(error.responseText);
                 }
             });
		},
		listaNRP : function() {
			var _this = this;
			var _container = $('div#sujetoWrapper', this.element);
			delete this.options.create;
       	 	delete this.options.disabled;	
			
        	 $.ajax({
                 url: '/portal-ventanilla-web/detalle/sujeto',
                 type: 'post',
                 dataType: 'html',
                 contentType: "application/json; charset=utf-8",
                 data: JSON.stringify(this.options),
                 beforeSend: function() {
                	 _container.html('<div class="loading well" style="text-align: center;"><img class="loading"></div>');
                 },
                 success: function(contenido) {
                	 _container.html(contenido);
                 },
                 error: function(error) {
                	 _container.html(error.responseText);
                 }
             });
		},
		actualizar : function() {
			this.limpiar();
			this.mostrar();
		},
		limpiar : function() {
			this.element.html(this._buildEmptyState());
			this._desactivarMenu();
		}
	});
})(jQuery);