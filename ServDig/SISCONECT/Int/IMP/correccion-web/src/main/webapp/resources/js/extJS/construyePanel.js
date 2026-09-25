
Ext.require([
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*'
]);


Ext.application({
	name : 'HelloExt',
	launch : function() {
		
		
//		
//		
		Ext.create('Ext.panel.Panel', {
		   // width: 900,
		    height: 600,
		    title: 'Sistema de Corrección en Línea',
		    layout: 'border',
		    items: [{
		        title: 'Correciones Pendientes',
		        region: 'center',     // position for region
		        xtype: 'panel',
		        height: 200,
		        split: true,         // enable resizing
		        margins: '5 5 5 5',
		        autoScroll: true,
		        id:"sur",
		        items:[generaConsultaCorrecciones()]
		    },{
		        title: 'Procesos de Patrón',
		        region: 'south',     // center region is required, no width/height specified
		        xtype: 'panel',
		        margins: '5 5 5 5',
		        split: true,
		        autoScroll: true,
		        id: 'centro',
		        collapsible:true,
		        collapseDirection:'bottom',
		        items:[generaGrid()]
		    }],
		    renderTo: "panelPrincipalExtj"
		});
//		
		
	//	generaGrid();
		
		
		
		
		
		
		
		
		
		
	}
});




function generaGrid(){
	
	Ext.define('Task', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'nombreProceso',     type: 'string'},
            {name: 'avance',     type: 'string'},
            {name: 'fecha',     type: 'string'}
        ]
    });
	
	var store = Ext.create('Ext.data.TreeStore', {
		model: 'Task',
	    root: {
	        expanded: true,
	        children: generaNodo(recuperaMenu())
	    }
	});

	var tree = Ext.create('Ext.tree.Panel', {
    
        useArrows: true,
        rootVisible: false,
        store: store,
        height: 330,
        id:"idPanelMenu",
        multiSelect: false,
        border:false,
        singleExpand: false,
        
        autoScroll: true,
        animate:true,
        columns: [{
            xtype: 'treecolumn', //this is so we know which column will show the tree
            text: 'Tarea',
            flex: 2,
            sortable: true,
            dataIndex: 'nombreProceso'          
            
        },{
            //we must use the templateheader component so we can use a custom tpl            
            text: 'Duracion',
            flex: 1,
            sortable: true,
            dataIndex: 'avance',
            align: 'center'
            //add in the custom tpl for the rows
            
        },{
            //we must use the templateheader component so we can use a custom tpl            
            text: 'Fecha',
            flex: 1,
            sortable: true,
            dataIndex: 'fecha',
            align: 'center'
            //add in the custom tpl for the rows
            
        }]        
    });
	return tree;
	
}



function generaConsultaCorrecciones(){
	Ext.create('Ext.data.Store', {
	    storeId:'simpsonsStore',
	    fields:['name', 'email', 'phone',"des","av"],
	    data:{'items':[
	        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224" ,"des":"Valor","av":"12.1" },
	        { 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" ,"des":"Valor","av":"12.3" },
	        { 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244" ,"des":"Valor","av":"13.1" },
	        { 'name': 'Marge', "email":"marge@simpsons.com", "phone":"555-222-1254" ,"des":"Valor","av":"22.1" }
	    ]},
	    proxy: {
	        type: 'memory',
	        reader: {
	            type: 'json',
	            root: 'items'
	        }
	    }
	});

	var panel=Ext.create('Ext.grid.Panel', {
		border:false,
	    store: Ext.data.StoreManager.lookup('simpsonsStore'),
	    columns: [
	        { text: 'Name',  dataIndex: 'name' },
	        { text: 'Email', dataIndex: 'email', flex: 1 },
	        { text: 'Phone', dataIndex: 'phone' },
	        { text: 'Proceso', dataIndex: 'des' },
	        { text: 'Avance', dataIndex: 'av' }
	    ],
	    height: 200
	   // autoWidth: true
	   
	});
	return panel;
	
}


function recuperaMenu(){
	var urlRecuperaMenu;
	var listaArregloMenu;
	if(window.location.href.indexOf("login")<0){
		urlRecuperaMenu=$("#contextoWeb").val()+"/login/recuperaMenu.do"
	}else{
		urlRecuperaMenu="../login/recuperaMenu.do"
	}
	
		
		 $.postJSON_Sync(urlRecuperaMenu, null, function(data) {				
			 listaArregloMenu=data;
			 //generaNodo(listaArregloMenu);
			 
		 });	
		 
		 return listaArregloMenu;
}

var menu;
var nodo;
function generaNodo(lista){
	 menu = [];
	 var menuFinal=[];
	 var padrePrincipal;
	 
	 for(var s=0;s<lista.length;s++){
		 
		 nodo={
				nombreProceso:lista[s].nombreProceso,
				leaf:false,
				children:[],
				IdFkMenu:lista[s].cveFkMenuItem,
				idMenu:lista[s].cvePkMenu,
				expanded: true,
				avance:s+"2%",
				fecha:s+"-12-2013"
		};
		if(lista[s].href!=null){
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
	
	 
	 
	 
	 
	 for(var t=0;t<menu.length;t++){		   
		    if(menu[t].IdFkMenu==null){
				//console.log(menu[t].nombreProceso);
				menuFinal.push(menu[t]);
				}				 
	}

	 
	 
	return menuFinal;

	}
	



