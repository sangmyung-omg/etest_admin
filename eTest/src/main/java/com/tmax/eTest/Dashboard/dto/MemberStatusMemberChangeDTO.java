package com.tmax.eTest.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatusMemberChangeDTO {
    private String[] time;
    private int[] memberTotal;
    private int[] memberRegistered;
//    private int[] memberWithdrawn;
}
