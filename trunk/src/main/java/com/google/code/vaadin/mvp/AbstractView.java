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

import com.google.code.vaadin.mvp.events.ViewOpenedEvent;

import javax.inject.Inject;

/**
 * Abstract implementation of MVP-pattern View.
 *
 * @author Alexey Krylov
 * @since 23.01.13
 */
//@SessionScoped
public abstract class AbstractView extends ViewComponent implements View {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -5555438854419301023L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Class<? extends View> viewInterface;
    private boolean initialized;

    /*===========================================[ INTERFACE METHODS ]============*/

    @Inject
    protected void init() {
        if (viewInterface == null) {
            // Determine the view interface
            for (Class<?> clazz : getClass().getInterfaces()) {
                if (!clazz.equals(View.class)
                        && View.class.isAssignableFrom(clazz)) {
                    viewInterface = (Class<? extends View>) clazz;
                }
            }
        }
    }

    @Override
    public void openView() {
        if (!initialized) {
            initView();
            initialized = true;
            logger.debug(String.format("View initialized: [%s#%d]", viewInterface.getName(), hashCode()));
        }

        fireViewEvent(new ViewOpenedEvent(viewInterface, this));
        logger.debug(String.format("View opened: [%s#%d]", viewInterface.getName(), hashCode()));
    }

    /**
     * Initialize the view
     */
    protected abstract void initView();

    public Class<? extends View> getViewInterface() {
        return viewInterface;
    }
}