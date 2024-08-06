/*
 * package com.example.bookbook.domain.entity;
 * 
 * import org.hibernate.annotations.ColumnDefault;
 * 
 * import com.example.bookbook.domain.entity.Role;
 * 
 * import jakarta.persistence.Column; import jakarta.persistence.Entity; import
 * jakarta.persistence.EnumType; import jakarta.persistence.Enumerated; import
 * jakarta.persistence.GeneratedValue; import
 * jakarta.persistence.GenerationType; import jakarta.persistence.Id; import
 * jakarta.persistence.JoinColumn; import jakarta.persistence.ManyToOne; import
 * jakarta.persistence.Table; import lombok.AllArgsConstructor; import
 * lombok.Builder; import lombok.Getter; import lombok.NoArgsConstructor; import
 * lombok.Setter;
 * 
 * @Getter // Setter는 주로 안 씀
 * 
 * @Setter
 * 
 * @Builder
 * 
 * @AllArgsConstructor
 * 
 * @NoArgsConstructor
 * 
 * @Entity
 * 
 * @Table(name = "user") // 별도로 지정하지 않으면 클래스이름이 테이블명 public class UserEntity {
 * 
 * @Id // PK 컬럼 설정하는 어노테이션
 * 
 * @GeneratedValue(strategy = GenerationType.SEQUENCE) private long userId; //
 * 사용자ID
 * 
 * @Column(nullable = false) private String userName; // 사용자이름
 * 
 * @Column(nullable = false) private String userRRN; // 주민등록번호
 * 
 * @Column(nullable = false) private String gender; // 성별
 * 
 * @Column(nullable = false) private String email; // 이메일
 * 
 * @Column(nullable = false) private String phoneNumber; // 핸드폰번호
 * 
 * @Column(nullable = false) private String password; // 비밀번호
 * 
 * @Enumerated(EnumType.STRING)
 * 
 * @Column(nullable = false) private Role role; // 역할
 * 
 * @ColumnDefault("true") private boolean emailAgree;
 * 
 * @ColumnDefault("true") private boolean smsAgree;
 * 
 * @ColumnDefault("0") private int point;
 * 
 * @ManyToOne // FK 단방향
 * 
 * @JoinColumn(name = "sellerId", nullable = false) private SellerEntity seller;
 * 
 * @Column(nullable=true)
 * 
 * @ColumnDefault("0") private int status; }
 * 
 */