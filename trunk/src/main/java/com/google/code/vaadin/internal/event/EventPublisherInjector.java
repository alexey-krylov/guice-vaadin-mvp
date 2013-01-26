/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.vaadin.internal.event;

import com.google.code.vaadin.internal.mapping.MVPApplicationContext;
import com.google.code.vaadin.internal.util.InjectorProvider;
import com.google.code.vaadin.mvp.EventBus;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Scopes;
import net.engio.mbassy.common.ReflectionUtils;

import javax.servlet.ServletContext;

/**
 * EventPublisherInjector - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 26.01.13
 */
class EventPublisherInjector<T> implements MembersInjector<T> {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private final ServletContext servletContext;

	/*===========================================[ CONSTRUCTORS ]=================*/

    EventPublisherInjector(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public void injectMembers(T instance) {
        Injector injector = InjectorProvider.getInjector(servletContext);
        Class<?> instanceClass = instance.getClass();

        // Do not subscribe classes without @Observes/@Listener methods
        if (injector != null && !ReflectionUtils.getMethods(CompositeMetadataReader.AllMessageHandlers, instanceClass).isEmpty()) {
            // Do not allowed to register Singleton as Session-scoped EventBus subscriber. This case only possible for not eager singletons that is injected by Session-scoped components
            if (!Scopes.isSingleton(injector.getBinding(instanceClass))) {
                EventBus viewEventBus = injector.getInstance(Key.get(EventBus.class, EventBusModule.ViewEventBus.class));
                injector.getInstance(MVPApplicationContext.class).registerSessionScopedSubscriber(instance);
                viewEventBus.subscribe(instance);
            }
            EventBus modelEventBus = injector.getInstance(Key.get(EventBus.class, EventBusModule.ModelEventBus.class));
            modelEventBus.subscribe(instance);
        }
    }
}