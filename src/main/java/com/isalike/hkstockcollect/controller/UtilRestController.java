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
    @RequestMapping(value="/test")
    public String test(@RequestParam(value = "index", required = true) String index){
        return stockService.getOneNow(index);
    }

    @RequestMapping(value="/test2")
    public String test2(){
        try{
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            Date start = parser.parse("09:25");
            Date end = parser.parse("16:10");
            Date userDate = parser.parse(parser.format(new Date()));
            if (userDate.after(start) && userDate.before(end)) {
                return "ok";
            }
        }catch (Exception e){

        }
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
