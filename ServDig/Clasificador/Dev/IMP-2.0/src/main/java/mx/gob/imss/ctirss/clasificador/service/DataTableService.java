package mx.gob.imss.ctirss.clasificador.service;

import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend;

public interface DataTableService {
  AbstractDataTableReply filter(AbstractDataTableSend paramAbstractDataTableSend);
  
  AbstractDataTableReply filterXPalabraAnterior(AbstractDataTableSend paramAbstractDataTableSend);
  
  AbstractDataTableReply filterXNumeroAnterior(AbstractDataTableSend paramAbstractDataTableSend);
  
  AbstractDataTableReply filterXNumero(AbstractDataTableSend paramAbstractDataTableSend);
  
  AbstractDataTableReply filterXPalabra(AbstractDataTableSend paramAbstractDataTableSend);
  
  AbstractDataTableReply filterXPalabraEnAnterior(AbstractDataTableSend paramAbstractDataTableSend);
  
  AbstractDataTableReply filterXNumeroEnAnterior(AbstractDataTableSend paramAbstractDataTableSend);
}

