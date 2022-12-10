package scanner.prototype.controller;


import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class ApiLandingController {

    @GetMapping
    public ResponseEntity<?> retrieveAPI(){
        HashMap<String, String> api = new HashMap<String, String>(); 
        api.put("checklist", "/api/v1/checklist");
        api.put("scan", "/api/v1/file/{provider}");
        api.put("history", "/api/v1/history");
        api.put("report", "/api/v1/report");
        return ResponseEntity.ok().body(api);
    }

    @GetMapping("/api/v1")
    public ResponseEntity<?> retrieveAPI2(){
        HashMap<String, String> api = new HashMap<String, String>(); 
        api.put("checklist", "/api/v1/checklist");
        api.put("scan", "/api/v1/file/{provider}");
        api.put("history", "/api/v1/history");
        api.put("report", "/api/v1/report");
        return ResponseEntity.ok().body(api);
    }
}
