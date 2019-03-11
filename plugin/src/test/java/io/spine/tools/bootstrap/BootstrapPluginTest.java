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

package io.spine.tools.bootstrap;

import com.google.common.truth.DefaultSubject;
import com.google.common.truth.Subject;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;
import org.junitpioneer.jupiter.TempDirectory.TempDir;

import java.nio.file.Path;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(TempDirectory.class)
@DisplayName("BootstrapPlugin should")
class BootstrapPluginTest {

    private Project project;

    @BeforeEach
    void setUp(@TempDir Path projectDir) {
        project = ProjectBuilder
                .builder()
                .withName(BootstrapPluginTest.class.getSimpleName())
                .withProjectDir(projectDir.toFile())
                .build();
    }

    @Test
    @DisplayName("register `spine` extension")
    void registerExtension() {
        BootstrapPlugin plugin = new BootstrapPlugin();
        plugin.apply(project);

        Object extension = project.getExtensions()
                                  .getByName(Extension.NAME);
        Subject<DefaultSubject, Object> assertExtension = assertThat(extension);
        assertExtension.isNotNull();
        assertExtension.isInstanceOf(Extension.class);
        Extension registeredExtension = (Extension) extension;
        assertThat(registeredExtension.project()).isSameAs(project);
    }
}
