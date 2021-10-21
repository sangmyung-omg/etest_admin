package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatusDTO {
    private String[] time;
    private int[] accessorCollect;
    private int[] memberTotal;
    private int[] memberRegistered;
    private int[] memberWithdrawn;
}
