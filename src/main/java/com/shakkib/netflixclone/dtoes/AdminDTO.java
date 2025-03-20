package com.shakkib.netflixclone.dtoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
public class AdminDTO {

    private Long id;
    private String email;
    private String name;
}
