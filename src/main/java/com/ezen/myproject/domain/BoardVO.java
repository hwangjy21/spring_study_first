package com.ezen.myproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
	
	

private int bno;
private String title;
private String content;
private String writer;
private String isDel;
private String registerData;
private int read_count;
}
