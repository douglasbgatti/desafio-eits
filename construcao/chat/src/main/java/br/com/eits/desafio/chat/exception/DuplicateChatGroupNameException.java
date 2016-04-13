/**
 * 
 */
package br.com.eits.desafio.chat.exception;

/**
 * @author douglas
 *
 */
public class DuplicateChatGroupNameException extends Exception{
	
	public DuplicateChatGroupNameException(){		
	}
	
	public DuplicateChatGroupNameException(String message){
		super(message);
	}
	
	public DuplicateChatGroupNameException(Throwable cause){
		super(cause);
	}
	
	public DuplicateChatGroupNameException(String message, Throwable cause){
		super(message, cause);
	}

}
