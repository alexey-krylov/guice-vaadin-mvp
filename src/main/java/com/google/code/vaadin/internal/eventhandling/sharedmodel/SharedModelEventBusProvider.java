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

package com.google.code.vaadin.internal.eventhandling.sharedmodel;

import com.google.code.vaadin.internal.eventhandling.AbstractEventBusProvider;
import com.google.code.vaadin.mvp.eventhandling.EventBusTypes;
import com.google.code.vaadin.mvp.eventhandling.EventBusType;
import net.engio.mbassy.bus.IMessageBus;

import javax.inject.Inject;

/**
 * Shared Model EventBus provider. Allows to inject MBassador {@link IMessageBus}.
 *
 * @author Alexey Krylov
 * @since 26.01.13
 */
class SharedModelEventBusProvider extends AbstractEventBusProvider {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    @EventBusType(EventBusTypes.SHARED_MODEL)
    private IMessageBus messageBus;

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public IMessageBus getMessageBus() {
        return messageBus;
    }
}