package com.zebra.constant.util;

import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.zebra.constant.app.SupportedLocale;
import com.zebra.constant.exception.ConstantLocalizationException;

/**
 * Constant util created to retrieve localized values for i18n Created this way
 * due the limitation of java to have abstract enums
 * 
 */
public class ConstantUtil {

	private static final SecureRandom	RANDOM	= new SecureRandom();

	private ConstantUtil() {
	}

	public static String key(final Enum<?> property) {
		return property.name();
	}

	public static String value(final Enum<?> property, final Locale locale) throws ConstantLocalizationException {
		String value = null;
		try {
			value = ResourceBundle.getBundle(property.getClass().getCanonicalName(), locale).getString(property.name());
		} catch (Exception e) {
		}

		if (value == null || value.equals(""))
			throw new ConstantLocalizationException("The enum " + property.getClass().getSimpleName()
				+ " does not have the value \"" + property + "\" in the locale \"" + locale + "\"");
		return value;
	}

	private static final Set<Class<? extends Enum<?>>>	processedEnums		= new HashSet<Class<? extends Enum<?>>>();
	private static final Map<String, Enum<?>>			cachedI18nValues	= new HashMap<String, Enum<?>>();

	public final static Enum<?> getKeyFromI18nValue(final Class<? extends Enum<?>> enumClass, final String i18nValue)
		throws RuntimeException {

		// ask if enum not already processed
		if (!processedEnums.contains(enumClass)) {

			processedEnums.add(enumClass);

			// get all possible enum values
			Enum<?>[] enumValues;
			try {
				Method valuesMethod = enumClass.getDeclaredMethod("values");
				enumValues = (Enum<?>[]) valuesMethod.invoke(enumClass);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			// add enum values to the list
			for (Enum<?> enumElement : enumValues) {
				for (SupportedLocale locale : SupportedLocale.values()) {

					cachedI18nValues.put(ResourceBundle.getBundle(enumClass.getCanonicalName(), locale.getLocale())
						.getString(enumElement.name()), enumElement);
				}
			}
		}

		return cachedI18nValues.get(i18nValue);
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = RANDOM.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}
