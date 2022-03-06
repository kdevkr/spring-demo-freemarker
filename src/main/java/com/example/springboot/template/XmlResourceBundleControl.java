package com.example.springboot.template;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class XmlResourceBundleControl extends ResourceBundle.Control {
    private static final String XML = "xml";

    @Override
    public List<String> getFormats(String baseName) {
        return Collections.singletonList(XML);
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                    ClassLoader loader, boolean reload) throws IOException {

        if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
            throw new NullPointerException();
        }
        ResourceBundle bundle = null;
        if (!XML.equals(format)) {
            return null;
        }

        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, format);
        URL url = loader.getResource(resourceName);
        if (url == null) {
            return null;
        }
        URLConnection connection = url.openConnection();
        if (connection == null) {
            return null;
        }
        if (reload) {
            connection.setUseCaches(false);
        }
        InputStream stream = connection.getInputStream();
        if (stream == null) {
            return null;
        }

        BufferedInputStream bis = new BufferedInputStream(stream);
        bundle = new XMLResourceBundle(bis);
        bis.close();

        return bundle;
    }

    private static class XMLResourceBundle extends ResourceBundle {
        private final Properties props;

        XMLResourceBundle(InputStream stream) throws IOException {
            props = new Properties();
            props.loadFromXML(stream);
        }

        protected Object handleGetObject(String key) {
            return props.getProperty(key);
        }

        public Enumeration<String> getKeys() {
            Set<String> handleKeys = props.stringPropertyNames();
            return Collections.enumeration(handleKeys);
        }
    }

}