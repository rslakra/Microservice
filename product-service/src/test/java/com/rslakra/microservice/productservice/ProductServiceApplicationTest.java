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

<<<<<<<< HEAD:config-service/src/test/java/com/rslakra/microservice/configservice/ConfigServiceApplicationTest.java
package com.rslakra.microservice.configservice;
========
package com.rslakra.microservice.productservice;
>>>>>>>> develop:product-service/src/test/java/com/rslakra/microservice/productservice/ProductServiceApplicationTest.java

import static org.assertj.core.api.Assertions.assertThat;

import com.rslakra.microservice.productservice.controller.rest.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceApplicationTest {

    @Autowired
    @Qualifier("productRestController")
    private ProductController controller;

    @Test
    public void contextLoads() {
        assertThat(controller.getAllProducts()).isNotNull();
    }

}
