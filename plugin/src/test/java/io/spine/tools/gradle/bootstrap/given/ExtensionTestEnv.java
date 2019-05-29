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

package io.spine.tools.gradle.bootstrap.given;

import com.google.common.collect.ImmutableMap;
import io.spine.tools.gradle.compiler.Extension;
import io.spine.tools.gradle.compiler.ModelCompilerPlugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;

import java.util.Map;

public final class ExtensionTestEnv {

    public static final String GRPC_DEPENDENCY = "io.foo.bar.grpc:fake-dependency:6.14";

    public static final String spineVersion = "42.3.14-AVOCADO";
    private static final Map<String, ?> deps = ImmutableMap.of(
            "versions", ImmutableMap.of(
                    "grpc", "1.18",
                    "protobuf", "3.6.1"
            ),
            "build", ImmutableMap.of(
                    "protoc", "com.google.protobuf:protoc:3.6.1"
            ),
            "grpc", ImmutableMap.of("grpcTest", GRPC_DEPENDENCY)
    );

    /**
     * Prevents the utility class instantiation.
     */
    private ExtensionTestEnv() {
    }

    public static void addExt(Project project) {
        ExtraPropertiesExtension ext = project.getExtensions()
                                              .getExtraProperties();
        ext.set("spineVersion", spineVersion);
        ext.set("deps", deps);

        project.getExtensions()
               .add(ModelCompilerPlugin.extensionName(), new Extension());
    }
}