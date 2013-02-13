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

package com.google.code.vaadin.application;

import com.google.code.vaadin.application.ui.ScopedUIProvider;
import com.google.code.vaadin.application.uiscope.UIScopeModule;
import com.google.code.vaadin.internal.components.VaadinComponentPreconfigurationModule;
import com.google.code.vaadin.internal.event.EventBusModule;
import com.google.code.vaadin.internal.localization.ResourceBundleInjectionModule;
import com.google.code.vaadin.internal.logging.LoggerModule;
import com.google.code.vaadin.internal.mapping.PresenterMapperModule;
import com.google.code.vaadin.internal.servlet.MVPApplicationInitParameters;
import com.google.code.vaadin.mvp.MVPApplicationException;
import com.google.common.base.Preconditions;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Base module for all guice-vaadin-mvp based applications.
 * Application module class should be specified in web.xml with {@link MVPApplicationInitParameters#P_APPLICATION_MODULE}
 * context parameter.
 *
 * @author Alexey Krylov
 * @see MVPApplicationContextListener
 * @since 24.01.13
 */
@SuppressWarnings("AbstractClassExtendsConcreteClass")
public abstract class AbstractMVPApplicationModule extends ServletModule {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Logger logger;
    protected ServletContext servletContext;
    protected Class uiClass;

    /*===========================================[ CONSTRUCTORS ]=================*/

    protected AbstractMVPApplicationModule(ServletContext servletContext) {
        Preconditions.checkNotNull(servletContext);
        logger = LoggerFactory.getLogger(getClass());
        this.servletContext = servletContext;
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configureServlets() {
        install(createLoggerModule());
        install(createEventBusModule());
        install(createResourceBundleInjectionModule());
        install(createUIScopeModule());
        install(createPresenterMapperModule());
        // support for @Preconfigured last because it depends on TextBundle bindings in ApplicationModule
        install(createComponentPreconfigurationModule());

        try {
            uiClass = Class.forName(servletContext.getInitParameter(MVPApplicationInitParameters.P_APPLICATION_UI_CLASS));
        } catch (Exception e) {
            throw new MVPApplicationException(String.format("ERROR: Unable to instantiate class of [%s]. " +
                    "Please check your webapp deployment descriptor.", UI.class.getName()), e);
        }

        serve("/*").with(GuiceApplicationServlet.class, extractInitParams(servletContext));
        bind(Class.class).annotatedWith(Names.named(MVPApplicationInitParameters.P_APPLICATION_UI_CLASS)).toInstance(uiClass);
        bindUIProvider();

        installModules();
        bindTextBundle();
        bindComponents();
    }

    protected LoggerModule createLoggerModule() {
        return new LoggerModule();
    }

    protected EventBusModule createEventBusModule() {
        return new EventBusModule();
    }

    protected ResourceBundleInjectionModule createResourceBundleInjectionModule() {
        return new ResourceBundleInjectionModule();
    }

    protected UIScopeModule createUIScopeModule() {
        return new UIScopeModule();
    }

    protected PresenterMapperModule createPresenterMapperModule() {
        return new PresenterMapperModule(servletContext);
    }

    protected VaadinComponentPreconfigurationModule createComponentPreconfigurationModule() {
        return new VaadinComponentPreconfigurationModule();
    }

    protected void bindUIProvider() {
        bind(UIProvider.class).to(ScopedUIProvider.class);
    }

    protected abstract void installModules();

    protected abstract void bindTextBundle();

    protected abstract void bindComponents();

    protected Map<String, String> extractInitParams(ServletContext servletContext) {
        Map<String, String> initParams = new HashMap<>();
        Enumeration parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement().toString();
            String value = servletContext.getInitParameter(name);
            initParams.put(name, value);
        }

        return initParams;
    }
}