package com.beta.replyservice;

public class Transformer{

    private final String org_message;
    private final String transformer;

    public Transformer(String org_message, String transformer){
        this.org_message = org_message;
        this.transformer = transformer;
    }

    public String modifyMessage(){
        String transformed_message = org_message;
		for (int i = 0; i < transformer.length(); i++){
			switch(transformer.charAt(i)){
				case '1':
					StringBuilder reversed_message = new StringBuilder();
					reversed_message.append(transformed_message);
					reversed_message.reverse();
					transformed_message = reversed_message.toString();
					break;
				case '2':
					MD5 encrypted_message = new MD5(transformed_message);
					transformed_message = encrypted_message.getMd5();
					break;
				default:
					return "Invalid Input";
			}
		}
		return transformed_message;
	}
}