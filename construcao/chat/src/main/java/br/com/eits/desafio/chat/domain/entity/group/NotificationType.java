/**
 * 
 */
package br.com.eits.desafio.chat.domain.entity.group;

import java.io.Serializable;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * Utilized to define which type of notification is being sent through the websocket connection
 * @author douglas
 * 
 *
 */
@DataTransferObject(type="enum")
public enum NotificationType implements Serializable {
	/*
	 * GROUP NOTIFICATIONS
	 */
	NEW_GROUP_NOTIFICATION,
	DELETE_GROUP_NOTIFICATION,
	
	/*
	 * MESSAGE NOTIFICATIONS
	 */
	NEW_MESSAGE_NOTIFICATION,
	DELETED_MESSAGE_NOTIFICATION,
	MESSAGE_VISUALIZED_NOTIFICATION
	
}
