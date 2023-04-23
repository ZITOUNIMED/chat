package med.zitouni.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import med.zitouni.chat.configuration.security.JwtTokenUtil;
import med.zitouni.chat.dto.PostMessageDTO;
import med.zitouni.chat.entities.Message;
import med.zitouni.chat.services.MessageService;
import med.zitouni.chat.services.NotificationsService;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {
	private final MessageService messageService;
	private final JwtTokenUtil jwtTokenUtil;
	
	public MessagesController(JwtTokenUtil jwtTokenUtil, MessageService messageService) {
		this.messageService = messageService;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Autowired
	private NotificationsService notificationsService;
	
	@GetMapping("/subscribe-user/{uuid}")
	public ResponseEntity<SseEmitter> subscribeUser(@PathVariable("uuid") String uuid, @RequestParam(name = "token", required = true) String token) {
		boolean isValid = jwtTokenUtil.validate(token);
		if(!isValid) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return new ResponseEntity<> (notificationsService.subscribeUser(uuid), HttpStatus.OK);
	}
	
	@PostMapping("/post")
	public void postMessage(@RequestBody PostMessageDTO request) {
		messageService.postMessage(request);
	}
	
	@GetMapping("/last-10/{groupUuid}")
	public List<Message> getLast10Messages(@PathVariable() String groupUuid){
		return messageService.getLast10Messages(groupUuid);
	}

}
