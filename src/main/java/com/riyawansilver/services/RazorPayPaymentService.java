//package com.riyawansilver.services;
//
//import com.razorpay.PaymentLink;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RazorPayPaymentService implements PaymentService{
//
//    private OrderService orderService;
//
//    @Autowired
//    public RazorPayPaymentService(OrderService orderService){
//        this.orderService=orderService;
//    }
//
//
//    public String createPaymentLink(long orderId) {
//        try {
//            JSONObject paymentLinkRequest = new JSONObject();
//            paymentLinkRequest.put("amount",1000); // 10.01 => 1001 // 99.99 => 9999 // 999 => 99900
//            paymentLinkRequest.put("currency","INR");
//            paymentLinkRequest.put("accept_partial",false);
////        paymentLinkRequest.put("first_min_partial_amount",100);
//            paymentLinkRequest.put("expire_by",System.currentTimeMillis() + 15 * 60 * 1000);
//            paymentLinkRequest.put("reference_id",orderId);
//            paymentLinkRequest.put("description","Payment for order no " + orderId);
//
//
//            // Order order = orderService.getOrderDetails(orderId)
//            // String cutomerName = order.getUser().getName()
//            // String contact = order.getUser().getMobile()
//
//            JSONObject customer = new JSONObject();
//            customer.put("name","+919996203771");
//            customer.put("contact","Naman Bhalla");
//            customer.put("email","naman@scaler.com");
//            paymentLinkRequest.put("customer",customer);
//
//            JSONObject notify = new JSONObject();
//            notify.put("sms",true);
//            notify.put("email",true);
//            paymentLinkRequest.put("reminder_enable",true);
//            JSONObject notes = new JSONObject();
//            notes.put("Order Items","1. iPhone 15 Pro Max");
//            paymentLinkRequest.put("notes",notes);
//            paymentLinkRequest.put("callback_url","https://naman.dev/");
//            paymentLinkRequest.put("callback_method","get");
//
//            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
//            return payment.get("short_url");
//        } catch (Exception e) {
//            // go to the DB
//            // get the old URL
//            return "";
//        }
//    }
//}
