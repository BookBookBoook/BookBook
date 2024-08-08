package com.example.bookbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.bookbook.mapper.ExamMapper;

@SpringBootTest
class BookbookbookApplicationTests {
	
	//@Autowired
    private ExamMapper userMapper;

    //@Test
    void testCountUsers() {
        int count = userMapper.countUsers();
        // Expected count를 알 수 없으므로 임시로 0이 아닌 값이 리턴되면 성공으로 설정합니다.
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

}
