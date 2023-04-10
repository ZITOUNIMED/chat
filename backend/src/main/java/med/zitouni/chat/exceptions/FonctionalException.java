package med.zitouni.chat.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FonctionalException extends RuntimeException {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public FonctionalException(String details) {
		super("Exception (FonctionalException) details: " + details);
		logger.debug("FonctionalException details: " + details);
	}
}
