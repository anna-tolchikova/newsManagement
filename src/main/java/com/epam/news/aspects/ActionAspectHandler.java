package com.epam.news.aspects;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.epam.news.exception.ServiceException;
import com.epam.news.exception.DaoException;

@Aspect
public class ActionAspectHandler {
	private static final Logger LOG = Logger.getLogger(ActionAspectHandler.class);
	
//	@Pointcut("within(@com.epam.news.aspects.Monitor *)")
//	public void beanAnnotatedWithMonitor() {LOG.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");}
//
//	@Pointcut("execution(public * (@Monitor *).*(..))")
//	public void publicMethod() {LOG.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");}
	
	@AfterThrowing(pointcut = "execution(* com.epam.news.service.INewsService.*(..))", throwing = "e")
	public void onThrowing(JoinPoint joinPoint, Throwable  e){
		Signature signature = joinPoint.getSignature();
	    String methodName = signature.getName();
	    String stuff = signature.toString();
	    String arguments = Arrays.toString(joinPoint.getArgs());
	    LOG.error("\nCaught exception in method: " + methodName
	         + "\nWith arguments: " + arguments
	         + "\nSignature: " + stuff
	         + "\nEexception: " + e.getMessage(), e);
	}
	
//	pointcut = "execution(* com.epam.news.presentation.action.*.*(..))"
// execution(* com.epam.news.presentation.action.NewsAction.deleteNews(..))
//	"@target(org.springframework.transaction.annotation.Transactional)"
//	execution(* com.epam.news.presentation.action.NewsAction.deleteNews(..))
//	@target(com.epam.news.aspects.Monitor)

}
