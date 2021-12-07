/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.jdbc.postgresql;

import com.navercorp.pinpoint.pluginit.jdbc.DriverProperties;
import com.navercorp.pinpoint.pluginit.jdbc.JDBCDriverClass;
import com.navercorp.pinpoint.pluginit.jdbc.JDBCTestConstants;
import com.navercorp.pinpoint.pluginit.utils.AgentPath;
import com.navercorp.pinpoint.pluginit.utils.TestcontainersOption;
import com.navercorp.pinpoint.test.plugin.Dependency;
import com.navercorp.pinpoint.test.plugin.JvmVersion;
import com.navercorp.pinpoint.test.plugin.PinpointAgent;
import com.navercorp.pinpoint.test.plugin.PinpointPluginTestSuite;
import com.navercorp.pinpoint.test.plugin.shared.BeforeSharedClass;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testcontainers.DockerClientFactory;

/**
 * @author HyunGil Jeong
 */
@RunWith(PinpointPluginTestSuite.class)
@PinpointAgent(AgentPath.PATH)
@JvmVersion(8)
@Dependency({"org.postgresql:postgresql:[9.min,9.4.1207)",
        "log4j:log4j:1.2.16", "org.slf4j:slf4j-log4j12:1.7.5",
        JDBCTestConstants.VERSION, TestcontainersOption.TEST_CONTAINER, TestcontainersOption.POSTGRESQL})
public class PostgreSql_9_x_to_9_4_1207_IT extends PostgreSqlBase {

    private static final Logger logger = LogManager.getLogger(PostgreSql_9_x_to_9_4_1207_IT.class);
    
    private static PostgreSqlItHelper HELPER;
    private static PostgreSqlJDBCDriverClass driverClass;
    private static PostgreSqlJDBCApi jdbcApi;

    @BeforeSharedClass
    public static void sharedSetup() {
        Assume.assumeTrue("Docker not enabled", DockerClientFactory.instance().isDockerAvailable());
        container = PostgreSQLContainerFactory.newContainer(logger.getName());

        container.start();
        setJdbcUrl(container.getJdbcUrl());
        setUserName(container.getUsername());
        setPassWord(container.getPassword());
    }

    @BeforeClass
    public static void beforeClass() {
        DriverProperties driverProperties = getDriverProperties();
        driverClass = new PostgreSql_9_x_to_9_4_1207_JDBCDriverClass();
        jdbcApi = new PostgreSqlJDBCApi(driverClass);

        driverClass.getDriver();

        HELPER = new PostgreSqlItHelper(driverProperties);
    }

    @Override
    protected JDBCDriverClass getJDBCDriverClass() {
        return driverClass;
    }

    @Test
    public void testStatements() throws Exception {
        HELPER.testStatements(jdbcApi);
    }
}
