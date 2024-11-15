package darak.community.domain.member;

import darak.community.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private MemberPassword password;

    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    private String phone;

    private LocalDate birth;

    // @Email
    private String email;

    // Builder 패턴의 단점 : 필수값을 놓칠 수 있음
    // -> NonNull을 붙여준다.
    @Builder
    public Member(String name, String password, String phone, LocalDate birth, String email) {
        this.name = name;
        this.password = new MemberPassword(password);
        this.phone = phone;
        this.birth = birth;
        this.email = email;
    }

    // 회원 정보 수정 메서드
    public void updateMember(Member editInfoMember) {
        if (!Objects.equals(editInfoMember.id, this.id)) {
            throw new IllegalArgumentException("회원 정보 수정 실패");
        }
        if (editInfoMember.password != null) {
            this.password = editInfoMember.password;
        }
        if (editInfoMember.name != null) {
            this.name = editInfoMember.name;
        }
        if (editInfoMember.phone != null) {
            this.phone = editInfoMember.phone;
        }
        if (editInfoMember.birth != null) {
            this.birth = editInfoMember.birth;
        }
        if (editInfoMember.email != null) {
            this.email = editInfoMember.email;
        }
    }

    public boolean isMatchedPassword(final String rawPassword) {
        return password.isMatched(rawPassword);
    }

    public void changePassword(final String newPassword, final String oldPassword) {
        password.changePassword(newPassword, oldPassword);
    }

    public boolean isMatchedPhone(String phone) {
        return this.phone.equals(phone);
    }

}
