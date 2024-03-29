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

package com.google.code.vaadin.mvp;

import com.google.code.vaadin.mvp.eventhandling.EventType;
import com.google.code.vaadin.mvp.eventhandling.Observes;
import com.google.code.vaadin.mvp.eventhandling.events.ContactOpenedEvent;
import com.google.code.vaadin.mvp.eventhandling.events.DomainEvent;
import com.google.code.vaadin.mvp.eventhandling.events.SharedModelEvent;

import javax.inject.Inject;

/**
 * @author Alexey Krylov
 * @since 24.01.13
 */
public class TestPresenter extends AbstractPresenter<TestView> {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -6223121159195154865L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private boolean contactOpened;
    private boolean domainEventReceived;
    private boolean sharedModelEventReceived;
    private boolean viewOpened;

    @Inject
    private DomainService domainService;

    /*===========================================[ CLASS METHODS ]================*/

    @Observes
    private void buttonClicked(ContactOpenedEvent contactOpenedEvent) {
        domainService.doSomething();
        contactOpened = true;
    }

    @Observes(EventType.MODEL)
    private void domainEventReceived(DomainEvent domainEvent) {
        domainEventReceived = true;
    }

    @Observes(EventType.SHARED_MODEL)
    private void sharedDomainEventReceived(SharedModelEvent sharedModelEvent) {
        sharedModelEventReceived = true;
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void initPresenter() {

    }

    @Override
    public void viewOpened() {
        viewOpened = true;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    public boolean isContactOpened() {
        return contactOpened;
    }

    public boolean isViewOpened() {
        return viewOpened;
    }

    public boolean isDomainEventReceived() {
        return domainEventReceived;
    }

    public boolean isSharedModelEventReceived() {
        return sharedModelEventReceived;
    }
}