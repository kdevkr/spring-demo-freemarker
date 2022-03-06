package com.example.springboot.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.*;

@Slf4j
@SpringBootTest
class FreemarkerTests {
    @Autowired
    private FreeMarkerConfigurationFactoryBean configurationFactory;

    @Test
    void displayVariables() {
        Assertions.assertDoesNotThrow(() -> {
            Configuration configuration = configurationFactory.createConfiguration();
            configuration.setTimeZone(TimeZone.getTimeZone("UTC"));
            configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
            Template template = configuration.getTemplate("variables.ftlh", Locale.ENGLISH);

            Map<String, Object> model = new HashMap<>();
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            log.info("{}", content);
            Assertions.assertNotNull(content);
        });
    }

    @Test
    void interpolation() {
        Assertions.assertDoesNotThrow(() -> {
            Locale locale = Locale.ROOT;

            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", "${bundle(\"application.name\")}");

            Configuration configuration = configurationFactory.createConfiguration();
            configuration.setTemplateLoader(stringTemplateLoader);

            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
            Map<String, Object> model = new HashMap<>();
            model.put("bundle", new ResourceBundleModel(resourceBundle, new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build()));

            Template template = configuration.getTemplate("template", locale);
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            log.info("{}", content);

            Assertions.assertEquals("Application", content);
        });
    }
}
