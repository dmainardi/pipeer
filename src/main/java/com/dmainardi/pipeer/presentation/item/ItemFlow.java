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
package com.dmainardi.pipeer.presentation.item;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
public class ItemFlow {
    @Produces
    @FlowDefinition
    public Flow defineItemFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        flowBuilder.id("", "itemF");

        flowBuilder.viewNode("openItem", "/secured/item/item.xhtml").markAsStartNode();
        flowBuilder.viewNode("openProcess", "/secured/workshop/process.xhtml");
        
        flowBuilder.returnNode("returnToItemNode").fromOutcome("openItemNode");
        
        flowBuilder.returnNode("exitFlow").fromOutcome("items");
        
        flowBuilder.inboundParameter("item", "#{itemPresenter.item}");
        flowBuilder.inboundParameter("returnOutcome", "#{itemPresenter.returnOutcome}");

        return flowBuilder.getFlow();
    }
}
