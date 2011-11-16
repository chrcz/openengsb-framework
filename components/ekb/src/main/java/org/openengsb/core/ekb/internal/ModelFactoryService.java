/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.core.ekb.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.openengsb.core.api.ekb.ModelFactory;
import org.openengsb.core.api.model.OpenEngSBModel;
import org.openengsb.core.api.model.OpenEngSBModelEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * gives an implementation for the ModelFactory interface of the EKB. Is able to proxy an OpenEngSBModel interface
 */
public class ModelFactoryService implements ModelFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelFactoryService.class);

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OpenEngSBModel> T createEmptyModelObject(Class<T> model, OpenEngSBModelEntry... entries) {
        LOGGER.debug("createEmpytModelObject for model interface {} called", model.getName());
        return (T) createModelObject(model, entries);
    }

    @Override
    public Object createModelObject(Class<?> model, OpenEngSBModelEntry... entries) {
        if (!OpenEngSBModel.class.isAssignableFrom(model)) {
            throw new IllegalArgumentException("OpenEngSBModel has to be deriveable from model parameter");
        }
        ClassLoader classLoader = model.getClassLoader();
        Class<?>[] classes = new Class<?>[]{ OpenEngSBModel.class, model };
        InvocationHandler handler = makeHandler(model, entries);

        return Proxy.newProxyInstance(classLoader, classes, handler);
    }

    private EKBProxyHandler makeHandler(Class<?> model, OpenEngSBModelEntry[] entries) {
        EKBProxyHandler handler = new EKBProxyHandler(model, entries);
        return handler;
    }
}
