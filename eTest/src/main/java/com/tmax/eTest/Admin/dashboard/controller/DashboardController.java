package com.tmax.eTest.Admin.dashboard.controller;

import com.tmax.eTest.Admin.dashboard.dto.*;
import com.tmax.eTest.Admin.dashboard.service.DashboardService;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Auth.repository.UserRepository;
import com.tmax.eTest.LRS.repository.StatementRepository;
import com.tmax.eTest.LRS.util.LRSAPIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("submaster/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    @Qualifier("AU-UserRepository")
    private UserRepository userRepository;
    @Autowired
    private LRSAPIManager lrsapiManager;

    @PostMapping("overall")
    public ResponseEntity<DashboardOverallDTO> getOverallCards
            (@RequestBody FilterDTO filterDTO){
        int diagnosis = dashboardService.getDiagnosis(filterDTO).size();
        int minitest = dashboardService.getMinitest(filterDTO).size();
        int userRegister = dashboardService.getUserRegister(filterDTO).size();
        int totalAccessUser = dashboardService.getStatements(filterDTO, null, null).size();
        int userTotal = dashboardService.getUserAll();
        return ResponseEntity.ok(DashboardOverallDTO.builder()
                .totalAccessUser(totalAccessUser)
                .userRegistered(userRegister)
                .userTotal(userTotal)
                .diagnosisTotal(diagnosis + minitest)
                .diagnosis(diagnosis)
                .minitest(minitest)
                .build());
    }

    @PostMapping("accessor")
    public CMRespDto<?> getAccessor (@RequestBody FilterDTO filterDTO){
        int result = dashboardService.getStatements(filterDTO, null, null).size();
        return new CMRespDto<>(200, "success", result);
    }
    @PostMapping("user/register")
    public CMRespDto<?> getUserRegister (@RequestBody FilterDTO filterDTO){
        int result = dashboardService.getUserRegister(filterDTO).size();
        return new CMRespDto<>(200, "success", result);
    }

    @GetMapping("user/all")
    public CMRespDto<?> getUserAll (){
        int result = dashboardService.getUserAll();
        return new CMRespDto<>(200, "success", result);
    }

    @GetMapping("member")
    public ResponseEntity<MemberStatusDTO> getMemberStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getMemberStatus(filterDTO));
    }


    @GetMapping("diagnosis")
    public ResponseEntity<DiagnosisStatusDTO> getDiagnosisStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getDiagnosisStatus(filterDTO));
    }


    @GetMapping("content")
    public ResponseEntity<ContentViewsStatusDTO> getContentViewsStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getContentViewsStatus(filterDTO));
    }
}
