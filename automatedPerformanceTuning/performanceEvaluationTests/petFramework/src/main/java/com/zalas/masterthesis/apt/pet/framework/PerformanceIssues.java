package com.zalas.masterthesis.apt.pet.framework;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.copyOf;

public class PerformanceIssues {
    private Set<PerformanceIssue> issues = Collections.synchronizedSet(new HashSet<PerformanceIssue>());

    public void add(PerformanceIssue performanceIssue) {
        if (issues.contains(performanceIssue)) {
            issues.remove(performanceIssue);
        }
        issues.add(performanceIssue);
    }

    public void clear() {
        issues.clear();
    }

    public Set<PerformanceIssue> getPerformanceIssues() {
        return copyOf(issues);
    }
}
