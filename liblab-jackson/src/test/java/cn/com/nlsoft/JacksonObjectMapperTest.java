package cn.com.nlsoft;

import cn.com.nlsoft.bean.Address;
import cn.com.nlsoft.bean.Employee;
import cn.com.nlsoft.bean.User;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
 */
public class JacksonObjectMapperTest {

    private final static Logger LOG = LoggerFactory.getLogger(JacksonObjectMapperTest.class);

    @Test
    public void testJson2Object() throws Exception {
        //read json file data to String
        URL resource = this.getClass().getResource("/");
        System.out.println(resource.getPath());
        byte[] jsonData = Files.readAllBytes(Paths.get("D:/ItWork/javaProject/liblab/liblab-jackson/target/test-classes/employee.json"));
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //convert json string to object
        Employee emp = objectMapper.readValue(jsonData, Employee.class);
        LOG.info("\nEmployee Object\n" + emp);
    }


    @Test
    public void testObject2Json() throws Exception {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //convert Object to json string
        Employee emp1 = createEmployee();
        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, emp1);
        System.out.println("Employee JSON is\n" + stringEmp);
    }

    private static Employee createEmployee() {

        Employee emp = new Employee();
        emp.setId(100);
        emp.setName("David");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[]{123456, 987654});
        emp.setRole("Manager");

        Address add = new Address();
        add.setCity("Bangalore");
        add.setStreet("BTM 1st Stage");
        add.setZipcode(560100);
        emp.setAddress(add);

        List<String> cities = new ArrayList<String>();
        cities.add("Los Angeles");
        cities.add("New York");
        emp.setCities(cities);

        Map<String, String> props = new HashMap<String, String>();
        props.put("salary", "1000 Rs");
        props.put("age", "28 years");
        emp.setProperties(props);

        return emp;
    }


    @Test
    public void testJson2Map() throws IOException {
        //converting json to Map
        byte[] mapData = Files.readAllBytes(Paths.get("D:/ItWork/javaProject/liblab/liblab-jackson/target/test-classes/data.json"));
        Map<String, String> myMap = new HashMap<String, String>();

        ObjectMapper objectMapper = new ObjectMapper();
        myMap = objectMapper.readValue(mapData, HashMap.class);
        System.out.println("Map is: " + myMap);

        //another way
        myMap = objectMapper.readValue(mapData, new TypeReference<HashMap<String, String>>() {});
        System.out.println("Map using TypeReference: " + myMap);
    }

    @Test
    public void testJsonOps() throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get("E:\\nd\\source_code\\liblab\\liblab-jackson\\src\\main\\resources\\employee.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        //create JsonNode
        JsonNode rootNode = objectMapper.readTree(jsonData);

        //update JSON data
        ((ObjectNode) rootNode).put("id", 500);
        //add new key value
        ((ObjectNode) rootNode).put("test", "test value");
        //remove existing key
        ((ObjectNode) rootNode).remove("role");
        ((ObjectNode) rootNode).remove("properties");
        objectMapper.writeValue(new File("updated_emp.json"), rootNode);
    }


    @Test
    public void testJson2Object1() throws Exception {
        byte[] jsonData = Files.readAllBytes(Paths.get("E:/nd/source_code/liblab/liblab-jackson/target/test-classes/今日三农积分榜流水.txt"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode items = rootNode.get("items");
        int produce = 0;
        int consume = 0;
        int count = 0;
        for (Iterator<JsonNode> elements = items.elements(); elements.hasNext();) {
            JsonNode next = elements.next();
            String content = next.get("content").asText().replace("\\", "");
//            LOG.info(content);
            JsonNode jsonContent = objectMapper.readTree(content);
            if (jsonContent.get("orderType").asInt() == 1) {
                produce += jsonContent.get("amount").asInt();
            } else {
                consume += jsonContent.get("amount").asInt();
            }
            count ++;
        }
        LOG.info("流水条数：" + count);
        LOG.info("获得积分：" + produce);
        LOG.info("消费积分：" + consume);
//        2019-1-27 23:59:59
    }


    @Test
    public void testStreamParse() throws IOException {
        //create JsonParser object
        JsonParser jsonParser = new JsonFactory().createParser(new File("D:/ItWork/javaProject/liblab/liblab-jackson/target/test-classes/employee.json"));

        //loop through the tokens
        Employee emp = new Employee();
        Address address = new Address();
        emp.setAddress(address);
        emp.setCities(new ArrayList<String>());
        emp.setProperties(new HashMap<String, String>());
        List<Long> phoneNums = new ArrayList<Long>();
        boolean insidePropertiesObj = false;

        parseJSON(jsonParser, emp, phoneNums, insidePropertiesObj);

        long[] nums = new long[phoneNums.size()];
        int index = 0;
        for (Long l : phoneNums) {
            nums[index++] = l;
        }
        emp.setPhoneNumbers(nums);

        jsonParser.close();
        //print employee object
        System.out.println("Employee Object\n\n" + emp);
    }

    private static void parseJSON(JsonParser jsonParser, Employee emp,
                                  List<Long> phoneNums, boolean insidePropertiesObj) throws JsonParseException, IOException {

        //loop through the JsonTokens
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String name = jsonParser.getCurrentName();
            if ("id".equals(name)) {
                jsonParser.nextToken();
                emp.setId(jsonParser.getIntValue());
            } else if ("name".equals(name)) {
                jsonParser.nextToken();
                emp.setName(jsonParser.getText());
            } else if ("permanent".equals(name)) {
                jsonParser.nextToken();
                emp.setPermanent(jsonParser.getBooleanValue());
            } else if ("address".equals(name)) {
                jsonParser.nextToken();
                //nested object, recursive call
                parseJSON(jsonParser, emp, phoneNums, insidePropertiesObj);
            } else if ("street".equals(name)) {
                jsonParser.nextToken();
                emp.getAddress().setStreet(jsonParser.getText());
            } else if ("city".equals(name)) {
                jsonParser.nextToken();
                emp.getAddress().setCity(jsonParser.getText());
            } else if ("zipcode".equals(name)) {
                jsonParser.nextToken();
                emp.getAddress().setZipcode(jsonParser.getIntValue());
            } else if ("phoneNumbers".equals(name)) {
                jsonParser.nextToken();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    phoneNums.add(jsonParser.getLongValue());
                }
            } else if ("role".equals(name)) {
                jsonParser.nextToken();
                emp.setRole(jsonParser.getText());
            } else if ("cities".equals(name)) {
                jsonParser.nextToken();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    emp.getCities().add(jsonParser.getText());
                }
            } else if ("properties".equals(name)) {
                jsonParser.nextToken();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String key = jsonParser.getCurrentName();
                    jsonParser.nextToken();
                    String value = jsonParser.getText();
                    emp.getProperties().put(key, value);
                }
            }
        }
    }

    @Test
    public void testGenerator() throws IOException {
        Employee emp = createEmployee();

        JsonGenerator jsonGenerator = new JsonFactory()
                .createGenerator(new FileOutputStream("stream_emp.json"));
        //for pretty printing
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

        jsonGenerator.writeStartObject(); // start root object
        jsonGenerator.writeNumberField("id", emp.getId());
        jsonGenerator.writeStringField("name", emp.getName());
        jsonGenerator.writeBooleanField("permanent", emp.isPermanent());

        jsonGenerator.writeObjectFieldStart("address"); //start address object
        jsonGenerator.writeStringField("street", emp.getAddress().getStreet());
        jsonGenerator.writeStringField("city", emp.getAddress().getCity());
        jsonGenerator.writeNumberField("zipcode", emp.getAddress().getZipcode());
        jsonGenerator.writeEndObject(); //end address object

        jsonGenerator.writeArrayFieldStart("phoneNumbers");
        for (long num : emp.getPhoneNumbers())
            jsonGenerator.writeNumber(num);
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("role", emp.getRole());

        jsonGenerator.writeArrayFieldStart("cities"); //start cities array
        for (String city : emp.getCities())
            jsonGenerator.writeString(city);
        jsonGenerator.writeEndArray(); //closing cities array

        jsonGenerator.writeObjectFieldStart("properties");
        Set<String> keySet = emp.getProperties().keySet();
        for (String key : keySet) {
            String value = emp.getProperties().get(key);
            jsonGenerator.writeStringField(key, value);
        }
        jsonGenerator.writeEndObject(); //closing properties
        jsonGenerator.writeEndObject(); //closing root object

        jsonGenerator.flush();
        jsonGenerator.close();
    }

    /**
     * 测试注解 <br>
     * @JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
     * @JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")。
     * @JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")
     */
    @Test
    public void testAnnotation() throws JsonProcessingException {
        User user = new User();
        user.setAge(10);
        user.setBirthday(new Date());
        user.setEmail("tom@sina.com");
        user.setName("tom");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        LOG.info(json);
    }


    @Test
    public void testJsonOps1() throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get("E:\\nd\\source_code\\liblab\\liblab-jackson\\src\\main\\resources\\snwjt_router.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        //create JsonNode
        JsonNode rootNode = objectMapper.readTree(jsonData);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putArray("items");

        for (Iterator<JsonNode> it = rootNode.iterator(); it.hasNext();) {
            JsonNode next = it.next();
            if (next.get("serviceId").asText().equals("rank_admin")) {
                ((ArrayNode) objectNode.get("items")).add(next);
            }
        }

        /*
        //update JSON data
        ((ObjectNode) rootNode).put("id", 500);
        //add new key value
        ((ObjectNode) rootNode).put("test", "test value");
        //remove existing key
        ((ObjectNode) rootNode).remove("role");
        ((ObjectNode) rootNode).remove("properties");


         */
        objectMapper.writeValue(new File("snwjt_router_dest.json"), objectNode);

    }

    @Test
    public void testTenantBind() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        result.putArray("delete");
        result.putArray("bind");

        byte[] jsonData = Files.readAllBytes(Paths.get("E:\\nd\\source_code\\liblab\\liblab-jackson\\src\\main\\resources\\preproduction_tenant.json"));
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode items = rootNode.get("items");
        Map<String, Integer> appIdCounter = new HashMap<>();
        for (Iterator<JsonNode> it = items.iterator(); it.hasNext();) {
            JsonNode item = it.next();
            // 不用处理一个projectId对应一个appId
            if (item.get("app_ids").size() == 1) {
                continue;
            }
//            System.out.println(item);
            JsonNode appIds = item.get("app_ids");
            for (Iterator<JsonNode> itApp = appIds.iterator(); itApp.hasNext();) {
                JsonNode next = itApp.next();
                String appId = next.get("app_id").asText();
                if (appIdCounter.containsKey(appId)) {
                    appIdCounter.put(appId, appIdCounter.get(appId).intValue() + 1);
                } else {
                    appIdCounter.put(appId, 1);
                }
            }
        }

        for (Iterator<JsonNode> it = items.iterator(); it.hasNext();) {
            JsonNode item = it.next();
            // 不用处理一个projectId对应一个appId
            if (item.get("app_ids").size() == 1) {
                continue;
            }
            JsonNode appIds = item.get("app_ids");
            Map<String, Boolean> appFlag = new HashMap<>();
            Map<String, String> appTenant = new HashMap<>();
            int cannotDelCount = 0;
            for (Iterator<JsonNode> itApp = appIds.iterator(); itApp.hasNext(); ) {
                JsonNode app = itApp.next();
                String appId = app.get("app_id").asText();
                boolean hasData = app.get("has_data").asBoolean();
                String tenantId = app.get("tenant_id").asText();
                appTenant.put(appId, tenantId);
                if (appIdCounter.get(appId) == 1) {
                    if (hasData) {
                        appFlag.put(appId, false);
                        cannotDelCount ++;
                    } else {
                        appFlag.put(appId, true);
                    }
                } else {
                    appFlag.put(appId, false);
                    cannotDelCount++;
                }
            }
            if (cannotDelCount > 1) {
                System.out.println("需要人工判断 - " + item);
                continue;
            }

            // 拼装指令
            StringBuffer bindList = new StringBuffer();
            String bindTenantId = null;
            for (Iterator<String> iterator = appFlag.keySet().iterator(); iterator.hasNext();) {
                String appId = iterator.next();
                if (cannotDelCount == 0) {
                    if (bindTenantId == null) {
                        bindTenantId = appTenant.get(appId);
                        continue;
                    } else {
                        ((ArrayNode) result.get("delete")).add(appTenant.get(appId));
                        bindList.append(appId).append(",");
                    }
                } else {
                    if (appFlag.get(appId)) {
                        ((ArrayNode) result.get("delete")).add(appTenant.get(appId));
                        bindList.append(appId).append(",");
                    } else {
                        bindTenantId = appTenant.get(appId);
                    }
                }
            }
            ((ArrayNode) result.get("bind")).add(bindTenantId + "," + bindList.toString().substring(0, bindList.toString().length() - 1));
        }
        objectMapper.writeValue(new File("preproduction_tenant_cmd.json"), result);
    }
}
