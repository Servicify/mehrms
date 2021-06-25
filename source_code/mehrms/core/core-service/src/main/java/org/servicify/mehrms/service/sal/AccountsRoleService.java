package org.servicify.mehrms.service.sal;

import org.apache.commons.math3.analysis.function.Acos;
import org.servicify.mehrms.mapper.AccountsAdvInfoMapper;
import org.servicify.mehrms.mapper.AccountsBaseInfoMapper;
import org.servicify.mehrms.mapper.AccountsRoleMapper;
import org.servicify.mehrms.model.AccountsAdvInfo;
import org.servicify.mehrms.model.AccountsBaseInfo;
import org.servicify.mehrms.model.RuleRole;
import org.servicify.mehrms.service.per.RuleRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class AccountsRoleService {
    @Autowired
    AccountsRoleMapper accountsRoleMapper;
    @Autowired
    RuleRoleService ruleRoleService;
    @Autowired
    AccountsAdvInfoMapper   accountsAdvInfoMapper;
    @Autowired
    AccountsBaseInfoMapper accountsBaseInfoMapper;
//    计算单条员工工资信息的数据
    public Integer compAccountsAdvInfo(AccountsBaseInfo accountsBaseInfo){
        float count=0f;
//        计算基础的账套资金，由于该表中的所有参数都会被修改，需重新计算
        count+=accountsBaseInfo.getBasicSalary();
        count+=accountsBaseInfo.getTrafficSalary();
        count+=accountsBaseInfo.getLunchSalary();
        count+=accountsBaseInfo.getBonus();
        count+=accountsBaseInfo.getPensionBase()*accountsBaseInfo.getPensionPer();
        count+=accountsBaseInfo.getMedicalBase()*accountsBaseInfo.getMedicalPer();
        count+=accountsBaseInfo.getAccumulationFundBase()*accountsBaseInfo.getAccumulationFundPer();
//        获取账套工资小计
        float allSalary=count;
//        计算绩效分数
        Date[] beginDateScope=new Date[2];
        beginDateScope[0]=accountsBaseInfo.getStartDate();
        beginDateScope[1]=accountsBaseInfo.getEndDate();
        RuleRole ruleRole=new RuleRole();
        ruleRole.setEid(accountsBaseInfo.getEid());
//        获取绩效总分
        Integer recordScore=ruleRoleService.getAllScoreByEid(ruleRole,beginDateScope);
//        计算绩效金
        float recordSalary=accountsBaseInfo.getRecordPer()*recordScore;
//        计算应发工资
        count*=accountsBaseInfo.getTimes();
        count+=accountsBaseInfo.getOthers();
        count-=accountsBaseInfo.getDeduct();
        count+=accountsBaseInfo.getPrepaid();
        count+=recordSalary;
//        获取实发工资
        float count1= count;
        if(accountsBaseInfo.getRealSalary()==0||accountsBaseInfo.getRealSalary()==null){
            accountsBaseInfo.setRealSalary(count1);
            accountsBaseInfoMapper.updateByPrimaryKeySelective(accountsBaseInfo);
        }
//        如果实际发放工资大于应发工资，则员工拖欠公司工资，
//        反之，公司拖欠员工工资
        float creditEmp=0f;
        float creditCom=0f;
        if(count-accountsBaseInfo.getRealSalary()>0)
        {
//        赊欠员工
             creditEmp=count-accountsBaseInfo.getRealSalary();
//        赊欠公司
             creditCom=accountsBaseInfo.getPrepaid();
        }else{
//        赊欠员工
             creditEmp=0;
//        赊欠公司
             creditCom=accountsBaseInfo.getRealSalary()-count+accountsBaseInfo.getPrepaid();
        }
//        获取对应的AdvInfo中的id
        AccountsAdvInfo accountsAdvInfo= accountsAdvInfoMapper.selectByPrimaryKey(accountsRoleMapper.selectByBaseId(accountsBaseInfo.getId()).getAccountsadvinfoid());
//        插入数据
        accountsAdvInfo.setAllSalary(allSalary);
        accountsAdvInfo.setRecordScore(recordScore);
        accountsAdvInfo.setCount(count1);
        accountsAdvInfo.setCreditEmp(creditEmp);
        accountsAdvInfo.setCreditCom(creditCom);
        accountsAdvInfo.setRecordSalary(recordSalary);
        return accountsAdvInfoMapper.updateByPrimaryKeySelective(accountsAdvInfo);
    }
}






















