(function($) {
	$.widget('delta.persona', {
		options : {
            valorBuscado : null,
            tipoBusqueda : null,
            _tipoPersona : null,
            tituloDialogo : null,
            fnOnClose : null,
            ubicarPersona : null,
            folioRecibido : null
        },
		_dialogoUbicarPersona : null,
		_create : function() {
		},
		_cargar : function(_url, _isBack) {
			var _this = this;
			
			this._dialogoUbicarPersona = this.element.dialog({
				title : _this.options.tituloDialogo,
				autoOpen : false,
				width : 900,
				modal : true,
				resizable : false,
				autoResize : true,
				overlay : {
					opacity : 0.5,
					background : 'black'
				},
				position : {
					my : 'top',
					at : 'top',
					of : window
				},
				close : function(event, ui) {
					$(this).dialog('destroy').empty();
				}
			});
			
			this._dialogoUbicarPersona.dialog('open');
			
			if(this.options.folioRecibido!=null){
				this.element.html('<iframe name="ubicarPersonaFrame" id="ubicarPersonaFrame" width="100%"'
						+'height="100%" frameborder="0" onload="set_size(\'ubicarPersonaFrame\', 900)"/>');
				this.element.append('<form target="ubicarPersonaFrame" id="formularioTemp" action="' + _url + '" method="POST">'
						+ '<input type="hidden" name="folioRecibido" value="' + this.options.folioRecibido + '">'
						+ '</form>');
				$('form#formularioTemp').submit();
			}else{
				if (this.options.tipoBusqueda == 'DATOS_BASICO' || _isBack) {
					this.element.html('<iframe id="ubicarPersonaFrame" src="' + _url
							+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'ubicarPersonaFrame\', 900)"/>');
				} else {
					this.element.html('<iframe name="ubicarPersonaFrame" id="ubicarPersonaFrame" width="100%"'
							+'height="100%" frameborder="0" onload="set_size(\'ubicarPersonaFrame\', 900)"/>');
					this.element.append('<form target="ubicarPersonaFrame" id="formularioTemp" action="' + _url + '" method="POST">'
							+ '<input type="hidden" name="valorBuscado" value="' + this.options.valorBuscado + '">'
							+ '<input type="hidden" name="tipoBusqueda" value="' + this.options.tipoBusqueda + '">' 
							+ '<input type="hidden" name="_tipoPersona" value="' + this.options._tipoPersona + '">'
							+ '</form>');
					$('form#formularioTemp').submit();
				}			
			}
		},
		mostrar : function() {			
			if (this.options.folioRecibido !== null) {
				this._cargar('/${mvn.web.app.root}'+'/ubicar/persona/mostrar/folio');
			} else if (this.options.valorBuscado === null || this.options.tipoBusqueda === null) {
				$.error('LOS VALORES BUSCADOS NO ESTAN DEFINIDOS');
			} else {					
				if (this.options.tipoBusqueda == 'RFC') {
					if (this.options.valorBuscado.length == 12 && this.options._tipoPersona == 2) {
						this.options.tituloDialogo = 'BUSCANDO PERSONA MORAL POR RFC: ' + this.options.valorBuscado.toUpperCase();
						this._cargar('/${mvn.web.app.root}'+'/ubicar/persona/buscar/moral');
					}else if(this.options.valorBuscado.length == 13 && this.options._tipoPersona==1){
						this.options.tituloDialogo = 'BUSCANDO PERSONA F\u00CDSICA POR RFC: ' + this.options.valorBuscado.toUpperCase();
						this._cargar('/${mvn.web.app.root}'+'/ubicar/persona/buscar/fisica');
					}else{
						$.error('DATOS INVALIDOS PARA LA BUSQUEDA POR RFC');
					}
				} else if (this.options.tipoBusqueda == 'CURP') {
					if(this.options.valorBuscado.length == 18 && this.options._tipoPersona==1){
						this.options.tituloDialogo = 'BUSCANDO PERSONA F\u00CDSICA POR CURP: ' + this.options.valorBuscado.toUpperCase();
						this._cargar('/${mvn.web.app.root}'+'/ubicar/persona/buscar/fisica');
					}else{
						$.error('DATOS INVALIDOS PARA LA BUSQUEDA POR CURP');
					}
				} else if (this.options.tipoBusqueda == 'DATOS_BASICO') {
					var _url = '/${mvn.web.app.root}'+'/ubicar/persona/iniciar';
					
					if (this.options._tipoPersona == 2) {
						this.options.tituloDialogo = 'BUSCANDO POR DATOS B\u00C1SICOS PERSONA MORAL';
						window.parent.formulario.tipo = 'moral';
						this._cargar(_url);
					} else if (this.options._tipoPersona == 1) {
						this.options.tituloDialogo = 'BUSCANDO POR DATOS B\u00C1SICOS PERSONA F\u00CDSICA';
						window.parent.formulario.tipo = 'fisica';
						this._cargar(_url);
					} else {
						$.error('DATOS INVALIDOS PARA LA BUSQUEDA POR DATOS BASICOS');
					}
				} else {
					$.error('DATOS INVALIDOS PARA LA BUSQUEDA');
				}
			}
		},
		cerrar : function(){
			this._dialogoUbicarPersona.dialog('close');
			if ($.isFunction(this.options.fnOnClose)) {
				this.options.fnOnClose.call(this, this.options.ubicarPersona);
			}
		},
		cerrarPersonaNoEncontrada : function(){
			this._dialogoUbicarPersona.dialog('close');
		},
		backPersonaMoral : function() {
			this.options.ubicarPersona = null;
			var _url = '/${mvn.web.app.root}/ubicar/persona/iniciar';
			this.options.tituloDialogo = 'BUSCANDO POR DATOS B\u00C1SICOS PERSONA MORAL';
			window.parent.formulario.tipo = 'moral';
			this._cargar(_url, true);
		},
		backPersonaFisica : function() {
			this.options.ubicarPersona = null;
			var _url = '/${mvn.web.app.root}/ubicar/persona/iniciar';
			this.options.tituloDialogo = 'BUSCANDO POR DATOS B\u00C1SICOS PERSONA F\u00CDSICA';
			window.parent.formulario.tipo = 'fisica';
			this._cargar(_url, true);
		}
	});
})(jQuery);