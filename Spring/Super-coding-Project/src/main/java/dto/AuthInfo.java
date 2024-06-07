package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter

@AllArgsConstructor(staticName = "of")
public class AuthInfo {
    private Long memberId;


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
