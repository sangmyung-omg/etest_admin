package com.tmax.eTest.Admin.dashboard.controller;

import com.tmax.eTest.Admin.dashboard.dto.DashboardOverallDTO;
import com.tmax.eTest.Admin.dashboard.dto.DiagnosisStatusDTO;
import com.tmax.eTest.Admin.dashboard.dto.FilterDTO;
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

    @GetMapping("diagnosis")
    public ResponseEntity<DiagnosisStatusDTO> getDiagnosisStatus(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(dashboardService.getDiagnosisStatus(filterDTO));
    }

    @PostMapping("overall")
    public ResponseEntity<DashboardOverallDTO> getOverallCards
            (@RequestBody FilterDTO filterDTO){
        Integer diagnosis = dashboardService.getDiagnosis(filterDTO).size();
        Integer minitest = dashboardService.getMinitest(filterDTO).size();
        Integer userRegister = dashboardService.getUserRegister(filterDTO).size();
        Integer totalAccessUser = dashboardService.getAccessor(filterDTO).size();
        Integer userTotal = dashboardService.getUserAll();
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
        int result = dashboardService.getAccessor(filterDTO).size();
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
}
