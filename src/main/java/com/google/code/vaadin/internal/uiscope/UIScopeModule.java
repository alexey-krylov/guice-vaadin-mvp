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
package com.google.code.vaadin.internal.uiscope;

import com.google.code.vaadin.application.uiscope.UIScope;
import com.google.code.vaadin.application.uiscope.UIScoped;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * {@link UIScope} support module.
 *
 * @author Alexey Krylov
 * @since 17.03.13
 */
public class UIScopeModule extends AbstractModule {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private final UIScope uiScope;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public UIScopeModule() {
        uiScope = UIScope.getCurrent();
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public void configure() {
        // tell Guice about the scope
        bindScope(UIScoped.class, uiScope);

        // make our scope instance injectable
        bind(UIScope.class).annotatedWith(Names.named("UIScope")).toInstance(uiScope);
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public UIScope getUIScope() {
        return uiScope;
    }
}