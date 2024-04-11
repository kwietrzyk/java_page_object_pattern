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

import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.aeonbits.owner.ConfigFactory;
import pl.sii.framework.configuration.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

public class FactoryHelper {

    public static final Configuration CONFIGURATION = ConfigFactory.create(Configuration.class);
    public static final DriverManagerType driverType = getDriver();

    private static DriverManagerType getDriver() {
        try {
            return DriverManagerType.valueOf(CONFIGURATION.browserName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Wrong browserName, supported browsers:\n" +
                    Arrays.toString(
                            Stream.of(DriverManagerType.values())
                                    .map(DriverManagerType::name)
                                    .toArray(String[]::new)));
        }
    }
}
