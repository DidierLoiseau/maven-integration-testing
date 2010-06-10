package org.apache.maven.it;

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

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.Properties;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-4450">MNG-4450</a>.
 * 
 * @author Benjamin Bentmann
 */
public class MavenITmng4450StubModelForMissingDependencyPomTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng4450StubModelForMissingDependencyPomTest()
    {
        super( "[2.0.3,3.0-alpha-1),[3.0-alpha-5,)" );
    }

    /**
     * Verify that building missing POMs for dependencies fails gracefully with a stub model.
     */
    public void testit()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-4450" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        Properties props = verifier.loadProperties( "target/pom.properties" );

        // NOTE: Some Maven versions generate faulty packaging for the stub model (always "pom"), not our business here
        assertEquals( "org.apache.maven.its.mng4450:missing:jar:0.1", 
            props.getProperty( "org.apache.maven.its.mng4450:missing:jar:0.1.project.id" ).replaceAll( "pom", "jar" ) );
        assertEquals( "org.apache.maven.its.mng4450:missing:jar:0.1", 
            props.getProperty( "org.apache.maven.its.mng4450:missing:jar:0.1.artifact.id" ).replaceAll( "pom", "jar" ) );
    }

}
