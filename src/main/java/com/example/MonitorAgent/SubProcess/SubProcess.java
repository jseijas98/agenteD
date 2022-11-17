package com.example.MonitorAgent.SubProcess;

import com.example.MonitorAgent.Entity.*;
import com.example.MonitorAgent.Repository.ApiRepository;
import com.example.MonitorAgent.Repository.IntegrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SubProcess {
    @Autowired
    ApiRepository apiRepository;
    @Autowired
    IntegrationRepository integrationRepository;
    Logger logger = LoggerFactory.getLogger(SubProcess.class);
    
    public CompletableFuture<List<Api>> apiSubProcessCompletableFuture(Application application) throws InterruptedException{
        Integer applicationId = application.getApplication_id();
        List<Api> result = apiRepository.findAllByApplicationId(applicationId);

        result.forEach(api -> {
            logger.info("application_Id = {}, Api_Id = {}, Test_interv = {}, description = {}",api.getApplicationId(),api.getApi_id(),api.getTestInterv(),api.getDescription());
            try {
                Thread.sleep(api.getTestInterv());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    return CompletableFuture.completedFuture(result);
    }

    /* public CompletableFuture<Long> applicationSubProcessCompletableFuture(Application application) throws InterruptedException{
         Integer applicationId = application.getApplication_id();
         List<Api> result = apiRepository.findAllByApplicationId(applicationId);

         result.forEach(api -> {
             logger.info("application_Id = {}, Api_Id = {}, Test_interv = {}, description = {}",api.getApplicationId(),api.getApi_id(),api.getTestInterv(),api.getDescription());
             try {
                 Thread.sleep(api.getTestInterv());
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         });

        return CompletableFuture.completedFuture(result);
    }

     */

    public CompletableFuture<List<Integration>> integrationSubProcessCompletableFuture(Application application) throws InterruptedException{
        Integer applicationId = application.getApplication_id();
        List<Integration> result = integrationRepository.findAllByApplicationId(applicationId);

        result.forEach(integration -> {
            logger.info("application_Id = {}, Integration_Id = {}, Test_interv = {}, status = {}",
                    integration.getIntegration_id(),integration.getApplicationId(),integration.getTestInterv()
                    ,integration.getStatus());
            try {
                Thread.sleep(integration.getTestInterv());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return CompletableFuture.completedFuture(result);
    }

    public CompletableFuture<Long> loadBalancerSubProcessCompletableFuture(LoadBalancer loadBalancer) throws InterruptedException{
        Long result = loadBalancer.getTestInterv();
        logger.info("loadBalancer_id = {}, testInterv = {}",loadBalancer.getVserver_id(), result);
        Thread.sleep(result);

        return CompletableFuture.completedFuture(result);
    }

    public CompletableFuture<Long> persistenceSubProcessCompletableFuture(Persistence persistence) throws InterruptedException{
        Long result = persistence.getTestInterv();
        logger.info("persistence_id = {}, testInterv = {}",persistence.getDb_id(), result);
        Thread.sleep(result);

        return CompletableFuture.completedFuture(result);
    }

    public CompletableFuture<Long> serviceSubProcessCompletableFuture(com.example.MonitorAgent.Entity.Service service) throws InterruptedException{
        Long result = service.getTestInterv();
        logger.info("service_id = {}, testInterv = {}",service.getService_id(), result);
        Thread.sleep(result);

        return CompletableFuture.completedFuture(result);
    }
}
