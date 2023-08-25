package com.sparta.i_mu.controller;

import com.sparta.i_mu.dto.requestDto.EmailPostDto;
import com.sparta.i_mu.dto.responseDto.EmailResponseDto;
import com.sparta.i_mu.entity.EmailMessage;
import com.sparta.i_mu.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Email", description = "이메일 인증 API Document")

public class EmailController {

    private final EmailService emailService;

    // 임시 비밀번호 발급
//    @PostMapping("/password")
//    public ResponseEntity<?> sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to(emailPostDto.getEmail())
//                .subject("[P.PLE] 임시 비밀번호 발급")
//                .build();
//
//        emailService.sendMail(emailMessage,emailPostDto.getEmail(), "password");
//
//        return ResponseEntity.ok().build();
//    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    @Operation(summary = "이메일 인증 번호 전송", description ="이메일 인증 번호 전송")
    public ResponseEntity<?> sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
        System.out.println(emailPostDto.getEmail());
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[P.PLE] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, emailPostDto.getEmail(), "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok("인증 메일을 발송했습니다");
    }

    @PostMapping("/check")
    @Operation(summary = "이메일 인증 번호 검증", description ="이메일 인증번호 검증")
    public ResponseEntity<?> Checkcode(@RequestBody EmailPostDto emailPostDto){
        Boolean check = emailService.verifyEmailCode(emailPostDto.getEmail(),emailPostDto.getCode());
        return ResponseEntity.ok(check);
    }
}

