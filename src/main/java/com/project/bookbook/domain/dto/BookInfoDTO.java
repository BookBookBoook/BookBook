package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInfoDTO {

    private String author;
    private String bookImg;
    private String bookName;
    private String isbn;
    private LocalDateTime searchDate;
    
    private int lastViewed;
    private boolean isViewedToday;

    public void calculateLastViewed() {
        if (this.searchDate != null) {
            LocalDateTime now = LocalDateTime.now();
            long daysBetween = ChronoUnit.DAYS.between(this.searchDate, now);
            
            if (daysBetween == 0) {
                // 오늘 본 경우
                this.isViewedToday = true;
                this.lastViewed = (int) ChronoUnit.HOURS.between(this.searchDate, now);
            } else {
                // 오늘이 아닌 경우
                this.isViewedToday = false;
                this.lastViewed = (int) daysBetween;
            }
        }
    }
    
    public String getLastViewedString() {
        if (this.searchDate == null) {
            return "날짜 정보 없음";
        }
        
        calculateLastViewed();  // 메서드 호출을 여기서 합니다.

        if (this.isViewedToday) {
            if (this.lastViewed == 0) {
                return "방금 전";
            } else {
                return this.lastViewed + "시간 전";
            }
        } else {
            if (this.lastViewed == 0) {
                return "오늘";
            } else if (this.lastViewed == 1) {
                return "어제";
            } else {
                return this.lastViewed + "일 전";
            }
        }
    }
}
