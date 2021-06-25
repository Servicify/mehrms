package org.servicify.mehrms.service.sal;

import org.servicify.mehrms.mapper.AccountsAdvInfoMapper;
import org.servicify.mehrms.mapper.AccountsAllMapper;
import org.servicify.mehrms.mapper.AccountsBaseInfoMapper;
import org.servicify.mehrms.mapper.AccountsRoleMapper;
import org.servicify.mehrms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class AccountsAllService {
    @Autowired
    AccountsRoleMapper accountsRoleMapper;
    @Autowired
    AccountsBaseInfoMapper accountsBaseInfoMapper;
    @Autowired
    AccountsAdvInfoMapper accountsAdvInfoMapper;
    @Autowired
    AccountsAllMapper accountsAllMapper;
//    更新单条AccountAll对应的所有员工的信息的工资统计数据
    public Integer putAccountsAllInfo(Integer id){
        List<AccountsRole> list=accountsRoleMapper.selectByAccountsAllId(id);
        AccountsAll accountsAll=accountsAllMapper.selectByPrimaryKey(list.get(0).getAccoutsallid());
        accountsAll.setStartDate(accountsBaseInfoMapper.selectByPrimaryKey(list.get(0).getAccountsbaseinfoid()).getStartDate());
        accountsAll.setEndDate(accountsBaseInfoMapper.selectByPrimaryKey(list.get(0).getAccountsbaseinfoid()).getEndDate());
        accountsAll.setAllCount(0f);
        accountsAll.setAllRealSalary(0f);
        accountsAll.setAllCreditCom(0f);
        accountsAll.setAllCreditEmp(0f);
        accountsAll.setAvgRecordBonus(0f);
        float allCount=0f;
        float allRealSalary=0f;
        float allCreditCom=0f;
        float allCreditEmp=0f;
        float avgRecordBonus=0f;
        for(int i=0;i<list.size();i++){
            AccountsBaseInfo accountsBaseInfo=accountsBaseInfoMapper.selectByPrimaryKey(list.get(i).getAccountsbaseinfoid());
            AccountsAdvInfo accountsAdvInfo=accountsAdvInfoMapper.selectByPrimaryKey(list.get(i).getAccountsadvinfoid());
            allCount+=accountsAdvInfo.getCount();
            allRealSalary+=accountsBaseInfo.getRealSalary();
            allCreditCom+=accountsAdvInfo.getCreditEmp();
            allCreditEmp+=accountsAdvInfo.getCreditEmp();
            avgRecordBonus+=accountsAdvInfo.getRecordScore();
        }
        accountsAll.setAllCount(allCount);
        accountsAll.setAllRealSalary(allRealSalary);
        accountsAll.setAllCreditCom(allCreditCom);
        accountsAll.setAllCreditEmp(allCreditEmp);
        accountsAll.setAvgRecordBonus(avgRecordBonus/list.size());
        return accountsAllMapper.updateByPrimaryKeySelective(accountsAll);
    }
//  更新所有AccountsAll中的记录的id对应的员工工资记录信息数据的统计
    public Integer putAllAccountsAllInfo(){
        List<AccountsAll> list = accountsAllMapper.getAllAccountsAllInfo();
        for(int i=0;i<list.size();i++){
            putAccountsAllInfo(list.get(i).getId());
        }
        return 1;
    }
//  统计数据日期过滤接口
    public List<MonthDate> getDateList(List<MonthDate> list){
        List<MonthDate> list1=new ArrayList<MonthDate>();
        for(int i=0;i<list.size();i++){
            AccountsAll accountsAll=accountsAllMapper.
                getAccountsInfoByDate(list.get(i).getStartDate(), list.get(i).getEndDate());
            //过滤不存在的记录
            if(accountsAll==null){
                list1.add(list.get(i));
            }
        }
        for(int i=0;i<list1.size();i++){
            list.remove(list1.get(i));
        }
        return list;
    }
//  统计数据提取接口
    public List<AccountsAll> getInfoByDates(List<MonthDate> monthDateList){
        List<AccountsAll> list = new ArrayList<AccountsAll>();
        for(int i=0;i<monthDateList.size();i++){
            AccountsAll accountsAll=accountsAllMapper.
                    getAccountsInfoByDate(monthDateList.get(i).getStartDate(), monthDateList.get(i).getEndDate());
            list.add(accountsAll);
        }
        return list;
    }
}






















