package com.proj.togedutch.entity;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Message {

    /**
     * MessageType 입니다.
     * INVITE(초대), ENTER(입장), TALK(대화), FILE(파일송신)
     */
    public enum MessageType{
        ENTER, TALK, FILE
    }

    private MessageType type;
    private int chatRoom_id;
    private int user_id;
    private String content;
    private String status;

    /**
     * MessageRequest 객체를 Message 객체로 변환합니다.
     *
     * @return message
     */
//    public Chat toEntity(){
//        return Chat.builder().
//    }

}
