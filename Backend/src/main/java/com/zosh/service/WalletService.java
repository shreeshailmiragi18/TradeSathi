package com.zosh.service;

import com.zosh.exception.WalletException;
import com.zosh.model.Order;
import com.zosh.model.Appuser;
import com.zosh.model.Wallet;

import java.math.BigDecimal;

public interface WalletService {

    Wallet getUserWallet(Appuser appuser) throws WalletException;

    public Wallet addBalanceToWallet(Wallet wallet, Long money) throws WalletException;

    public Wallet findWalletById(Long id) throws WalletException;

    public Wallet walletToWalletTransfer(Appuser sender, Wallet receiverWallet, Long amount) throws WalletException;

    public Wallet payOrderPayment(Order order, Appuser appuser) throws WalletException;

}
