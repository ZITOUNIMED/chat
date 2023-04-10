package med.zitouni.chat.services;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import med.zitouni.chat.entities.Message;

public interface NotificationsService {

	public SseEmitter subscribeUser(String uuid);
	
	public void dispatchMessage(Message message);
}
