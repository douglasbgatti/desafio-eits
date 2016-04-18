package br.com.eits.desafio.chat.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import br.com.eits.desafio.chat.websocket.controller.WebSocketController;

/**
 * 
 * @author douglas
 *
 */
@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages="br.com.eits.desafio.chat.websocket.controller")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	private static final Logger LOG = Logger.getLogger(WebSocketConfig.class);

	/**
	 * Message broker configuration - enabling a simple broker and setting destination
	 */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
    
    LOG.debug("ConfigureMessageBroker:" + config.toString());
  }

  /**
   * Registering websocket endpoint
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket").withSockJS();
    LOG.debug("registerStompEndpoints:" + registry.toString());
  }
}