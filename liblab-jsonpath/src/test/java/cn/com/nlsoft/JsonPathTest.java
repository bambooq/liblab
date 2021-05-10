package cn.com.nlsoft;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Test;

public class JsonPathTest {

    String json = "{\"code\":200,\"msg\":\"ok\",\"list\":[{\"id\":20,\"no\":\"1000020\",\"items\":[{\"name\":\"n1\",\"price\":21,\"infos\":{\"feature\":\"\"}}]}],\"metainfo\":{\"total\":20,\"info\":{\"owner\":\"qinshu\",\"parts\":[{\"count\":13,\"time\":{\"start\":1230002456,\"end\":234001234}}]}}}";

    // 常用的 Json Path 可以缓存起来重用，类似正则里的 Pattern p = Pattern.compile('regexString')
    JsonPath codePath = JsonPath.compile("$.code");

    @Test
    public void test() {
        Assert.assertEquals(200, (int) codePath.read(json));
        Assert.assertEquals("qinshu", JsonPath.read(json, "$.metainfo.info.owner"));
        Assert.assertEquals("n1", JsonPath.read(json, "$.list[0].items[0].name"));
        Assert.assertEquals(13, (int) JsonPath.read(json, "$.metainfo.info.parts[0].count"));
    }

}
