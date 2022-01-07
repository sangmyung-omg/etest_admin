package com.tmax.eTest.Dashboard.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatusDTO {
    private CollectDTO accessor;
    private MemberStatusMemberChangeDTO memberChange;
}
