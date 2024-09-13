package com.project.bookbook.domain.dto.mypage;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryApiResponseByIsbnDTO {
    private Response response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private Request request;
        private int resultNum;
        private List<Doc> docs;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Request {
        private String isbn13;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Doc {
        private Book book;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Book {
        private int no;
        private String bookname;
        private String authors;
        private String publisher;
        private String publication_year;
        private String isbn13;
        private String addition_symbol;
        private String vol;
        private String class_no;
        private String class_nm;
        private String bookImageURL;
    }
}