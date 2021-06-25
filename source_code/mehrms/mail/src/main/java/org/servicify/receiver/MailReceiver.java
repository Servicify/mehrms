package org.servicify.receiver;

import org.servicify.mehrms.mapper.AccountsMailSendStatusMapper;
import org.servicify.mehrms.mapper.EmpInfoMailStatusMapper;
import org.servicify.mehrms.mapper.EmployeeMapper;
import org.servicify.mehrms.model.AccountsBaseInfo;
import org.servicify.mehrms.model.AccountsMailSendStatus;
import org.servicify.mehrms.model.EmpInfoMailStatus;
import org.servicify.mehrms.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Component
public class MailReceiver {
//  添加日志文件
    public static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmpInfoMailStatusMapper empInfoMailStatusMapper;
    @Autowired
    AccountsMailSendStatusMapper accountsMailSendStatusMapper;

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//  发送入职邮件
@RabbitListener(queues="welcome")
    public void handler(Employee employee){
        logger.info(employee.toString());
//  收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        EmpInfoMailStatus empInfoMailStatus=new EmpInfoMailStatus();
        try{
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("***公司聘用通知");
            helper.setSentDate(new Date());
//          渲染实现模板
            Context context = new Context();
            String genderName="";
            if(employee.getGender().equals("男"))
            {
                genderName="先生";
            }else{
                genderName="女士";
            }
            context.setVariable("gender", genderName);
            context.setVariable("name", employee.getName());
            context.setVariable("positionName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJobLevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            context.setVariable("enterdate", dateFormat.format(new Date()));
            String mail = templateEngine.process("mail",context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
//            记录发送成功日志
            empInfoMailStatus.setEid(employee.getId());
            empInfoMailStatus.setStatus(true);
            empInfoMailStatus.setSendDate(new Date());
            System.out.println("员工号为："+employee.getId()+"的入职邮件发送成功!");
//            检查该入职邮件是否存在相应的发送记录,若发送成功,则执行更新操作
//            否则,执行插入数据操作
            if(empInfoMailStatusMapper.selectByEidKey(empInfoMailStatus.getEid())!=null){
                Integer id=empInfoMailStatusMapper.selectByEidKey(empInfoMailStatus.getEid()).getEmpInfoMailStatusId();
                empInfoMailStatus.setEmpInfoMailStatusId(id);
                empInfoMailStatusMapper.updateByPrimaryKeySelective(empInfoMailStatus);
                System.out.println("成功更新成功");
            }else{
                empInfoMailStatusMapper.insertSelective(empInfoMailStatus);
                System.out.println("成功插入成功");
            }

//
            empInfoMailStatus.setEmpInfoMailStatusId(null);
        }
        catch (MailSendException | MessagingException  e){
            e.printStackTrace();
//          记录邮件发送失败的日志
            logger.error("邮件发送失败:"+e.getMessage());
//            记录发送失败日志
            empInfoMailStatus.setEid(employee.getId());
            empInfoMailStatus.setStatus(false);
            empInfoMailStatus.setSendDate(new Date());
            System.out.println("员工号为："+employee.getId()+"的入职邮件发送失败!");
//            检查该入职邮件是否存在相应的发送记录,若发送失败,则执行更新操作
//            否则,执行插入数据操作
            if(empInfoMailStatusMapper.selectByEidKey(empInfoMailStatus.getEid())!=null){
                Integer id=empInfoMailStatusMapper.selectByEidKey(empInfoMailStatus.getEid()).getEmpInfoMailStatusId();
                empInfoMailStatus.setEmpInfoMailStatusId(id);
                empInfoMailStatusMapper.updateByPrimaryKeySelective(empInfoMailStatus);
                System.out.println("失败更新成功");
            }else{
                empInfoMailStatusMapper.insertSelective(empInfoMailStatus);
                System.out.println("失败插入成功");
            }

//
            empInfoMailStatus.setEmpInfoMailStatusId(null);
        }
    }
//  发送工资邮件
    @RabbitListener(queues="salary")
    public void handleSalary(AccountsBaseInfo accountsBaseInfo){
        logger.info(accountsBaseInfo.toString());
//  收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        Employee employee=employeeMapper.selectByPrimaryKey(accountsBaseInfo.getEid());
        AccountsMailSendStatus accountsMailSendStatus = new AccountsMailSendStatus();
//        避免发送时出现日期格式错误的问题
        String start=dateFormat.format(accountsBaseInfo.getStartDate());
        String end=dateFormat.format(accountsBaseInfo.getEndDate());
        String send=dateFormat.format(accountsBaseInfo.getSendDate());
        try{
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject(start+"至"+end+"工资表信息邮件");
            helper.setSentDate(new Date());
            String genderName="";
            if(employee.getGender().equals("男"))
            {
                genderName="先生";
            }else{
                genderName="女士";
            }
//          渲染实现模板
            Context context = new Context();
//          名字
            context.setVariable("name", employee.getName());
//          性别称谓
            context.setVariable("genderName", genderName);
//          性别
            context.setVariable("gender", employee.getGender());
//          工号
            context.setVariable("workID", employee.getWorkID());
//          开始日期
            context.setVariable("startDate", start);
//          结束日期
            context.setVariable("endDate", end);
//          部门名称
            context.setVariable("dname", accountsBaseInfo.getDepartment().getName());
//          职位名称
            context.setVariable("pname", accountsBaseInfo.getPosition().getName());
//          职称名称
            context.setVariable("jname", accountsBaseInfo.getJobLevel().getName());
//          工龄
            context.setVariable("workAge", accountsBaseInfo.getWorkAge());
//          基本工资
            context.setVariable("scopeRowBasicSalary", accountsBaseInfo.getBasicSalary());
//          交通补助
            context.setVariable("scopeRowTrafficSalary", accountsBaseInfo.getTrafficSalary());
//          午餐补助
            context.setVariable("scopeRowLunchSalary", accountsBaseInfo.getLunchSalary());
//          奖金
            context.setVariable("scopeRowBonus", accountsBaseInfo.getBonus());
//          养老金比率
            context.setVariable("scopeRowPensionPer", accountsBaseInfo.getPensionPer());
//          养老金基数
            context.setVariable("scopeRowPensionBase", accountsBaseInfo.getPensionBase());
//          医疗保险比率
            context.setVariable("scopeRowMedicalPer", accountsBaseInfo.getMedicalPer());
//          医疗保险基数
            context.setVariable("scopeRowMedicalBase", accountsBaseInfo.getMedicalBase());
//          公积金比率
            context.setVariable("scopeRowAccumulationFundPer", accountsBaseInfo.getAccumulationFundPer());
//          公积金基数
            context.setVariable("scopeRowAccumulationFundBase", accountsBaseInfo.getAccumulationFundBase());
//          账套名称
            context.setVariable("scopeRowSname", accountsBaseInfo.getSname());
//          工资翻倍
            context.setVariable("times", accountsBaseInfo.getTimes());
//          其他补助
            context.setVariable("others", accountsBaseInfo.getOthers());
//          减薪（罚款）
            context.setVariable("deduct", accountsBaseInfo.getDeduct());
//          预支工资
            context.setVariable("prepaid", accountsBaseInfo.getPrepaid());
//          当月绩效总分
            context.setVariable("accountsAdvInfoRecordScore", accountsBaseInfo.getAccountsAdvInfo().getRecordScore());
//          当月绩效分值（元）
            context.setVariable("recordPer", accountsBaseInfo.getRecordPer());
//          当月绩效奖金
            context.setVariable("accountsAdvInfoRecordSalary", accountsBaseInfo.getAccountsAdvInfo().getRecordSalary());
//          应发
            context.setVariable("accountsAdvInfoCount", accountsBaseInfo.getAccountsAdvInfo().getCount());
//          实际发放
            context.setVariable("realSalary", accountsBaseInfo.getRealSalary());
//          赊欠员工
            context.setVariable("accountsAdvInfoCreditEmp", accountsBaseInfo.getAccountsAdvInfo().getCreditEmp());
//          赊欠公司
            context.setVariable("accountsAdvInfoCreditCom", accountsBaseInfo.getAccountsAdvInfo().getCreditCom());
//          备注
            context.setVariable("remark", accountsBaseInfo.getRemark());
//          出纳人签名
            context.setVariable("cashierName", accountsBaseInfo.getCashierName());
//          签名时间
            context.setVariable("sendDate",send);
            String mail = templateEngine.process("salary",context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
            accountsMailSendStatus.setAccountsBaseInfoId(accountsBaseInfo.getId());
            accountsMailSendStatus.setSendDate(new Date());
            accountsMailSendStatus.setStatus(true);
            System.out.println("员工工资表序号为："+accountsBaseInfo.getId()+"的工资表邮件发送成功!");
//            查询发送该条工资表信息的邮件是否存在，若存在，则查找对应的id，执行更新操作；
//            否则，执行插入新记录操作
            if(accountsMailSendStatusMapper.selectByAccountsBaseInfoIdKey(accountsBaseInfo.getId())!=null){
                Integer id = accountsMailSendStatusMapper.selectByAccountsBaseInfoIdKey(accountsBaseInfo.getId()).getAccountsMailId();
                accountsMailSendStatus.setAccountsMailId(id);
                accountsMailSendStatusMapper.updateByPrimaryKeySelective(accountsMailSendStatus);
                System.out.println("成功更新成功！");
            }else{
                accountsMailSendStatusMapper.insertSelective(accountsMailSendStatus);
                System.out.println("成功插入成功！");
            }

//
            accountsMailSendStatus.setAccountsMailId(null);
        }
        catch (MailSendException | MessagingException e){
            e.printStackTrace();
//          记录邮件发送失败的打印日志
            logger.error("邮件发送失败:"+e.getMessage());
//          在数据库表中记录邮件发送失败日志
            accountsMailSendStatus.setAccountsBaseInfoId(accountsBaseInfo.getId());
            accountsMailSendStatus.setSendDate(new Date());
            accountsMailSendStatus.setStatus(false);
            System.out.println("员工工资表序号为："+accountsBaseInfo.getId()+"的工资表邮件发送失败!");
//            查询发送该条工资表信息的邮件是否存在，若存在，则查找对应的id，执行发送失败更新操作；
//            否则，对本次发送失败执行插入新记录操作
            if(accountsMailSendStatusMapper.selectByAccountsBaseInfoIdKey(accountsBaseInfo.getId())!=null){
                Integer id = accountsMailSendStatusMapper.selectByAccountsBaseInfoIdKey(accountsBaseInfo.getId()).getAccountsMailId();
                accountsMailSendStatus.setAccountsMailId(id);
                accountsMailSendStatusMapper.updateByPrimaryKeySelective(accountsMailSendStatus);
                System.out.println("失败更新成功！");
            }else{
                accountsMailSendStatusMapper.insertSelective(accountsMailSendStatus);
                System.out.println("失败插入成功！");
            }

//
            accountsMailSendStatus.setAccountsMailId(null);
        }

    }
}