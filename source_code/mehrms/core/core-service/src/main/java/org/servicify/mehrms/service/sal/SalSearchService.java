package org.servicify.mehrms.service.sal;

import com.alibaba.druid.sql.visitor.functions.If;
import org.servicify.mehrms.mapper.*;
import org.servicify.mehrms.model.*;
import org.servicify.mehrms.service.WorkAgeService;
import org.servicify.mehrms.utils.GetLastMonths;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class SalSearchService {
    @Autowired
    AccountsBaseInfoMapper accountsBaseInfoMapper;
    @Autowired
    SalSearchService salSearchService;
    @Autowired
    WorkAgeService workAgeService;
    @Autowired
    AccountsAdvInfoMapper accountsAdvInfoMapper;
    @Autowired
    AccountsAllMapper accountsAllMapper;
    @Autowired
    AccountsRoleMapper accountsRoleMapper;
    @Autowired
    AccountsRoleService accountsRoleService;
    @Autowired
    AccountsAllService accountsAllService;
    @Autowired
    Work_AgeMapper work_ageMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    //    查询所有薪资信息
    public RespPageBean getAllInfosByPage( Integer page,  Integer size, AccountsBaseInfo accountsBaseInfo,
                                          float[] RealSalaryScope, float[] CreditEmpScope,
                                           float[] CreditComScope, Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        RespPageBean resp=new RespPageBean();
        List<AccountsBaseInfo> data=accountsBaseInfoMapper.getAllInfoByPage(page,size,
                accountsBaseInfo,RealSalaryScope,CreditEmpScope,
                CreditComScope,beginDateScope);
        Long total=accountsBaseInfoMapper.getTotal(accountsBaseInfo,RealSalaryScope,CreditEmpScope,
                CreditComScope,beginDateScope);
        resp.setData(data);
        resp.setTotal(total);
        return resp;
    }
//查询所有的可修改的工资信息记录
    public RespPageBean getAllEnabledInfosByPage( Integer page,  Integer size, AccountsBaseInfo accountsBaseInfo,
                                           float[] RealSalaryScope, float[] CreditEmpScope,
                                           float[] CreditComScope, Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        RespPageBean resp=new RespPageBean();
        List<AccountsBaseInfo> data=accountsBaseInfoMapper.getAllEnabledInfoByPage(page,size,
                accountsBaseInfo,RealSalaryScope,CreditEmpScope,
                CreditComScope,beginDateScope);
        Long total=accountsBaseInfoMapper.getEnabledTotal(accountsBaseInfo,RealSalaryScope,CreditEmpScope,
                CreditComScope,beginDateScope);
        resp.setData(data);
        resp.setTotal(total);
        return resp;
    }
//    获取前端传来的开始时间，和结束时间，遍历员工目前工资的信息，合并生成该阶段的工资信息
//    在技术上，在AccountsRole中使用mybatis的插入数据后回调传递新插入数据的id键值信息。
//    在创建完成后更新所有的accountsAll中记录关联的所有的员工工资信息记录的工资合计信息。
@Transactional
    public Integer getAndCreateEnabledInfos(Date beginDate) throws Exception{
//        由于工龄随着时间会增长，故需要即时更新工龄。
        workAgeService.compWorkAge();
//        查询该月的起始日期
        List<MonthDate> dateScope= GetLastMonths.getLastMonths(beginDate,1);
        Date[] beginDateScope=new Date[2];
        beginDateScope[0]=dateScope.get(0).getStartDate();
        beginDateScope[1]=dateScope.get(0).getEndDate();
//        查询员工目前工资信息，为创建并导入员工目前工资表信息作准备
        List<AccountsBaseInfo> list = accountsBaseInfoMapper.getCreateInfo();
        Integer integer=0;
//        检查list2中是否存在创建日期范围内的数据，若存在则不创建。若不存在，则创建。
        List<AccountsAll> list2=accountsAllMapper.getAccountsAllInfoByBeginDate(beginDateScope);
        if(list2==null||list2.size()==0){
            AccountsAll accountsAll=new AccountsAll();
            accountsAll.setStartDate(beginDateScope[0]);
            accountsAll.setStartDate(beginDateScope[1]);
            accountsAllMapper.insertSelective(accountsAll);
            integer=accountsAll.getId();
        }else{
            return 2;
        }
        for(int i=0;i<list.size();i++){
            list.get(i).setId(null);
            AccountsBaseInfo accountsBaseInfo=list.get(i);

            salSearchService.createMethod(accountsBaseInfo,beginDateScope,integer);
        }
        return 1;
    }
//    查询工资表中的更新备注，为避免用户越过前端手动更给，新建个对象，提取id值对应的数据库中的信息，并设置备注信息，进行插入更新。
@Transactional
    public Integer putInfo(AccountsBaseInfo accountsBaseInfo) {
        Integer id = accountsBaseInfo.getId();
        String remark=accountsBaseInfo.getRemark();
        AccountsBaseInfo accountsBaseInfo1=accountsBaseInfoMapper.selectByPrimaryKey(id);
        accountsBaseInfo1.setRemark(remark);
        accountsBaseInfoMapper.updateByPrimaryKeySelective(accountsBaseInfo1);
        accountsRoleService.compAccountsAdvInfo(accountsBaseInfoMapper.selectByPrimaryKey(id));
        return accountsAllService.putAllAccountsAllInfo();
    }
//    修改enabled=1的工资信息
@Transactional
    public Integer putEnabledInfo(AccountsBaseInfo accountsBaseInfo) {
        AccountsBaseInfo accountsBaseInfo1=accountsBaseInfoMapper.selectByPrimaryKey(accountsBaseInfo.getId());
        accountsBaseInfo1.setBasicSalary(accountsBaseInfo.getBasicSalary());
        accountsBaseInfo1.setLunchSalary(accountsBaseInfo.getLunchSalary());
        accountsBaseInfo1.setTrafficSalary(accountsBaseInfo.getTrafficSalary());
        accountsBaseInfo1.setBonus(accountsBaseInfo.getBonus());
        accountsBaseInfo1.setPensionBase(accountsBaseInfo.getPensionBase());
        accountsBaseInfo1.setPensionPer(accountsBaseInfo.getPensionPer());
        accountsBaseInfo1.setMedicalBase(accountsBaseInfo.getMedicalBase());
        accountsBaseInfo1.setMedicalPer(accountsBaseInfo.getMedicalPer());
        accountsBaseInfo1.setAccumulationFundBase(accountsBaseInfo.getAccumulationFundBase());
        accountsBaseInfo1.setAccumulationFundPer(accountsBaseInfo.getAccumulationFundPer());
        accountsBaseInfo1.setAccumulationFundPer(accountsBaseInfo.getAccumulationFundPer());
        accountsBaseInfo1.setAccumulationFundPer(accountsBaseInfo.getAccumulationFundPer());
        accountsBaseInfo1.setAccumulationFundPer(accountsBaseInfo.getAccumulationFundPer());
        accountsBaseInfo1.setTimes(accountsBaseInfo.getTimes());
        accountsBaseInfo1.setOthers(accountsBaseInfo.getOthers());
        accountsBaseInfo1.setPrepaid(accountsBaseInfo.getPrepaid());
        accountsBaseInfo1.setDeduct(accountsBaseInfo.getDeduct());
        accountsBaseInfo1.setRecordPer(accountsBaseInfo.getRecordPer());
        accountsBaseInfo1.setRealSalary(accountsBaseInfo.getRealSalary());
        accountsBaseInfoMapper.updateByPrimaryKeySelective(accountsBaseInfo1);
        accountsRoleService.compAccountsAdvInfo(accountsBaseInfo);
        return accountsAllService.putAllAccountsAllInfo();
    }

//  创建新生成的员工工资信息
    @Transactional
    public Integer createMethod(AccountsBaseInfo accountsBaseInfo,Date[] beginDateScope,Integer integer){
        accountsBaseInfo.setEid(accountsBaseInfo.getEmployee().getId());
        accountsBaseInfo.setDname(accountsBaseInfo.getDepartment().getName());
        accountsBaseInfo.setPname(accountsBaseInfo.getPosition().getName());
        accountsBaseInfo.setJname(accountsBaseInfo.getJobLevel().getName());
        accountsBaseInfo.setBasicSalary(accountsBaseInfo.getSalary().getBasicSalary());
        accountsBaseInfo.setBonus(accountsBaseInfo.getSalary().getBonus());
        accountsBaseInfo.setLunchSalary(accountsBaseInfo.getSalary().getLunchSalary());
        accountsBaseInfo.setTrafficSalary(accountsBaseInfo.getSalary().getTrafficSalary());
        accountsBaseInfo.setPensionBase(accountsBaseInfo.getSalary().getPensionBase());
        accountsBaseInfo.setPensionPer(accountsBaseInfo.getSalary().getPensionPer());
        accountsBaseInfo.setMedicalBase(accountsBaseInfo.getSalary().getMedicalBase());
        accountsBaseInfo.setMedicalPer(accountsBaseInfo.getSalary().getMedicalPer());
        accountsBaseInfo.setAccumulationFundBase(accountsBaseInfo.getSalary().getAccumulationFundBase());
        accountsBaseInfo.setAccumulationFundPer(accountsBaseInfo.getSalary().getAccumulationFundPer());
        accountsBaseInfo.setSname(accountsBaseInfo.getSalary().getName());
        accountsBaseInfo.setTimes(accountsBaseInfo.getArgsRole().getTimes());
        accountsBaseInfo.setOthers(accountsBaseInfo.getArgsRole().getOthers());
        accountsBaseInfo.setDeduct(accountsBaseInfo.getArgsRole().getDeduct());
        accountsBaseInfo.setRecordPer(accountsBaseInfo.getArgsRole().getRecordPer());
        accountsBaseInfo.setWorkAge(accountsBaseInfo.getWork_age().getWorkAge());
        accountsBaseInfo.setStartDate(beginDateScope[0]);
        accountsBaseInfo.setPrepaid(0f);
        accountsBaseInfo.setEndDate(beginDateScope[1]);
        accountsBaseInfo.setCreateDate(new Date());
        accountsBaseInfo.setEnabled(true);
        accountsBaseInfo.setPrepaid(0f);
        accountsBaseInfo.setRealSalary(0f);
        accountsBaseInfoMapper.insertSelective(accountsBaseInfo);
        Integer abiId=accountsBaseInfo.getId();
        float workAge=work_ageMapper.selectByEidKey(accountsBaseInfoMapper.selectByPrimaryKey(abiId).getEid()).getWorkAge();
        accountsBaseInfo.setWorkAge(workAge);
        accountsBaseInfoMapper.updateByPrimaryKeySelective(accountsBaseInfo);
        AccountsAdvInfo accountsAdvInfo=new AccountsAdvInfo();
        accountsAdvInfo.setAllSalary(0f);
        accountsAdvInfo.setCount(0f);
        accountsAdvInfo.setCreditCom(0f);
        accountsAdvInfo.setCreditEmp(0f);
        accountsAdvInfo.setRecordSalary(0f);
        accountsAdvInfo.setRecordScore(0);
        accountsAdvInfoMapper.insertSelective(accountsAdvInfo);
        Integer adiId=accountsAdvInfo.getId();
        AccountsRole accountsRole = new AccountsRole();
        accountsRole.setAccountsbaseinfoid(abiId);
        accountsRole.setAccountsadvinfoid(adiId);
        accountsRole.setAccoutsallid(integer);
        accountsRoleMapper.insertSelective(accountsRole);
        accountsRoleService.compAccountsAdvInfo(accountsBaseInfo);
        return accountsAllService.putAllAccountsAllInfo();
    }
//  月末处理工资信息
    @Transactional
    public Integer putdisabledInfo(DisabledData disabledData) {
        Integer[] ids= disabledData.getIds();
        String name=disabledData.getName();
        AccountsBaseInfo accountsBaseInfo=new AccountsBaseInfo();
        List<AccountsBaseInfo> list1=new ArrayList<AccountsBaseInfo>();
        for(int i=0;i<ids.length;i++){
            accountsBaseInfo=accountsBaseInfoMapper.selectByPrimaryKey(ids[i]);
            accountsBaseInfo.setCashierName(name);
            accountsBaseInfo.setSendDate(new Date());
            accountsBaseInfo.setEnabled(false);
            accountsBaseInfoMapper.updateByPrimaryKeySelective(accountsBaseInfo);
            accountsRoleService.compAccountsAdvInfo(accountsBaseInfo);
            accountsAllService.putAllAccountsAllInfo();
            AccountsBaseInfo accountsBaseInfo1=new AccountsBaseInfo();
            accountsBaseInfo1.setId(ids[i]);
            //            查找并添加该员工信息
            list1.add(accountsBaseInfoMapper.getAllInfoByPage(null,
                    null, accountsBaseInfo1, null, null,
                    null, null).get(0));
        }
        for(int i=0;i<ids.length;i++){
            //        注意发送的对象必须实现序列化接口，且重写的toString（）方法正确或尚未实现
            rabbitTemplate.convertAndSend("Servicify-topic","salary",list1.get(i));
        }
        return 1;
    }


//  统计数据所需要员工阶段分数信息。
    public ScoreStatic getInfosByTimes(List<MonthDate> listDate) {
        List<Integer> lessThan60=new ArrayList<Integer>();
        List<Integer> lessThan80=new ArrayList<Integer>();
        List<Integer> lessThan90=new ArrayList<Integer>();
        List<Integer> lessThan100=new ArrayList<Integer>();
        Integer empLessThan60=new Integer(0);
        Integer empLessThan80=new Integer(0);
        Integer empLessThan90=new Integer(0);
        Integer empLessThan100=new Integer(0);
//        统计最大人数
        Integer count=new Integer(0);
        for(int i=0;i<listDate.size();i++){
            List<AccountsBaseInfo> list = accountsBaseInfoMapper.
                    getInfosByMonths(listDate.get(i).getStartDate(), listDate.get(i).getEndDate());
            if(count<list.size()){
                count=list.size();
            }
            for(int j=0;j<list.size();j++){
                float score=list.get(j).getAccountsAdvInfo().getRecordScore();
                    if(score>=90&&score<=100){
                        empLessThan100++;
                    }
                    if(score>=80&&score<90){
                        empLessThan90++;
                    }
                    if(score>=60&&score<80){
                        empLessThan80++;
                    }
                    if(score>=0&&score<60) {
                        empLessThan60++;
                    }
                score=0f;
            }
            lessThan60.add(empLessThan60);
            lessThan80.add(empLessThan80);
            lessThan90.add(empLessThan90);
            lessThan100.add(empLessThan100);
            empLessThan60=0;
            empLessThan80=0;
            empLessThan90=0;
            empLessThan100=0;
        }
        ScoreStatic scoreStatic = new ScoreStatic();
        scoreStatic.setLessThan60(lessThan60);
        scoreStatic.setLessThan80(lessThan80);
        scoreStatic.setLessThan90(lessThan90);
        scoreStatic.setLessThan100(lessThan100);
        scoreStatic.setCount(count);
        return scoreStatic;
    }
    //  统计数据所需要员工阶段分数信息。
    public SalaryStatic getAllInfosByTimes(List<MonthDate> listDate) {
        List<Integer> lessThan4000list = new ArrayList<Integer>();
        List<Integer> lessThan6000list = new ArrayList<Integer>();
        List<Integer> lessThan8000list = new ArrayList<Integer>();
        List<Integer> lessThan10000list = new ArrayList<Integer>();
        List<Integer> moreThan10000list = new ArrayList<Integer>();
        List<Float> creditEmpslist = new ArrayList<Float>();
        List<Float> creditComslist = new ArrayList<Float>();
        List<Float> realSalaryslist = new ArrayList<Float>();
        List<Float> countslist = new ArrayList<Float>();
        List<Float> avgSalaryslist = new ArrayList<Float>();
        List<String> Monthslist = new ArrayList<String>();
        Integer lessThan4000 = new Integer(0);
        Integer lessThan6000 = new Integer(0);
        Integer lessThan8000 = new Integer(0);
        Integer lessThan10000 = new Integer(0);
        Integer moreThan10000 = new Integer(0);
        Float creditEmp = new Float(0f);
        Float creditCom = new Float(0f);
        Float realSalary = new Float(0f);
        Float count = new Float(0f);
        for(int i=0;i<listDate.size();i++){
//            if判断的作用是避免没有对应的月份记录，使得查询出错
            if(accountsBaseInfoMapper.getAllInfosByMonths(listDate.get(i).getStartDate(),listDate.get(i).getEndDate()).size()>0) {
                List<AccountsBaseInfo> list = accountsBaseInfoMapper.getAllInfosByMonths(listDate.get(i).getStartDate(), listDate.get(i).getEndDate());
                for (int j = 0; j < list.size(); j++) {
                    float realSal = list.get(j).getRealSalary();
                    if (realSal >= 10000) {
                        moreThan10000++;
                    } else if (realSal >= 8000 && realSal < 10000) {
                        lessThan10000++;
                    } else if (realSal >= 6000 && realSal < 8000) {
                        lessThan8000++;
                    } else if (realSal >= 4000 && realSal < 6000) {
                        lessThan6000++;
                    } else {
                        lessThan4000++;
                    }
                }
                creditEmp += list.get(0).getAccountsAll().getAllCreditEmp();
                creditCom += list.get(0).getAccountsAll().getAllCreditCom();
                realSalary += list.get(0).getAccountsAll().getAllRealSalary();
                count += list.get(0).getAccountsAll().getAllCount();
                lessThan4000list.add(lessThan4000);
                lessThan6000list.add(lessThan6000);
                lessThan8000list.add(lessThan8000);
                lessThan10000list.add(lessThan10000);
                moreThan10000list.add(moreThan10000);
                creditEmpslist.add(creditEmp);
                creditComslist.add(creditCom);
                realSalaryslist.add(realSalary);
                countslist.add(count);
                avgSalaryslist.add(realSalary / list.size());
                Monthslist.add(listDate.get(i).getYearAndMonth());
//              重置变量
                lessThan4000=0;
                lessThan6000=0;
                lessThan8000=0;
                lessThan10000=0;
                moreThan10000=0;
                creditEmp=0f;
                creditCom=0f;
                realSalary=0f;
                count=0f;
            }else{
                continue;
            }
        }
        SalaryStatic salaryStatic = new SalaryStatic();
        salaryStatic.setLessThan4000(lessThan4000list);
        salaryStatic.setLessThan6000(lessThan6000list);
        salaryStatic.setLessThan8000(lessThan8000list);
        salaryStatic.setLessThan10000(lessThan10000list);
        salaryStatic.setMoreThan10000(moreThan10000list);
        salaryStatic.setCreditEmps(creditEmpslist);
        salaryStatic.setCreditCom(creditComslist);
        salaryStatic.setRealSalary(realSalaryslist);
        salaryStatic.setCounts(countslist);
        salaryStatic.setAvgSalary(avgSalaryslist);
        salaryStatic.setMonths(Monthslist);
        return salaryStatic;
    }
//  批量更改错误的员工工资表开始与结束时间
    public Integer putEmpSalaryInfos(Integer[] ids, Date date) throws Exception{
//        修改创建的错误时间段的工资表信息需要满足两个条件
//        1.选则的记录集合只能对应一条员工工资表统计记录
//        2.选则的修改时间段必须与员工工资表统计信息记录中除
//        获取该月的更改日期
        List<MonthDate> dateScope= GetLastMonths.getLastMonths(date,1);
        Date[] beginDateScope=new Date[2];
        beginDateScope[0]=dateScope.get(0).getStartDate();
        beginDateScope[1]=dateScope.get(0).getEndDate();
//      创建返回类型信息指示器
        Integer state=0;
//        查询该修改时间段内是否存在大于0条的工资表统计信息，若存在，直接返回更改时间已存在
        List<AccountsAll> list=accountsAllMapper.getAccountsAllInfoByBeginDate(beginDateScope);
        if(list.size()>0){
            state=1;   //由于更改的时间段对应的记录已存在，返回更改时间段已存在，请重新选则的错误消息。
            return state;
        }
//        由于创建员工工资表信息时，会检测创建时间的唯一性，故当只有一条时，即尚未与其他时间段创建的工资表信息相重复，可进行修改
        if(list.size()==0){
//            查询所有选中记录的时间段是否一致
            AccountsRole accountsRole=accountsRoleMapper.selectByBaseId(ids[0]);
            for(int i=1;i<ids.length;i++){
                AccountsRole accountsRole1=accountsRoleMapper.selectByBaseId(ids[i]);
                if(accountsRole1.getAccoutsallid()!=accountsRole.getAccoutsallid()){
                    state=2;
                    return state;
                }
            }
            //        查询任意一条基础信息的日期范围对应的所有工资表基础信息集合及对应的AccountRole记录
            AccountsAll accountsAll=accountsAllMapper.selectByPrimaryKey(accountsRole.getAccoutsallid());
            Date[] dates=new Date[2];
            dates[0]=accountsAll.getStartDate();
            dates[1]=accountsAll.getEndDate();
//            获取所有的在该时间段的基础信息集合
            List<AccountsBaseInfo> list1=accountsBaseInfoMapper.getAllInfoByPage(null,null
                    ,null,null,null,null, dates);
//            修改统计信息的开始与结束日期
            accountsAll.setStartDate(beginDateScope[0]);
            accountsAll.setEndDate(beginDateScope[1]);
            accountsAllMapper.updateByPrimaryKeySelective(accountsAll);
//            依次修改基础信息的开始与结束日期
            for(int i=0;i<list1.size();i++){
                list1.get(i).setStartDate(beginDateScope[0]);
                list1.get(i).setEndDate(beginDateScope[1]);
                accountsBaseInfoMapper.updateByPrimaryKeySelective(list1.get(i));
            }
            state=3;
            return state;
        }
        return state;
    }

    public Integer resendSalaryInfo(Integer id) {
        AccountsBaseInfo accountsBaseInfo=new AccountsBaseInfo();
        accountsBaseInfo.setId(id);
        rabbitTemplate.convertAndSend("Servicify-topic","salary",accountsBaseInfoMapper.getAllInfoByPage(
                null, null, accountsBaseInfo, null, null,
                null, null).get(0));
        return 1;
    }

}
