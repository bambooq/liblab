package nd.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;

@Setter
@Getter
@ContentFontStyle(fontHeightInPoints = 14, fontName = "微软雅黑", bold = true, color = Font.COLOR_RED)
@OnceAbsoluteMerge(firstRowIndex = 0, lastRowIndex = 0, firstColumnIndex = 0, lastColumnIndex = 2)
@ContentStyle(borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
public class DemoDataTitle {

    @ExcelProperty("字符串标题字符串标题字符串标题字符串标题字符串标题字符串标题字符串标题")
    private String string;

}
