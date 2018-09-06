package com.isalike.hkstockcollect.component.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScheduledCollectDataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${collect.enable:false}")
    private boolean enable;
    @Scheduled(cron = "${collect.cronExpression}")
    public void getReport() throws Exception {
//        if (enable) {
//            logger.info("queryEpayBill:STRAT");
//            try {
//                List<PgBill> pgBills = pgDServiceTransaction.selectAllDepositNonFinishExceptReal(startDt, endDt);
//
//                ExecutorService executor = Executors.newFixedThreadPool(100);
//                for (PgBill pgBill : pgBills) {
//                    Runnable worker = new RunnableQueryDepositPaymentService(pgBill, pgManageService
//                            .getQueryService(pgBill.getPaySystem()));
//                    executor.execute(worker);
//                }
//                executor.shutdown();
//                while (!executor.isTerminated()) {
//                }
//                logger.info("Finished all getReport threads");
//				for (PgBill pgBill : pgBills) {
//					PaymentGatewayQueryDepositServiceAbstract service = pgManageService
//							.getQueryService(pgBill.getPaySystem());
//					RunnableQueryDepositPaymentService threadClass = new RunnableQueryDepositPaymentService(pgBill, service);
//					Thread thread = new Thread(threadClass);
//					thread.start();
//				}
//            } catch (Exception e) {

 //           }
  //      }
    }
}
