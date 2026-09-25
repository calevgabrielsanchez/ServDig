function ProcesandoUI(module){
  this.module = module;
  this.init();
 
}

ProcesandoUI.prototype.init = function(){
this.metadata={ui:{
 components: [
	  {
	      type: "PanelComponent",
	      id: "procesaConsultaSolicitud", 
	      components: [
	        
	        {
		     type:"ProcesandoComponent",
		     id:"procesando",
		     model:"procesando",              
		     data:"fetchConsulta",
                          
	        }
	      ],
	      layout: [[{span:12}]]
	  }
 ]
 }}

};