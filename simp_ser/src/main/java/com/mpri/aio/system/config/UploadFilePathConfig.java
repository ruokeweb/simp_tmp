package com.mpri.aio.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 将物理路径映射
 * <p>
 * Title: UploadFilePathConfig
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2018年9月14日
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

	@Value("${file.staticAccessPath}")
	private String staticAccessPath;

	@Value("${file.uploadFolder}")
	private String uploadFolder;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        .addResourceLocations("classpath:/META-INF/resources/")

				
		registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
	}

}
