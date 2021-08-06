package com.sber.config;

import com.sber.model.ValCurs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

@Configuration
public class ClientConfig {
    @Value("${currency.schemaXSD}")
    private String schemaXSD;

    @Bean
    @ConfigurationProperties(prefix = "rest.template.currency")
    public RestTemplate currencyRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Bean
    public void currencyService() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(schemaXSD));
            Marshaller s_jaxbMarshaller = jaxbContext.createMarshaller();
            Unmarshaller s_jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            s_jaxbUnmarshaller.setSchema(schema);
            s_jaxbMarshaller.setSchema(schema);
        } catch (JAXBException | SAXException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
