package mx.gob.imss.ctirss.delta.comet.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.FactoryFinder;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.PubSubServerFactory;

@Component
public class DeltaPubSubSeverClient implements Client {

	private static final long serialVersionUID = 1L;
	private final Log log = LogFactory.getLog(getClass());

	private PubSubServer pubSubServer;
	private LocalClient localClient;

	public DeltaPubSubSeverClient() {
	}

	private void init() throws PubSubServerException {

		if (this.pubSubServer == null && this.localClient == null) {
		
			log.info("Se va a obtener el servidor pubSub");
	
			PubSubServerFactory pubSubServerFactory = (PubSubServerFactory) FactoryFinder
					.getFactory(FactoryFinder.PUBSUBSERVER_FACTORY);
	
			pubSubServer = pubSubServerFactory
					.lookupPubSubServer("DeltaPubSubServer");
	
			log.info("Se obtiene el servidor pubSub: " + pubSubServer);
	
			localClient = pubSubServer.getClientManager().createLocalClient();
		} else {
			log.debug("Ya se tiene referencia al servidor PUBSUB");
		}
	}

	public void publish(String channel, Map<String, Object> data)
			throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);

		publish(channel, json);
	}

	public void publish(String channel, String data) throws IOException {
		try {

			init();
			
			this.log.info("Datos a publicar [" + data + "] en el canal "
					+ channel);

			pubSubServer.publishToChannel(localClient, channel, data);

			this.log.info("Datos a publicados exitosamente [" + data
					+ "] en el canal " + channel);
		} catch (PubSubSecurityException e) {
			this.log.error("Error al publicar datos al comet", e);
		} catch (PubSubServerException e) {
			this.log.error("Error al publicar datos al comet", e);
		}
	}

	@Override
	public AuthenticatedUser getAuthenticatedUser() {
		return null;
	}

	@Override
	public String getBrowserId() {
		return null;
	}

	@Override
	public Set<String> getChannelSubscriptions() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public long getLastAccessTime() {
		return 0;
	}

	@Override
	public long getPublishedMessageCount() {
		return 0;
	}

	@Override
	public boolean isMultiFrame() {
		return false;
	}

	@Override
	public void setMultiFrame(boolean arg0) {
	}

	@Override
	public void updateLastAccessTime() {
	}
}
