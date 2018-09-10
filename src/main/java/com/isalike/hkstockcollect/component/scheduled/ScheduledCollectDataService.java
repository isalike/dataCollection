package com.isalike.hkstockcollect.component.scheduled;

import com.isalike.hkstockcollect.constant.Stock;
import com.isalike.hkstockcollect.services.stock.StockService;
import com.isalike.hkstockcollect.util.CommonFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScheduledCollectDataService {
    @Autowired
    private StockService stockService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "${scheduledGetDailyStockReport.cronExpression}")
    public void scheduledGetDailyStockReport() throws Exception {
        logger.info("scheduledGetDailyStockReport START");
        for (Map.Entry<String, String> entry : Stock.hsiStock.entrySet()) {
            stockService.getStockDataToday(entry.getKey());
        }
        logger.info("scheduledGetDailyStockReport END");
    }

    @Scheduled(cron = "${scheduledGetInstantStockReport.cronExpression}")
    public void scheduledGetInstantStockReport() throws Exception {
        logger.info("scheduledGetInstantStockReport START");
//        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
//        Date start = parser.parse("09:25");
//        Date end = parser.parse("16:10");
//        Date userDate = parser.parse(CommonFunction.getNow());
//        if (userDate.after(start) && userDate.before(end)) {
//            retrun "ok";
//        }
        for (Map.Entry<String, String> entry : Stock.hsiStock.entrySet()) {
            String result = stockService.getOneNow(entry.getKey());
            stockService.insertOneData(entry.getKey(),result);
        }
        logger.info("scheduledGetInstantStockReport END");
    }
}
