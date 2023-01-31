package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.Declaration;
import com.proj.togedutch.service.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRoom")
public class DeclarationController {
    @Autowired
    DeclarationService declarationService;

    // 신고 생성 (채팅방 안)
    @ResponseBody
    @PostMapping(value = "/{chatRoomIdx}/declaration")
    public BaseResponse<Declaration> createDeclaration(@PathVariable("chatRoomIdx") int chatRoomIdx, @RequestBody Declaration declaration) {
        try {
            Declaration de = declarationService.createDeclaration(declaration, chatRoomIdx);
            return new BaseResponse<>(de);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 신고 전체 조회
    @GetMapping("/declaration")
    public BaseResponse<List<Declaration>> getAllDeclarations() throws BaseException {
        try{
            List<Declaration> declarations = declarationService.getAllDeclarations();
            return new BaseResponse<>(declarations);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{chatRoomIdx}/declaration")
    public BaseResponse<List<Declaration>> getDeclarationByChatRoomId(@PathVariable("chatRoomIdx") int chatRoomIdx) throws BaseException {
        try{
            List<Declaration> declarations = declarationService.getDeclarationByChatRoomId(chatRoomIdx);
            return new BaseResponse<>(declarations);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
