package com.zalas.masterthesis.apts.manager;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.main.DecisionModule;
import com.zalas.masterthesis.apts.decisionmodule.main.DecisionModuleFactory;
import com.zalas.masterthesis.apts.pet.framework.PerformanceIssueTO;
import com.zalas.masterthesis.apts.pet.framework.PetCaseRunner;

import java.util.Set;

public class AptsManger {

    private final DecisionModule decisionModule = new DecisionModuleFactory().create();
    private final PetCaseRunner petCaseRunner = new PetCaseRunner("com.zalas.masterthesis.apts.pet.cases");

    public void run() throws Exception{
        petCaseRunner.start();

        while(petCaseRunner.isAlive()) {
            Thread.sleep(1000);
            Set<PerformanceIssueTO> newIssues = petCaseRunner.getNewIssues();
            System.out.println(newIssues);
            handleIssues(newIssues);
        }
    }

    private void handleIssues(Set<PerformanceIssueTO> performanceIssueTOs) {
        for (PerformanceIssueTO issue : performanceIssueTOs) {
            decisionModule.performDecision(new IssueToHandle(issue.getMetric(), issue.getStatus()));
        }
    }

    public static void main(String[] args) throws Exception {
        new AptsManger().run();
    }
}