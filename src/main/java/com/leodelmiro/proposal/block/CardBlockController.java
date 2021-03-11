package com.leodelmiro.proposal.block;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cards")
public class CardBlockController {

    @PostMapping("/{cardId}/block")
    public ResponseEntity<?> blockCard(@PathVariable Long cardId, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        System.out.println(userAgent);
        System.out.println(request.getRemoteAddr());

        return ResponseEntity.ok().build();
    }
}
