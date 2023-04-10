package med.zitouni.chat.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import med.zitouni.chat.entities.Message;
import med.zitouni.chat.services.NotificationsService;

@Service
public class NotificationServiceImpl implements NotificationsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, SseEmitter> notifications = new HashMap<>();
	
	@Override
	public SseEmitter subscribeUser(String uuid) {
		SseEmitter notifier = new SseEmitter(0L);
		notifier.onTimeout(() -> {
			notifier.complete();
		});
		
		notifier.onCompletion(() -> {
			logger.info("Close communication between server and user "+ uuid);
			notifications.remove(uuid);
		});
		
		notifier.onError(error -> {
			logger.debug("Error during communication between server and user "+uuid);
		});
		
		logger.info("New communication is opened with user "+ uuid);
		notifications.put(uuid, notifier);
		return notifier;
	}

	@Override
	public void dispatchMessage(Message message) {
		for(Map.Entry<String, SseEmitter> entry: notifications.entrySet()) {
			try {
				entry.getValue().send(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
