package com.proj.togedutch.service;

import com.proj.togedutch.dao.ApplicationDao;
import com.proj.togedutch.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

public class ApplicationService {
    final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationDao applicationDao;


    //공고신청
   /* public Post applyPost(Post post) throw BaseException{
        try{

        }
    }
`*/


}
