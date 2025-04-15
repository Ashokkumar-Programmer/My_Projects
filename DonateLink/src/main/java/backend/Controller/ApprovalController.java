package backend.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/EduSphere")
public class ApprovalController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/updateApproval")
    public ResponseEntity<Map<String, Object>> updateApproval(
            @RequestParam("username") String username,
            @RequestParam("status") boolean status) {

        String sql = "UPDATE userlogin SET approval = ? WHERE username = ?";
        int rowsUpdated = jdbcTemplate.update(sql, status, username);

        Map<String, Object> response = new HashMap<>();
        if (rowsUpdated > 0) {
            response.put("success", true);
            response.put("status", status);
        } else {
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }
}
