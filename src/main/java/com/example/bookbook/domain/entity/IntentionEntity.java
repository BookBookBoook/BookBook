package com.example.bookbook.domain.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="intention")
public class IntentionEntity {

	@Id
    private int intentionNo;
	
	@Column(nullable = false)
    private String intention;

	@Column(nullable = false)
    private String answer;

    @OneToMany(mappedBy = "intention") //Keyword 엔티티의 category 필드
    private List<KeywordEntity> keywords;
}
