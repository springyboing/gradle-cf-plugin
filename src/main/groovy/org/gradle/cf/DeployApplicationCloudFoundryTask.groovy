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

import org.cloudfoundry.client.lib.CloudApplication
import org.cloudfoundry.client.lib.CloudFoundryException
import org.cloudfoundry.client.lib.Staging
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.springframework.http.HttpStatus

/**
 * Tasks used to determine if push or update is of an application on the CloudFoundry is required.
 *
 * @author Tim Redding
 */
class DeployApplicationCloudFoundryTask extends AbstractCreateApplicationCloudFoundryTask {

    DeployApplicationCloudFoundryTask() {
        super()
        description = 'Deploys an application to the cloud'
    }

    @TaskAction
    void deploy() {
        connectToCloudFoundry()

        if (!client) {
            return
        }

        boolean found = true
        try {
            client.getApplication(getApplication())
        } catch (CloudFoundryException e) {
            if (HttpStatus.NOT_FOUND == e.statusCode) {
                found = false;
            } else {
                throw new GradleException("Unable to retrieve application info from CloudFoundry", e)
            }
        }
        if (found) {
            log "Application ${getApplication()} found"
            getProject().getTasksByName('cf-update', false).each { it.execute() }
        } else {
            log "Application ${getApplication()} not found"
            getProject().getTasksByName('cf-push', false).each { it.execute() }
        }
    }
}
