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

package com.google.code.vaadin.components.eventhandling.configuration;

import com.google.code.vaadin.mvp.eventhandling.EventBusTypes;
import net.engio.mbassy.bus.BusConfiguration;

import javax.validation.constraints.NotNull;

/**
 * Each Event Bus should have binding to its configuration and this builder is an only way to do this.
 *
 * @author Alexey Krylov
 * @see EventBusBinder#bind(EventBusTypes)
 * @since 20.02.13
 */
public interface EventBusBindingBuilder {

    void withConfiguration(@NotNull BusConfiguration configuration);

    /**
     * @see BusConfiguration#Default()
     */
    void withDefaultConfiguration();
}