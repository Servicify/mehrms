package org.servicify.mehrms.utils;

import javafx.scene.control.Cell;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.servicify.mehrms.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class POIUtils {

    //    使用excel表将数据提供给用户
    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        DecimalFormat decimalFormat= new DecimalFormat("##.00");
        //1. 创建一个 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 创建文档摘要
        workbook.createInformationProperties();
        //3. 获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("员工信息");
        //文档管理员
        docInfo.setManager("Servicify");
        //设置公司信息
        docInfo.setCompany("www.servicify.com");
        //4. 获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("员工信息表");
        //文档作者
        summInfo.setAuthor("Servicify");
        // 文档备注
        summInfo.setComments("本文档由 Servicify 提供");
        //5. 创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//      创建日期格式，m/d/yy等价于yyyy-mm-dd,并在遍历数据集合处，添加日期处理格式
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        //设置列的宽度，第一个参数为第几列，第二个参数为多少宽度，5*256大概5个字符宽
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 16 * 256);
        sheet.setColumnWidth(9, 12 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 16 * 256);
        sheet.setColumnWidth(13, 14 * 256);
        sheet.setColumnWidth(14, 14 * 256);
        sheet.setColumnWidth(15, 12 * 256);
        sheet.setColumnWidth(16, 8 * 256);
        sheet.setColumnWidth(17, 20 * 256);
        sheet.setColumnWidth(18, 20 * 256);
        sheet.setColumnWidth(19, 15 * 256);
        sheet.setColumnWidth(20, 8 * 256);
        sheet.setColumnWidth(21, 25 * 256);
        sheet.setColumnWidth(22, 14 * 256);
        sheet.setColumnWidth(23, 15 * 256);
        sheet.setColumnWidth(24, 15 * 256);
//        添加列分别为工作状态、离职日期、总工龄、入职前工龄、入职后工龄
        sheet.setColumnWidth(25, 15 * 256);
        sheet.setColumnWidth(26, 15 * 256);
        sheet.setColumnWidth(27, 15 * 256);
        sheet.setColumnWidth(28, 15 * 256);
        sheet.setColumnWidth(29, 15 * 256);
        //6. 创建标题行
//        创建一个空标题行
        HSSFRow r0 = sheet.createRow(0);
//        在空标题行里插入列
//        此处插入第一列
        HSSFCell c0 = r0.createCell(0);
        c0.setCellStyle(headerStyle);
        c0.setCellValue("编号");
//        插入标题行的第二列
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("姓名");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("工号");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("性别");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("出生日期");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("身份证号码");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("婚姻状况");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("民族");
        HSSFCell c8 = r0.createCell(8);
        c8.setCellStyle(headerStyle);
        c8.setCellValue("籍贯");
        HSSFCell c9 = r0.createCell(9);
        c9.setCellStyle(headerStyle);
        c9.setCellValue("政治面貌");
        HSSFCell c10 = r0.createCell(10);
        c10.setCellStyle(headerStyle);
        c10.setCellValue("电话号码");
        HSSFCell c11 = r0.createCell(11);
        c11.setCellStyle(headerStyle);
        c11.setCellValue("联系地址");
        HSSFCell c12 = r0.createCell(12);
        c12.setCellStyle(headerStyle);
        c12.setCellValue("所属部门");
        HSSFCell c13 = r0.createCell(13);
        c13.setCellStyle(headerStyle);
        c13.setCellValue("职称");
        HSSFCell c14 = r0.createCell(14);
        c14.setCellStyle(headerStyle);
        c14.setCellValue("职位");
        HSSFCell c15 = r0.createCell(15);
        c15.setCellStyle(headerStyle);
        c15.setCellValue("聘用形式");
        HSSFCell c16 = r0.createCell(16);
        c16.setCellStyle(headerStyle);
        c16.setCellValue("最高学历");
        HSSFCell c17 = r0.createCell(17);
        c17.setCellStyle(headerStyle);
        c17.setCellValue("专业");
        HSSFCell c18 = r0.createCell(18);
        c18.setCellStyle(headerStyle);
        c18.setCellValue("毕业院校");
        HSSFCell c19 = r0.createCell(19);
        c19.setCellStyle(headerStyle);
        c19.setCellValue("入职日期");
        HSSFCell c20 = r0.createCell(20);
        c20.setCellStyle(headerStyle);
        c20.setCellValue("在职状态");
        HSSFCell c21 = r0.createCell(21);
        c21.setCellStyle(headerStyle);
        c21.setCellValue("邮箱");
        HSSFCell c22 = r0.createCell(22);
        c22.setCellStyle(headerStyle);
        c22.setCellValue("合同期限(年)");
        HSSFCell c23 = r0.createCell(23);
        c23.setCellStyle(headerStyle);
        c23.setCellValue("合同起始日期");
        HSSFCell c24 = r0.createCell(24);
        c24.setCellStyle(headerStyle);
        c24.setCellValue("合同终止日期");
