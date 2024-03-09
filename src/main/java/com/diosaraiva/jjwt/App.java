package com.diosaraiva.jjwt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {

	public static void main(String[] args) {

		new SpringApplicationBuilder(App.class)
		//.bannerMode(Banner.Mode.OFF)
		//.banner(null)
		//.logStartupInfo(false)
		//.web(WebApplicationType.SERVLET)
		.run(args);
	}
}
