package nd.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.BorderStyle;

import java.util.Date;

@Setter
@Getter
@HeadFontStyle(fontHeightInPoints = 10, fontName = "微软雅黑")
@ContentFontStyle(fontHeightInPoints = 10, fontName = "微软雅黑")
@ContentStyle(borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
public class DemoData {

    @ColumnWidth(50)
    @ExcelProperty("字符串标题字符串标题字符串标题字符串标题字符串标题字符串标题字符串标题")
    private String string;

    @ColumnWidth(20)
    @ExcelProperty("日期标题")
    private Date date;

    @ColumnWidth(30)
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

}
