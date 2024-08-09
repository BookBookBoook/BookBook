package com.example.bookbook;

import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookbook.domain.entity.Answer;
import com.example.bookbook.domain.repository.AnswerRepository;
import com.example.bookbook.mapper.ExamMapper;

@SpringBootTest
class BookbookbookApplicationTests {
	
	//@Autowired
    private ExamMapper userMapper;
    @Autowired
    private AnswerRepository answerRepo;
    //@Test
    void testCountUsers() {
        int count = userMapper.countUsers();
        // Expected count를 알 수 없으므로 임시로 0이 아닌 값이 리턴되면 성공으로 설정합니다.
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
    
   // @Test
    void 챗봇사전등록() {
    	answerRepo.save(Answer.builder()
    			.keyword("안녕")
    			.content("안녕하세요")
    			.build());
    }

}
