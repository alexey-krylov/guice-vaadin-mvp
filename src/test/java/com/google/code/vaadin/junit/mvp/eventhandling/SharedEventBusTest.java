/*
 * Copyright (C) 2013 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.vaadin.junit.mvp.eventhandling;

import com.google.code.vaadin.MVPTestModule;
import com.google.code.vaadin.mvp.eventhandling.EventBusTypes;
import com.google.code.vaadin.junit.AbstractMVPTest;
import com.google.code.vaadin.mvp.eventhandling.*;
import com.google.code.vaadin.mvp.eventhandling.events.DomainEvent;
import com.google.code.vaadin.mvp.eventhandling.events.SharedModelEvent;
import com.google.inject.Stage;
import com.mycila.testing.plugin.guice.GuiceContext;
import net.engio.mbassy.bus.IMessageBus;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Alexey Krylov
 * @since 14.02.13
 */
@GuiceContext(value = {SharedEventBusReceiverModule.class, MVPTestModule.class}, stage = Stage.PRODUCTION)
public class SharedEventBusTest extends AbstractMVPTest {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private SharedModelEventPublisher publisher;

    @Inject
    @EventBusType(EventBusTypes.SHARED_MODEL)
    private IMessageBus messageBus;

    @Inject
    @EventBusType(EventBusTypes.SHARED_MODEL)
    private EventBus eventBus;

    @Inject
    @EventBusType(EventBusTypes.SHARED_MODEL)
    private EventPublisher eventPublisher;
    private int eventCounter;

    /*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testComponentsInjection() {
        Assert.assertNotNull("SharedModelEventPublisher not null", publisher);
        Assert.assertNotNull("Shared MessageBus is null", messageBus);
        Assert.assertNotNull("Shared EventBus is null", eventBus);
        Assert.assertNotNull("Shared EventPublisher is null", eventPublisher);
    }

    @Test
    public void testPublishSharedDomainEvent() {
        publisher.publish(new SharedModelEvent());

        SharedEventBusReceiverService eventBusReceiverService = injector.getInstance(SharedEventBusReceiverService.class);
        int received = eventBusReceiverService.getSharedModelEventReceivedCount();
        Assert.assertEquals("Event was not received", 2, received);
    }

    @Observes(EventType.SHARED_MODEL)
    protected void on(DomainEvent event) {
        eventCounter++;
    }

    @Test
    public void testFireEvent() {
        publisher.publish(new DomainEvent());
        eventBus.publish(new DomainEvent());
        messageBus.post(new DomainEvent()).now();
        eventPublisher.publish(new DomainEvent());

        Assert.assertEquals("Invalid received event count", 4, eventCounter);
    }
}