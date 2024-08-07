package com.example.bookbook.service.bot;
/*
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bookbook.domain.dto.AnswerDTO;
import com.example.bookbook.domain.dto.MessageDTO;
import com.example.bookbook.domain.dto.PhoneInfo;
import com.example.bookbook.domain.entity.ChatBotIntentionEntity;
import com.example.bookbook.domain.repository.ChatBotIntentionRepository;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KomoranService {

    private final Komoran komoran; // NLP 분석을 위한 Komoran 인스턴스
    private final ChatBotIntentionRepository intention; // 챗봇 의도를 가져오는 레포지토리
    
    // NLP 분석과 메시지 처리 메인 메서드
    public MessageDTO nlpAnalyze(String message) {
        
        // Komoran NLP 도구를 사용하여 메시지 분석
        KomoranResult result = komoran.analyze(message);
        
        // 각 토큰의 시작 인덱스, 끝 인덱스, 형태소, 품사를 출력
        result.getTokenList().forEach(token -> {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(),
                    token.getMorph(), token.getPos());
        });
        
        // 분석된 토큰에서 명사만 추출하고 중복 제거를 위해 Set 사용
        Set<String> nouns = result.getNouns().stream()
                .collect(Collectors.toSet());
        nouns.forEach(noun -> {
            System.out.println(">>>:" + noun);
        }); // 추출된 명사를 디버깅을 위해 출력

        // 추출된 명사들을 처리하여 응답을 결정
        return analyzeToken(nouns);
    }

    // 명사 집합을 처리하여 적절한 응답을 결정하는 메서드
    
    
    private MessageDTO analyzeToken(Set<String> nouns) {
        
        // 현재 날짜와 시간 가져오기
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        
        // 현재 시간을 포함한 MessageDTO 객체 생성
        MessageDTO messageDTO = MessageDTO.builder()
                .time(today.format(timeFormatter)) // 메시지에 시간 설정
                .build();
        
        // 추출된 명사들을 순회하며 주요 의도(primary intention)를 찾음
        for (String token : nouns) {
            
            // 토큰이 데이터베이스에서 주요 의도와 일치하는지 확인
            Optional<ChatBotIntentionEntity> result = decisionTree(token, null);
            System.out.println("token:" + token);
            if (result.isEmpty()) continue; // 일치하는 의도가 없으면 다음 토큰으로 진행
            
            // 주요 의도 토큰 로그
            System.out.println(">>>>1차:" + token);
            
            // 명사 집합의 복사본을 만들고 현재 토큰을 제거
            Set<String> next = nouns.stream().collect(Collectors.toSet());
            next.remove(token);
            
            // 남은 토큰들로 2차 분석 수행
            AnswerDTO answer = analyzeToken(next, result).toAnswerDTO();
            
            // 토큰이 전화 정보와 관련이 있으면 전화 세부사항 처리
            if (token.contains("전화") || token.contains("전번") || token.contains("번호")) {
                List<PhoneInfo> phone = analyzeTokenIsPhone(next);
                answer.phone(phone); // 전화 정보를 응답에 추가
                
            } else if (token.contains("인사말")) {
                // 토큰이 인사말을 나타내는 경우, 메시지에 날짜 포함
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                messageDTO.today(today.format(dateFormatter));
            }
            
            // 응답 정보를 메시지 DTO에 설정
            messageDTO.answer(answer);
            
            // 응답이 설정된 메시지 DTO를 반환
            return messageDTO;
        }
        
        // 일치하는 의도를 찾지 못한 경우, 기본 응답 제공
        AnswerDTO answer = decisionTree("기타", null).get().getAnswer().toAnswerDTO();
        messageDTO.answer(answer);
        return messageDTO;
    }
    
    private final EmpEntityRepository empEntityRepository; // 직원 정보를 가져오는 레포지토리
     
    // 전화 문의인 경우, 데이터베이스에서 직원 정보 찾기
    private List<PhoneInfo> analyzeTokenIsPhone(Set<String> next) {
        for (String name : next) {
            // 주어진 이름으로 직원 검색
            List<EmpEntity> emps = empEntityRepository.findByName(name);
            if (emps.isEmpty()) continue; // 직원이 없으면 다음 이름으로 진행
            
            // 찾은 직원들을 PhoneInfo로 매핑하여 리스트 반환
            return emps.stream()
                    .map(EmpEntity::toPhoneInfo)
                    .collect(Collectors.toList());
        }
        return null; // 전화 정보가 없으면 null 반환
    }
    
    // 2차 의도 분석
    private Answer analyzeToken(Set<String> next, Optional<ChatBotIntentionEntity> upper) {
        for (String token : next) {
            // 주어진 상위 의도에 대한 2차 의도와 일치하는 토큰이 있는지 확인
            Optional<ChatBotIntentionEntity> result = decisionTree(token, upper.get());
            if (result.isEmpty()) continue; // 일치하는 의도가 없으면 다음 토큰으로 진행
            
            // 2차 의도 토큰 로그
            System.out.println(">>>>2차:" + token);
            return result.get().getAnswer(); // 2차 의도에 대한 응답 반환
        }
        return upper.get().getAnswer(); // 2차 의도가 없으면 상위 의도에 대한 응답 반환
    }
    
    // 데이터베이스에서 의도가 존재하는지 확인
    private Optional<ChatBotIntentionEntity> decisionTree(String token, ChatBotIntentionEntity upper) {
        return intention.findByNameAndUpper(token, upper); 
    }
}
*/