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

package com.google.code.vaadin.internal.event;

import com.google.code.vaadin.mvp.Observes;
import net.engio.mbassy.common.IPredicate;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.listener.*;
import net.engio.mbassy.subscription.MessageEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * CompositeMetadataReader - TODO: description
 *
 * @author Alexey Krylov
 * @since 24.01.13
 */
class CompositeMetadataReader extends MetadataReader {

    /*===========================================[ STATIC VARIABLES ]=============*/

    public static final IPredicate<Method> AllMessageHandlers = new IPredicate<Method>() {
        @Override
        public boolean apply(Method target) {
            return target.getAnnotation(Listener.class) != null || target.getAnnotation(Observes.class) != null;
        }
    };

    private static final Logger logger = LoggerFactory.getLogger(CompositeMetadataReader.class.getName());

    /*===========================================[ CLASS METHODS ]================*/

    @Override
    public List<MessageHandlerMetadata> getMessageHandlers(Class<?> target) {
        // get all handlers (this will include overridden handlers)
        List<Method> allMethods = ReflectionUtils.getMethods(AllMessageHandlers, target);
        List<MessageHandlerMetadata> handlers = new LinkedList<MessageHandlerMetadata>();
        for (Method handler : allMethods) {
            Method overriddenHandler = ReflectionUtils.getOverridingMethod(handler, target);
            if (overriddenHandler == null && isValidHandler(handler)) {
                // add the handler only if it has not been overridden because
                // either the override in the subclass deactivates the handler (by not specifying the @Listener)
                // or the handler defined in the subclass is part of the list and will be processed itself
                handlers.add(getHandlerMetadata(handler));
            }
        }
        return handlers;
    }

    private static boolean isValidHandler(Method handler) {
        if (handler.getParameterTypes().length != 1) {
            // a messageHandler only defines one parameter (the message)
            logger.warn("Found no or more than one parameter in messageHandler [" + handler.getName()
                    + "]. A messageHandler must define exactly one parameter");
            return false;
        }
        Enveloped envelope = handler.getAnnotation(Enveloped.class);
        if (envelope != null && !MessageEnvelope.class.isAssignableFrom(handler.getParameterTypes()[0])) {
            logger.warn("Message envelope configured but no subclass of MessageEnvelope found as parameter");
            return false;
        }
        if (envelope != null && envelope.messages().length == 0) {
            logger.warn("Message envelope configured but message types defined for handler");
            return false;
        }
        return true;
    }

    @Override
    public MessageHandlerMetadata getHandlerMetadata(Method messageHandler) {
        Listener listenerAnnotation = messageHandler.getAnnotation(Listener.class);
        if (listenerAnnotation == null) {
            Observes observesAnnotation = messageHandler.getAnnotation(Observes.class);
            if (observesAnnotation != null) {
                listenerAnnotation = new MappedListener();
            }
        }
        return new MessageHandlerMetadata(messageHandler, new IMessageFilter[0], listenerAnnotation);
    }

    /*===========================================[ INNER CLASSES ]================*/

    private static class MappedListener implements Listener {
        @Override
        public Filter[] filters() {
            return new Filter[0];
        }

        @Override
        public Mode dispatch() {
            return Mode.Synchronous;
        }

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public boolean rejectSubtypes() {
            return false;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Listener.class;
        }
    }
}