package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.Declaration;
import com.proj.togedutch.service.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeclarationController {
    @Autowired
    DeclarationService declarationService;

    @ResponseBody
    @PostMapping(value = "/declaration/{chatRoomIdx}")
    public BaseResponse<Declaration> createDeclaration(@PathVariable("chatRoomIdx") int chatRoomIdx, @RequestBody Declaration declaration) {
        try {
            Declaration de = declarationService.createDeclaration(declaration, chatRoomIdx);
            return new BaseResponse<>(de);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
