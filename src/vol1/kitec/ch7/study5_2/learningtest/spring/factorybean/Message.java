package vol1.kitec.ch7.study5_2.learningtest.spring.factorybean;


public class Message {
String text;
	
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public static Message newMessage(String text) {
		return new Message(text);
	}

}
