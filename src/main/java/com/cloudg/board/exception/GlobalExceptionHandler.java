package com.cloudg.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException e, Model model) {
        System.out.println("========== IllegalStateException ==========");
        log.warn("IllegalStateException 발생", e);
        model.addAttribute("errorMessage", e.getMessage());
        return "error/403";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e, Model model) {
        System.out.println("========== IllegalArgumentException ==========");
        log.warn("IllegalArgumentException 발생", e);
        model.addAttribute("errorMessage", e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        System.out.println("========== handleException ==========");
        log.error("예상치 못한 예외 발생", e);
        model.addAttribute("errorMessage", "서버 오류가 발생했습니다.");
        return "error/500";
    }


}
