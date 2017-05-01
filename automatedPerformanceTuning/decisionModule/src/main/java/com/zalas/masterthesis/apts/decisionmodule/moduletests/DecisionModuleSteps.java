package com.zalas.masterthesis.apts.decisionmodule.moduletests;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecisionModuleSteps {

    private int variable;

    @Given("a variable")
    public void setup() {
        variable = 0;
    }

    @When("action")
    public void action() {
        variable = 5;
    }

    @Then("variable is equal to $value")
    public void assertValue(int value) {
        assertThat(variable, equalTo(value));
    }

}
