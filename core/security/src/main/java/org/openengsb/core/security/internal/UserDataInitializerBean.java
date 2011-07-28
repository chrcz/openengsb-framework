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

package org.openengsb.core.security.internal;

import java.util.ArrayList;
import java.util.List;

import org.openengsb.core.api.security.UserManager;
import org.openengsb.core.api.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class UserDataInitializerBean {

    private UserManager userManager;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void init() {
        if (userManager.getAllUser().isEmpty()) {
            List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
            auth.add(new GrantedAuthorityImpl("ROLE_USER"));
            auth.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
            userManager.createUser(new User("admin", "password", auth));

            List<GrantedAuthority> userAuth = new ArrayList<GrantedAuthority>();
            userAuth.add(new GrantedAuthorityImpl("ROLE_USER"));
            userManager.createUser(new User("user", "password", userAuth));
        }
    }
}
