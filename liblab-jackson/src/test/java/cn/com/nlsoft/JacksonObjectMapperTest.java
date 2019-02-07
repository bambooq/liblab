package cn.com.nlsoft;

import cn.com.nlsoft.bean.Address;
import cn.com.nlsoft.bean.Employee;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
 */
public class JacksonObjectMapperTest {

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
        System.out.println("Employee Object\n" + emp);
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

    public static Employee createEmployee() {

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
        myMap = objectMapper.readValue(mapData, new TypeReference<HashMap<String, String>>() {
        });
        System.out.println("Map using TypeReference: " + myMap);
    }

    @Test
    public void testJsonOps() throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get("D:/ItWork/javaProject/liblab/liblab-jackson/target/test-classes/employee.json"));
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
}
