/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.cdi.test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.cdi.CdiCamelExtension;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class Camel15493Test {

    @Inject
    private TestBean testBean;

    @Deployment
    public static WebArchive deployment() {
        JavaArchive testJar = ShrinkWrap.create(JavaArchive.class, "test.jar")
            // Camel CDI
            .addPackage(CdiCamelExtension.class.getPackage())
            // Test class
            .addClasses(Camel15493Test.class, TestBean.class)
            // Property file for Config injection
            .addAsManifestResource(
                    new StringAsset("test.strings=Test1,Test2"),
                    "microprofile-config.properties")
            // Bean archive deployment descriptor
            .addAsManifestResource("META-INF/beans.xml");

        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addAsLibrary(testJar);
    }

    @Test
    public void verifyDeployment() {
        assertNotNull("Camel context beans are deployed!", testBean.getContext());
        assertEquals("Array length is 2", 2, testBean.getStrings().length);
        assertEquals("Test1", testBean.getStrings()[0]);
        assertEquals("Test2", testBean.getStrings()[1]);
    }

    @ApplicationScoped
    public static class TestBean {

        @Inject
        private CamelContext context;

        @Inject
        @ConfigProperty(name = "test.strings")
        private String[] strings;

        public CamelContext getContext() {
            return context;
        }

        public String[] getStrings() {
            return strings;
        }

    }

}