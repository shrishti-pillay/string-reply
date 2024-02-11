package com.beta.replyservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ReplyController {

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("/v2/reply")
	public ReplyMessage v2Replying() {
		return new ReplyMessage("Message is empty");
	}

	// version 2
	@GetMapping("/v2/reply/{message}")
	public ReplyMessage v2Replying(@PathVariable String message, HttpServletRequest request, HttpServletResponse response) {
		Pattern pattern = Pattern.compile("[0-9]+[-][a-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(message);
		boolean matchFound = matcher.find();
    	if(matchFound) {
			String transformer = message.split("-")[0];
			String message_str = message.split("-")[1];
			Transformer transformed_message = new Transformer(message_str, transformer);
			if (transformed_message.modifyMessage() == "Invalid Input") {
				response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
			}
			return new ReplyMessage(transformed_message.modifyMessage());
		}
		else{
			return new ReplyMessage(message);
		}
	}
}