//      添加新字段名称
        HSSFCell c25 = r0.createCell(25);
        c25.setCellStyle(headerStyle);
        c25.setCellValue("转正日期");
//        HSSFCell c26 = r0.createCell(26);
//        c26.setCellStyle(headerStyle);
//        c26.setCellValue("工作状态");
        HSSFCell c26 = r0.createCell(26);
        c26.setCellStyle(headerStyle);
        c26.setCellValue("离职日期");
        HSSFCell c27 = r0.createCell(27);
        c27.setCellStyle(headerStyle);
        c27.setCellValue("入职前工龄");
        HSSFCell c28 = r0.createCell(28);
        c28.setCellStyle(headerStyle);
        c28.setCellValue("入职后工龄");
        HSSFCell c29 = r0.createCell(29);
        c29.setCellStyle(headerStyle);
        c29.setCellValue("总工龄");

//        遍历上述的列集合，将参数传入列集合中
        for (int i = 0; i < list.size(); i++) {
            Employee emp = list.get(i);
//            创建第一行
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());
//          对日期类型的数据进行格式的处理，下同
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellStyle(dateCellStyle);
            cell4.setCellValue(emp.getBirthday());
            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(emp.getPhone());
            row.createCell(11).setCellValue(emp.getAddress());
            row.createCell(12).setCellValue(emp.getDepartment().getName());
            row.createCell(13).setCellValue(emp.getJobLevel().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getEngageForm());
            row.createCell(16).setCellValue(emp.getTiptopDegree());
            row.createCell(17).setCellValue(emp.getSpecialty());
            row.createCell(18).setCellValue(emp.getSchool());
            HSSFCell cell19 = row.createCell(19);
            cell19.setCellStyle(dateCellStyle);
            cell19.setCellValue(emp.getBeginDate());
            row.createCell(20).setCellValue(emp.getWorkState());
            row.createCell(21).setCellValue(emp.getEmail());
            row.createCell(22).setCellValue(emp.getContractTerm());
            HSSFCell cell23 = row.createCell(23);
            cell23.setCellStyle(dateCellStyle);
            cell23.setCellValue(emp.getBeginContract());
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellStyle(dateCellStyle);
            cell24.setCellValue(emp.getEndContract());
//            入职日期
            HSSFCell cell25 = row.createCell(25);
            cell25.setCellStyle(dateCellStyle);
            cell25.setCellValue(emp.getConversionTime());
//            离职日期
            HSSFCell cell26 = row.createCell(26);
            cell26.setCellStyle(dateCellStyle);
            cell26.setCellValue(emp.getNotWorkDate());
//            入职前工龄
            row.createCell(27).setCellValue(decimalFormat.format(emp.getWork_age().getBeforeWorkAge()));
//            入职后工龄
            row.createCell(28).setCellValue(decimalFormat.format(emp.getWork_age().getAfterWorkAge()));
//            入职前工龄
            row.createCell(29).setCellValue(decimalFormat.format(emp.getWork_age().getWorkAge()));
        }
//      打印输出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
//          文件传输时，避免文件名乱码
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }


