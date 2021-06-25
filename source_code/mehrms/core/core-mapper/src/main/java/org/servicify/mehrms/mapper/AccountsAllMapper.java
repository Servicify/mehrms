package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.AccountsAll;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface AccountsAllMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountsAll record);

    int insertSelective(AccountsAll record);

    AccountsAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountsAll record);

    int updateByPrimaryKey(AccountsAll record);
//  查询所有的统计记录
    List<AccountsAll> getAllAccountsAllInfo();
//  用于检查是否存在对应日期范围内的统计记录
    List<AccountsAll> getAccountsAllInfoByBeginDate(@Param("beginDateScope") Date[] beginDateScope);
//  用于查询统计信息进行统计
    AccountsAll getAccountsInfoByDate(Date startDate,Date endDate);

}