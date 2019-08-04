package com.shopping.base.foundation.util;

import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;


public class JaxbReadXml {

    @SuppressWarnings("unchecked")
    public static <T> T readString(Class<T> clazz, String context) throws JAXBException, IOException {
        try {
            ClassPathResource resource = new ClassPathResource(context);
            InputStream inputStream = resource.getInputStream();
            /**
             * 这是因为打包后Spring试图访问文件系统路径，但无法访问JAR中的路径。
             * 因此必须使用resource.getInputStream()
             */
            //InputStream inputStream = ClassLoader.getSystemResource(context).openStream();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return (T) u.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readConfig(Class<T> clazz, String config, Object... arguments) throws IOException,
            JAXBException {
        InputStream is = null;
        try {
            if (arguments.length > 0) {
                config = MessageFormat.format(config, arguments);
            }
            // logger.trace("read configFileName=" + config);
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            is = new FileInputStream(config);
            return (T) u.unmarshal(is);
        } catch (IOException e) {
            // logger.trace(config, e);
            throw e;
        } catch (JAXBException e) {
            // logger.trace(config, e);
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readConfigFromStream(Class<T> clazz, InputStream dataStream) throws JAXBException {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return (T) u.unmarshal(dataStream);
        } catch (JAXBException e) {
            // logger.trace(e);
            throw e;
        }
    }
    /*
    @Test
    public void test_xml() throws JAXBException, IOException{

        ObjList arrayObj = JaxbReadXml.readString(ObjList.class, "test.xml");
        System.out.println(arrayObj.getSize());

        for (TestObj testObj : arrayObj.getObjList()) {
            System.out.println("姓名："+testObj.getName()+"  出生日期："+testObj.getBirthday());
        }
    }

    @Test
    public void test_xml1() throws JAXBException, IOException{

        People People = JaxbReadXml.readString(People.class, "people.xml");
        System.out.println("姓名："+People.getName()+"  年龄："+People.getAge());
    }*/
}