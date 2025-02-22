package com.jwt.auth;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.jwt.auth")
@ComponentScan(basePackages = "com.jwt.auth")
public class AuthApplication {

	public static void main(String[] args) {
		Email from = new Email("danielfcl95@gmail.com");
		String subject = "Sending with Twilio SendGrid is Fun";
		Email to = new Email("test@example.com");
		Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
		Mail mail = new Mail(from, subject, to, content);

		SpringApplication.run(AuthApplication.class, args);
	}

}
