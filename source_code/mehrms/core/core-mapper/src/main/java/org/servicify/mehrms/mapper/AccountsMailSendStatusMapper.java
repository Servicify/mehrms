package org.servicify.mehrms.mapper;

import org.servicify.mehrms.model.AccountsMailSendStatus;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface AccountsMailSendStatusMapper {
    int deleteByPrimaryKey(Integer accountsmailid);

    int insert(AccountsMailSendStatus record);

    int insertSelective(AccountsMailSendStatus record);

    AccountsMailSendStatus selectByPrimaryKey(Integer accountsmailid);

    int updateByPrimaryKeySelective(AccountsMailSendStatus record);

    int updateByPrimaryKey(AccountsMailSendStatus record);

    AccountsMailSendStatus selectByAccountsBaseInfoIdKey(Integer id);
}