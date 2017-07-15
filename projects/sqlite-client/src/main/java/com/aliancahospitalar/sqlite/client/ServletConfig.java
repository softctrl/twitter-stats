package com.aliancahospitalar.sqlite.client;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author timoshenko
 *
 */
@Controller
public class ServletConfig {

	/**
	 * 
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (container -> {
			container.setPort(7080);
		});
	}

}