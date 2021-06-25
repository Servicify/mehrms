package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.AccountsBaseInfo;
import org.servicify.mehrms.model.AccountsRole;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface AccountsRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountsRole record);

    int insertSelective(AccountsRole record);

    AccountsRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountsRole record);

    int updateByPrimaryKey(AccountsRole record);

    List<AccountsBaseInfo> selectAllEnabledInfo();

//    添加一个通过AccountsBaseInfoId查询记录
    AccountsRole selectByBaseId(Integer id);
//    添加一个通过AccountsAllId查询记录集合
    List<AccountsRole> selectByAccountsAllId(@Param("id") Integer id);
//    添加
}