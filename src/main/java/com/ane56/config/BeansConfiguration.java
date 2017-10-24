package com.ane56.config;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.MimeType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

import com.ane56.service.FilmService;
import com.ane56.service.MoviesAddressService;
import com.ane56.service.PermissionService;
import com.ane56.service.RolePermissionService;
import com.ane56.service.RoleService;
import com.ane56.service.UserService;
import com.squareup.okhttp.OkHttpClient;

@Configuration
public class BeansConfiguration {

	@Autowired
	private CommonPropertyConfiguration commonConfig;
	
	@Autowired
	private ThymeleafProperties properties;
	
	@Bean
	public SpringResourceResourceResolver thymeleafResourceResolver(){
	    return new SpringResourceResourceResolver();
	}
	
	@Bean
	public TemplateResolver templateResolver(){
		TemplateResolver resolver = new TemplateResolver();
		resolver.setResourceResolver(thymeleafResourceResolver());
		resolver.setPrefix(this.properties.getPrefix());
		resolver.setSuffix(this.properties.getSuffix());
		resolver.setTemplateMode(this.properties.getMode());
		if (this.properties.getEncoding() != null) {
			resolver.setCharacterEncoding(this.properties.getEncoding().name());
		}
		resolver.setCacheable(this.properties.isCache());
		Integer order = this.properties.getTemplateResolverOrder();
		if (order != null) {
			resolver.setOrder(order);
		}
		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(){
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.addDialect(new LayoutDialect());
	    return templateEngine;
	}
	
	@Bean
	public ViewResolver viewResolver(){
		ThymeleafViewResolver resolver = new CustomThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding(this.properties.getEncoding().name());
		resolver.setContentType(appendCharset(this.properties.getContentType(), resolver.getCharacterEncoding()));
		resolver.setExcludedViewNames(this.properties.getExcludedViewNames());
		resolver.setViewNames(this.properties.getViewNames());
		// This resolver acts as a fallback resolver (e.g. like a
		// InternalResourceViewResolver) so it needs to have low precedence
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
		resolver.setCache(this.properties.isCache());
		return resolver;
	}
	
	private String appendCharset(MimeType type, String charset) {
		if (type.getCharSet() != null) {
			return type.toString();
		}
		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("charset", charset);
		parameters.putAll(type.getParameters());
		return new MimeType(type, parameters).toString();
	}
	
	/*@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
		return container -> container.addErrorPages(
			new ErrorPage(HttpStatus.NOT_FOUND, "/" + HttpStatus.NOT_FOUND.value()), 
			new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/" + HttpStatus.INTERNAL_SERVER_ERROR.value())
		);
	}*/
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public OkClient okClient() {
		return new OkClient(okHttpClient());
	}

	@Bean
	public OkHttpClient okHttpClient() {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setConnectTimeout(commonConfig.getConnectionTimeout(), TimeUnit.SECONDS);
		okHttpClient.setReadTimeout(commonConfig.getReadTimeout(), TimeUnit.SECONDS);
		okHttpClient.setWriteTimeout(commonConfig.getWriteTimeout(), TimeUnit.SECONDS);
		return okHttpClient;
	}
	
	/**
	 * 自定义http客户端(默认超时10秒,文件上传下载需自定义超时时间)
	 */
	/*private <T> T service(String restUrl, Class<T> clazz){
		return new RestAdapter.Builder().setConverter(new JacksonConverter()).setClient(okClient()).setEndpoint(restUrl).build().create(clazz);
	}*/
	
	private <T> T commonService(String restUrl, Class<T> clazz){
		return new RestAdapter.Builder().setConverter(new JacksonConverter()).setEndpoint(restUrl).build().create(clazz);
	}
	
	@Bean
	public UserService userService() {
		return commonService(commonConfig.getUserUrl(), UserService.class);
	}
	
	/*@Bean
	public FileService fileService() {
		return service(commonConfig.getFileUrl(), FileService.class);
	}*/
	
	@Bean
	public PermissionService permissionService() {
		return commonService(commonConfig.getUserUrl(), PermissionService.class);
	}
	
	@Bean
	public RolePermissionService rolePermissionService() {
		return commonService(commonConfig.getUserUrl(), RolePermissionService.class);
	}
	
	@Bean
	public RoleService roleService() {
		return commonService(commonConfig.getUserUrl(), RoleService.class);
	}
	
	@Bean
	public MoviesAddressService moviesAddressService() {
		return commonService(commonConfig.getUserUrl(), MoviesAddressService.class);
	}
	
	@Bean
	public FilmService filmService() {
		return commonService(commonConfig.getUserUrl(), FilmService.class);
	}
	
}
