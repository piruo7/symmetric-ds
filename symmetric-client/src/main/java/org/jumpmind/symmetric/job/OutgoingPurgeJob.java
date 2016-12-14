/**
 * Licensed to JumpMind Inc under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU General Public License, version 3.0 (GPLv3)
 * (the "License"); you may not use this file except in compliance
 * with the License.
 *
 * You should have received a copy of the GNU General Public License,
 * version 3.0 (GPLv3) along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jumpmind.symmetric.job;

import static org.jumpmind.symmetric.job.JobDefaults.EVERY_NIGHT_AT_MIDNIGHT;

import org.jumpmind.symmetric.ISymmetricEngine;
import org.jumpmind.symmetric.model.JobDefinition.ScheduleType;
import org.jumpmind.symmetric.model.JobDefinition.StartupType;
import org.jumpmind.symmetric.service.ClusterConstants;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/*
 * Background job that is responsible for purging already synchronized data
 */
public class OutgoingPurgeJob extends AbstractJob {

    public OutgoingPurgeJob(ISymmetricEngine engine, ThreadPoolTaskScheduler taskScheduler) {
        super(ClusterConstants.PURGE_OUTGOING, engine, taskScheduler);
    }
    
    @Override
    public JobDefaults getDefaults() {
        return new JobDefaults()
                .scheduleType(ScheduleType.CRON)
                .schedule(EVERY_NIGHT_AT_MIDNIGHT)
                .startupType(StartupType.AUTOMATIC)
                .description("Purge sync'd outgoing data");
    }    
        
    @Override
    public void doJob(boolean force) throws Exception {
        engine.getPurgeService().purgeOutgoing(force);        
    }
    
}