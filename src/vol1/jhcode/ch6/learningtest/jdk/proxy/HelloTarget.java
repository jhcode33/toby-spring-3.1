package vol1.jhcode.ch6.learningtest.jdk.proxy;

public class HelloTarget implements Hello {
	
	//타겟 : 핵심 기능을 가지고 있는 객체
	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public String sayHi(String name) {
		return "Hi " + name;
	}

	@Override
	public String sayThankYou(String name) {
		return "Thank You " + name;
	}
}
