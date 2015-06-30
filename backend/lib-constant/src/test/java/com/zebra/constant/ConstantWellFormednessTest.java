package com.zebra.constant;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import com.zebra.constant.app.SupportedLocale;
import com.zebra.constant.util.ConstantUtil;

public class ConstantWellFormednessTest extends TestCase {

	private static final String PATTERN = "classpath*:com/zebra/constant/**/*.class";
	private static final String[] EXCLUDED_CONSTANTS = { "WebElementType",
			"SupportedProtocol", "LinkType" };

	public void testConstantWellFormedness() {

		try {
			List<Enum<?>> allConstants = findAllConstants();

			for (Enum<?> enum1 : allConstants) {

				for (SupportedLocale locale : SupportedLocale.values()) {
					ConstantUtil.value(enum1, locale.getLocale());
				}
			}

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	private List<Enum<?>> findAllConstants() throws Exception {

		List<Enum<?>> allEnums = new ArrayList<Enum<?>>();

		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
				resourcePatternResolver);

		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources(PATTERN);
		for (Resource resource : resources) {
			if (resource.isReadable()) {
				MetadataReader metadataReader = metadataReaderFactory
						.getMetadataReader(resource);

				ClassMetadata classMetadata = metadataReader.getClassMetadata();
				String className = classMetadata.getClassName();

				if (Class.forName(className).isEnum() && !isExcluded(className)) {
					Enum<?>[] enumConstants = (Enum<?>[]) Class.forName(
							className).getEnumConstants();

					for (Enum<?> enum1 : enumConstants) {
						allEnums.add(enum1);
					}
				}
			}

		}
		return allEnums;
	}

	private boolean isExcluded(String className) {

		for (String excluded : EXCLUDED_CONSTANTS) {
			if (className.endsWith(excluded))
				return true;
		}
		return false;
	}
}
