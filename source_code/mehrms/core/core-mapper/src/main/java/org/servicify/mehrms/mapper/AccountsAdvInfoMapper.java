package org.servicify.mehrms.mapper;

import org.servicify.mehrms.model.AccountsAdvInfo;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface AccountsAdvInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountsAdvInfo record);

    int insertSelective(AccountsAdvInfo record);

    AccountsAdvInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountsAdvInfo record);

    int updateByPrimaryKey(AccountsAdvInfo record);
}