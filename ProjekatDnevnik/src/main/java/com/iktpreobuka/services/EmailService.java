package com.iktpreobuka.services;

import com.iktpreobuka.email.EmailObject;

public interface EmailService {
	
	void sendSimpleMessage (EmailObject object);
	
	void sendTemplateMessage (EmailObject object) throws Exception;
	
	void sendMessageWithAttachment (EmailObject object, String pathToAttachment) throws Exception;

	void posaljiEmail(String email, String poruka);

}
