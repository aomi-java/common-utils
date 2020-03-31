package tech.aomi.common.utils.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * XML 工具类
 *
 * @author 田尘殇Sean sean.snow@live.com
 */
public class Xml {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static XmlMapper defaultXmlMapper = new XmlMapper();
    private static volatile XmlMapper xmlMapper = null;

    private static XmlMapper mapper() {
        if (xmlMapper == null) {
            return defaultXmlMapper;
        } else {
            return xmlMapper;
        }
    }


    /**
     * XML 转换为JSON
     *
     * @param xml XML 字符串
     * @return json 字符串 解析错误返回NULL
     */
    public static String toJson(String xml) {
        StringWriter writer = new StringWriter();
        try {
            JsonParser jsonParser = mapper().getFactory().createParser(xml);
            JsonGenerator jg = objectMapper.getFactory().createGenerator(writer);
            while (jsonParser.nextToken() != null) {
                jg.copyCurrentEvent(jsonParser);
            }
            jsonParser.close();
            jg.close();
            return writer.toString();
        } catch (IOException e) {
            return null;
        }
    }


    public static void setXmlMapper(XmlMapper mapper) {
        xmlMapper = mapper;
    }


}
