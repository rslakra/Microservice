/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rslakra.microservice.configclient;

import static org.assertj.core.api.Assertions.assertThat;

import com.rslakra.microservice.configclient.controller.ConfigClientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.ConfigurableEnvironment;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigClientApplicationTest {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ConfigClientController controller;

    @Autowired
    private ContextRefresher refresher;

    @Test
    public void contextLoads() {
        assertThat(controller.getMessage()).isNotEqualTo("Hello test");
        TestPropertyValues
            .of("message:Hello test")
            .applyTo(environment);
        assertThat(controller.getMessage()).isNotEqualTo("Hello test");
        refresher.refresh();
        assertThat(controller.getMessage()).isEqualTo("Hello test");
    }

}
