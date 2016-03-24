//package br.com.eits.desafio.chat.config;
//
//import javax.servlet.FilterRegistration;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
//
//import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.filter.DelegatingFilterProxy;
//import org.springframework.web.servlet.DispatcherServlet;
//
//
///**
// * 
// * @author douglas
// * Todos as configuracoes do web.xml configurado dentro desta classe
// */
//public class SpringWebXmlConfig implements WebApplicationInitializer {
//
//	private AnnotationConfigWebApplicationContext getContext(){
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//	    context.setConfigLocation("br.com.eits.desafio.chat.config");
//	    
//	    return context;
//	}
//	
//	/**
//	 * starta nossa aplicacao apartir do contexto do servidor
//	 **/
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		AnnotationConfigWebApplicationContext webContext = getContext();
//		
//		servletContext.addListener(new ContextLoaderListener(webContext));
//		
//		webContext.register(SpringMvcConfig.class); //configuracao inicial a partir desta classe
////		webContext.setServletContext(servletContext);
//		
//		DispatcherServlet dispatcherServlet 
//			= new DispatcherServlet(webContext);//faz toda a parte de request e response da nossa aplicacao
//			
//		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true); // setando mostrar pagina de erro
//		
//		ServletRegistration.Dynamic registrationDynamic 
//			= servletContext.addServlet("dispatcher", dispatcherServlet); //
//		
//		registrationDynamic.setLoadOnStartup(1);
//		registrationDynamic.addMapping("/dwr/*"); // url dwr
//		registrationDynamic.addMapping("/"); // url inicial
//		
//		
//		
//		FilterRegistration.Dynamic encodingFilter = 
//				servletContext.addFilter("encodingFilter", new CharacterEncodingFilter()) ;
//		encodingFilter.setInitParameter("encoding", "UTF-8");
//		encodingFilter.setInitParameter("forceEncoding", "true");
//		encodingFilter.addMappingForUrlPatterns(null, true, "/*"); //a partir do / tudo e tratado como utf-8
//		
////		FilterRegistration.Dynamic securityFilter = 
////				servletContext.addFilter(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, DelegatingFilterProxy.class);
////		securityFilter.setAsyncSupported(Boolean.TRUE);
////		securityFilter.addMappingForUrlPatterns(null, true, "/*");
//	}
//	
//	
//
//}
