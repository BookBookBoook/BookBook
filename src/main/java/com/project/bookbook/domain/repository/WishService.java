package com.project.bookbook.domain.repository;

public interface WishService {

	void addToWishlist(String isbn, long userId) throws Exception;

}
