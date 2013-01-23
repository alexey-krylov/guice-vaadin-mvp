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

package com.google.code.vaadin.mvp.servlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * GuiceApplicationServlet - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 23.01.13
 */
@Singleton
public class GuiceApplicationServlet extends AbstractApplicationServlet {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -999641550877695877L;

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Provider<Application> applicationProvider;
    private Class applicationClass;

	/*===========================================[ CONSTRUCTORS ]=================*/

    @Inject
    public void init(Provider<Application> applicationProvider,
                     @Named(AbstractMVPApplicationContextListener.P_APPLICATION) Class applicationClass) {
        this.applicationProvider = applicationProvider;
        this.applicationClass = applicationClass;
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected Class getApplicationClass() throws ClassNotFoundException {
        return applicationClass;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        return applicationProvider.get();
    }
}