/*
 * Copyright (C) 2016 Davide Mainardi <ingmainardi at live.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dmainardi.pipeer.presentation.customerSupplier;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
public class CustomerFlow {
    @Produces
    @FlowDefinition
    public Flow defineCustomerFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        flowBuilder.id("", "customerF");

        flowBuilder.viewNode("openCustomer", "/secured/customerSupplier/customer.xhtml").markAsStartNode();
        flowBuilder.viewNode("openPlant", "/secured/CustomerSupplier/plant.xhtml");
        flowBuilder.viewNode("openReferee", "/secured/CustomerSupplier/referee.xhtml");

        flowBuilder.returnNode("exitFlow").fromOutcome("customers");

        return flowBuilder.getFlow();
    }    
}
