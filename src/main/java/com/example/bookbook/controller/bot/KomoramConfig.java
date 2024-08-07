package com.example.bookbook.controller.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class KomoramConfig {

    // 사용자 정의 사전 파일 이름
    private String USER_DIC = "user.dic";

    // Komoran 객체를 Spring Bean으로 정의
    @Bean
    Komoran komoran() throws IOException {
        // 사용자 정의 사전 파일을 생성
        createDIC();
        // Komoran 인스턴스를 생성하고, 기본 모델을 FULL로 설정
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        // 사용자 정의 사전 파일 경로를 Komoran에 설정
        komoran.setUserDic(USER_DIC);
        return komoran; // Komoran 객체를 반환
    }

    // 사용자 정의 사전 파일을 생성하거나 업데이트하는 메서드
    private void createDIC() throws IOException {
        // 사용자 정의 사전 파일 경로를 지정
        File file = new File(USER_DIC);
        
        // 파일이 존재하지 않으면 새로 생성
        if (!file.exists()) file.createNewFile();

        // 명사 집합을 저장할 Set
        Set<String> nnpset = new HashSet<>();
        
        // 파일에서 기존 데이터를 읽어와 명사 집합에 추가
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data = null;
        while ((data = br.readLine()) != null) {
            if (data.startsWith("#")) continue; // 주석 라인은 무시
            // 탭을 기준으로 데이터를 분리하여 명사만 추출
            nnpset.add(data.split("\\t")[0]);
        }
        br.close();

        // 사용자 정의 사전에 명사를 추가
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        
        // 명사 집합의 내용을 파일에 기록
        nnpset.forEach(name -> {
            try {
                bw.write(name + "\tNNP\n"); // NNP는 명사 고유명사
            } catch (IOException e1) {
                // 예외 발생 시 처리
                e1.printStackTrace();
            }
        });
        bw.close();
    }
}
