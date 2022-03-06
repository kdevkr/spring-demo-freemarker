package com.example.springboot.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@DisplayName("XML 리소스 번들 테스트")
@Slf4j
@SpringBootTest
class XmlResourceBundleTests {

    @Autowired
    private FreeMarkerConfigurationFactoryBean configurationFactory;

    @Test
    void interpolation() {
        Assertions.assertDoesNotThrow(() -> {
            Locale locale = Locale.ROOT;

            Configuration configuration = configurationFactory.createConfiguration();
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", "${bundle(\"application.maintainer\")}");
            configuration.setTemplateLoader(stringTemplateLoader);
            Template template = configuration.getTemplate("template", locale);

            // NOTE: Load templates from xml.
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale, new XmlResourceBundleControl());
            Map<String, Object> model = new HashMap<>();
            model.put("bundle", new ResourceBundleModel(resourceBundle, new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build()));
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            log.info("content: {}", content);

            Assertions.assertEquals("Mambo", content);
        });
    }
}
