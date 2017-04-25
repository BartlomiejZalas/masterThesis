package com.zalas.masterthesis.apts.decisionmodule.impl;

import com.zalas.masterthesis.apts.decisionmodule.api.Rule;

import java.util.Comparator;

public class RuleComparatorFromHighToLow implements Comparator<Rule> {
    @Override
    public int compare(Rule rule1, Rule rule2) {
        return rule2.getPriority() - rule1.getPriority();
    }
}
