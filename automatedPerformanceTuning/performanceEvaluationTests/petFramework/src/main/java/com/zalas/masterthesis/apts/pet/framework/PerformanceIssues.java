package com.zalas.masterthesis.apts.pet.framework;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.copyOf;

public class PerformanceIssues {
    private Set<PerformanceIssueTO> issues = Collections.synchronizedSet(new HashSet<PerformanceIssueTO>());

    public void add(PerformanceIssueTO performanceIssueTO) {
        if (issues.contains(performanceIssueTO)) {
            issues.remove(performanceIssueTO);
        }
        issues.add(performanceIssueTO);
    }

    public void clear() {
        issues.clear();
    }

    public Set<PerformanceIssueTO> getPerformanceIssues() {
        return copyOf(issues);
    }
}
