package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dao.DeclarationDao;
import com.proj.togedutch.entity.Declaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeclarationService {
    @Autowired
    DeclarationDao declarationDao;

    public Declaration createDeclaration(Declaration declaration, int chatRoomIdx) throws BaseException {
        try {
            int declarationIdx = declarationDao.createDeclaration(declaration, chatRoomIdx);
            Declaration newDeclaration = getDeclarationById(declarationIdx);
            return newDeclaration;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public Declaration getDeclarationById(int declarationIdx) throws  BaseException {
        try {
            Declaration findDeclaration = declarationDao.getDeclarationById(declarationIdx);
            return findDeclaration;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<Declaration> getAllDeclarations() throws BaseException {
        try{
            List<Declaration> getAllDeclarations = declarationDao.getAllDeclarations();
            return getAllDeclarations;
        } catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<Declaration> getDeclarationByChatRoomId(int chatRoomIdx) throws BaseException {
        try{
            List<Declaration> getDeclaration = declarationDao.getDeclarationByChatRoomId(chatRoomIdx);
            return getDeclaration;
        } catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
