package vol1.jhcode.ch6.learningtest.spring.factorybean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FactoryBeanConfig.class})
public class FactoryBeanTest {
	
	@Autowired
	ApplicationContext context;
	
	@Test
	public void getMessageFromFactoryBean() {
		Object message = context.getBean("message");
		assertEquals(message.getClass(), Message.class);
		assertEquals(((Message)message).getText(), "Factory Bean");
	}
	
	@Test
	public void getFactoryBena() throws Exception {
		Object factory = context.getBean("&message");
		assertEquals(factory.getClass(), MessageFactoryBean.class);
	}
}
