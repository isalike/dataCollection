package com.isalike.hkstockcollect.controller;

import com.isalike.hkstockcollect.constant.Stock;
import com.isalike.hkstockcollect.services.UtilService;
import com.isalike.hkstockcollect.services.stock.StockService;
import com.isalike.hkstockcollect.util.CommonFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class UtilRestController {
    @Autowired
    private StockService stockService;
    @RequestMapping(value="/getOne")
    public String test(@RequestParam(value = "startDt", required = true) String startDt,
                       @RequestParam(value = "endDt", required = true) String endDt){
        try{
            for (Map.Entry<String, String> entry : Stock.hsiStock.entrySet()) {
                stockService.getStockData(entry.getKey(),startDt,endDt);
            }
        }catch (Exception e){

        }
        return "ok";
    }

    @RequestMapping(value="/test")
    public String test(){
        return "ok";
    }




    @RequestMapping(value="/initHsiDb")
    public String initHsiDb() throws Exception{
        for (Map.Entry<String, String> entry : Stock.hsiStock.entrySet()) {
            stockService.getStockDataFromPast(entry.getKey());
        }
        return "initHsiDb ok";
    }

    @RequestMapping(value="/getOneNow")
    public String getOneNow() throws Exception{
        for (Map.Entry<String, String> entry : Stock.hsiStock.entrySet()) {
            String result = stockService.getOneNow(entry.getKey());
            stockService.insertOneData(entry.getKey(),result);
        }
        return "initHsiDb ok";
    }
}
