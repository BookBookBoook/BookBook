package com.project.bookbook.controller;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.project.bookbook.domain.dto.BookHistoryDTO;
import com.project.bookbook.domain.dto.BookSearchResponse.Item;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.mapper.BookHistoryMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.BookService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AopAspect {
	
	private final BookRepository bookRepository;
	private final BookHistoryMapper bookHistoryMapper;
	private final BookService bookService;

    @Pointcut("execution(* com.project.bookbook.controller.IndexController.detail(..)) || " +
            "execution(* com.project.bookbook.controller.IndexController.newdetail(..))")
    public void bookDetailPointcut() {}
    
    @Pointcut("execution(* com.project.bookbook.controller.IndexController.search(..))")
    public void searchPointcut() {}

    @Before("bookDetailPointcut() && args(isbn,..)")
    public void logIsbnAndUserId(JoinPoint joinPoint, String isbn) {
        Long userId = getCurrentUserId();
        if (userId != null) {
        	long bookNum = bookRepository.findBookNumByIsbn(isbn).orElseGet(() -> {
        	    findOrCreateBook(isbn);
        	    return bookRepository.findBookNumByIsbn(isbn).orElseThrow();
        	});
        	
        	BookHistoryDTO bookHistoryDTO = BookHistoryDTO.builder()
    				.userId(userId)
    				.bookNum(bookNum)
    				.build();
        	long searchId = bookHistoryMapper.findByUserIdAndBookNum(bookHistoryDTO);
        	
        	if(searchId == 0) {
        		bookHistoryMapper.save(bookHistoryDTO);
        	}else {
        		bookHistoryMapper.updateSearchDate(bookHistoryDTO);
        	}
			        	
        }
    }
    
    @Before("searchPointcut()")
    public void beforeSearch(JoinPoint joinPoint) {
        // 메서드의 인자들을 가져옴
        Object[] args = joinPoint.getArgs();
        Long userId = getCurrentUserId();
        
        if (userId != null) {
	        for (Object arg : args) {
	            if (arg instanceof String) {
	                String searchQuery = (String) arg;
	                System.out.println("Search query: " + searchQuery);
	                
	                Map<String, Object> params = new HashMap<>();
	                params.put("searchQuery", searchQuery);
	                params.put("userId", userId);
	                
	                long queryCount = bookHistoryMapper.findQueryAndUserId(params);
	                if(queryCount == 0) {
	                	bookHistoryMapper.saveQuery(params);
	                }else {
	                	bookHistoryMapper.updateQuery(params);
	                }
	            }
	        }
        }
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUserId();
            }
        }
        return null;
    }
    
    private BookEntity findOrCreateBook(String isbn) {
		return bookRepository.findByIsbn(isbn).orElseGet(() -> {
			System.out.println("책을 찾지 못함. 네이버 API에서 정보 가져오기 시도");
			Item naverBookItem = bookService.getBookByIsbn(isbn);
			if (naverBookItem == null) {
				throw new RuntimeException("Book not found in Naver API");
			}
			BookEntity newBook = createAndSaveBookEntity(naverBookItem);
			System.out.println("새 책 저장됨 - BookNum: " + newBook.getBookNum());
			return newBook;
		});
	}

	private BookEntity createAndSaveBookEntity(Item item) {
		BookEntity book = BookEntity.builder().bookName(item.getTitle()).bookImg(item.getImage())
				.author(item.getAuthor()).publisher(item.getPublisher())
				.description(truncateDescription(item.getDescription())).isbn(item.getIsbn())
				.discount(parseDiscountPrice(item.getDiscount())).link(item.getLink()).build();
		return bookRepository.save(book);
	}
	
	private String truncateDescription(String description) {
		if (description == null) {
			return "";
		}

		// 300자 이하면 그대로 반환
		if (description.length() <= 250) {
			return description;
		}

		// 300자까지 자르기
		String truncated = description.substring(0, 250);

		// 단어 중간에서 잘리는 것을 방지하기 위해 마지막 공백 이후로 자르기
		int lastSpace = truncated.lastIndexOf(' ');
		if (lastSpace > 0) {
			truncated = truncated.substring(0, lastSpace);
		}

		// 생략 부호 추가
		String result = truncated + "...";

		return result;
	}
	
	private Integer parseDiscountPrice(String discount) {
		try {
			return Integer.parseInt(discount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null; // 또는 다른 기본값 사용
		}
	}
	
}