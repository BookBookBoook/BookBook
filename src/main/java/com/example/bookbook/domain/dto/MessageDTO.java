package com.example.bookbook.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageDTO {
	
	private String from; //질문한사람
	private String today;
	private String time;
	
	private AnswerDTO answer;

	public MessageDTO today(String today) {
		this.today=today;
		return this;		
	}
	public MessageDTO answer(AnswerDTO answer) {
		this.answer=answer;
		return this;		
	}
	public MessageDTO from(String name) {
		from=name; //발신자
		if(from==null)from="고객";
		return this;
	}
}