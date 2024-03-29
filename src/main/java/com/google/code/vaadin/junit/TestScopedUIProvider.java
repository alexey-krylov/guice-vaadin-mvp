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

package com.google.code.vaadin.junit;

import com.google.code.vaadin.application.ui.ScopedUIProvider;
import com.google.inject.Inject;
import com.vaadin.ui.UI;
import com.vaadin.util.CurrentInstance;

/**
 * Special {@link ScopedUIProvider} implementation which immediately creates one instance of UI - it is required for
 * JUnit tests.
 *
 * @author Alexey Krylov
 * @since 09.02.13
 */
public class TestScopedUIProvider extends ScopedUIProvider {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = 2050144768207694166L;

	/*===========================================[ CLASS METHODS ]================*/

    @Inject
    protected void initScope() {
        UI ui = createInstance(uiClass);
        CurrentInstance.set(UI.class, ui);
    }
}