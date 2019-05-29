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

import io.spine.logging.Logging;
import io.spine.tools.gradle.TaskName;
import io.spine.tools.gradle.compiler.Extension;
import io.spine.tools.gradle.config.Ext;
import io.spine.tools.gradle.project.Dependant;
import io.spine.tools.gradle.protoc.ProtobufGenerator;
import io.spine.tools.gradle.protoc.ProtocPlugin;
import io.spine.tools.gradle.protoc.ProtocPlugin.Name;
import org.gradle.api.Project;
import org.gradle.api.Task;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.spine.tools.gradle.TaskName.generateRejections;
import static io.spine.tools.gradle.TaskName.generateTestRejections;
import static io.spine.tools.gradle.TaskName.generateTestValidatingBuilders;
import static io.spine.tools.gradle.TaskName.generateValidatingBuilders;
import static io.spine.tools.gradle.protoc.ProtocPlugin.called;

/**
 * A Gradle extension nested in {@link JavaExtension} which configures Java code generation.
 */
public final class JavaCodegenExtension implements Logging {

    private static final ProtocPlugin JAVA_PLUGIN = called(Name.java);
    private static final ProtocPlugin GRPC_PLUGIN = called(Name.grpc);
    private static final ProtocPlugin SPINE_PLUGIN = called(Name.spineProtoc);

    private final Project project;
    private final ProtobufGenerator protobufGenerator;
    private final Dependant dependant;

    private boolean protobuf = true;
    private boolean grpc = false;
    private boolean spine = true;

    private JavaCodegenExtension(Project project,
                                 ProtobufGenerator protobufGenerator,
                                 Dependant dependant) {
        this.project = project;
        this.protobufGenerator = protobufGenerator;
        this.dependant = dependant;
    }

    /**
     * Creates a new instance of the extension.
     */
    public static JavaCodegenExtension of(Project project, Dependant dependant) {
        checkNotNull(project);
        checkNotNull(dependant);
        ProtobufGenerator generator = new ProtobufGenerator(project);
        return new JavaCodegenExtension(project, generator, dependant);
    }

    public boolean getProtobuf() {
        return protobuf;
    }

    public boolean getGrpc() {
        return grpc;
    }

    public boolean getSpine() {
        return spine;
    }

    /**
     * Enables or disables Protobuf to Java code generation.
     *
     * <p>Enabled by default.
     *
     * @param protobuf {@code true} to enable, {@code false} to disable
     */
    public void setProtobuf(boolean protobuf) {
        this.protobuf = protobuf;
        if (protobuf) {
            protobufGenerator.enableBuiltIn(JAVA_PLUGIN);
        } else {
            protobufGenerator.disableBuiltIn(JAVA_PLUGIN);
        }
    }

    /**
     * Enables or disables gRPC stub generation.
     *
     * <p>Disabled by default.
     *
     * @param grpc {@code true} to enable, {@code false} to disable
     */
    public void setGrpc(boolean grpc) {
        this.grpc = grpc;
        switchPlugin(GRPC_PLUGIN, grpc);
        if (grpc) {
            addGrpcDependencies();
        }
    }

    private void addGrpcDependencies() {
        Ext.of(project)
           .artifacts()
           .grpc()
           .forEach(dependant::implementation);
    }

    /**
     * Enables or disables Spine-specific Java code generation.
     *
     * <p>Enabled by default.
     *
     * <p>If enabled, validating builders and rejections will be generated and the generated Java
     * code will be tweaked by the Spine Protobuf compiler plugin.
     *
     * @param spine {@code true} to enable, {@code false} to disable
     */
    public void setSpine(boolean spine) {
        this.spine = spine;
        switchPlugin(SPINE_PLUGIN, spine);
        Extension modelCompilerExtension = project.getExtensions()
                                                  .getByType(Extension.class);
        modelCompilerExtension.generateValidatingBuilders = spine;
        updateModelCompilerTask(generateRejections);
        updateModelCompilerTask(generateTestRejections);
        updateModelCompilerTask(generateValidatingBuilders);
        updateModelCompilerTask(generateTestValidatingBuilders);
    }

    private void updateModelCompilerTask(TaskName taskName) {
        Task task = project.getTasks()
                           .findByName(taskName.value());
        if (task != null) {
            task.setEnabled(spine);
        } else {
            _debug("Task `{}` not found in project `{}`.", taskName, project.getPath());
        }
    }

    private void switchPlugin(ProtocPlugin plugin, boolean enabled) {
        if (enabled) {
            protobufGenerator.enablePlugin(plugin);
        } else {
            protobufGenerator.disablePlugin(plugin);
        }
    }
}