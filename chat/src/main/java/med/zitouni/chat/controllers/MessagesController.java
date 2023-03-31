package med.zitouni.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import med.zitouni.chat.dto.PostMessageDTO;
import med.zitouni.chat.entities.Message;
import med.zitouni.chat.services.MessageService;
import med.zitouni.chat.services.NotificationsService;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessagesController {
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@GetMapping("/subscribe-user/{uuid}")
	public SseEmitter subscribeUser(@PathVariable("uuid") String uuid) {
		return notificationsService.subscribeUser(uuid);
	}
	
	@PostMapping("/post")
	public void postMessage(@RequestBody PostMessageDTO request) {
		messageService.postMessage(request);
	}
	
	@GetMapping("/last-10")
	public List<Message> getLast10Messages(){
		return messageService.getLast10Messages();
	}

}
