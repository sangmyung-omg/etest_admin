package com.tmax.eTest.Dashboard.controller;

import com.tmax.eTest.Dashboard.dto.*;
import com.tmax.eTest.Dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("submaster/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    /**
     * 대시보드 전체 현황
     * @param filterDTO
     */
    @PostMapping("overall")
    public ResponseEntity<OverallStatusDTO> getOverallStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getOverallStatus(filterDTO));
    }

    /**
     * 대시보드 회원 현황
     * @param filterDTO
     */
    @PostMapping("member")
    public ResponseEntity<MemberStatusDTO> getMemberStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getMemberStatus(filterDTO));
    }

    /**
     * 대시보드 진단 현황
     * @param filterDTO
     */
    @PostMapping("diagnosis")
    public ResponseEntity<DiagnosisStatusDTO> getDiagnosisStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getDiagnosisStatus(filterDTO));
    }


    /**
     * 대시보드 컨텐츠 조회 현황
     * @param filterDTO
     */
    @PostMapping("content")
    public ResponseEntity<ContentViewsStatusDTO> getContentViewsStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getContentViewsStatus(filterDTO));
    }
}
