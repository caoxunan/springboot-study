package com.cxn.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.cxn.annotation.DemoActivity;
import com.cxn.annotation.DemoActivityParam;

@Aspect
@Component
public class LogAspect {

	/** 
	execution(* com.midai.service.impl..*.*(..)) 
	解释如下： 
	符号 含义 
	execution（） 
	表达式的主体； 
	第一个”*“符号 
	表示返回值的类型任意； 
	com.sample.service.impl    AOP所切的服务的包名，即，我们的业务部分 
	 包名后面的”..“  表示当前包及子包 
	 第二个”*“ 表示类名，*即所有类。此处可以自定义，下文有举例 
	.*(..) 表示任何方法名，括号表示参数，两个点表示任何参数类型 
	execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)  除了返回类型模式、方法名模式和参数模式外，其它项都是可选的。
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Before("@annotation(activity)")
	// @Before("@annotation(com.cxn.annotation.DemoActivity)")
	public void logServiceAccessBefore(JoinPoint joinPoint, DemoActivity activity){  
		System.out.println("Before: " + joinPoint);
		System.out.println("activity:" + activity);
		Object[] args = joinPoint.getArgs();
		if (args != null) {
			Method m = null;
			Object target = joinPoint.getTarget();
			String methodName = joinPoint.getSignature().getName();
			Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
			try {
				m = target.getClass().getMethod(methodName, parameterTypes);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Annotation[][] an = m.getParameterAnnotations();
			for (int i = 0; i < an.length; i++) {
				Annotation[] n = an[i];
				for (int k = 0; k < n.length; k++) {
					if (n[k] instanceof DemoActivityParam) {
						DemoActivityParam p = (DemoActivityParam) n[k];
						// 如果p中有定义的值，可以取出来
						if (args[i] instanceof String) {
							// 得到自定义注解对应参数的值
							String	test = (String) args[i];
							System.out.println("自定义注解@DemoActivityParam对应参数的值为:" + test);
							break;
						}
					}
				}
	
			}
	
		}
	}

	/*	@Before("execution(* com.cxn.controller..*.*(..))")  
	public void logServiceAccessBefore(JoinPoint joinPoint){  
		System.out.println("Before1: " + joinPoint);
		if (joinPoint.getArgs().length > 0) {
			System.out.println("日志记录:用户" +joinPoint.getArgs()[0] + "在" + new SimpleDateFormat("yyyy-MM-dd hh:mm;ss").format(new Date()) + "调用了"+ joinPoint.getSignature()+"方法" );
		}else{
			System.out.println("日志记录:在" + new SimpleDateFormat("yyyy-MM-dd hh:mm;ss").format(new Date()) + "调用了"+ joinPoint.getSignature()+"方法" );
		}
	}  

	@After("execution(* com.cxn.controller..*.*(..))")  
	public void logServiceAccessAfter(JoinPoint joinPoint){  
		System.out.println("After: " + joinPoint);  
	}  
	@AfterReturning("execution(* com.cxn.controller..*.*(..))")  
	public void logServiceAccessAfterReturning(JoinPoint joinPoint) {  
		System.out.println("Completed: " + joinPoint);  
	}  

	 *//** 
	 * around 开启后会覆盖before 
	 * @param joinPoint 
	 *//*  
	// @Around("execution(* com.midai.controller..*.*(..))")  
	public void logServiceAccessAround(JoinPoint joinPoint){  
		System.out.println("Around: " + joinPoint);  
	}  

	//声明一个切入点  
	@Pointcut("execution(* com.cxn.controller..*.*(..))")  
	public void logServiceAccessPointCut(){  
	}

	@Before("logServiceAccessPointCut()")  
	public void losgUseThePointCut(JoinPoint joinPoint){  
		System.out.println("THIS IS USE THE POINT CUT: " + joinPoint);  
	}*/  

}