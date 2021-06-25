package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.AccountsBaseInfo;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface AccountsBaseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountsBaseInfo record);

    int insertSelective(AccountsBaseInfo record);

    AccountsBaseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountsBaseInfo record);

    int updateByPrimaryKey(AccountsBaseInfo record);

    List<AccountsBaseInfo> getCreateInfo();

    List<AccountsBaseInfo> getAllInfoByPage(@Param("page") Integer page, @Param("size") Integer size,
                                            @Param("abi") AccountsBaseInfo accountsBaseInfo,
                                            @Param("RealSalaryScope")float[] RealSalaryScope,
                                            @Param("CreditEmpScope")float[] CreditEmpScope,
                                            @Param("CreditComScope")float[] CreditComScope,
                                            @Param("beginDateScope") Date[] beginDateScope);


    Long getTotal(@Param("abi") AccountsBaseInfo accountsBaseInfo,
                  @Param("RealSalaryScope")float[] RealSalaryScope,
                  @Param("CreditEmpScope")float[] CreditEmpScope,
                  @Param("CreditComScope")float[] CreditComScope,
                  @Param("beginDateScope") Date[] beginDateScope);

    List<AccountsBaseInfo> getAllEnabledInfoByPage(@Param("page") Integer page, @Param("size") Integer size,
                                            @Param("abi") AccountsBaseInfo accountsBaseInfo,
                                            @Param("RealSalaryScope")float[] RealSalaryScope,
                                            @Param("CreditEmpScope")float[] CreditEmpScope,
                                            @Param("CreditComScope")float[] CreditComScope,
                                            @Param("beginDateScope") Date[] beginDateScope);

    Long getEnabledTotal(@Param("abi") AccountsBaseInfo accountsBaseInfo,
                        @Param("RealSalaryScope")float[] RealSalaryScope,
                        @Param("CreditEmpScope")float[] CreditEmpScope,
                        @Param("CreditComScope")float[] CreditComScope,
                        @Param("beginDateScope") Date[] beginDateScope);

//    统计数据信息，获取包括base、adv中的信息
    List<AccountsBaseInfo> getInfosByMonths(Date startDate,Date endDate);
//    获取包括base、adv、all信息中的数据
    List<AccountsBaseInfo> getAllInfosByMonths(Date startDate,Date endDate);

    AccountsBaseInfo getAccountsBaseInfoById(Integer id);

    List<AccountsBaseInfo> getAllInfos();
}