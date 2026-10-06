package tech.aomi.common.utils.xml;

import java.io.StringWriter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * XML 工具类
 *
 * @author 田尘殇Sean sean.snow@live.com
 */
public class Xml {
    private static JsonMapper objectMapper = JsonMapper.builder().build();

    private static XmlMapper defaultXmlMapper = XmlMapper.builder().build();
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
            JsonParser jsonParser = mapper().tokenStreamFactory().createParser(xml);
            JsonGenerator jg = objectMapper.tokenStreamFactory().createGenerator(writer);
            while (jsonParser.nextToken() != null) {
                jg.copyCurrentEvent(jsonParser);
            }
            jsonParser.close();
            jg.close();
            return writer.toString();
        } catch (JacksonException e) {
            return null;
        }
    }


    public static void setXmlMapper(XmlMapper mapper) {
        xmlMapper = mapper;
    }


}
