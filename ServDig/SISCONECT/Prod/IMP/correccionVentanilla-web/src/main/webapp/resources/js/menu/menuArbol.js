


var listaArregloMenu;
$(document).ready(function() {
	
		
	
	var urlRecuperaMenu;
	if(window.location.href.indexOf("login")<0){
		urlRecuperaMenu=$("#contextoWeb").val()+"/login/recuperaMenu.do"
	}else{
		urlRecuperaMenu="../login/recuperaMenu.do"
	}
	
		
		 $.postJSON_Sync(urlRecuperaMenu, null, function(data) {				
			 listaArregloMenu=data;			
		 });	
		 
		 var	data = [
			            {
			                label: 'Gestion',
			                children: [
			                    { label: 'Promocion',
			                      children:[{label:'Generar Nuevo Folio Satic A'},
			                                {label:'Generar Nuevo Folio Satic B'}
			                                ]
			                    },
			                    { label: 'Invitacion' ,
			                    	children:[{label:'Seguimiento'}
				                                ]}
			                ]
			            },
			            {
			                label: 'Asignacion de Auditores',
			                children: [
			                    { label: 'Visor' }
			                ]
			            }
			        ];
		 $('#idTreeMenu').tree({
             data: data,
             autoOpen: true
		 });
	
		
	
		 $('#idTreeMenu').bind(
				    'tree.click',
				    function(event) {
				        // The clicked node is 'event.node'
				        var node = event.node;
				       // alert(node.name);
				        window.location.href="/correccion-web/catalogo/promocionDet.do";
				    }
	
	);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});