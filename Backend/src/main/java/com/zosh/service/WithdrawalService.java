package com.zosh.service;

import com.zosh.model.Appuser;
import com.zosh.model.Withdrawal;
import lombok.With;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, Appuser appuser);

    Withdrawal procedWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(Appuser appuser);

    List<Withdrawal> getAllWithdrawalRequest();
}
