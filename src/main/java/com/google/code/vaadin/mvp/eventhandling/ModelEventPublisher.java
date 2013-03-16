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

package com.google.code.vaadin.mvp.eventhandling;

import com.google.code.vaadin.application.AbstractMVPApplicationModule;
import com.google.code.vaadin.application.uiscope.UIScoped;
import com.google.code.vaadin.components.eventhandling.configuration.EventBusBinder;

/**
 * Event publisher for UIScoped Model-related events.
 * Default subscribers for this kind of events is Presenters and other UIScoped model components.
 * This publisher is also UIScoped.
 * <p/>
 * NOTE: model EventBus is disabled by default. Use {@link EventBusBinder} and
 * {@link AbstractMVPApplicationModule#bindEventBuses(EventBusBinder)} to bind EventBus with {@link EventBusTypes#MODEL} type.
 *
 * @author Alexey Krylov
 * @see UIScoped
 * @since 23.01.13
 */
public interface ModelEventPublisher extends EventPublisher {
    /*===========================================[ INTERFACE METHODS ]==============*/
}
