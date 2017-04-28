package com.zalas.masterthesis.apts.manager;

import com.zalas.masterthesis.apts.decisionmodule.api.ProblemToSolve;
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
            System.out.println(petCaseRunner.getNewIssues());
//            handleIssues(petCaseRunner.getNewIssues());
        }
    }

    private void handleIssues(Set<PerformanceIssueTO> performanceIssueTOs) {
        for (PerformanceIssueTO issue : performanceIssueTOs) {
            decisionModule.performDecision(new ProblemToSolve(issue.getKpiName(), issue.getReason().toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        new AptsManger().run();
    }
}
