package com.example.springboot.template;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@DisplayName("템플릿 로더 테스트")
@Slf4j
@SpringBootTest
class TemplateLoaderTests {
    @Autowired
    private FreeMarkerConfigurationFactoryBean configurationFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @DisplayName("템플릿 체이닝")
    @Test
    void templateChaining() {
        Assertions.assertDoesNotThrow(() -> {
            Locale locale = Locale.ROOT;

            // NOTE: Dynamic Templates
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("email.ftlh", "${bundle(\"application.name\")}");
            stringTemplateLoader.putTemplate("template", "<#include \"email.ftlh\" >");

            // NOTE: Classpath Templates
            SpringTemplateLoader springTemplateLoader = new SpringTemplateLoader(resourceLoader, "classpath:/templates/");

            // NOTE: Use MultiTemplateLoader for chaining.
            MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[]{stringTemplateLoader, springTemplateLoader});

            Configuration configuration = configurationFactory.createConfiguration();
            configuration.setTemplateLoader(multiTemplateLoader);

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
