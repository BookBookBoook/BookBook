package com.project.bookbook.domain.dto.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserRecommendDTO {
	@JsonProperty("resultNum")
    private int resultNum;

    @JsonProperty("docs")
    private Docs docs;


    public static class Docs {
        @JsonProperty("doc")
        private List<Doc> doc;

    }

    public static class Doc {
        private int no;
        private int ranking;
        private String bookname;
        private String authors;
        private String publisher;
        
        @JsonProperty("publication_year")
        private String publicationYear;
        
        private String isbn13;
        
        @JsonProperty("addition_symbol")
        private String additionSymbol;
        
        private String vol;
        
        @JsonProperty("class_no")
        private String classNo;
        
        @JsonProperty("loan_count")
        private int loanCount;
        
        @JsonProperty("bookImageURL")
        private String bookImageUrl;

    }
}
