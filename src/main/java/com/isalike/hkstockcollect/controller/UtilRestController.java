package com.isalike.hkstockcollect.controller;

import com.isalike.hkstockcollect.constant.Stock;
import com.isalike.hkstockcollect.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UtilRestController {
    @Autowired
    private UtilService utilService;
    @RequestMapping(value="/test")
    public String test(@RequestParam(value = "index", required = true) String index){
        return utilService.test(index);
    }
/*
    @RequestMapping(value="/testing")
    public String testing(){
        return "<3 u";
    }
*/
/*@RequestMapping(value="/testStr")
    public String testStr(){
        return utilService.testString();
    }*/
    @RequestMapping(value="/initHsiDb")
    public String initHsiDb(@RequestParam(value = "index", required = true) String index,
                        @RequestParam(value = "startDt", required = true) String startDt,
                        @RequestParam(value = "endDt", required = true) String endDt) throws Exception{
        //return "";
        for (Map.Entry<String, String> entry : Stock.hsiStock.entrySet()) {
            utilService.getStockData(entry.getKey(),startDt,endDt);
//            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
        return "ok";
    }
}
