package com.project.bookbook.domain.dto.mypage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryApiResponseDTO {
	@JsonProperty("response")
    private UserRecommendDTO response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserRecommendDTO {
        @JsonProperty("resultNum")
        private int resultNum;

        @JsonProperty("docs")
        private List<Docs> docs;

        @Data
        public static class Docs {
            @JsonProperty("doc")
            private Doc doc;
        }

        @Data
        public static class Doc {
            private int no;
            private String ranking;
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
            
            @JsonProperty("class_nm")
            private String classNm;

            @JsonProperty("loan_count")
            private String loanCount;

            @JsonProperty("bookImageURL")
            private String bookImageUrl;

            @JsonProperty("bookDtlUrl")
            private String bookDtlUrl;
        }
    }
}
