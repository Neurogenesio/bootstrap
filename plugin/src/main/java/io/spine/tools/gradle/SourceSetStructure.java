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

package io.spine.tools.gradle;

import io.spine.tools.gradle.GeneratedSourceRoot.GeneratedSourceSet;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A {@link DirectoryStructure} implementation based on source sets.
 *
 * <p>{@code SourceSetStructure} does not try to resolve any files or find the current project
 * source sets unless {@link #markCodeGenRoot} is called.
 */
public final class SourceSetStructure implements DirectoryStructure {

    private final Project project;

    private SourceSetStructure(Project project) {
        this.project = project;
    }

    /**
     * Creates a new instance of {@code SourceSetStructure} for the given project.
     */
    public static SourceSetStructure of(Project project) {
        checkNotNull(project);
        return new SourceSetStructure(project);
    }

    @Override
    public void markCodeGenRoot(GeneratedSourceRoot rootDirectory) {
        checkNotNull(rootDirectory);

        SourceSetContainer sourceSets = sourceSets();
        sourceSets.forEach(sourceSet -> {
            GeneratedSourceSet scopeDir = rootDirectory.sourceSet(sourceSet.getName());
            sourceSet.getJava()
                     .srcDirs(scopeDir.java(), scopeDir.spine(), scopeDir.grpc());
        });
    }

    private SourceSetContainer sourceSets() {
        JavaPluginConvention javaConvention = project.getConvention()
                                                     .getPlugin(JavaPluginConvention.class);
        SourceSetContainer sourceSets = javaConvention.getSourceSets();
        return sourceSets;
    }
}
