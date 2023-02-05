package com.zrylovestan.arsenal;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.zrylovestan.arsenal.core.util.restTemplate.RestTemplateUtils;
import com.zrylovestan.arsenal.modules.jacksonTest.entity.ExtendableBean;
import lombok.Data;
import org.junit.Test;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class JacksonTest {


    static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        mapper.registerModule(javaTimeModule);
    }

    @Test
    public void testJsonAnyGetter() throws JsonProcessingException {

        ExtendableBean b1 = new ExtendableBean();
        b1.setName("zz");
        Map<String, String> map = b1.getProperties();
        map.put("field1", "1");
        map.put("field2", "2");

        ObjectMapper mapper = new ObjectMapper();
        String s1 = mapper.writeValueAsString(b1);
        System.out.println("s1= " + s1);


        String jsonS1 = "{\"name\":\"zz\",\"name1\":123,\"field1\":\"1\",\"field2\":\"2\"}";
        ExtendableBean b1FromJson = mapper.readValue(jsonS1, ExtendableBean.class);
        System.out.println(b1FromJson);

        ExtendableBean b1FromJsonUseFastjson = JSONObject.parseObject(jsonS1, ExtendableBean.class);
        System.out.println(b1FromJsonUseFastjson);


    }



    @Test
    public void testRestTemplateUseJackson() {

//        RawBean b = RestTemplateUtils.getHttp("http://localhost:8081/test/testIgnoreUnknownField", RawBean.class);
//        System.out.println(b);



    }

    @Data
    public static class MyBean2 {

        private String name;


        /**
         * 序列化时， jackson找所有的 get方法，
         * 不用注解， 生成 key:"theName"
         * 用了注解， 生成 key: "name1"
         * @return
         */
        @JsonGetter("name1")
        public String getTheName() {
            return name;
        }

    }

    @Test
    public void testJsonGetter() throws JsonProcessingException {
        MyBean2 myBean2 = new MyBean2();
        myBean2.setName("zry");

        String s = mapper.writeValueAsString(myBean2);
        System.out.println(s);

    }


    public static enum TypeEnumWithValue {
        TYPE1(1, "type A"),
        TYPE2(2, "TYPE 2");

        private Integer id;
        private String name;

        TypeEnumWithValue(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }

    @Test
    public void whenSerializingUsingJsonValue_thenCorrect()
            throws JsonProcessingException {

        String enumAsString = new ObjectMapper()
                .writeValueAsString(TypeEnumWithValue.TYPE1);

        System.out.println(enumAsString);

    }


    public static class MyBean3 {
        private Integer id;
        private String name;

        public MyBean3(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        @JsonValue
        public Integer getId() {
            return id;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }

    /**
     * @JsonValue注解
     * 只能给一个方法加上该注解
     *
     * 若加上该注解，对该对象序列化的结果是 加上这个注解的方法的返回值
     *
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testJsonValueForClass() throws JsonProcessingException {
        MyBean3 bean3 = new MyBean3(2, "zry");
        String s3 = mapper.writeValueAsString(bean3);
        System.out.println(s3);
    }


    @JsonRootName(value = "user")
    public static class MyBean4 {
        public int id;
        public String name;
    }

    /**
     * @JsonRootName
     * 用了该注解后， 序列化对象时， 会在外面再套上一层
     *
     * 但必须配置 objectMapper
     * mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
     *
     *
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testJsonRootName() throws JsonProcessingException {
        MyBean4 myBean4 = new MyBean4();
        myBean4.id = 1;
        myBean4.name = "zry";

        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String s4 = mapper.writeValueAsString(myBean4);

        System.out.println(s4);

    }


    public static class Event {
        public String name;

        @JsonSerialize(using = CustomDateSerializer.class)
        public Date eventDate;
    }
    public static class CustomDateSerializer extends JsonSerializer<Date> {

        private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh:mm:ss");


        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(formatter.format(date));
        }
    }

    /**
     * 注解  @JsonSerialize
     * 指定某个方式， 去序列化对应字段
     *
     *
     * @throws JsonProcessingException
     */
    @Test
    public void whenSerializingUsingJsonSerialize_thenCorrect()
            throws JsonProcessingException {

        Event event = new Event();
        event.name = "p1";
        event.eventDate = new Date();
        String result = mapper.writeValueAsString(event);

        System.out.println(result);

    }

    public static class BeanWithCreator {
        public int id;
        public String name;
        
        @JsonCreator
        public BeanWithCreator(
                @JsonProperty("id") int id,
                @JsonProperty("theName") String name) {
            this.id = id;
            this.name = name;
        }
    }


    /**
     * 注解  @JsonCreator
     * 给构造器加上该注解，
     * 并给参数加上  @JsonProperty注解
     *
     * 可解决 给定json某个key和成员变量名不同的问题
     *
     *
     * @throws IOException
     */
    @Test
    public void whenDeserializingUsingJsonCreator_thenCorrect()
            throws IOException {
        String json = "{\"id\":1,\"theName\":\"My bean\"}";
        BeanWithCreator bean = mapper
                .readerFor(BeanWithCreator.class)
                .readValue(json);

        System.out.println(mapper.writeValueAsString(bean));
    }


    public static class BeanWithInject {
        @JacksonInject
        public int id;

        public String name;

        @JacksonInject
        public LocalDate date;
    }


    /**
     *
     * 注解  @JacksonInject
     * 使用场景： 返回的字段中 与 某个成员变量 无关
     *
     * 又不想每次 给 这个成员变量赋值 如 responseDate
     *
     *
     * @throws IOException
     */
    @Test
    public void whenDeserializingUsingJsonInject_thenCorrect()
            throws IOException {
//        String json = "{\"name\":\"My bean\",\"id\":55,\"date\":\"2022-02-02\"}";
        String json = "{\"name\":\"My bean\",\"id\":55}";

        InjectableValues inject = new InjectableValues.Std()
                .addValue(int.class, 1)
                .addValue(LocalDate.class, LocalDate.now());


        BeanWithInject bean = mapper.reader(inject)
                .forType(BeanWithInject.class)
                .readValue(json);

        System.out.println(mapper.writeValueAsString(bean));
    }
}
