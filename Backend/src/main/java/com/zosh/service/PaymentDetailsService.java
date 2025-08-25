package com.zosh.service;

import com.zosh.model.PaymentDetails;
import com.zosh.model.Appuser;
import jakarta.persistence.OneToOne;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
            String accountHolderName,
            String ifsc,
            String bankName,
            Appuser appuser);

    public PaymentDetails getUsersPaymentDetails(Appuser appuser);

}
