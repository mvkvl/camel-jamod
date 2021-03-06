/*
 * Copyright 2012 Steven Swor.
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
package cameljamod;

import java.util.Arrays;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.procimg.Register;
import org.apache.camel.Processor;

/**
 * A Camel consumer which polls a Modbus device for its input registers.
 *
 * @author Steven Swor
 */
public class RegisterPollingConsumer extends ModbusPollingConsumer<ReadMultipleRegistersRequest, ReadMultipleRegistersResponse, Register> {

    /**
     * Creates a new DiscreteInputsPollingConsumer.
     *
     * @param endpoint the endpoint
     * @param processor the processor
     */
    public RegisterPollingConsumer(final JamodEndpoint endpoint, final Processor processor) {
        super(endpoint, processor);
    }

    @Override
    protected ReadMultipleRegistersRequest createRequest() {
        return new ReadMultipleRegistersRequest(getReferenceAddress(), 1);
    }

    @Override
    protected Register getBodyFromResponse(ReadMultipleRegistersResponse response) {
        return response.getRegisters()[0];
    }

    @Override
    protected boolean valueHasChanged(final Register oldValue, final Register newValue) {
        if (oldValue == null) {
            return newValue != null;
        }
        if (newValue == null) {
            return true;
        }
        return !Arrays.equals(oldValue.toBytes(), newValue.toBytes());
    }
}
