package com.shopping.base.foundation.result;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class ErrorMessageSource {

	private ResourceBundleMessageSource messageSource;
	private Locale locale = Locale.SIMPLIFIED_CHINESE;

	public ErrorMessageSource() {
		messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(new String[] { "error",
				ErrorMessageSource.class.getPackage().getName().replaceAll("[.]", "/") + "/error" });
	}

	public String getMessage(int code) {
		String key = String.valueOf(code);
		return messageSource.getMessage(key, null, locale);
	}

}
