/*
 * Copyright 2019, TeamDev. All rights reserved.
 *
 * Redistribution and use in source and/or binary forms, with or without
 * modification, must retain the above copyright notice and the following
 * disclaimer.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.spine.tools.gradle.protoc.given;

import com.google.protobuf.gradle.GenerateProtoTask.PluginOptions;
import org.gradle.api.internal.AbstractNamedDomainObjectContainer;
import org.gradle.api.internal.CollectionCallbackActionDecorator;
import org.gradle.api.reflect.ObjectInstantiationException;
import org.gradle.internal.reflect.Instantiator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.gradle.api.internal.CollectionCallbackActionDecorator.NOOP;

public final class TestPluginOptionsContainer extends AbstractNamedDomainObjectContainer<PluginOptions> {

    public TestPluginOptionsContainer() {
        super(PluginOptions.class, ContainerInstantiator.INSTANCE, NOOP);
    }

    @Override
    protected PluginOptions doCreate(String name) {
        checkNotNull(name);
        return new PluginOptions(name);
    }

    private enum ContainerInstantiator implements Instantiator {

        INSTANCE;

        @Override
        public <T> T newInstance(Class<? extends T> type, Object... parameters)
                throws ObjectInstantiationException {
            checkArgument(type == CollectionCallbackActionDecorator.class);
            @SuppressWarnings("unchecked") // Checked with a precondition.
            T result = (T) new TestPluginOptionsContainer();
            return result;
        }
    }
}