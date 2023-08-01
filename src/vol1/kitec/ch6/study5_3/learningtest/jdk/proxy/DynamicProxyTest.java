package vol1.kitec.ch6.study5_3.learningtest.jdk.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class DynamicProxyTest {
	
	@Test
	public void classNamePointcutAdvisor() {
		
		//== 클래스 선정 알고리즘 ==//
		NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
			public ClassFilter getClassFilter() {
				return new ClassFilter() {
					public boolean matches(Class<?> clazz) {
						
						// advisor을 적용할 클래스 이름이 HelloT 로 시작
						return clazz.getSimpleName().startsWith("HelloT");
					}
				};
			}
		};
		
		//== lambda ==//		
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut() {
			
			public ClassFilter getClassFilter() {
				
				//return ((Class<?> clazz) -> clazz.getSimpleName().startsWith("HelloT"));
				//return ((clazz) -> clazz.getSimpleName().startsWith("HelloT"));
				//return (clazz) -> clazz.getSimpleName().startsWith("HelloT");
				
				return clazz ->  clazz.getSimpleName().startsWith("HelloT");
			}
		};
		
		//== 메소드 선정 알고리즘 ==//
		classMethodPointcut.setMappedName("sayH*");
		
		
		//== 테스트 ==//
		checkAdviced(new HelloTarget(), classMethodPointcut, true);
		
		//적용 클래스가 아닐 경우
		class HelloWorld extends HelloTarget {};
		checkAdviced(new HelloWorld(), classMethodPointcut, false);
	
		//적용 클래스일 경우
		class HelloToby extends HelloTarget {};
		checkAdviced(new HelloToby(), classMethodPointcut, true);
	}
	
	private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
		
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(target);
		
		// advisor = advice + pointcut
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, 
				(MethodInterceptor)((MethodInvocation invocation) -> {
					String ret = (String)invocation.proceed();
					return ret.toUpperCase();
				}//lambda 구현부
				)//lambda의 끝
			)//DefaultPointcutAdvisor()의 끝
		); //addAdvisor()의 끝

		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, (MethodInterceptor)(invocation) -> {
			String ret = (String) invocation.proceed();
			return ret.toUpperCase();
		}));
		
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, (MethodInterceptor) invocation -> {
			String ret = (String) invocation.proceed();
			return ret.toUpperCase();
		}));
		
		//프록시 생성
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		//advice 적용 메소드일 경우 -> advice의 invoke 메소드를 거친다
		if (adviced) {
			assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
			assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
			assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby");
		
		//advice 적용 메소드가 아닐 경우 -> 바로 target의 메소드가 호출됨
		} else {
			assertEquals(proxiedHello.sayHello("Toby"), "Hello Toby");
			assertEquals(proxiedHello.sayHi("Toby"), "Hi Toby");
			assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby");
		}
	}
	
	
	// advice
	static class UppercaseAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
	}
	
	// target interface -> proxy와 target이 구현할 인터페이스
	static interface Hello {
		
		String sayHello(String name);
		String sayHi(String name);
		String sayThankYou(String name);
	}
	
	// target
	static class HelloTarget implements Hello {
		
		public String sayHello(String name) {
			return "Hello " + name;
		}
		
		public String sayHi(String name) {
			return "Hi " + name;
		}
		
		public String sayThankYou(String name) {
			return "Thank You " + name;
		}
	}
}
