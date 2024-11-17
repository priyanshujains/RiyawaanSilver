//package com.riyawansilver.controllers;
//
//import com.riyawansilver.dtos.PaymentLinkDto;
//import com.riyawansilver.services.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//
//    @PostMapping()
//    public String createPaymentLink(@RequestBody PaymentLinkDto paymentLinkDto){
//        String link = paymentService.createPaymentLink(paymentLinkDto.getOrderId());
//
//        return link;
//
//    }
//}
