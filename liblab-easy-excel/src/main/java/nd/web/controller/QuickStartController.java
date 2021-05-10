package nd.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import lombok.SneakyThrows;
import nd.entity.DemoData;
import nd.entity.DemoDataTitle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class QuickStartController {

    @RequestMapping("/quick")
    @ResponseBody
    public String quick(){
        return "springboot hello quick";
    }

    @RequestMapping("/quick1")
    @ResponseBody
    public String quick1(){
        return "springboot hello quick1";
    }

    @RequestMapping("/quick2")
    @ResponseBody
    public String quick2(){
        return "springboot hello quick2";
    }

    @SneakyThrows
    @GetMapping("/e1")
    public void e1(HttpServletResponse response) {
        DemoDataTitle title = new DemoDataTitle();
        title.setString("表 1：项目成本监控总表（财务取数截止2021-2-28 人力截止日期：2021-03-31  程序取数日期 2021-03-31）");
        List<DemoDataTitle> titles = new ArrayList<>();
        titles.add(title);
        List<List<DemoData>> data = data();

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            for (int i = 0; i < data.size(); i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetNames.get(i)).needHead(Boolean.FALSE).build();
                WriteTable writeTable0 = EasyExcel.writerTable(0).head(DemoDataTitle.class).build();
                WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).head(DemoData.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(titles, writeSheet, writeTable0);
                excelWriter.write(data.get(i), writeSheet, writeTable1);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private Map<Integer, String> sheetNames = new HashMap<Integer, String>() {{
        put(0, "成本监控总表");
        put(1, "运营人力监控表");
        put(2, "研发人力监控表");
        put(3, "资源人力监控表");
        put(4, "支持人力监控表");
    }};

    private List<List<DemoData>> data() {
        List<List<DemoData>> result = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            List<DemoData> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                DemoData data = new DemoData();
                data.setString("字符串" + j + "_" + i);
                data.setDate(new Date());
                data.setDoubleData(0.56);
                list.add(data);
            }
            result.add(list);
        }
        return result;
    }

}
