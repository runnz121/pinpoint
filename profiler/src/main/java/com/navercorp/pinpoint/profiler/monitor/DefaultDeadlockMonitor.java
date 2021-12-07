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

package com.navercorp.pinpoint.profiler.monitor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Taejin Koo
 */
public class DefaultDeadlockMonitor implements DeadlockMonitor {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final DeadlockMonitorThread deadlockMonitorThread;

    public DefaultDeadlockMonitor(DeadlockThreadRegistry deadlockThreadRegistry, long intervalMillis) {
        this.deadlockMonitorThread = new DeadlockMonitorThread(deadlockThreadRegistry, intervalMillis);
    }

    @Override
    public void start() {
        logger.info("DefaultDeadlockMonitor started");
        deadlockMonitorThread.start();
    }

    @Override
    public void stop() {
        logger.info("DefaultDeadlockMonitor stopped");
        deadlockMonitorThread.stop();
    }

}
