package vol1.jhcode.ch6.learningtest.spring.factorybean;

public class Message {
	
	String text;
	
	//생성자
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	//생성자를 대신할 스태틱 팩토리 메소드
	public static Message newMessage(String text) {
		return new Message(text);
	}

}
