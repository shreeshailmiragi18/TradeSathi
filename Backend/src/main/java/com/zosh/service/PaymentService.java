package com.zosh.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.zosh.domain.PaymentMethod;
import com.zosh.model.PaymentOrder;
import com.zosh.model.Appuser;
import com.zosh.response.PaymentResponse;

public interface PaymentService {

        PaymentOrder createOrder(Appuser appuser, Long amount, PaymentMethod paymentMethod);

        PaymentOrder getPaymentOrderById(Long id) throws Exception;

        Boolean ProccedPaymentOrder(PaymentOrder paymentOrder,
                        String paymentId) throws RazorpayException;

        PaymentResponse createRazorpayPaymentLink(Appuser appuser,
                        Long Amount,
                        Long orderId) throws RazorpayException;

        PaymentResponse createStripePaymentLink(Appuser appuser, Long Amount,
                        Long orderId) throws StripeException;
}
