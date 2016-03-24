//package br.com.eits.desafio.chat.config;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.directwebremoting.annotations.DataTransferObject;
//import org.directwebremoting.annotations.GlobalFilter;
//import org.directwebremoting.annotations.RemoteProxy;
//import org.directwebremoting.extend.Configurator;
//import org.directwebremoting.spring.DwrClassPathBeanDefinitionScanner;
//import org.directwebremoting.spring.DwrController;
//import org.directwebremoting.spring.DwrHandlerMapping;
//import org.directwebremoting.spring.SpringConfigurator;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//
////@Configuration
////@EnableWebMvc
////@ComponentScan("br.com.eits.desafio.chat")
////@Import({ SpringSecurityConfig.class })
//public class SpringMvcConfig extends WebMvcConfigurerAdapter {
//	
//	/**
//	 * Mapeando as paginas jsp em WEB-INF/views/
//	 * @return
//	 */
//	@Bean
//	public InternalResourceViewResolver resourceViewResolver(){
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/WEB-INF/static/views/");
//		resolver.setSuffix(".html");
////		resolver.setViewClass(JstlView.class);
//		
//		return resolver;
//	}
//	
//	
//
//	
//	  @Override
//	  public void addViewControllers(ViewControllerRegistry registry) {
//	    registry.addViewController("/").setViewName("forward:index.html");
//	  }
//	  
//	   @Override
//	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//	        configurer.enable();
//	    }
//	
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         registry.addResourceHandler("/static/css/**").addResourceLocations("/WEB-INF/static/css/");
//         registry.addResourceHandler("/static/js/**").addResourceLocations("/WEB-INF/static/js/");      
//         registry.addResourceHandler("/static/views/**").addResourceLocations("/WEB-INF/static/views/");  
//         registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//	
//    
//    
//    //	DWR CONFIGURATIONS
//    @Bean
//    public DwrController dwrController(ApplicationContext applicationContext){
//
//        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
//        Map<String,String> configParam = new HashMap<String, String>();
//        configParam.put("activeReverseAjaxEnabled","true");
//
//        ClassPathBeanDefinitionScanner scanner = new DwrClassPathBeanDefinitionScanner(beanDefinitionRegistry);
//        scanner.addIncludeFilter(new AnnotationTypeFilter(RemoteProxy.class));
//        scanner.addIncludeFilter(new AnnotationTypeFilter(DataTransferObject.class));
//        scanner.addIncludeFilter(new AnnotationTypeFilter(GlobalFilter.class));
//        scanner.scan("package.to.scan");
//
//        DwrController dwrController = new DwrController();
//        dwrController.setDebug(true);
//        dwrController.setConfigParams(configParam);
//
//        SpringConfigurator springConfigurator = new SpringConfigurator();
//        List<Configurator> configurators = new ArrayList<Configurator>();
//        configurators.add(springConfigurator);
//        dwrController.setConfigurators(configurators);
//
//
//        return dwrController;
//    }
//    @Bean
//    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping(){
//        BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
//        return beanNameUrlHandlerMapping;
//    }
//    @Bean
//    public DwrHandlerMapping dwrHandlerMapping(DwrController dwrController){
//        Map<String,DwrController> urlMap = new HashMap<String, DwrController>();
//        urlMap.put("/dwr/**/*",dwrController);
//
//        DwrHandlerMapping dwrHandlerMapping = new DwrHandlerMapping();
//        dwrHandlerMapping.setAlwaysUseFullPath(true);
//        dwrHandlerMapping.setUrlMap(urlMap);
//        return dwrHandlerMapping;
//    }
//
//	
//
//}
