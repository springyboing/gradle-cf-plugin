/*
 * Copyright 2012 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.cf

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore

class CloudFoundryPluginTest {
    Project project

    @Before
    public void setUp() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'cloudfoundry'
    }

    @Test
    @Ignore
    public void testInfo() {
        project.tasks['cf-info'].with {
          target = 'http://api.cloudfoundry.com'
          execute()
        }
    }
}