//    从用户上传的excel表中导入数据
//    即将Excel表解析成员工集合

    public static List<Employee> excel2Employee(MultipartFile file, List<Nation> allNations,
                                                List<Politicsstatus> allPoliticsstatus,
                                                List<Department> allDepartments,
                                                List<Position> allPositions,
                                                List<JobLevel> allJobLevels) {
        List<Employee> list = new ArrayList<>();
        Employee employee = null;
        try {
            //1. 创建一个 workbook 对象，进行解析excel表
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //2. 获取 workbook 中表单的数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //3. 获取表单
                HSSFSheet sheet = workbook.getSheetAt(i);
                //4. 获取表单中的行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    //5. 跳过标题行
                    if (j == 0) {
                        continue;//跳过标题行
                    }
                    //6. 获取行
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//防止数据中间有空行
                    }
                    //7. 获取列数
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    employee = new Employee();
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        HSSFCell cell = row.getCell(k);
//                        添加一个空过滤器避免读入尾部的空行数据，造成系统抛出空错误
//                        若该行数据为空，则跳过；否则写入
                        if(cell==null){
                            continue;
                        } else {
                            switch (cell.getCellType()) {
                                //匹配数据类型为浮点数和日期类型的相关数据
                                case NUMERIC:
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        Date date = cell.getDateCellValue();
                                        switch (k) {
                                            case 2:
                                                employee.setBirthday(date);
                                                break;
                                            case 17:
                                                employee.setBeginDate(date);
                                                break;
                                            case 20:
                                                employee.setBeginContract(date);
                                                break;
                                            case 21:
                                                employee.setEndContract(date);
                                                break;
                                            case 22:
                                                employee.setConversionTime(date);
                                                break;
                                            case 23:
                                                employee.setNotWorkDate(date);
                                                break;
                                        }
                                    }else{
//                                        将读取的数据转换为非科学计数法的数
                                        double number=cell.getNumericCellValue();
                                        BigDecimal bg=new BigDecimal(number+"");
                                        switch (k) {
                                            case 8:
                                                employee.setPhone(bg+"");
                                                break;
                                            case 24:
                                                employee.setBeforeWorkAge(bg.floatValue());
                                                break;
                                        }
                                    }
                                    break;
                                case STRING:
                                    String cellValue = cell.getStringCellValue();
                                    switch (k) {
                                        case 0:
                                            employee.setName(cellValue);
                                            break;
//                                    case 2:
//                                        employee.setWorkID(cellValue);
//                                        break;
                                        case 1:
                                            employee.setGender(cellValue);
                                            break;
                                        case 3:
                                            employee.setIdCard(cellValue);
                                            break;
                                        case 4:
                                            employee.setWedlock(cellValue);
                                            break;
                                        case 5:
//                                        重写Nation类中的hashCode()和equals()方法，通过此来判断nation的ID
//                                        下同
                                            int nationIndex = allNations.indexOf(new Nation(cellValue));
                                            employee.setNationId(allNations.get(nationIndex).getId());
                                            break;
                                        case 6:
                                            employee.setNativePlace(cellValue);
                                            break;
                                        case 7:
                                            int politicstatusIndex = allPoliticsstatus.indexOf(new Politicsstatus(cellValue));
                                            employee.setPoliticId(allPoliticsstatus.get(politicstatusIndex).getId());
                                            break;
                                        case 9:
                                            employee.setAddress(cellValue);
                                            break;
                                        case 10:
                                            int departmentIndex = allDepartments.indexOf(new Department(cellValue));
                                            employee.setDepartmentId(allDepartments.get(departmentIndex).getId());
                                            break;
                                        case 11:
                                            int jobLevelIndex = allJobLevels.indexOf(new JobLevel(cellValue));
                                            employee.setJobLevelId(allJobLevels.get(jobLevelIndex).getId());
                                            break;
                                        case 12:
                                            int positionIndex = allPositions.indexOf(new Position(cellValue));
                                            employee.setPosId(allPositions.get(positionIndex).getId());
                                            break;
                                        case 13:
                                            employee.setEngageForm(cellValue);
                                            break;
                                        case 14:
                                            employee.setTiptopDegree(cellValue);
                                            break;
                                        case 15:
                                            employee.setSpecialty(cellValue);
                                            break;
                                        case 16:
                                            employee.setSchool(cellValue);
                                            break;
                                        case 18:
                                            employee.setWorkState(cellValue);
                                            break;
                                        case 19:
                                            employee.setEmail(cellValue);
                                            break;
                                        case 24:
//                                            workAge理应为数值处捕获，此处或有异常
                                            employee.setBeforeWorkAge(Float.parseFloat(cellValue));
                                            break;
                                    }
                                    break;
                                default:
                                break;
                            }
                        }

                    }
