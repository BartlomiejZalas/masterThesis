package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public class EmptyRule {

    private static Rule instance = new DoNothingRule();

    private EmptyRule() {
    }

    public static Rule getInstance() {
        return instance;
    }

    static class DoNothingRule implements Rule {
        @Override
        public boolean isRuleApplicable(IssueToHandle issueToHandle, ApplicationConfiguration currentConfiguration) {
            return true;
        }

        @Override
        public void executeAction() {

        }

        @Override
        public int getPriority() {
            return 0;
        }
    }

}
