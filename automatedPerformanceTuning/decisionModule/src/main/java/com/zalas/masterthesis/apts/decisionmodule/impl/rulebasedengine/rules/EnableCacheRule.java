package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules;

import com.zalas.masterthesis.apts.decisionmodule.api.ProblemToSolve;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public class EnableCacheRule implements Rule {
    @Override
    public boolean isRuleApplicable(ProblemToSolve problemToSolve, ApplicationConfiguration currentConfiguration) {
//        if (currentConfiguration.getCache().equals(ConfigurationConstants.Value.NO_CACHE))
//
        return false;
    }

    @Override
    public void executeAction() {

    }

    @Override
    public int getPriority() {
        return 1;
    }
}