//                    设置过滤器，避免最后一条空employee创建后，尚未添加数据却被添加
                    if(employee.getName()!=null){
                        list.add(employee);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

//  创建一个上传信息的表格模板
    public static ResponseEntity<byte[]> employee2download() {
        //1. 创建一个 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 创建文档摘要
        workbook.createInformationProperties();
        //3. 获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("员工信息");
        //文档管理员
        docInfo.setManager("Servicify");
        //设置公司信息
        docInfo.setCompany("www.servicify.com");
        //4. 获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("员工信息表");
        //文档作者
        summInfo.setAuthor("Servicify");
        // 文档备注
        summInfo.setComments("本文档由 Servicify 提供");
        //5. 创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//      创建日期格式，m/d/yy等价于yyyy-mm-dd,并在遍历数据集合处，添加日期处理格式
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        //设置列的宽度，第一个参数为第几列，第二个参数为多少宽度，5*256大概5个字符宽
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 25 * 256);
        sheet.setColumnWidth(7, 16 * 256);
        sheet.setColumnWidth(8, 15 * 256);
        sheet.setColumnWidth(9, 20 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 20 * 256);
        sheet.setColumnWidth(13, 20 * 256);
        sheet.setColumnWidth(14, 15 * 256);
        sheet.setColumnWidth(15, 20 * 256);
        sheet.setColumnWidth(16, 25 * 256);
        sheet.setColumnWidth(17, 14 * 256);
        sheet.setColumnWidth(18, 15 * 256);
        sheet.setColumnWidth(19, 20 * 256);
//        添加列分别为离职日期、总工龄、入职前工龄、入职后工龄
        sheet.setColumnWidth(20, 15 * 256);
        sheet.setColumnWidth(21, 15 * 256);
        sheet.setColumnWidth(22, 15 * 256);
        sheet.setColumnWidth(23, 15 * 256);
        sheet.setColumnWidth(24, 15 * 256);
        //6. 创建标题行
//        创建一个空标题行
        HSSFRow r0 = sheet.createRow(0);
//        在空标题行里插入列
//        插入标题行的第二列
        HSSFCell c0 = r0.createCell(0);
        c0.setCellStyle(headerStyle);
        c0.setCellValue("姓名");

        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("性别");

        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("出生日期");

        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("身份证号码");

        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("婚姻状况");

        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("民族");

        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("籍贯");

        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("政治面貌");

        HSSFCell c8 = r0.createCell(8);
        c8.setCellStyle(headerStyle);
        c8.setCellValue("电话号码");

        HSSFCell c9 = r0.createCell(9);
        c9.setCellStyle(headerStyle);
        c9.setCellValue("联系地址");

        HSSFCell c10 = r0.createCell(10);
        c10.setCellStyle(headerStyle);
        c10.setCellValue("所属部门");

        HSSFCell c11 = r0.createCell(11);
        c11.setCellStyle(headerStyle);
        c11.setCellValue("职称");

        HSSFCell c12 = r0.createCell(12);
        c12.setCellStyle(headerStyle);
        c12.setCellValue("职位");

        HSSFCell c13 = r0.createCell(13);
        c13.setCellStyle(headerStyle);
        c13.setCellValue("聘用形式");

        HSSFCell c14 = r0.createCell(14);
        c14.setCellStyle(headerStyle);
        c14.setCellValue("最高学历");

        HSSFCell c15 = r0.createCell(15);
        c15.setCellStyle(headerStyle);
        c15.setCellValue("专业");

        HSSFCell c16 = r0.createCell(16);
        c16.setCellStyle(headerStyle);
        c16.setCellValue("毕业院校");

        HSSFCell c17 = r0.createCell(17);
        c17.setCellStyle(headerStyle);
        c17.setCellValue("入职日期");

        HSSFCell c18 = r0.createCell(18);
        c18.setCellStyle(headerStyle);
        c18.setCellValue("在职状态");

        HSSFCell c19 = r0.createCell(19);
        c19.setCellStyle(headerStyle);
        c19.setCellValue("邮箱");

        HSSFCell c20 = r0.createCell(20);
        c20.setCellStyle(headerStyle);
        c20.setCellValue("合同起始日期");

        HSSFCell c21 = r0.createCell(21);
        c21.setCellStyle(headerStyle);
        c21.setCellValue("合同终止日期");
//      添加新字段名称
        HSSFCell c22 = r0.createCell(22);
        c22.setCellStyle(headerStyle);
        c22.setCellValue("转正日期");

//        HSSFCell c23 = r0.createCell(23);
//        c23.setCellStyle(headerStyle);
//        c23.setCellValue("工作状态");

        HSSFCell c23 = r0.createCell(23);
        c23.setCellStyle(headerStyle);
        c23.setCellValue("离职日期");

        HSSFCell c24 = r0.createCell(24);
        c24.setCellStyle(headerStyle);
        c24.setCellValue("入职前工龄");
//      打印输出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
//          文件传输时，避免文件名乱码
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
}
