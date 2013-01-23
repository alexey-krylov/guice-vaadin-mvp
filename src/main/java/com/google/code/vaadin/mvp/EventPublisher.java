/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.vaadin.mvp;

import javax.annotation.Nonnull;

/**
 * EventPublisher - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 23.01.13
 */
public interface EventPublisher {

    /*===========================================[ INTERFACE METHODS ]==============*/

    void publish(@Nonnull Object event);
}
