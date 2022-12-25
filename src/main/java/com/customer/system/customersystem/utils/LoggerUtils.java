package com.customer.system.customersystem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	private LoggerUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static Logger getLogger() {
		String caller = null;

		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			String className = ste.getClassName();
			if (className.indexOf(LoggerUtils.class.getName()) != 0 && className.indexOf("java.lang.Thread") != 0) {
				caller = className;
				break;
			}
		}

		if (caller == null)
			throw new RuntimeException("Caller not found for Logger");

		return LoggerFactory.getLogger(caller);
	}
}
