package com.example.bookbook.config;

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
public class KomoranConfig {

    // 사용자 정의 사전 파일 이름
    private String USER_DIC = "user.dic";
    //private String BOT_DIC = "bot.dic";
    // "bot.dic" 파일명

    @Bean // Komoran 객체를 Spring Bean으로 정의
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

        // 명사(NNP) 집합을 저장할 Set //Set을 사용하여 중복된 명사가 저장되지 않음
        Set<String> nnpset = new HashSet<>();
        
        // 파일에서 기존 데이터를 읽어와 명사 집합에 추가
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data = null;
        while ((data = br.readLine()) != null) {
            if (data.startsWith("#")) continue; // 주석 라인은 무시
 
            //탭(\t)으로 구분된 데이터에서 명사 부분만 추출하여 Set에 추가
            nnpset.add(data.split("\\t")[0]);
        }
        br.close();

        // 사용자 정의 사전에 명사를 추가
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        
        // Set에 저장된 명사들을 파일에 기록
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
