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

import io.spine.io.Resource;
import io.spine.tools.gradle.PluginScript;

public class SpinePluginScripts {

    /**
     * Obtains the {@code dependencies.gradle} script.
     *
     * <p>The script adds several dependency-related properties to the project.
     */
    public static PluginScript dependencies() {
        return PluginScript.declaredIn(Name.DEPENDENCIES.resourceFile());
    }

    /**
     * Obtains the {@code version.gradle} script.
     *
     * <p>The script adds the {@code spineVersion} property to the project.
     */
    public static PluginScript version() {
        return PluginScript.declaredIn(Name.VERSION.resourceFile());
    }

    /**
     * Obtains the {@code model-compiler.gradle} script.
     *
     * <p>The script configures the {@link io.spine.tools.gradle.compiler.ModelCompilerPlugin} to
     * the recommended settings.
     */
    public static PluginScript modelCompilerConfig() {
        return PluginScript.declaredIn(Name.MODEL_COMPILER.resourceFile());
    }

    /**
     * The names of the existing plugin scripts.
     */
    enum Name {

        DEPENDENCIES("dependencies"),
        VERSION("version"),
        MODEL_COMPILER("model-compiler");

        private static final String SCRIPT_EXTENSION = ".gradle";

        private final String name;

        Name(String name) {
            this.name = name;
        }

        private Resource resourceFile() {
            String resourceName = name + SCRIPT_EXTENSION;
            return Resource.file(resourceName);
        }
    }
}