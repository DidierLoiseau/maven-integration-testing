/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.it;

import java.io.File;
import java.util.List;

import org.apache.maven.shared.verifier.Verifier;
import org.apache.maven.shared.verifier.util.ResourceExtractor;
import org.junit.jupiter.api.Test;

class MavenITmng7891ConfigurationForExtensionsTest extends AbstractMavenIntegrationTestCase {

    protected MavenITmng7891ConfigurationForExtensionsTest() {
        super("(4.0.0-alpha-7,)");
    }

    @Test
    void testConfigurationForCoreExtension() throws Exception {
        File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/mng-7891-extension-configuration");

        Verifier verifier = newVerifier(new File(testDir, "extension").getAbsolutePath());
        verifier.addCliArgument("install");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        verifier = newVerifier(new File(testDir, "core-extension").getAbsolutePath());
        verifier.addCliArgument("install");
        verifier.addCliArgument("-DuserValue=the-value");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        List<String> logFile = verifier.loadFile(verifier.getBasedir(), verifier.getLogFileName(), false);
        String projects = logFile.stream()
                .filter(s -> s.contains("All projects are read now"))
                .findFirst()
                .orElse(null);
        assertNotNull(projects);
        assertFalse(projects.contains("$"));
    }

    @Test
    void testConfigurationForBuildExtension() throws Exception {
        File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/mng-7891-extension-configuration");

        Verifier verifier = newVerifier(new File(testDir, "extension").getAbsolutePath());
        verifier.addCliArgument("install");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        verifier = newVerifier(new File(testDir, "build-extension").getAbsolutePath());
        verifier.addCliArgument("install");
        verifier.addCliArgument("-DuserValue=the-value");
        verifier.execute();
        verifier.verifyErrorFreeLog();

        List<String> logFile = verifier.loadFile(verifier.getBasedir(), verifier.getLogFileName(), false);
        String projects = logFile.stream()
                .filter(s -> s.contains("All projects are read now"))
                .findFirst()
                .orElse(null);
        assertNotNull(projects);
        assertFalse(projects.contains("$"));
    }
}
