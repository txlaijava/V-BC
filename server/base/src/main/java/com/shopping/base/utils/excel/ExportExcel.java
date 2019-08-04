package com.shopping.base.utils.excel;

import com.shopping.base.utils.CommUtils;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Boolean;
import jxl.write.*;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * @author wuyajun
 */
public class ExportExcel {

    /**
     * 日志类
     */
    private static Logger log = Logger.getLogger(ExportExcel.class);

    /***************************************************************************
     * @param fileName            EXCEL文件名称
     * @param Title               EXCEL文件第一行列标题集合   ["名称","对应属性名"，"名称","对应属性名"....](如果listContent的Object为某个对象  齐对应属性名不填写)
     * @param listContent         EXCEL文件正文数据集合
     * @return
     */
    public final static void exportExcel(HttpServletResponse response, String fileName, String[] Title, List<Object> listContent, int[] width) {
        // 以下开始输出到EXCEL
        try {
            //定义输出流，以便打开保存对话框______________________begin
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
            // 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            //定义输出流，以便打开保存对话框_______________________end

            /** **********创建工作簿************ */
            WritableWorkbook workbook = Workbook.createWorkbook(os);

            /** **********创建工作表************ */

            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            /** **********设置纵横打印（默认为纵打）、打印纸***************** */
            jxl.SheetSettings sheetset = sheet.getSettings();
            sheetset.setProtected(false);


            /** ************设置单元格字体************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 8);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

            /** ************以下设置三种单元格样式，灵活备用************ */
            // 用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_center.setWrap(false); // 文字是否换行

            // 用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_left.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_left.setWrap(false); // 文字是否换行


            /** ***************以下是EXCEL开头大标题，暂时省略********************* */
            //sheet.mergeCells(0, 0, colWidth, 0);
            //sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
            /** ***************以下是EXCEL第一行列标题********************* */
            for (int i = 0; i < Title.length; i++) {
                if (i % 2 == 0) {
                    sheet.addCell(new Label(i / 2, 0, Title[i], wcf_center));
                } else {

                    continue;
                }
            }
            /*设置每列宽度*/
            for (int i = 0; i < width.length; i++) {
                sheet.setColumnView(i, width[i]);
            }
            /** ***************以下是EXCEL正文数据********************* */
            Field[] fields = null;
            int i = 1;

            for (Object obj : listContent) {
                int j = 0;
                if (obj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> maps = (Map<String, Object>) obj;
                    maps.put("id", i);
                    for (int k = 0; k < Title.length; k++) {
                        if (k % 2 != 0) {

                            sheet.addCell(new Label(j, i, maps.get(Title[k]) + "", wcf_left));
                            j++;
                        } else {
                            continue;
                        }
                    }


                } else {
                    fields = obj.getClass().getDeclaredFields();
                    for (Field v : fields) {
                        v.setAccessible(true);
                        Object va = v.get(obj);
                        if (va == null) {
                            va = "";
                        } else if (va instanceof Date) {
                            Date date = (Date) va;
                            va = CommUtils.formatLongDate(date);
                        } else if (va instanceof Integer) {
                            int value = CommUtils.null2Int(va);
                            va = value;
                        } else if (va instanceof Boolean) {
                            continue;
                        }
                        sheet.addCell(new Label(j, i, va.toString(), wcf_left));
                        j++;
                    }
                }
                i++;
            }

            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();
            /** *********关闭文件************* */
            workbook.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /***************************************************************************
     * @param fileName            EXCEL文件名称
     * @param sheetname           页数
     * @param titles              EXCEL文件第一行列标题集合
     * @param reportList          EXCEL文件正文数据集合
     * @return
     */
    public final static boolean export2Excel(HttpServletResponse response, String fileName, String sheetname, String[] titles, List reportList) {
        // 以下开始输出到EXCEL
        try {
            //定义输出流，以便打开保存对话框______________________begin
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
            // 设定输出文件头
            response.setContentType("application/vnd.ms-excel");
            //定义输出流，以便打开保存对话框_______________________end
            WritableWorkbook workbook = null;
            int addRow = 0;//如果增加了sap非正常的标题，则要加增量
            try {
                // 创建新的Excel 工作簿
                workbook = Workbook.createWorkbook(os);
                // 在Excel工作簿中建一工作表，如果没有为其命名，刚自动为其命名为其名为:第一页
                if (sheetname == null || "".equals(sheetname.trim()))
                    sheetname = "第一页";
                jxl.write.WritableSheet wsheet = workbook.createSheet(sheetname, 0); // sheet();
                for (int i = 0; titles != null && i < titles.length; i++) {// 如果数据行有标题，就不用单独设置标题
                    Object firstString = titles[0];
                    if ("nottitle".equals(firstString)) {//这是处理sap报表时非正常标题的情况
                        addRow = titles.length;
                        if (i == 0) continue;//此行是标题判断信息，不要
                        wsheet.mergeCells(0, i, 10, i);//(x,y,m,n)表示从第x列到第m列，从第y行到第n行的数据执行合并，在此我们对每行的执行1到10列的合并
                        Label wlabel1 = new Label(0, i, titles[i]);// 行、列、单元格中的文本、文本格式
                        wsheet.addCell(wlabel1);
                    } else {//这是处理标题的情况
                        Label wlabel1 = new Label(i, 0, titles[i]);
                        wsheet.addCell(wlabel1);
                    }
                }
                for (int i = 0; reportList != null && i < reportList.size(); i++) { // 在索引0的位置创建行（最顶端的行）
                    String[] sdata = (String[]) reportList.get(i);
                    int rowId = addRow;
                    for (int j = 0; j < sdata.length; j++) { // 在索引0的位置创建单元格（左上端）
                        String temp = sdata[j];
                        temp = temp.replaceAll(",", "");
                        try {
                            double dotemp = Double.parseDouble(temp);
                            jxl.write.Number labelNF = new jxl.write.Number(j, i + rowId, dotemp);
                            wsheet.addCell(labelNF);
                        } catch (Exception ecp) {
                            Label wlabel1 = new Label(j, i + rowId, sdata[j]);
                            wsheet.addCell(wlabel1);
                        }
                    }
                }

                /** **********将以上缓存中的内容写到EXCEL文件中******** */
                workbook.write();
                /** *********关闭文件************* */
                workbook.close();
            } catch (WriteException ex1) {
                System.out.println("WriteException:" + ex1.getMessage());
                return false;
            } catch (IOException ex2) {
                System.out.println("IOException:" + ex2.getMessage());
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /***************************************************************************
     * 导出的数据进行分页 5万数据一页
     * @param fileName            EXCEL文件名称
     * @param Title               EXCEL文件第一行列标题集合   ["名称","对应属性名"，"名称","对应属性名"....](如果listContent的Object为某个对象  齐对应属性名不填写)
     * @param listContent         EXCEL文件正文数据集合
     * @return
     */
    public final static void exportExcel1(HttpServletResponse response, String fileName, String[] Title, List<Object> listContent, int[] width) {
        // 以下开始输出到EXCEL
        try {
            //定义输出流，以便打开保存对话框______________________begin
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
            // 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            //定义输出流，以便打开保存对话框_______________________end

            /** **********创建工作簿************ */
            WritableWorkbook workbook = Workbook.createWorkbook(os);

            int total = listContent.size();
            int size = 50000;
            int avg = total / size;
            /** **********创建工作表************ */
            for(int i_sheet = 0; i_sheet < avg + 1; i_sheet++){
                WritableSheet sheet = workbook.createSheet("列表"+(i_sheet+1), i_sheet);

                /** **********设置纵横打印（默认为纵打）、打印纸***************** */
                jxl.SheetSettings sheetset = sheet.getSettings();
                sheetset.setProtected(false);


                /** ************设置单元格字体************** */
                WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 8);
                WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

                /** ************以下设置三种单元格样式，灵活备用************ */
                // 用于标题居中
                WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
                wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
                wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
                wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
                wcf_center.setWrap(false); // 文字是否换行

                // 用于正文居左
                WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
                wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
                wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
                wcf_left.setAlignment(Alignment.CENTRE); // 文字水平对齐
                wcf_left.setWrap(false); // 文字是否换行


                /** ***************以下是EXCEL开头大标题，暂时省略********************* */
                //sheet.mergeCells(0, 0, colWidth, 0);
                //sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
                /** ***************以下是EXCEL第一行列标题********************* */
                for (int i = 0; i < Title.length; i++) {
                    if (i % 2 == 0) {
                        sheet.addCell(new Label(i / 2, 0, Title[i], wcf_center));
                    } else {

                        continue;
                    }
                }
                /*设置每列宽度*/
                for (int i = 0; i < width.length; i++) {
                    sheet.setColumnView(i, width[i]);
                }
                /** ***************以下是EXCEL正文数据********************* */
                Field[] fields = null;
                int i = 1;
                for(int scx = (i_sheet*size)+1;scx<(i_sheet+1)*size;scx++){
                    if(scx==(total-1)){
                        break;
                    }
                    Object obj = listContent.get(scx);
                    int j = 0;
                    if (obj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> maps = (Map<String, Object>) obj;
                        maps.put("id", i);
                        for (int k = 0; k < Title.length; k++) {
                            if (k % 2 != 0) {

                                sheet.addCell(new Label(j, i, maps.get(Title[k]) + "", wcf_left));
                                j++;
                            } else {
                                continue;
                            }
                        }


                    } else {
                        fields = obj.getClass().getDeclaredFields();
                        for (Field v : fields) {
                            v.setAccessible(true);
                            Object va = v.get(obj);
                            if (va == null) {
                                va = "";
                            } else if (va instanceof Date) {
                                Date date = (Date) va;
                                va = CommUtils.formatLongDate(date);
                            } else if (va instanceof Integer) {
                                int value = CommUtils.null2Int(va);
                                va = value;
                            } else if (va instanceof Boolean) {
                                continue;
                            }
                            sheet.addCell(new Label(j, i, va.toString(), wcf_left));
                            j++;
                        }
                    }
                    i++;
                }
            }
            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();
            /** *********关闭文件************* */
            workbook.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}

