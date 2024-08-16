package com.project.bookbook.domain.dto;


import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;


@XmlRootElement(name = "rss")
public class BookSearchResponse {
    @Getter(onMethod_ = @XmlElement(name = "channel"))
    @Setter
    private Channel channel;

    @Getter
    @Setter
    public static class Channel {
        @Getter(onMethod_ = @XmlElement(name = "item"))
        @Setter
        private List<Item> items;
    }

    @Getter(onMethod_ = @XmlElement)
    @Setter
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String author;
        private String discount;
        private String publisher;
        private String pubdate;
        private String isbn;
        private String description;
    }
}


