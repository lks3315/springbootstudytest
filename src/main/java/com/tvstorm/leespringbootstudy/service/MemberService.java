package com.tvstorm.leespringbootstudy.service;

import com.tvstorm.leespringbootstudy.domain.Role;
import com.tvstorm.leespringbootstudy.domain.entity.MemberEntity;
import com.tvstorm.leespringbootstudy.domain.repository.MemberRepository;
import com.tvstorm.leespringbootstudy.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto) {
        // password 암호화, 회원가입 처리
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getUserNo();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException { // 상세 정보 조회 처리하는 메소드
        // 사용자 정보 및 권한을 갖는 UserDetails interface 반환
        // String userEmail 파라미터는 실제 db의 Entity 중 PK가 아니고 유저를 식별할 수 있는 값이면 됨
        // 기본적으로 Spring Security에선 username을 쓰나 여기선 email을 username 대신 사용할 예정, 하지만 로그인 form에서는 name="username" 으로 요청해야 함
        Optional<MemberEntity> userEntityOptional = memberRepository.findByEmail(userEmail);
        MemberEntity memberEntity = userEntityOptional.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        // role을 부여하는 방식으로 회원가입 시 role을 정할 수 있게 Entity를 만들어서 매핑
        if (("admin@tvstorm.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // UserDetails를 구현한 User객체를 반환(id,pw,권한리스트)
        return new User(memberEntity.getEmail(), memberEntity.getPassword(), authorities);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorMemberResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) { // 유효성 검사에 실패한 필드들을 가져와 봄
            String validaKeyName = String.format("vaild_%s", error.getField()); // 유효성 검사 실패시 가져올 필드명
            validatorMemberResult.put(validaKeyName, error.getDefaultMessage()); // 유효성 검사 실패시 가져오는 default msg
        }
        return validatorMemberResult;
    }
}

// loadUserByUsername() 관련 설명, 대략 user name을 기준으로 user를 찾는다는 것
// Locates the user based on the username. In the actual implementation, the search may possibly be case sensitive, or case insensitive depending on how the implementation instance is configured. In this case, the <code>UserDetails</code> object that comes back may have a username that is of a different case than what was actually requested..
// @param username the username identifying the user whose data is required.
// @return a fully populated user record (never <code>null</code>)
// @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority