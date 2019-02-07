package isa.projekat.Projekat.controller.airline;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseFormatter {
    public static ResponseEntity<?> format(Boolean result, Boolean autFlag ){
        Map<String, String> retVal = new HashMap<>();
        if(result)
        {
            retVal.put("result", "success");
            return ResponseEntity.ok(retVal);
        }
        if(autFlag) {
            retVal.put("result", "error");
            retVal.put("body","401, Unauthorized access");
            return ResponseEntity.badRequest().body(retVal);
        }
        else
        {
            retVal.put("result", "error");
            retVal.put("body","400, Bad Request");
            return ResponseEntity.badRequest().body(retVal);
        }
    }
}
