/*
 * Copyright (c) 2024.  Sii Poland
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sii.framework.base.factory.driver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DriverFactoryProvider {
    public IDriverFactory getDriverFactory() {
        String driverType = FactoryHelper.CONFIGURATION.driverType();
        return switch (driverType) {
            case "LOCAL" ->  new LocalDriverFactory();
            case "REMOTE" -> new RemoteDriverFactory();
            default -> throw new IllegalStateException("Wrong driverType, supported types: [LOCAL, REMOTE]");
        };
    }
}
