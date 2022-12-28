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

package com.rslakra.microservices.configservice;

import com.rslakra.microservices.configservice.utils.PathUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.config.server.environment.EnvironmentController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConfigServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = { "spring.profiles.active=native" })
public class ConfigServiceApplicationTest {

    @Autowired
    private EnvironmentController envController;

    @Value("${local.server.port}")
    private int port = 0;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void contextLoads() {
        assertThat(envController).isNotNull();
    }


    @Test
    public void configurationAvailable() {
        String serverUrl = PathUtils.pathString(port, "app/cloud");
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = restTemplate.getForEntity(serverUrl, Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void envPostAvailable() {
        String serverUrl = PathUtils.pathString(port, "admin/env");
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = restTemplate.getForEntity(serverUrl, Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
