package med.zitouni.chat.services;

import java.util.List;

import med.zitouni.chat.dto.PostMessageDTO;
import med.zitouni.chat.entities.Message;

public interface MessageService {

	public void postMessage(PostMessageDTO request);
	
	public List<Message> getLast10Messages(String groupUuid);
}
