package br.com.eits.desafio.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("br.com.eits.desafio.chat")
@Import({ SpringSecurityConfig.class })
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * Mapeando as paginas jsp em WEB-INF/views/
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver resourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/static/views/");
		resolver.setSuffix(".html");
//		resolver.setViewClass(JstlView.class);
		
		return resolver;
	}
	
	

	
	  @Override
	  public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:index.html");
	  }
	  
	   @Override
	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	        configurer.enable();
	    }
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/static/css/**").addResourceLocations("/WEB-INF/static/css/");
         registry.addResourceHandler("/static/js/**").addResourceLocations("/WEB-INF/static/js/");      
         registry.addResourceHandler("/static/views/**").addResourceLocations("/WEB-INF/static/views/");  
         registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	

	

}
