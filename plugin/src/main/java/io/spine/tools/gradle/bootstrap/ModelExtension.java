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

package io.spine.tools.gradle.bootstrap;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import io.spine.tools.gradle.GeneratedSourceRoot;
import io.spine.tools.gradle.project.SourceSuperset;
import io.spine.tools.gradle.protoc.ProtocPlugin;
import org.gradle.api.Project;

import static io.spine.tools.gradle.protoc.ProtocPlugin.Name.java;

/**
 * An extension which declares a module as one that contains the Protobuf model definition.
 */
public final class ModelExtension extends CodeGenExtension {

    private final Project project;
    private final SourceSuperset sourceSuperset;

    private ModelExtension(Builder builder) {
        super(builder);
        this.project = builder.project();
        this.sourceSuperset = builder.sourceSuperset;
    }

    /** Obtains a new builder of {@code ModelExtension}s. */
    static Builder newBuilder() {
        return new Builder();
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    void enableGeneration() {
        super.enableGeneration();
        addSourceSets();
    }

    /**
     * Adds a Gradle source set that contains the Protobuf files that define the model.
     */
    private void addSourceSets() {
        sourceSuperset.register(GeneratedSourceRoot.of(project));
    }

    /**
     * Builder of {@code ModelExtension}s.
     */
    static final class Builder extends CodeGenExtension.Builder<ModelExtension, Builder> {

        private SourceSuperset sourceSuperset;

        private Builder() {
            super(ProtocPlugin.called(java));
        }

        Builder setSourceSuperset(SourceSuperset sourceSuperset) {
            this.sourceSuperset = sourceSuperset;
            return this;
        }

        @Override
        Builder self() {
            return this;
        }

        @Override
        ModelExtension doBuild() {
            return new ModelExtension(this);
        }
    }
}